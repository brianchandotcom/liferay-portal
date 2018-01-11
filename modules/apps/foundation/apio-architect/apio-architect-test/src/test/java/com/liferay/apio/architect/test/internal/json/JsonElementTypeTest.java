/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.apio.architect.test.internal.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import com.liferay.apio.architect.test.json.JsonElementType;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class JsonElementTypeTest {

	@Test
	public void testInvokingGetJsonElementTypeReturnsCorrectType() {
		JsonElement arrayJsonElement = new JsonArray();

		JsonElementType arrayJsonElementType =
			JsonElementType.getJsonElementType(arrayJsonElement);

		JsonElement booleanJsonElement = new JsonPrimitive(true);

		JsonElementType booleanJsonElementType =
			JsonElementType.getJsonElementType(booleanJsonElement);

		JsonElement numberJsonElement = new JsonPrimitive(42);

		JsonElementType numberJsonElementType =
			JsonElementType.getJsonElementType(numberJsonElement);

		JsonElement objectJsonElement = new JsonObject();

		JsonElementType objectJsonElementType =
			JsonElementType.getJsonElementType(objectJsonElement);

		JsonElement otherJsonElement = JsonNull.INSTANCE;

		JsonElementType otherJsonElementType =
			JsonElementType.getJsonElementType(otherJsonElement);

		JsonElement stringJsonElement = new JsonPrimitive(
			"Live long and prosper");

		JsonElementType stringJsonElementType =
			JsonElementType.getJsonElementType(stringJsonElement);

		assertThat(arrayJsonElementType, is(JsonElementType.ARRAY));
		assertThat(booleanJsonElementType, is(JsonElementType.BOOLEAN));
		assertThat(numberJsonElementType, is(JsonElementType.NUMBER));
		assertThat(objectJsonElementType, is(JsonElementType.OBJECT));
		assertThat(otherJsonElementType, is(JsonElementType.OTHER));
		assertThat(stringJsonElementType, is(JsonElementType.STRING));
	}

	@Test
	public void testInvokingGetReadableNameReturnsCorrectName() {
		assertThat(JsonElementType.ARRAY.getReadableName(), is("an array"));
		assertThat(JsonElementType.BOOLEAN.getReadableName(), is("a boolean"));
		assertThat(JsonElementType.NUMBER.getReadableName(), is("a number"));
		assertThat(JsonElementType.OBJECT.getReadableName(), is("an object"));
		assertThat(JsonElementType.OTHER.getReadableName(), is("other"));
		assertThat(JsonElementType.STRING.getReadableName(), is("a string"));
	}

}