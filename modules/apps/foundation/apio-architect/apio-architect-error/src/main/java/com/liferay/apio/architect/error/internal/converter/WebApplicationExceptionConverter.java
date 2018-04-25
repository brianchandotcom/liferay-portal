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

package com.liferay.apio.architect.error.internal.converter;

import com.liferay.apio.architect.error.APIError;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Converts a {@code WebApplicationException} to its {@link APIError}
 * representation.
 *
 * @author Alejandro Hernández
 */
public abstract class WebApplicationExceptionConverter {

	/**
	 * Converts a {@code WebApplicationException} to its {@code APIError}
	 * representation.
	 *
	 * @param  exception the {@code WebApplicationException} to map
	 * @return the exception's {@code APIError} representation
	 */
	protected APIError convert(WebApplicationException exception) {
		String description = _getDescription(exception.getMessage());

		return new APIError(
			exception, getTitle(), description, getType(),
			getStatusType().getStatusCode());
	}

	/**
	 * Returns the current {@code WebApplicationException} instance's status
	 * type.
	 *
	 * @return the exception's status type
	 */
	protected abstract Response.StatusType getStatusType();

	/**
	 * Returns the current {@code WebApplicationException} instance's title.
	 *
	 * @return the exception's title
	 */
	protected abstract String getTitle();

	/**
	 * Returns the current {@code WebApplicationException} instance's type.
	 *
	 * @return the exception's type
	 */
	protected abstract String getType();

	private String _getDescription(String message) {
		Response.StatusType statusType = getStatusType();

		String statusCode = String.valueOf(statusType.getStatusCode());

		String defaultMessage = String.join(
			" ", "HTTP", statusCode, statusType.getReasonPhrase());

		if (defaultMessage.equals(message)) {
			return null;
		}

		return message;
	}

}