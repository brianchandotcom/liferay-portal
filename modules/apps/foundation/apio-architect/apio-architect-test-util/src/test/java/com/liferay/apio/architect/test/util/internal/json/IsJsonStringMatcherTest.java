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

import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;

import static org.hamcrest.CoreMatchers.equalTo;
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
public class IsJsonStringMatcherTest {

	@Test
	public void testInvalidJsonTextFails() {
		JsonPrimitive jsonPrimitive = new JsonPrimitive("Live long");

		assertThat(jsonPrimitive, is(not(aJsonString(equalTo("and prosper")))));
	}

	@Test
	public void testInvalidJsonTextUpdatesValidDescription() {
		JsonPrimitive jsonPrimitive = new JsonPrimitive("Live long");

		Matcher<JsonElement> matcher = aJsonString(equalTo("and prosper"));

		Description description = new StringDescription();

		matcher.describeMismatch(jsonPrimitive, description);

		String expected =
			"was a string element with a value that was \"Live long\"";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testIsJsonTextMatcherUpdatesDescription() {
		Matcher<JsonElement> matcher = aJsonString(equalTo("and prosper"));

		Description description = new StringDescription();

		matcher.describeTo(description);

		String expected =
			"a string element with a value that is \"and prosper\"";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testValidJsonTextValidates() {
		Matcher<JsonElement> matcher = aJsonString(equalTo("Live long"));

		JsonPrimitive jsonPrimitive = new JsonPrimitive("Live long");

		boolean matches = matcher.matches(jsonPrimitive);

		assertThat(matches, is(true));
	}

}