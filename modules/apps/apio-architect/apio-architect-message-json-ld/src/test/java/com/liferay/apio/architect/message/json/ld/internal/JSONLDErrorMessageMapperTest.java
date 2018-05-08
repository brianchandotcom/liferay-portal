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

import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonInt;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectStringWith;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.test.util.json.Conditions;
import com.liferay.apio.architect.writer.ErrorWriter;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class JSONLDErrorMessageMapperTest {

	@Test
	public void testJSONLDErrorMessageMapper() {
		APIError apiError = new APIError(
			new IllegalArgumentException(), "A title", "A description",
			"A type", 404);

		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		String error = ErrorWriter.writeError(
			_errorMessageMapper, apiError, httpHeaders);

		Conditions.Builder builder = new Conditions.Builder();

		Conditions conditions = builder.where(
			"@type", is(aJsonString(equalTo("A type")))
		).where(
			"description", is(aJsonString(equalTo("A description")))
		).where(
			"statusCode", is(aJsonInt(equalTo(404)))
		).where(
			"title", is(aJsonString(equalTo("A title")))
		).build();

		assertThat(error, is(aJsonObjectStringWith(conditions)));
	}

	@Test
	public void testMediaTypeIsCorrect() {
		String mediaType = _errorMessageMapper.getMediaType();

		assertThat(mediaType, is("application/ld+json"));
	}

	private final ErrorMessageMapper _errorMessageMapper =
		new JSONLDErrorMessageMapper();

}