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

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.servlet.HttpMethods;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Converts any {@code NoSuchModelException} to a {@code 404} error.
 *
 * <p>In the case of a DELETE request a {@code 204} (NO-CONTENT) response is
 * returned so the request is idempotent.
 *
 * @author Alejandro Hernández
 * @review
 */
public class NoSuchModelExceptionMapper
	implements ExceptionMapper<NoSuchModelException> {

	@Override
	public Response toResponse(NoSuchModelException noSuchModelException) {
		String method = _httpServletRequest.getMethod();

		if (method.equals(HttpMethods.DELETE)) {
			return Response.status(
				Response.Status.NO_CONTENT
			).build();
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).entity(
			noSuchModelException.getMessage()
		).type(
			MediaType.TEXT_PLAIN
		).build();
	}

	@Context
	private HttpServletRequest _httpServletRequest;

}