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

package com.liferay.apio.architect.writer;

import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonBoolean;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonInt;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonObjectStringWith;
import static com.liferay.apio.architect.test.util.json.JsonMatchers.aJsonString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.test.util.json.Conditions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.Objects;

import javax.ws.rs.core.HttpHeaders;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class ErrorWriterTest {

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			ErrorWriter.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test
	public void testWriteErrorCreatesCorrectJsonObject() {
		ErrorMessageMapper errorMessageMapper = new TestErrorMessageMapper();

		APIError apiError = new APIError(
			new IllegalArgumentException(), "A title", "A description",
			"A type", 404);

		HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);

		Mockito.when(
			httpHeaders.getHeaderString("start")
		).thenReturn(
			"true"
		);

		Mockito.when(
			httpHeaders.getHeaderString("end")
		).thenReturn(
			"true"
		);

		String error = ErrorWriter.writeError(
			errorMessageMapper, apiError, httpHeaders);

		Conditions.Builder builder = new Conditions.Builder();

		Conditions conditions = builder.where(
			"description", is(aJsonString(equalTo("A description")))
		).where(
			"title", is(aJsonString(equalTo("A title")))
		).where(
			"type", is(aJsonString(equalTo("A type")))
		).where(
			"status", is(aJsonInt(equalTo(404)))
		).where(
			"start", is(aJsonBoolean(true))
		).where(
			"end", is(aJsonBoolean(true))
		).withStrictModeDeactivated(
		).build();

		assertThat(error, is(aJsonObjectStringWith(conditions)));
	}

	private static class TestErrorMessageMapper implements ErrorMessageMapper {

		@Override
		public String getMediaType() {
			return "mediaType";
		}

		@Override
		public void mapDescription(
			JSONObjectBuilder jsonObjectBuilder, String description) {

			jsonObjectBuilder.field(
				"description"
			).stringValue(
				description
			);
		}

		@Override
		public void mapStatusCode(
			JSONObjectBuilder jsonObjectBuilder, Integer statusCode) {

			jsonObjectBuilder.field(
				"status"
			).numberValue(
				statusCode
			);
		}

		@Override
		public void mapTitle(
			JSONObjectBuilder jsonObjectBuilder, String title) {

			jsonObjectBuilder.field(
				"title"
			).stringValue(
				title
			);
		}

		@Override
		public void mapType(JSONObjectBuilder jsonObjectBuilder, String type) {
			jsonObjectBuilder.field(
				"type"
			).stringValue(
				type
			);
		}

		@Override
		public void onFinish(
			JSONObjectBuilder jsonObjectBuilder, APIError apiError,
			HttpHeaders httpHeaders) {

			if (Objects.equals(httpHeaders.getHeaderString("end"), "true")) {
				jsonObjectBuilder.field(
					"end"
				).booleanValue(
					true
				);
			}
		}

		@Override
		public void onStart(
			JSONObjectBuilder jsonObjectBuilder, APIError apiError,
			HttpHeaders httpHeaders) {

			if (Objects.equals(httpHeaders.getHeaderString("start"), "true")) {
				jsonObjectBuilder.field(
					"start"
				).booleanValue(
					true
				);
			}
		}

	}

}