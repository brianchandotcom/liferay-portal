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

package com.liferay.commerce.data.integration.apio.internal.exceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 * @author Zoltán Takács
 */
public class UnprocessableEntityException extends ClientErrorException {

	public UnprocessableEntityException(int status) {
		super(status);
	}

	public UnprocessableEntityException(int status, Throwable cause) {
		super(status, cause);
	}

	public UnprocessableEntityException(Response response) {
		super(response);
	}

	public UnprocessableEntityException(Response response, Throwable cause) {
		super(response, cause);
	}

	public UnprocessableEntityException(String message, int status) {
		super(message, status);
	}

	public UnprocessableEntityException(
		String message, int status, Throwable cause) {

		super(message, status, cause);
	}

	public UnprocessableEntityException(String message, Response response) {
		super(message, response);
	}

	public UnprocessableEntityException(
		String message, Response response, Throwable cause) {

		super(message, response, cause);
	}

}