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

import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonBoolean;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class IsJsonBooleanMatcherTest {

	@Test
	public void testInvalidJsonBooleanFails() {
		JsonPrimitive jsonPrimitive = new JsonPrimitive(true);

		assertThat(jsonPrimitive, is(not(aJsonBoolean(false))));
	}

	@Test
	public void testInvalidJsonBooleanUpdatesValidDescription() {
		JsonPrimitive jsonPrimitive = new JsonPrimitive(true);

		Matcher<JsonElement> matcher = aJsonBoolean(false);

		Description description = new StringDescription();

		matcher.describeMismatch(jsonPrimitive, description);

		String expected = "was a boolean element with a value that was <true>";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testIsJsonBooleanMatcherUpdatesDescription() {
		Matcher<JsonElement> matcher = aJsonBoolean(false);

		Description description = new StringDescription();

		matcher.describeTo(description);

		String expected = "a boolean element with a value that is <false>";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testValidJsonBooleanValidates() {
		Matcher<JsonElement> matcher = aJsonBoolean(true);

		JsonPrimitive jsonPrimitive = new JsonPrimitive(true);

		boolean matches = matcher.matches(jsonPrimitive);

		assertThat(matches, is(true));
	}

}