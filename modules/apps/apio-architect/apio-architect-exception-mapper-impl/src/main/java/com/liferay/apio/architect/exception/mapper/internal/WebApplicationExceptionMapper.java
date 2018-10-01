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

import static com.liferay.apio.architect.exception.mapper.internal.WebApplicationExceptionMapperUtil.isNotDefaultMessage;

import com.liferay.apio.architect.error.APIError;

import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.StatusType;

/**
 * Converts a {@code WebApplicationException} to its {@link APIError}
 * representation.
 *
 * @author Alejandro Hernández
 */
public abstract class WebApplicationExceptionMapper {

	/**
	 * Converts a {@code WebApplicationException} to its {@code APIError}
	 * representation.
	 *
	 * @param  exception the {@code WebApplicationException} to map
	 * @return the exception's {@code APIError} representation
	 */
	protected APIError convert(WebApplicationException exception) {
		String description = Optional.ofNullable(
			exception.getMessage()
		).filter(
			isNotDefaultMessage(getStatusType())
		).orElse(
			null
		);

		StatusType statusType = getStatusType();

		return new APIError(
			exception, statusType.getReasonPhrase(), description, getType(),
			statusType.getStatusCode());
	}

	/**
	 * Returns the current {@code WebApplicationException} instance's status
	 * type.
	 *
	 * @return the exception's status type
	 */
	protected abstract StatusType getStatusType();

	/**
	 * Returns the current {@code WebApplicationException} instance's type.
	 *
	 * @return the exception's type
	 */
	protected abstract String getType();

}