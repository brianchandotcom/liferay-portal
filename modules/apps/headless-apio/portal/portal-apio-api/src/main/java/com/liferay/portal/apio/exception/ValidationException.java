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

package com.liferay.portal.apio.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 * If this exception is thrown it means that the server understands the content
 * type of the request entity and the syntax of the request entity is correct
 * but the request's context is semantically incorrect.
 *
 * <p>This exception is represented as a {@code 422} HTTP error.
 *
 * @author Sarai Díaz
 * @review
 */
public class ValidationException extends ClientErrorException {

	/**
	 * Creates a {@code 422} exception with the given message.
	 *
	 * @param message exception message.
	 * @review
	 */
	public ValidationException(String message) {
		super(
			message,
			Response.status(
				422, "Unprocessable Entity"
			).build());
	}

	/**
	 * Creates a {@code 422} exception with the given message and cause.
	 *
	 * @param message exception message.
	 * @param cause
	 * @review
	 */
	public ValidationException(String message, Throwable cause) {
		super(
			message,
			Response.status(
				422, "Unprocessable Entity"
			).build(),
			cause);
	}

}