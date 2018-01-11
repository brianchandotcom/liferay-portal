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

package com.liferay.apio.architect.test.json;

import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;

import com.liferay.apio.architect.test.internal.json.IsJsonArray;
import com.liferay.apio.architect.test.internal.json.IsJsonBoolean;
import com.liferay.apio.architect.test.internal.json.IsJsonInt;
import com.liferay.apio.architect.test.internal.json.IsJsonLong;
import com.liferay.apio.architect.test.internal.json.IsJsonObjectString;
import com.liferay.apio.architect.test.internal.json.IsJsonString;

import org.hamcrest.Matcher;

/**
 * Provides {@link Matcher} objects that can be used in testing JSON files and
 * strings.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public final class JsonMatchers {

	/**
	 * Returns a matcher that checks if an element is a {@code
	 * com.google.gson.JsonArray}.
	 *
	 * @return the matcher that checks if an element is a JSON array
	 */
	public static Matcher<JsonElement> aJsonArrayThat(
		Matcher<Iterable<? extends JsonElement>> elementsMatcher) {

		return new IsJsonArray(elementsMatcher);
	}

	/**
	 * Returns a matcher that checks if an element is a boolean {@code
	 * JsonElement}.
	 *
	 * @return the matcher that checks if an element is a boolean {@code
	 *         JsonElement}
	 */
	public static Matcher<JsonElement> aJsonBoolean(boolean bool) {
		return new IsJsonBoolean(is(bool));
	}

	/**
	 * Returns a matcher that checks if an element is an integer {@code
	 * JsonElement}.
	 *
	 * @return the matcher that checks if an element is an integer {@code
	 *         JsonElement}
	 */
	public static Matcher<JsonElement> aJsonInt(
		Matcher<Integer> numberMatcher) {

		return new IsJsonInt(is(numberMatcher));
	}

	/**
	 * Returns a matcher that checks if an element is a long {@code
	 * JsonElement}.
	 *
	 * @return the matcher that checks if an element is a long {@code
	 *         JsonElement}
	 */
	public static Matcher<JsonElement> aJsonLong(Matcher<Long> numberMatcher) {
		return new IsJsonLong(is(numberMatcher));
	}

	/**
	 * Returns a matcher that checks if a string is a JSON object.
	 *
	 * @return the matcher that checks if a string is a JSON object
	 */
	public static Matcher<String> aJsonObjectStringWith(Conditions conditions) {
		return new IsJsonObjectString(conditions);
	}

	/**
	 * Returns a matcher that checks if a {@code JsonElement} is a valid JSON
	 * object containing an element.
	 *
	 * @return the matcher that checks if a {@code JsonElement} is a valid JSON
	 *         object containing an element
	 */
	public static Matcher<JsonElement> aJsonObjectWhere(
		String key, Matcher<? extends JsonElement> matcher) {

		Conditions.Builder builder = new Conditions.Builder();

		Conditions conditions = builder.where(
			key, matcher
		).build();

		return aJsonObjectWith(conditions);
	}

	/**
	 * Returns a matcher that uses different conditions to check if a {@code
	 * JsonElement} is a valid JSON object.
	 *
	 * @return the matcher that uses different conditions to check if a {@code
	 *         JsonElement} is a valid JSON object
	 */
	public static Matcher<JsonElement> aJsonObjectWith(Conditions conditions) {
		return conditions;
	}

	/**
	 * Returns a matcher that checks if an element is a string {@code
	 * JsonElement}.
	 *
	 * @return the matcher that checks if an element is a string {@code
	 *         JsonElement}
	 */
	public static Matcher<JsonElement> aJsonString(
		Matcher<String> stringMatcher) {

		return new IsJsonString(is(stringMatcher));
	}

	private JsonMatchers() {
		throw new UnsupportedOperationException();
	}

}