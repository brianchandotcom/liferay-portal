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

package com.liferay.apio.architect.test.util.form;

import static com.liferay.apio.architect.test.util.form.FormMatchers.isReturnedIn;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.test.util.form.FormConditions.Builder;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class FormConditionsTest {

	@Test
	public void testFormWithInvalidDescriptionUpdatesDescription() {
		Form.Builder<Map<String, Object>> builder = Form.Builder.empty();

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> null
		).constructor(
			HashMap::new
		).build();

		Builder<Map<String, Object>> formConditionsBuilder = new Builder<>();

		FormConditions<Map<String, Object>> formConditions =
			formConditionsBuilder.whereString(
				"string", isReturnedIn(map -> null)
			).build();

		Description description = new StringDescription();

		formConditions.describeMismatch(form, description);

		String expected =
			"was a Form...\n\t...whose description was (null or an empty " +
				"string)\n\t...that should have returned something that is " +
					"\"String\" instead of null\n";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testFormWithInvalidFieldsUpdatesDescription() {
		Form.Builder<Map<String, Object>> builder = Form.Builder.empty();

		Form<Map<String, Object>> form = builder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).build();

		Builder<Map<String, Object>> formConditionsBuilder = new Builder<>();

		FormConditions<Map<String, Object>> formConditions =
			formConditionsBuilder.whereDouble(
				"double", isReturnedIn(map -> null)
			).build();

		Description description = new StringDescription();

		formConditions.describeMismatch(form, description);

		String expected =
			"was a Form...\n\t...that should have returned something that is " +
				"<21.2> instead of null\n";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testFormWithInvalidTitleUpdatesDescription() {
		Form.Builder<Map<String, Object>> builder = Form.Builder.empty();

		Form<Map<String, Object>> form = builder.title(
			__ -> ""
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).build();

		Builder<Map<String, Object>> formConditionsBuilder = new Builder<>();

		FormConditions<Map<String, Object>> formConditions =
			formConditionsBuilder.whereString(
				"string", isReturnedIn(map -> null)
			).build();

		Description description = new StringDescription();

		formConditions.describeMismatch(form, description);

		String expected =
			"was a Form...\n\t...whose title was (null or an empty string)" +
				"\n\t...that should have returned something that is " +
					"\"String\" instead of null\n";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testInvokingDescribesToUpdatesDescription() {
		Builder<Map<String, Object>> builder = new Builder<>();

		FormConditions formConditions = builder.whereBoolean(
			"boolean", isReturnedIn(map -> map.get("boolean"))
		).whereString(
			"string", isReturnedIn(map -> map.get("string"))
		).build();

		Description description = new StringDescription();

		formConditions.describeTo(description);

		StringBuilder stringBuilder = new StringBuilder();

		String expected = stringBuilder.append(
			"a Form...\n\t...that should have a non empty title\n\t...that "
		).append(
			"should have a non empty description\n\t...that should return "
		).append(
			"something that is <true>\n\t...that should return something that "
		).append(
			"is \"String\"\n"
		).toString();

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testInvokingMatchesUpdatedDescription() {
		Builder<Map<String, Object>> builder = new Builder<>();

		FormConditions formConditions = builder.whereBoolean(
			"boolean", isReturnedIn(__ -> null)
		).whereString(
			"string", isReturnedIn(__ -> null)
		).build();

		Description description = new StringDescription();

		Form.Builder<Map<String, Object>> formBuilder = Form.Builder.empty();

		Form<Map<String, Object>> form = formBuilder.title(
			__ -> "title"
		).description(
			__ -> "description"
		).constructor(
			HashMap::new
		).build();

		boolean matchesElement = formConditions.matches(form);
		formConditions.describeMismatch(form, description);

		String expected =
			"was a Form...\n\t...that should have returned something that is " +
				"<true> instead of null\n\t...that should have returned " +
					"something that is \"String\" instead of null\n";

		assertThat(matchesElement, is(false));
		assertThat(description.toString(), is(expected));
	}

}