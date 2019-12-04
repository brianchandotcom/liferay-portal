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
		try {
			_liferayResponseValidator.validate(null);

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(LiferayError.SERVER_ERROR, me.getType());

			throw me;
		}
	}

	@Test(expected = Test.None.class)
	public void testValidateMessage_Status200() throws Exception {
		HttpConstants.HttpStatus httpStatus = HttpConstants.HttpStatus.OK;

		_liferayResponseValidator.validate(
			_getHttpResponse(
				httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status400() throws Exception {
		HttpConstants.HttpStatus httpStatus =
			HttpConstants.HttpStatus.BAD_REQUEST;

		try {
			_liferayResponseValidator.validate(
				_getHttpResponse(
					httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(LiferayError.BAD_REQUEST, me.getType());

			throw me;
		}
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status404() throws Exception {
		HttpConstants.HttpStatus httpStatus =
			HttpConstants.HttpStatus.NOT_FOUND;

		try {
			_liferayResponseValidator.validate(
				_getHttpResponse(
					httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(LiferayError.NOT_FOUND, me.getType());

			throw me;
		}
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status405() throws Exception {
		HttpConstants.HttpStatus httpStatus =
			HttpConstants.HttpStatus.METHOD_NOT_ALLOWED;

		try {
			_liferayResponseValidator.validate(
				_getHttpResponse(
					httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(LiferayError.NOT_ALLOWED, me.getType());

			throw me;
		}
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status406() throws Exception {
		HttpConstants.HttpStatus httpStatus =
			HttpConstants.HttpStatus.NOT_ACCEPTABLE;

		try {
			_liferayResponseValidator.validate(
				_getHttpResponse(
					httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(LiferayError.NOT_ACCEPTABLE, me.getType());

			throw me;
		}
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status415() throws Exception {
		HttpConstants.HttpStatus httpStatus =
			HttpConstants.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

		try {
			_liferayResponseValidator.validate(
				_getHttpResponse(
					httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(
				LiferayError.UNSUPPORTED_MEDIA_TYPE, me.getType());

			throw me;
		}
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status500() throws Exception {
		HttpConstants.HttpStatus httpStatus =
			HttpConstants.HttpStatus.INTERNAL_SERVER_ERROR;

		try {
			_liferayResponseValidator.validate(
				_getHttpResponse(
					httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(LiferayError.SERVER_ERROR, me.getType());

			throw me;
		}
	}

	@Test(expected = ModuleException.class)
	public void testValidateMessage_Status501() throws Exception {
		HttpConstants.HttpStatus httpStatus =
			HttpConstants.HttpStatus.NOT_IMPLEMENTED;

		try {
			_liferayResponseValidator.validate(
				_getHttpResponse(
					httpStatus.getReasonPhrase(), httpStatus.getStatusCode()));

			Assert.fail();
		}
		catch (ModuleException me) {
			Assert.assertEquals(LiferayError.NOT_IMPLEMENTED, me.getType());

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