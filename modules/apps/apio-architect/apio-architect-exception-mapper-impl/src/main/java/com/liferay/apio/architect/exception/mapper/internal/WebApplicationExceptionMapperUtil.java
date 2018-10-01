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

import java.util.function.Predicate;

import javax.ws.rs.core.Response.StatusType;

/**
 * Provides utility functions for {@code WebApplicationException} mappers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 * @review
 */
public class WebApplicationExceptionMapperUtil {

	/**
	 * Returns {@code true} if a {@code message} is equals to the default
	 * message for the {@code statusType}. Returns {@<code>false</code>}
	 * otherwise.
	 *
	 * @param  statusType the status type
	 * @return {@code true} if the message is equals to the status type default
	 *         message; {@code false} otherwise
	 * @review
	 */
	public static Predicate<String> isNotDefaultMessage(StatusType statusType) {
		return anObject -> !_getDefaultMessage(statusType).equals(anObject);
	}

	private static String _getDefaultMessage(StatusType statusType) {
		String statusCode = String.valueOf(statusType.getStatusCode());

		return String.join(
			" ", "HTTP", statusCode, statusType.getReasonPhrase());
	}

	private WebApplicationExceptionMapperUtil() {
		throw new UnsupportedOperationException();
	}

}