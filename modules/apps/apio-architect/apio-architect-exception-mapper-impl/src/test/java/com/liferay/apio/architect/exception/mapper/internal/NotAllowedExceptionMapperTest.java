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

import javax.ws.rs.NotAllowedException;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class NotAllowedExceptionMapperTest {

	@Test
	public void testConvertReturnsCorrectAPIErrorWithExceptionMessage() {
		ExceptionMapper<NotAllowedException> exceptionMapper =
			new NotAllowedExceptionMapper();

		NotAllowedException notAllowedException = new NotAllowedException(
			"Message", "POST", new String[0]);

		APIError apiError = exceptionMapper.map(notAllowedException);

		assertThat(
			apiError.getDescription(),
			is(optionalWithValue(equalTo("Message"))));
		assertThat(apiError.getException(), is(notAllowedException));
		assertThat(apiError.getMessage(), is("Message"));
		assertThat(apiError.getStatusCode(), is(405));
		assertThat(apiError.getTitle(), is("Method Not Allowed"));
		assertThat(apiError.getType(), is("not-allowed"));
	}

	@Test
	public void testConvertReturnsCorrectAPIErrorWithoutExceptionMessage() {
		ExceptionMapper<NotAllowedException> exceptionMapper =
			new NotAllowedExceptionMapper();

		NotAllowedException notAllowedException = new NotAllowedException(
			"POST");

		APIError apiError = exceptionMapper.map(notAllowedException);

		assertThat(apiError.getDescription(), is(emptyOptional()));
		assertThat(apiError.getException(), is(notAllowedException));
		assertThat(apiError.getMessage(), is("HTTP 405 Method Not Allowed"));
		assertThat(apiError.getStatusCode(), is(405));
		assertThat(apiError.getTitle(), is("Method Not Allowed"));
		assertThat(apiError.getType(), is("not-allowed"));
	}

}