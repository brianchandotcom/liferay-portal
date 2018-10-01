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

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.exception.mapper.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code Exception} to its {@link APIError} representation.
 *
 * @author Javier Gamarra
 */
@Component(service = ExceptionMapper.class)
public class FallbackExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public APIError map(Exception exception) {
		return new APIError(
			exception, INTERNAL_SERVER_ERROR.getReasonPhrase(), "server-error",
			INTERNAL_SERVER_ERROR.getStatusCode());
	}

}