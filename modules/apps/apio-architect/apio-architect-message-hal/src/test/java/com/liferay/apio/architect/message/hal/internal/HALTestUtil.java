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

package com.liferay.apio.architect.message.hal.internal;

import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonBoolean;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonInt;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWhere;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWith;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.isAJsonArrayContaining;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;

import com.liferay.apio.architect.test.util.json.Conditions;

import org.hamcrest.Matcher;

/**
 * Provides utility functions for testing HAL message mappers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class HALTestUtil {

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of a
	 * {@code RootElement} that matches the provided ID.
	 *
	 * @param  id the ID of the {@code RootElement}
	 * @return the matcher
	 */
	public static Matcher<JsonElement> aRootElementJsonObjectWithId(String id) {
		Conditions.Builder builder = new Conditions.Builder();

		Conditions conditions = builder.where(
			"_embedded", isAJsonObjectWithTheFirstEmbedded(id)
		).where(
			"_links", isAJsonObjectWithTheLinks(id)
		).where(
			"boolean1", is(aJsonBoolean(true))
		).where(
			"boolean2", is(aJsonBoolean(false))
		).where(
			"booleanList1", isAJsonArrayContaining(true, true, false, false)
		).where(
			"booleanList2", isAJsonArrayContaining(true, false, true, false)
		).where(
			"date1", is(aJsonString(equalTo("2016-06-15T09:00Z")))
		).where(
			"date2", is(aJsonString(equalTo("2017-04-03T18:36Z")))
		).where(
			"localizedString1", is(aJsonString(equalTo("Translated 1")))
		).where(
			"localizedString2", is(aJsonString(equalTo("Translated 2")))
		).where(
			"number1", is(aJsonInt(equalTo(2017)))
		).where(
			"number2", is(aJsonInt(equalTo(42)))
		).where(
			"numberList1", isAJsonArrayContaining(1, 2, 3, 4, 5)
		).where(
			"numberList2", isAJsonArrayContaining(6, 7, 8, 9, 10)
		).where(
			"string1", is(aJsonString(equalTo("Live long and prosper")))
		).where(
			"string2", is(aJsonString(equalTo("Hypermedia")))
		).where(
			"stringList1", isAJsonArrayContaining("a", "b", "c", "d", "e")
		).where(
			"stringList2", isAJsonArrayContaining("f", "g", "h", "i", "j")
		).build();

		return aJsonObjectWith(conditions);
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the first embedded model.
	 *
	 * @param  id
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheFirstEmbedded(
		String id) {

		Conditions.Builder builder = new Conditions.Builder();

		Conditions firstEmbeddedLinkConditions = builder.where(
			"binary", isALinkTo("localhost/b/first-inner-model/first/binary")
		).where(
			"link", isALinkTo("www.liferay.com")
		).where(
			"linked", isALinkTo("localhost/p/second-inner-model/second")
		).where(
			"relatedCollection",
			isALinkTo("localhost/p/first-inner-model/first/models")
		).where(
			"self", isALinkTo("localhost/p/first-inner-model/first")
		).build();

		Matcher<JsonElement> isAJsonObjectWithTheSecondEmbedded = is(
			aJsonObjectWhere("embedded", isAJsonObjectWithTheSecondEmbedded()));

		Conditions firstEmbeddedConditions = builder.where(
			"_embedded", isAJsonObjectWithTheSecondEmbedded
		).where(
			"_links", is(aJsonObjectWith(firstEmbeddedLinkConditions))
		).where(
			"boolean", is(aJsonBoolean(true))
		).where(
			"booleanList", isAJsonArrayContaining(true, false)
		).where(
			"localizedString", is(aJsonString(equalTo("Translated")))
		).where(
			"number", is(aJsonInt(equalTo(42)))
		).where(
			"numberList", isAJsonArrayContaining(1, 2)
		).where(
			"string", is(aJsonString(equalTo("A string")))
		).where(
			"stringList", isAJsonArrayContaining("a", "b")
		).build();

		Conditions conditions = builder.where(
			"embedded1", is(aJsonObjectWith(firstEmbeddedConditions))
		).where(
			"nested1", isAJsonObjectWithTheFirstNested()
		).where(
			"nested2", isAJsonObjectWithTheSecondNested(id)
		).build();

		return is(aJsonObjectWith(conditions));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the first nested model.
	 *
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheFirstNested() {
		Conditions.Builder builder = new Conditions.Builder();

		Conditions conditions = builder.where(
			"number1", is(aJsonInt(equalTo(2017)))
		).where(
			"string1", is(aJsonString(equalTo("id 1")))
		).where(
			"string2", is(aJsonString(equalTo("string2")))
		).build();

		return aJsonObjectWith(conditions);
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object
	 * containing the links of a {@code RootElement} that matches the provided
	 * ID.
	 *
	 * @param  id the ID of the {@code RootElement}
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheLinks(String id) {
		Conditions.Builder builder = new Conditions.Builder();

		Conditions linkConditions = builder.where(
			"binary1", isALinkTo("localhost/b/model/" + id + "/binary1")
		).where(
			"binary2", isALinkTo("localhost/b/model/" + id + "/binary2")
		).where(
			"embedded2", isALinkTo("localhost/p/first-inner-model/second")
		).where(
			"link1", isALinkTo("www.liferay.com")
		).where(
			"link2", isALinkTo("community.liferay.com")
		).where(
			"linked1", isALinkTo("localhost/p/first-inner-model/third")
		).where(
			"linked2", isALinkTo("localhost/p/first-inner-model/fourth")
		).where(
			"relatedCollection1",
			isALinkTo("localhost/p/model/" + id + "/models")
		).where(
			"relatedCollection2",
			isALinkTo("localhost/p/model/" + id + "/models")
		).where(
			"self", isALinkTo("localhost/p/model/" + id)
		).build();

		return is(aJsonObjectWith(linkConditions));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the second embedded model.
	 *
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheSecondEmbedded() {
		Conditions.Builder builder = new Conditions.Builder();

		Conditions secondEmbeddedLinkConditions = builder.where(
			"binary", isALinkTo("localhost/b/second-inner-model/first/binary")
		).where(
			"embedded", isALinkTo("localhost/p/third-inner-model/first")
		).where(
			"link", isALinkTo("community.liferay.com")
		).where(
			"linked", isALinkTo("localhost/p/third-inner-model/second")
		).where(
			"relatedCollection",
			isALinkTo("localhost/p/second-inner-model/first/models")
		).where(
			"self", isALinkTo("localhost/p/second-inner-model/first")
		).build();

		Conditions secondEmbeddedConditions = builder.where(
			"_links", is(aJsonObjectWith(secondEmbeddedLinkConditions))
		).where(
			"boolean", is(aJsonBoolean(false))
		).where(
			"booleanList", isAJsonArrayContaining(true)
		).where(
			"number", is(aJsonInt(equalTo(2017)))
		).where(
			"numberList", isAJsonArrayContaining(1)
		).where(
			"string", is(aJsonString(equalTo("A string")))
		).where(
			"stringList", isAJsonArrayContaining("a")
		).build();

		return is(aJsonObjectWith(secondEmbeddedConditions));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the second nested model.
	 *
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheSecondNested(
		String id) {

		Conditions.Builder builder = new Conditions.Builder();

		Matcher<JsonElement> aNestedObjectMatcher = aJsonObjectWhere(
			"nested3",
			aJsonObjectWhere("string1", is(aJsonString(equalTo("id 3")))));

		Conditions firstEmbeddedLinkConditions = builder.where(
			"linked3", isALinkTo("localhost/p/third-inner-model/fifth")
		).where(
			"bidirectionalModel3",
			isALinkTo("localhost/p/first-inner-model/" + id)
		).where(
			"relatedCollection3",
			isALinkTo("localhost/p/model/" + id + "/models")
		).build();

		Conditions conditions = builder.where(
			"number1", is(aJsonInt(equalTo(42)))
		).where(
			"string1", is(aJsonString(equalTo(id)))
		).where(
			"_links", aJsonObjectWith(firstEmbeddedLinkConditions)
		).where(
			"_embedded", aNestedObjectMatcher
		).build();

		return aJsonObjectWith(conditions);
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a link to the URL.
	 *
	 * @param  url the URL
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isALinkTo(String url) {
		return is(aJsonObjectWhere("href", is(aJsonString(equalTo(url)))));
	}

	private HALTestUtil() {
		throw new UnsupportedOperationException();
	}

}