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

package com.liferay.vulcan.message.json.ld.internal;

import static com.liferay.vulcan.test.json.JsonMatchers.aJsonArrayThat;
import static com.liferay.vulcan.test.json.JsonMatchers.aJsonBoolean;
import static com.liferay.vulcan.test.json.JsonMatchers.aJsonInt;
import static com.liferay.vulcan.test.json.JsonMatchers.aJsonObjectWhere;
import static com.liferay.vulcan.test.json.JsonMatchers.aJsonObjectWith;
import static com.liferay.vulcan.test.json.JsonMatchers.aJsonString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.vulcan.test.json.Conditions;
import com.liferay.vulcan.test.resource.model.RootModel;
import com.liferay.vulcan.test.writer.MockSingleModelWriter;

import javax.ws.rs.core.HttpHeaders;

import org.hamcrest.Matcher;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Javier Gamarra
 * @author Alejandro Hernández
 */
public class JSONLDSingleModelMessageMapperTest {

	@Test
	public void testJSONLDSingleModelMessageMapper() {
		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		JsonObject jsonObject = MockSingleModelWriter.write(
			httpHeaders, _singleModelMessageMapper);

		Conditions.Builder builder = new Conditions.Builder();

		Conditions conditions = builder.where(
			"@context", _isAJsonObjectWithTheContext
		).where(
			"@id", _isALinkTo("localhost/p/model/first")
		).where(
			"@type", is(aJsonArrayThat(_containsTheTypes))
		).where(
			"binary1", _isALinkTo("localhost/b/model/first/binary1")
		).where(
			"binary2", _isALinkTo("localhost/b/model/first/binary2")
		).where(
			"boolean1", is(aJsonBoolean(true))
		).where(
			"boolean2", is(aJsonBoolean(false))
		).where(
			"date1", is(aJsonString(equalTo("2016-06-15T09:00Z")))
		).where(
			"date2", is(aJsonString(equalTo("2017-04-03T18:36Z")))
		).where(
			"embedded1", _isAJsonObjectWithTheFirstEmbedded
		).where(
			"embedded2", _isALinkTo("localhost/p/first-inner-model/second")
		).where(
			"link1", _isALinkTo("www.liferay.com")
		).where(
			"link2", _isALinkTo("community.liferay.com")
		).where(
			"linked1", _isALinkTo("localhost/p/first-inner-model/third")
		).where(
			"linked2", _isALinkTo("localhost/p/first-inner-model/fourth")
		).where(
			"localizedString1", is(aJsonString(equalTo("Translated 1")))
		).where(
			"localizedString2", is(aJsonString(equalTo("Translated 2")))
		).where(
			"number1", is(aJsonInt(equalTo(2017)))
		).where(
			"number2", is(aJsonInt(equalTo(42)))
		).where(
			"relatedCollection1", _isALinkTo("localhost/p/model/first/models")
		).where(
			"relatedCollection2", _isALinkTo("localhost/p/model/first/models")
		).where(
			"string1", is(aJsonString(equalTo("Live long and prosper")))
		).where(
			"string2", is(aJsonString(equalTo("Hypermedia")))
		).build();

		assertThat(jsonObject, is(aJsonObjectWith(conditions)));
	}

	@Test
	public void testMediaTypeIsCorrect() {
		String mediaType = _singleModelMessageMapper.getMediaType();

		assertThat(mediaType, is(equalTo("application/ld+json")));
	}

	private static Matcher<? extends JsonElement> _isALinkTo(String url) {
		return is(aJsonString(equalTo(url)));
	}

	@SuppressWarnings("unchecked")
	private static Matcher<Iterable<? extends JsonElement>> _containsTheTypes =
		contains(
			aJsonString(equalTo("Type 1")), aJsonString(equalTo("Type 2")));

	private static final Matcher<JsonElement> _isAJsonArrayWithTheType = is(
		aJsonArrayThat(contains(aJsonString(equalTo("Type")))));
	private static final Matcher<JsonElement> _isAJsonObjectWithTheContext;
	private static final Matcher<JsonElement>
		_isAJsonObjectWithTheFirstEmbedded;
	private static final Matcher<JsonElement> _isATypeIdContext = is(
		aJsonObjectWhere("@type", is(aJsonString(equalTo("@id")))));

	static {
		Conditions.Builder builder = new Conditions.Builder();

		Conditions secondEmbeddedContextConditions = builder.where(
			"embedded", _isATypeIdContext
		).where(
			"linked", _isATypeIdContext
		).where(
			"relatedCollection", _isATypeIdContext
		).build();

		Conditions secondEmbeddedConditions = builder.where(
			"@context", is(aJsonObjectWith(secondEmbeddedContextConditions))
		).where(
			"@id", _isALinkTo("localhost/p/second-inner-model/first")
		).where(
			"@type", _isAJsonArrayWithTheType
		).where(
			"boolean", is(aJsonBoolean(false))
		).where(
			"binary", _isALinkTo("localhost/b/second-inner-model/first/binary")
		).where(
			"embedded", _isALinkTo("localhost/p/third-inner-model/first")
		).where(
			"link", _isALinkTo("community.liferay.com")
		).where(
			"number", is(aJsonInt(equalTo(2017)))
		).where(
			"relatedCollection",
			_isALinkTo("localhost/p/second-inner-model/first/models")
		).where(
			"string", is(aJsonString(equalTo("A string")))
		).where(
			"linked", _isALinkTo("localhost/p/third-inner-model/second")
		).build();

		Conditions firstEmbeddedContextConditions = builder.where(
			"linked", _isATypeIdContext
		).where(
			"relatedCollection", _isATypeIdContext
		).build();

		Conditions firstEmbeddedConditions = builder.where(
			"@context", is(aJsonObjectWith(firstEmbeddedContextConditions))
		).where(
			"@id", _isALinkTo("localhost/p/first-inner-model/first")
		).where(
			"@type", _isAJsonArrayWithTheType
		).where(
			"binary", _isALinkTo("localhost/b/first-inner-model/first/binary")
		).where(
			"boolean", is(aJsonBoolean(true))
		).where(
			"link", _isALinkTo("www.liferay.com")
		).where(
			"number", is(aJsonInt(equalTo(42)))
		).where(
			"embedded", is(aJsonObjectWith(secondEmbeddedConditions))
		).where(
			"linked", _isALinkTo("localhost/p/second-inner-model/second")
		).where(
			"relatedCollection",
			_isALinkTo("localhost/p/first-inner-model/first/models")
		).where(
			"string", is(aJsonString(equalTo("A string")))
		).build();

		_isAJsonObjectWithTheFirstEmbedded = is(
			aJsonObjectWith(firstEmbeddedConditions));

		Conditions contextConditions = builder.where(
			"@vocab", is(aJsonString(equalTo("http://schema.org")))
		).where(
			"embedded2", _isATypeIdContext
		).where(
			"linked1", _isATypeIdContext
		).where(
			"linked2", _isATypeIdContext
		).where(
			"relatedCollection1", _isATypeIdContext
		).where(
			"relatedCollection2", _isATypeIdContext
		).build();

		_isAJsonObjectWithTheContext = is(aJsonObjectWith(contextConditions));
	}

	private final JSONLDSingleModelMessageMapper<RootModel>
		_singleModelMessageMapper = new JSONLDSingleModelMessageMapper<>();

}