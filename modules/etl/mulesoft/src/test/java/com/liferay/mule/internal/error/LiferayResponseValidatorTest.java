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

package com.liferay.mule.internal.error;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
public class LiferayResponseValidatorTest {

	@Before
	public void setUp() {
		_liferayResponseValidator = new LiferayResponseValidator();
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_NoResponse() throws Exception {
		_assertThatResponseValidationProducesLiferayError(
			null, LiferayError.SERVER_ERROR);
	}

	@Test
	public void testValidateMessage_Status200() throws Exception {
		HttpConstants.HttpStatus httpStatus = HttpConstants.HttpStatus.OK;

		_liferayResponseValidator.validate(
			_getHttpResponse(
				httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status400() throws Exception {
		_assertThatResponseValidationProducesLiferayError(
			HttpConstants.HttpStatus.BAD_REQUEST, LiferayError.BAD_REQUEST);
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status404() throws Exception {
		_assertThatResponseValidationProducesLiferayError(
			HttpConstants.HttpStatus.NOT_FOUND, LiferayError.NOT_FOUND);
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status405() throws Exception {
		_assertThatResponseValidationProducesLiferayError(
			HttpConstants.HttpStatus.METHOD_NOT_ALLOWED,
			LiferayError.NOT_ALLOWED);
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status406() throws Exception {
		_assertThatResponseValidationProducesLiferayError(
			HttpConstants.HttpStatus.NOT_ACCEPTABLE,
			LiferayError.NOT_ACCEPTABLE);
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status415() throws Exception {
		_assertThatResponseValidationProducesLiferayError(
			HttpConstants.HttpStatus.UNSUPPORTED_MEDIA_TYPE,
			LiferayError.UNSUPPORTED_MEDIA_TYPE);
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status500() throws Exception {
		_assertThatResponseValidationProducesLiferayError(
			HttpConstants.HttpStatus.INTERNAL_SERVER_ERROR,
			LiferayError.SERVER_ERROR);
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status501() throws Exception {
		_assertThatResponseValidationProducesLiferayError(
			HttpConstants.HttpStatus.NOT_IMPLEMENTED,
			LiferayError.NOT_IMPLEMENTED);
	}

	private void _assertThatResponseValidationProducesLiferayError(
			HttpConstants.HttpStatus httpStatus, LiferayError liferayError)
		throws IOException {

		try {
			if (httpStatus != null) {
				_liferayResponseValidator.validate(
					_getHttpResponse(
						httpStatus.getReasonPhrase(),
						httpStatus.getStatusCode()));
			}
			else {
				_liferayResponseValidator.validate(null);
			}

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(liferayError, me.getType());

			throw me;
		}
	}

	private HttpResponse _getHttpResponse(String reasonPhrase, int status) {
		return HttpResponse.builder(
		).statusCode(
			status
		).reasonPhrase(
			reasonPhrase
		).build();
	}

	private LiferayResponseValidator _liferayResponseValidator;

}