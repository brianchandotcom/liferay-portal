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

package com.liferay.apio.architect.exception.mapper.internal;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.exception.mapper.ExceptionMapper;

import javax.ws.rs.NotAcceptableException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class NotAcceptableExceptionMapperTest {

	@Test
	public void testConvertReturnsCorrectAPIErrorWithExceptionMessage() {
		ExceptionMapper<NotAcceptableException> exceptionMapper =
			new NotAcceptableExceptionMapper();

		NotAcceptableException notAcceptableException =
			new NotAcceptableException("Message");

		APIError apiError = exceptionMapper.map(notAcceptableException);

		assertThat(
			apiError.getDescription(),
			is(optionalWithValue(equalTo("Message"))));
		assertThat(apiError.getException(), is(notAcceptableException));
		assertThat(apiError.getMessage(), is("Message"));
		assertThat(apiError.getStatusCode(), is(406));
		assertThat(apiError.getTitle(), is("Not Acceptable"));
		assertThat(apiError.getType(), is("not-acceptable"));
	}

	@Test
	public void testConvertReturnsCorrectAPIErrorWithoutExceptionMessage() {
		ExceptionMapper<NotAcceptableException> exceptionMapper =
			new NotAcceptableExceptionMapper();

		NotAcceptableException notAcceptableException =
			new NotAcceptableException();

		APIError apiError = exceptionMapper.map(notAcceptableException);

		assertThat(apiError.getDescription(), is(emptyOptional()));
		assertThat(apiError.getException(), is(notAcceptableException));
		assertThat(apiError.getMessage(), is("HTTP 406 Not Acceptable"));
		assertThat(apiError.getStatusCode(), is(406));
		assertThat(apiError.getTitle(), is("Not Acceptable"));
		assertThat(apiError.getType(), is("not-acceptable"));
	}

}