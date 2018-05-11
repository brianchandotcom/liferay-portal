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
public class ConflictException extends ClientErrorException {

	public ConflictException(int status) {
		super(status);
	}

	public ConflictException(Response.Status status) {
		super(status);
	}

	public ConflictException(Response.Status status, Throwable cause) {
		super(status, cause);
	}

	public ConflictException(String message, int status, Throwable cause) {
		super(message, status, cause);
	}

	public ConflictException(String message, Response.Status status) {
		super(message, status);
	}

	public ConflictException(
		String message, Response.Status status, Throwable cause) {

		super(message, status, cause);
	}

}