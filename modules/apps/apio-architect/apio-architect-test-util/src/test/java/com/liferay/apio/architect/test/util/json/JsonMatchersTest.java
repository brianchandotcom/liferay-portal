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

package com.liferay.apio.architect.test.util.json;

import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWhere;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWith;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.hamcrest.Matcher;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class JsonMatchersTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			JsonMatchers.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test
	public void testIsJsonObjectValidatesJsonObject() {
		Conditions.Builder builder = new Conditions.Builder();

		Conditions conditions = builder.where(
			"apio", is(aJsonString(equalTo("Live long and prosper")))
		).build();

		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("apio", "Live long and prosper");

		assertThat(jsonObject, is(aJsonObjectWith(conditions)));
	}

	@Test
	public void testIsJsonObjectWhereValidatesJsonObject() {
		JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("apio", "Live long and prosper");

		Matcher<JsonElement> isAJsonObjectWithOneField = is(
			aJsonObjectWhere(
				"apio", is(aJsonString(equalTo("Live long and prosper")))));

		assertThat(jsonObject, isAJsonObjectWithOneField);
	}

}