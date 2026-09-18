package com.example.listycity3

import android.R.attr.onClick
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Color

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {

    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var showEditCityFields by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<City?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            //Edit button
            //Checks to see if there is a selected city
            if(selectedCity != null){
                FloatingActionButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        showEditCityFields = !showEditCityFields
                        showAddCityFields = false
                    }
                ) {
                    Text("Edit")
                }
            }

            //'+' button
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                    showEditCityFields = false
                }
            ) {
                Text("+")
            }
        }

        //Add city text input
        if (showAddCityFields) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName = ""
                            newProvinceName = ""

                            showAddCityFields = false
                        }
                    }
                ) {
                    Text("Add City")
                }
            }
        }

        //edit city text input
        if(showEditCityFields){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            showAddCityFields = false


                            onUpdateCity(
                                    selectedCity!!,
                                    City(newCityName, newProvinceName)
                            )



                            newCityName = ""
                            newProvinceName = ""

                            showEditCityFields = false
                        }
                    }
                ) {
                    Text("Edit City")
                }
            }
        }


        //List of cities
        LazyColumn(
            modifier = Modifier
        ) {
            /*Looked up how to make items clickable
            * Mastering Item Clicks in Lazy Column: A Jetpack Compose Guide
            * By: Godlin Josheela Rani S
            * Date: Nov 16, 2023
            * https://medium.com/@godlinjosheela/mastering-item-clicks-in-lazy-column-a-jetpack-compose-guide-c5c8affe3cb5
            * Accessed: Sep 17, 2026
            *
            * Also used android developer page:
            * https://developer.android.com/reference/kotlin/androidx/compose/foundation/clickable.modifier
            */

            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    selectedCity == city,

                    //lambda for onCLick to pass city up to selectedCity
                    {selectedCity = city }
                )
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    selected: Boolean,

    /*
    *looked up how lambda functions work in order to pass the selected city listener into the row's onCLick
    *https://kotlinlang.org/docs/lambdas.html#returning-a-value-from-a-lambda-expression
    *
    * Source - https://stackoverflow.com/a/68769826
    * Posted by Sayan Subhra Banerjee, modified by community. See post 'Timeline' for change history
    * Retrieved 2026-09-17, License - CC BY-SA 4.0
    */
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .clickable( onClick = onClick)

            //changes background to show selection
            .background(
                if(selected) Color.LightGray
                else Color.Transparent
            )

            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = {name , province ->}

        )
    }
}