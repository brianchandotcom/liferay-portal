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

package com.liferay.apio.architect.error;

import aQute.bnd.annotation.ProviderType;

import java.util.Optional;

/**
 * Describes an API error. Instances of {@code
 * javax.ws.rs.WebApplicationException} and its descendants are converted to
 * {@code APIError} . All other exceptions are converted to a {@code 500} error
 * with a standard message.
 *
 * @author Alejandro Hernández
 */
@ProviderType
public class APIError {

	public APIError(
		Exception exception, String title, String type, int statusCode) {

		_exception = exception;
		_title = title;
		_type = type;
		_statusCode = statusCode;
	}

	public APIError(
		Exception exception, String title, String description, String type,
		int statusCode) {

		_exception = exception;
		_title = title;
		_description = description;
		_type = type;
		_statusCode = statusCode;
	}

	/**
	 * Returns the API error's description, if present; {@code Optional#empty()}
	 * otherwise.
	 *
	 * @return the API error's description, if present; {@code Optional#empty()}
	 *         otherwise
	 */
	public Optional<String> getDescription() {
		return Optional.ofNullable(_description);
	}

	/**
	 * Returns the API error's original exception.
	 *
	 * @return the API error's original exception
	 */
	public Exception getException() {
		return _exception;
	}

	/**
	 * Returns the API error's HTTP status code.
	 *
	 * @return the API error's HTTP status code
	 */
	public int getStatusCode() {
		return _statusCode;
	}

	/**
	 * Returns the API error's title. This value is the same for all API errors
	 * of the same type.
	 *
	 * @return the API error's title
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * Returns the API error's type. Note that this is different from the API
	 * error's exception. For example, if the API error's exception is {@code
	 * NotAuthorizedException}, an implementation of this method could return
	 * {@code "not-authorized"}.
	 *
	 * @return the API error's type
	 */
	public String getType() {
		return _type;
	}

	private String _description;
	private final Exception _exception;
	private final int _statusCode;
	private final String _title;
	private final String _type;

}