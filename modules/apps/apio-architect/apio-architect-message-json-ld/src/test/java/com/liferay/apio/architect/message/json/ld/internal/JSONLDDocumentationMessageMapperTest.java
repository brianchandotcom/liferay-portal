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

import static com.liferay.apio.architect.message.json.ld.internal.JSONLDTestUtil.IS_A_LINK_TO_HYDRA_PROFILE;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectWith;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.google.gson.JsonObject;

import com.liferay.apio.architect.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.test.util.json.Conditions;
import com.liferay.apio.architect.test.util.writer.MockDocumentationWriter;

import javax.ws.rs.core.HttpHeaders;

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

		Conditions conditions = builder.where(
			"@context", IS_A_LINK_TO_HYDRA_PROFILE
		).where(
			"@id", is(aJsonString(equalTo("http://api.example.com/doc/")))
		).where(
			"@type", is(aJsonString(equalTo("ApiDocumentation")))
		).where(
			"description", is(aJsonString(equalTo("Description")))
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

	private final DocumentationMessageMapper _documentationMessageMapper =
		new JSONLDDocumentationMessageMapper();

}