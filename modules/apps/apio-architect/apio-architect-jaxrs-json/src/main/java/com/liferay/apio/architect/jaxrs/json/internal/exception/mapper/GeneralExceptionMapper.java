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

package com.liferay.apio.architect.jaxrs.json.internal.exception.mapper;

import com.liferay.apio.architect.jaxrs.json.internal.util.ErrorUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Captures and converts an exception to its corresponding {@link Response}.
 *
 * @author Alejandro Hernández
 */
@Component(
	immediate = true, property = "liferay.apio.architect.exception.mapper=true"
)
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		return _errorUtil.getErrorResponse(exception, _request, _httpHeaders);
	}

	@Reference
	private ErrorUtil _errorUtil;

	@Context
	private HttpHeaders _httpHeaders;

	@Context
	private Request _request;

}