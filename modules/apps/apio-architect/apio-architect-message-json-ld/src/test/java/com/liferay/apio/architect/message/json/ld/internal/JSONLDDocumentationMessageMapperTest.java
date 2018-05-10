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

import static com.liferay.apio.architect.message.json.ld.internal.JSONLDTestUtil.isALinkTo;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonArrayThat;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWith;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.test.util.json.Conditions;
import com.liferay.apio.architect.test.util.writer.MockDocumentationWriter;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.hamcrest.Matcher;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class JSONLDDocumentationMessageMapperTest {

	@Test
	public void testJSONLDDocumentationMessageMapper() {
		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		JsonObject jsonObject = MockDocumentationWriter.write(
			httpHeaders, _documentationMessageMapper);

		Conditions.Builder builder = new Conditions.Builder();

		Conditions collectionsConditions = _getOperationConditions(
			builder, _getMatchersCollection());

		Conditions itemConditions = _getOperationConditions(
			builder, _getMatchersItem());

		List<Matcher<? super JsonElement>> supportedClassConditions =
			Arrays.asList(collectionsConditions, itemConditions);

		Conditions contextConditions = _getContextConditions();

		Conditions conditions = builder.where(
			"@context", is(aJsonObjectWith(contextConditions))
		).where(
			"@id", is(aJsonString(equalTo("http://api.example.com/doc/")))
		).where(
			"@type", is(aJsonString(equalTo("ApiDocumentation")))
		).where(
			"description", is(aJsonString(equalTo("Description")))
		).where(
			"supportedClass",
			is(aJsonArrayThat(contains(supportedClassConditions)))
		).where(
			"title", is(aJsonString(equalTo("Title")))
		).build();

		assertThat(jsonObject, is(aJsonObjectWith(conditions)));
	}

	@Test
	public void testMediaTypeIsCorrect() {
		String mediaType = _documentationMessageMapper.getMediaType();

		assertThat(mediaType, is("application/ld+json"));
	}

	private Conditions _getContextConditions() {
		Conditions.Builder builder = new Conditions.Builder();

		Conditions property = _getTypedObjectConditions(builder, "property");

		Conditions expect = _getTypedObjectConditions(builder, "hydra:expect");

		Conditions returns = _getTypedObjectConditions(
			builder, "hydra:returns");

		return builder.where(
			"hydra", is(isALinkTo("https://www.w3.org/ns/hydra/core#"))
		).where(
			"ApiDocumentation",
			is(aJsonString(equalTo("hydra:ApiDocumentation")))
		).where(
			"code", is(aJsonString(equalTo("hydra:statusCodes")))
		).where(
			"expect", is(aJsonObjectWith(expect))
		).where(
			"method", is(aJsonString(equalTo("hydra:method")))
		).where(
			"property", is(aJsonObjectWith(property))
		).where(
			"readonly", is(aJsonString(equalTo("hydra:readonly")))
		).where(
			"returns", is(aJsonObjectWith(returns))
		).where(
			"supportedClass", is(aJsonString(equalTo("hydra:supportedClass")))
		).where(
			"supportedProperty",
			is(aJsonString(equalTo("hydra:supportedProperty")))
		).where(
			"supportedOperation",
			is(aJsonString(equalTo("hydra:supportedOperation")))
		).where(
			"statusCodes", is(aJsonString(equalTo("hydra:statusCodes")))
		).where(
			"writeonly", is(aJsonString(equalTo("hydra:writeonly")))
		).build();
	}

	private Conditions _getMatcherOperation(
		String id, String method, String returns) {

		Conditions.Builder builder = new Conditions.Builder();

		return builder.where(
			"@id", is(aJsonString(equalTo(id)))
		).where(
			"@type", is(aJsonString(equalTo("Operation")))
		).where(
			"method", is(aJsonString(equalTo(method)))
		).where(
			"returns", isALinkTo(returns)
		).build();
	}

	private Matcher[] _getMatchersCollection() {
		return new Matcher[] {
			_getMatcherOperation(
				"_:root", "GET", "https://www.w3.org/ns/hydra/core#Collection"),
			_getMatcherOperation(
				"_:root/create", "POST", "http://schema.org/root")
		};
	}

	private Matcher[] _getMatchersItem() {
		return new Matcher[] {
			_getMatcherOperation(
				"_:root/retrieve", "GET",
				"https://www.w3.org/ns/hydra/core#Collection"),
			_getMatcherOperation(
				"_:root/update", "PUT", "http://schema.org/root"),
			_getMatcherOperation(
				"_:root/delete", "DELETE",
				"http://www.w3.org/2002/07/owl#Nothing")
		};
	}

	private Conditions _getOperationConditions(
		Conditions.Builder builder, Matcher[] matchers) {

		return builder.where(
			"@id", isALinkTo("http://schema.org/root")
		).where(
			"@type", aJsonString(equalTo("Class"))
		).where(
			"supportedOperation", is(aJsonArrayThat(contains(matchers)))
		).where(
			"title", aJsonString(equalTo("root"))
		).build();
	}

	private Conditions _getTypedObjectConditions(
		Conditions.Builder builder, String type) {

		return builder.where(
			"@id", is(aJsonString(equalTo(type)))
		).where(
			"@type", is(aJsonString(equalTo("@id")))
		).build();
	}

	private final DocumentationMessageMapper _documentationMessageMapper =
		new JSONLDDocumentationMessageMapper();

}