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

package com.liferay.vulcan.error.internal.converter;

import com.liferay.vulcan.result.APIError;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Converts a {@link WebApplicationException} into its {@link APIError}
 * representation.
 *
 * @author Alejandro Hernández
 * @review
 */
public abstract class WebApplicationExceptionConverter {

	/**
	 * Converts a {@code WebApplicationException} to its {@code APIError}
	 * representation.
	 *
	 * @param  exception the {@code WebApplicationException} to be converted.
	 * @return the {@code APIError} representation for the exception.
	 * @review
	 */
	protected APIError convert(WebApplicationException exception) {
		String description = _getDescription(exception.getMessage());

		return new APIErrorImpl(
			exception, getTitle(), description, getType(),
			getStatusType().getStatusCode());
	}

	/**
	 * Returns the status type for this {@code WebApplicationException} class.
	 *
	 * @return the status type for the exception class.
	 * @review
	 */
	protected abstract Response.StatusType getStatusType();

	/**
	 * Returns the title for this {@code WebApplicationException} class.
	 *
	 * @return the title for the exception class.
	 * @review
	 */
	protected abstract String getTitle();

	/**
	 * Returns the type for this {@code WebApplicationException} class.
	 *
	 * @return the type for the exception class.
	 * @review
	 */
	protected abstract String getType();

	private String _getDescription(String message) {
		Response.StatusType statusType = getStatusType();

		String defaultMessage =
			"HTTP " + statusType.getStatusCode() + ' ' +
				statusType.getReasonPhrase();

		if (defaultMessage.equals(message)) {
			return null;
		}

		return message;
	}

}