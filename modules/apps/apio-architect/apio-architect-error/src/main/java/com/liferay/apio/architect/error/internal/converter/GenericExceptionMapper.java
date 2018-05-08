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

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import com.liferay.apio.architect.converter.ExceptionMapper;
import com.liferay.apio.architect.error.APIError;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any exception to its {@link APIError} representation.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public APIError map(Exception exception) {
		return new APIError(
			exception, "General server error", "server-error",
			INTERNAL_SERVER_ERROR.getStatusCode());
	}

}