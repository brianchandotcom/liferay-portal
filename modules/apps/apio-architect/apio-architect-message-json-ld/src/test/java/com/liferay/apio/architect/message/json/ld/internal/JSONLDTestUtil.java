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

package com.liferay.apio.architect.message.json.ld.internal;

import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonArrayThat;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonBoolean;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonInt;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWhere;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWith;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.isAJsonArrayContaining;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;

import com.liferay.apio.architect.test.util.json.Conditions;
import com.liferay.apio.architect.test.util.json.Conditions.Builder;
import com.liferay.apio.architect.test.util.json.JsonMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.CoreMatchers;
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
public class JSONLDTestUtil {

	/**
	 * A {@code Matcher} that checks if a {@code JsonElement} is a link to
	 * Hydra's profile.
	 */
	public static final Matcher<JsonElement> IS_A_LINK_TO_HYDRA_PROFILE =
		isALinkTo("https://www.w3.org/ns/hydra/core#");

	/**
	 * A {@link Matcher} that checks if a field is a {@code {"@type" = " ID"}}
	 * JSON Object.
	 */
	public static final Matcher<JsonElement> IS_A_TYPE_ID_JSON_OBJECT = is(
		aJsonObjectWhere("@type", is(aJsonString(equalTo("@id")))));

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of a
	 * {@code RootElement} that matches the provided ID.
	 *
	 * @param  id the ID of the {@code RootElement}
	 * @param  addVocab whether the {@code @vocab} check must be added
	 * @param  member whether this {@code RootElement} is added as a
	 *         collection's member
	 * @return the matcher
	 */
	public static Matcher<JsonElement> aRootElementJsonObjectWithId(
		String id, boolean addVocab, boolean member) {

		Builder builder = new Builder();

		List<Matcher<? super JsonElement>> theContext = new ArrayList<>();

		theContext.add(aJsonObjectWhere("embedded2", IS_A_TYPE_ID_JSON_OBJECT));
		theContext.add(aJsonObjectWhere("linked1", IS_A_TYPE_ID_JSON_OBJECT));
		theContext.add(aJsonObjectWhere("linked2", IS_A_TYPE_ID_JSON_OBJECT));
		theContext.add(
			aJsonObjectWhere("relatedCollection1", IS_A_TYPE_ID_JSON_OBJECT));
		theContext.add(
			aJsonObjectWhere("relatedCollection2", IS_A_TYPE_ID_JSON_OBJECT));

		if (addVocab) {
			theContext.add(
				aJsonObjectWhere(
					"@vocab", is(aJsonString(equalTo("http://schema.org/")))));
			theContext.add(IS_A_LINK_TO_HYDRA_PROFILE);
		}

		Builder rootElementBuilder = builder.where(
			"@context", is(aJsonArrayThat(contains(theContext)))
		).where(
			"@id", isALinkTo("localhost/p/model/" + id)
		).where(
			"@type", containsTheTypes("Type 1", "Type 2")
		).where(
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
			"embedded1", isAJsonObjectWithTheFirstEmbedded(member)
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
			"string1", is(aJsonString(equalTo("Live long and prosper")))
		).where(
			"string2", is(aJsonString(equalTo("Hypermedia")))
		).where(
			"stringList1", isAJsonArrayContaining("a", "b", "c", "d", "e")
		).where(
			"stringList2", isAJsonArrayContaining("f", "g", "h", "i", "j")
		);

		if (member) {
			return aJsonObjectWith(rootElementBuilder.build());
		}

		Conditions conditions = rootElementBuilder.where(
			"operation", containsTheRootOperations()
		).build();

		return aJsonObjectWith(conditions);
	}

	/**
	 * Returns a {@code Matcher} that checks if the field contains the {@code
	 * RootModel} operations.
	 *
	 * @return the matcher
	 */
	public static Matcher<? extends JsonElement> containsTheRootOperations() {
		Builder builder = new Builder();

		Conditions firstOperationConditions = builder.where(
			"@id", is(aJsonString(equalTo("_:delete-operation")))
		).where(
			"@type", is(aJsonString(equalTo("Operation")))
		).where(
			"method", is(aJsonString(equalTo("DELETE")))
		).build();

		Conditions secondOperationConditions = builder.where(
			"@id", is(aJsonString(equalTo("_:update-operation")))
		).where(
			"@type", is(aJsonString(equalTo("Operation")))
		).where(
			"expects", is(aJsonString(equalTo("localhost/f/u/r")))
		).where(
			"method", is(aJsonString(equalTo("PUT")))
		).build();

		List<Matcher<? super JsonElement>> theOperations = Arrays.asList(
			is(aJsonObjectWith(firstOperationConditions)),
			is(aJsonObjectWith(secondOperationConditions)));

		return is(aJsonArrayThat(contains(theOperations)));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field contains the provided
	 * types as a JSON Array.
	 *
	 * @param  types the types
	 * @return the matcher
	 */
	public static Matcher<? extends JsonElement> containsTheTypes(
		String... types) {

		Stream<String> stream = Arrays.stream(types);

		List<Matcher<? super JsonElement>> matchers = stream.map(
			CoreMatchers::equalTo
		).map(
			JsonMatchers::aJsonString
		).collect(
			Collectors.toList()
		);

		return is(aJsonArrayThat(contains(matchers)));
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the first embedded model.
	 *
	 * @param  member whether the {@code FirstEmbeddedModel} is added as a
	 *         collection member
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheFirstEmbedded(
		boolean member) {

		Builder builder = new Builder();

		List<Matcher<? super JsonElement>> theContext = Arrays.asList(
			aJsonObjectWhere("linked", IS_A_TYPE_ID_JSON_OBJECT),
			aJsonObjectWhere("relatedCollection", IS_A_TYPE_ID_JSON_OBJECT));

		Builder firstEmbeddedBuilder = builder.where(
			"@context", is(aJsonArrayThat(contains(theContext)))
		).where(
			"@id", isALinkTo("localhost/p/first-inner-model/first")
		).where(
			"@type", containsTheTypes("Type")
		).where(
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
			"string", is(aJsonString(equalTo("A string")))
		).where(
			"stringList", isAJsonArrayContaining("a", "b")
		);

		if (member) {
			return aJsonObjectWith(firstEmbeddedBuilder.build());
		}

		Matcher<? super JsonElement> anOperation = builder.where(
			"@id", is(aJsonString(equalTo("_:delete-operation")))
		).where(
			"@type", is(aJsonString(equalTo("Operation")))
		).where(
			"method", is(aJsonString(equalTo("DELETE")))
		).build();

		Conditions conditions = firstEmbeddedBuilder.where(
			"operation", is(aJsonArrayThat(contains(anOperation)))
		).build();

		return aJsonObjectWith(conditions);
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
			"@type", containsTheTypes("Type 3")
		).where(
			"number1", is(aJsonInt(equalTo(2017)))
		).where(
			"string1", is(aJsonString(equalTo("id 1")))
		).where(
			"string2", is(aJsonString(equalTo("string2")))
		).build();

		return aJsonObjectWith(conditions);
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the second embedded model.
	 *
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isAJsonObjectWithTheSecondEmbedded() {
		List<Matcher<? super JsonElement>> theContext = Arrays.asList(
			aJsonObjectWhere("embedded", IS_A_TYPE_ID_JSON_OBJECT),
			aJsonObjectWhere("linked", IS_A_TYPE_ID_JSON_OBJECT),
			aJsonObjectWhere("relatedCollection", IS_A_TYPE_ID_JSON_OBJECT));

		Conditions.Builder builder = new Conditions.Builder();

		Conditions secondEmbeddedConditions = builder.where(
			"@context", is(aJsonArrayThat(contains(theContext)))
		).where(
			"@id", isALinkTo("localhost/p/second-inner-model/first")
		).where(
			"@type", containsTheTypes("Type")
		).where(
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

		List<Matcher<? super JsonElement>> theContext = Arrays.asList(
			aJsonObjectWhere("bidirectionalModel3", IS_A_TYPE_ID_JSON_OBJECT),
			aJsonObjectWhere("linked3", IS_A_TYPE_ID_JSON_OBJECT),
			aJsonObjectWhere("relatedCollection3", IS_A_TYPE_ID_JSON_OBJECT));

		Builder builder = new Builder();

		Conditions secondNestedConditions = builder.where(
			"@context", is(aJsonArrayThat(contains(theContext)))
		).where(
			"@type", containsTheTypes("Type 4")
		).where(
			"bidirectionalModel3",
			isALinkTo("localhost/p/first-inner-model/" + id)
		).where(
			"linked3", isALinkTo("localhost/p/third-inner-model/fifth")
		).where(
			"nested3", isAJsonObjectWithTheThirdNested()
		).where(
			"number1", is(aJsonInt(equalTo(42)))
		).where(
			"relatedCollection3",
			isALinkTo("localhost/p/model/" + id + "/models")
		).where(
			"string1", is(aJsonString(equalTo(id)))
		).build();

		return aJsonObjectWith(secondNestedConditions);
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a JSON object of
	 * the third nested model.
	 *
	 * @return the matcher
	 */
	public static Conditions isAJsonObjectWithTheThirdNested() {
		Builder builder = new Builder();

		return builder.where(
			"@type", containsTheTypes("Type 5")
		).where(
			"string1", is(aJsonString(equalTo("id 3")))
		).build();
	}

	/**
	 * Returns a {@code Matcher} that checks if the field is a link to the URL.
	 *
	 * @param  url the URL
	 * @return the matcher
	 */
	public static Matcher<JsonElement> isALinkTo(String url) {
		return is(aJsonString(equalTo(url)));
	}

}