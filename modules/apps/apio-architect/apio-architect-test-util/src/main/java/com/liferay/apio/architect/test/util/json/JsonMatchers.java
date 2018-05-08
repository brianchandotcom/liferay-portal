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

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;

import com.liferay.apio.architect.test.util.internal.json.IsJsonArray;
import com.liferay.apio.architect.test.util.internal.json.IsJsonBoolean;
import com.liferay.apio.architect.test.util.internal.json.IsJsonInt;
import com.liferay.apio.architect.test.util.internal.json.IsJsonLong;
import com.liferay.apio.architect.test.util.internal.json.IsJsonObjectString;
import com.liferay.apio.architect.test.util.internal.json.IsJsonString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	/**
	 * Returns a matcher that checks if an element is a {@code
	 * com.google.gson.JsonArray} containing the list of booleans provided.
	 *
	 * @param  values the list of boolean values
	 * @return the matcher
	 */
	@SuppressWarnings("unchecked")
	public static Matcher<JsonElement> isAJsonArrayContaining(
		Boolean... values) {

		Stream<Boolean> stream = Arrays.stream(values);

		List<Matcher<? super JsonElement>> matchers = stream.map(
			JsonMatchers::aJsonBoolean
		).collect(
			Collectors.toList()
		);

		return is(aJsonArrayThat(contains(matchers)));
	}

	/**
	 * Returns a matcher that checks if an element is a {@code
	 * com.google.gson.JsonArray} containing the list of integers provided.
	 *
	 * @param  values the list of integer values
	 * @return the matcher
	 */
	@SuppressWarnings("unchecked")
	public static Matcher<JsonElement> isAJsonArrayContaining(
		Integer... values) {

		Stream<Integer> stream = Arrays.stream(values);

		List<Matcher<? super JsonElement>> matchers = stream.map(
			integer -> aJsonInt(equalTo(integer))
		).collect(
			Collectors.toList()
		);

		return is(aJsonArrayThat(contains(matchers)));
	}

	/**
	 * Returns a matcher that checks if an element is a {@code
	 * com.google.gson.JsonArray} containing the list of long values provided.
	 *
	 * @param  values the list of long values
	 * @return the matcher
	 */
	@SuppressWarnings("unchecked")
	public static Matcher<JsonElement> isAJsonArrayContaining(Long... values) {
		Stream<Long> stream = Arrays.stream(values);

		List<Matcher<? super JsonElement>> matchers = stream.map(
			aLong -> aJsonLong(equalTo(aLong))
		).collect(
			Collectors.toList()
		);

		return is(aJsonArrayThat(contains(matchers)));
	}

	/**
	 * Returns a matcher that checks if an element is a {@code
	 * com.google.gson.JsonArray} containing the list of strings provided.
	 *
	 * @param  values the list of string values
	 * @return the matcher
	 */
	@SuppressWarnings("unchecked")
	public static Matcher<JsonElement> isAJsonArrayContaining(
		String... values) {

		Stream<String> stream = Arrays.stream(values);

		List<Matcher<? super JsonElement>> matchers = stream.map(
			string -> aJsonString(equalTo(string))
		).collect(
			Collectors.toList()
		);

		return is(aJsonArrayThat(contains(matchers)));
	}

	private JsonMatchers() {
		throw new UnsupportedOperationException();
	}

}