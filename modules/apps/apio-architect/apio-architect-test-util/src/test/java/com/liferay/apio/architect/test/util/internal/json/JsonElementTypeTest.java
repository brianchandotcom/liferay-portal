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

package com.liferay.apio.architect.test.util.internal.json;

import static com.liferay.apio.architect.test.util.json.JsonElementType.ARRAY;
import static com.liferay.apio.architect.test.util.json.JsonElementType.BOOLEAN;
import static com.liferay.apio.architect.test.util.json.JsonElementType.NUMBER;
import static com.liferay.apio.architect.test.util.json.JsonElementType.OBJECT;
import static com.liferay.apio.architect.test.util.json.JsonElementType.OTHER;
import static com.liferay.apio.architect.test.util.json.JsonElementType.STRING;
import static com.liferay.apio.architect.test.util.json.JsonElementType.getJsonElementType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import com.liferay.apio.architect.test.util.json.JsonElementType;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class JsonElementTypeTest {

	@Test
	public void testInvokingGetJsonElementTypeReturnsCorrectType() {
		JsonElement arrayJsonElement = new JsonArray();

		JsonElementType arrayJsonElementType = getJsonElementType(
			arrayJsonElement);

		JsonElement booleanJsonElement = new JsonPrimitive(true);

		JsonElementType booleanJsonElementType = getJsonElementType(
			booleanJsonElement);

		JsonElement numberJsonElement = new JsonPrimitive(42);

		JsonElementType numberJsonElementType = getJsonElementType(
			numberJsonElement);

		JsonElement objectJsonElement = new JsonObject();

		JsonElementType objectJsonElementType = getJsonElementType(
			objectJsonElement);

		JsonElement otherJsonElement = JsonNull.INSTANCE;

		JsonElementType otherJsonElementType = getJsonElementType(
			otherJsonElement);

		JsonElement stringJsonElement = new JsonPrimitive(
			"Live long and prosper");

		JsonElementType stringJsonElementType = getJsonElementType(
			stringJsonElement);

		assertThat(arrayJsonElementType, is(ARRAY));
		assertThat(booleanJsonElementType, is(BOOLEAN));
		assertThat(numberJsonElementType, is(NUMBER));
		assertThat(objectJsonElementType, is(OBJECT));
		assertThat(otherJsonElementType, is(OTHER));
		assertThat(stringJsonElementType, is(STRING));
	}

	@Test
	public void testInvokingGetReadableNameReturnsCorrectName() {
		assertThat(ARRAY.getReadableName(), is("an array"));
		assertThat(BOOLEAN.getReadableName(), is("a boolean"));
		assertThat(NUMBER.getReadableName(), is("a number"));
		assertThat(OBJECT.getReadableName(), is("an object"));
		assertThat(OTHER.getReadableName(), is("other"));
		assertThat(STRING.getReadableName(), is("a string"));
	}

}