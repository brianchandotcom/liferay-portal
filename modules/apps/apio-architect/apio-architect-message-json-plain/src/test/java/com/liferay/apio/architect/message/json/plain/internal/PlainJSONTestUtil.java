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

package com.liferay.apio.architect.message.json.plain.internal;

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
import com.liferay.apio.architect.test.util.json.Conditions.Builder;

import org.hamcrest.Matcher;

/**
 * Provides utility functions for testing JSON Plain message mappers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class PlainJSONTestUtil {

	/**
	 * Returns a {@code Matcher} that checks if the field is the JSON Object of
	 * a {@code RootElement} with the provided ID.
	 *
	 * @param  id the ID of the {@code RootElement}
	 * @return the matcher
	 */
	public static Matcher<JsonElement> aRootElementJsonObjectWithId(String id) {
		Builder builder = new Builder();

		Conditions conditions = builder.where(
			"binary1", isALinkTo("localhost/b/model/" + id + "/binary1")
		).where(
			"binary2", isALinkTo("localhost/b/model/" + id + "/binary2")
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
			"embedded1", isAJsonObjectWithTheFirstEmbedded()
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
			"localizedString1", is(aJsonString(equalTo("Translated 1")))
		).where(
			"localizedString2", is(aJsonString(equalTo("Translated 2")))
		).where(
			"nested1", isAJsonObjectWithTheFirstNested()
		).where(
			"nested2", isAJsonObjectWithTheSecondNested(id)
		).where(
			"number1", is(aJsonInt(equalTo(2017)))
		).where(
			"number2", is(aJsonInt(equalTo(42)))
		).where(
			"numberList1", isAJsonArrayContaining(1, 2, 3, 4, 5)
		).where(
			"numberList2", isAJsonArrayContaining(6, 7, 8, 9, 10)
		).where(
			"relatedCollection1",
			isALinkTo("localhost/p/model/" + id + "/models")
		).where(
			"relatedCollection2",
			isALinkTo("localhost/p/model/" + id + "/models")
		).where(
			"self", isALinkTo("localhost/p/model/" + id)
		).where(
			"string1", is(aJsonString(equalTo("Live long and prosper")))
		).where(
			"string2", is(aJsonString(equalTo("Hypermedia")))
		).where(
			"stringList1", isAJsonArrayContaining("a", "b", "c", "d", "e")
		).where(
			"stringList2", isAJsonArrayContaining("f", "g", "h", "i", "j")
		).build();

		return is(aJsonObjectWith(conditions));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the first embedded model.
	 *
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheFirstEmbedded() {
		Conditions.Builder builder = new Conditions.Builder();

		Conditions firstEmbeddedConditions = builder.where(
			"binary", isALinkTo("localhost/b/first-inner-model/first/binary")
		).where(
			"boolean", is(aJsonBoolean(true))
		).where(
			"booleanList", isAJsonArrayContaining(true, false)
		).where(
			"embedded", isAJsonObjectWithTheSecondEmbedded()
		).where(
			"link", isALinkTo("www.liferay.com")
		).where(
			"linked", isALinkTo("localhost/p/second-inner-model/second")
		).where(
			"localizedString", is(aJsonString(equalTo("Translated")))
		).where(
			"number", is(aJsonInt(equalTo(42)))
		).where(
			"numberList", isAJsonArrayContaining(1, 2)
		).where(
			"relatedCollection",
			isALinkTo("localhost/p/first-inner-model/first/models")
		).where(
			"self", isALinkTo("localhost/p/first-inner-model/first")
		).where(
			"string", is(aJsonString(equalTo("A string")))
		).where(
			"stringList", isAJsonArrayContaining("a", "b")
		).build();

		return is(aJsonObjectWith(firstEmbeddedConditions));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the first nested model.
	 *
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheFirstNested() {
		Builder builder = new Builder();

		Conditions conditions = builder.where(
			"number1", is(aJsonInt(equalTo(2017)))
		).where(
			"string1", is(aJsonString(equalTo("id 1")))
		).where(
			"string2", is(aJsonString(equalTo("string2")))
		).build();

		return is(aJsonObjectWith(conditions));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the second embedded model.
	 *
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheSecondEmbedded() {
		Builder builder = new Builder();

		Conditions secondEmbeddedConditions = builder.where(
			"binary", isALinkTo("localhost/b/second-inner-model/first/binary")
		).where(
			"boolean", is(aJsonBoolean(false))
		).where(
			"booleanList", isAJsonArrayContaining(true)
		).where(
			"embedded", isALinkTo("localhost/p/third-inner-model/first")
		).where(
			"link", isALinkTo("community.liferay.com")
		).where(
			"linked", isALinkTo("localhost/p/third-inner-model/second")
		).where(
			"number", is(aJsonInt(equalTo(2017)))
		).where(
			"numberList", isAJsonArrayContaining(1)
		).where(
			"relatedCollection",
			isALinkTo("localhost/p/second-inner-model/first/models")
		).where(
			"self", isALinkTo("localhost/p/second-inner-model/first")
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

		Builder builder = new Builder();

		Conditions conditions = builder.where(
			"bidirectionalModel3",
			isALinkTo("localhost/p/first-inner-model/" + id)
		).where(
			"linked3", is(isALinkTo("localhost/p/third-inner-model/fifth"))
		).where(
			"nested3",
			aJsonObjectWhere("string1", is(aJsonString(equalTo("id 3"))))
		).where(
			"number1", is(aJsonInt(equalTo(42)))
		).where(
			"relatedCollection3",
			is(isALinkTo("localhost/p/model/" + id + "/models"))
		).where(
			"string1", is(aJsonString(equalTo(id)))
		).build();

		return is(aJsonObjectWith(conditions));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a link to the URL.
	 *
	 * @param  url the URL
	 * @return the matcher
	 */
	public static Matcher<? extends JsonElement> isALinkTo(String url) {
		return is(aJsonString(equalTo(url)));
	}

	private PlainJSONTestUtil() {
		throw new UnsupportedOperationException();
	}

}