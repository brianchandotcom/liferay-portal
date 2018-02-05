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

import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonLong;

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
public class IsJsonLongMatcherTest {

	@Test
	public void testInvalidJsonLongFails() {
		JsonPrimitive jsonPrimitive = new JsonPrimitive(42L);

		assertThat(jsonPrimitive, is(not(aJsonLong(equalTo(23L)))));
	}

	@Test
	public void testInvalidJsonLongUpdatesValidDescription() {
		JsonPrimitive jsonPrimitive = new JsonPrimitive(42L);

		Matcher<JsonElement> matcher = aJsonLong(equalTo(23L));

		Description description = new StringDescription();

		matcher.describeMismatch(jsonPrimitive, description);

		String expected = "was a number element with a value that was <42L>";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testIsJsonLongMatcherUpdatesDescription() {
		Matcher<JsonElement> matcher = aJsonLong(equalTo(23L));

		Description description = new StringDescription();

		matcher.describeTo(description);

		String expected = "a number element with a value that is <23L>";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testValidJsonLongValidates() {
		Matcher<JsonElement> matcher = aJsonLong(equalTo(42L));

		JsonPrimitive jsonPrimitive = new JsonPrimitive(42L);

		boolean matches = matcher.matches(jsonPrimitive);

		assertThat(matches, is(true));
	}

}