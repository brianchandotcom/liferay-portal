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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.liferay.apio.architect.error.APIError;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class FallbackExceptionMapperTest {

	@Test
	public void testConvertReturnsCorrectAPIErrorWithExceptionMessage() {
		FallbackExceptionMapper exceptionMapper = new FallbackExceptionMapper();

		Exception exception = new Exception("Message");

		APIError apiError = exceptionMapper.map(exception);

		assertThat(apiError.getDescription(), emptyOptional());
		assertThat(apiError.getException(), is(exception));
		assertThat(apiError.getMessage(), is("Message"));
		assertThat(apiError.getStatusCode(), is(500));
		assertThat(apiError.getTitle(), is("Internal Server Error"));
		assertThat(apiError.getType(), is("server-error"));
	}

	@Test
	public void testConvertReturnsCorrectAPIErrorWithoutExceptionMessage() {
		FallbackExceptionMapper exceptionMapper = new FallbackExceptionMapper();

		Exception exception = new Exception();

		APIError apiError = exceptionMapper.map(exception);

		assertThat(apiError.getDescription(), is(emptyOptional()));
		assertThat(apiError.getException(), is(exception));
		assertThat(apiError.getMessage(), is("java.lang.Exception"));
		assertThat(apiError.getStatusCode(), is(500));
		assertThat(apiError.getTitle(), is("Internal Server Error"));
		assertThat(apiError.getType(), is("server-error"));
	}

}