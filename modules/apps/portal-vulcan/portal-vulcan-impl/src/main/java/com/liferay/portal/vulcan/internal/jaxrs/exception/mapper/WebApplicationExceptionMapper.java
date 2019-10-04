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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Converts any {@code WebApplicationException} to a {@code 500} error.
 *
 * @author Alejandro Hernández
 * @review
 */
public class WebApplicationExceptionMapper
	implements ExceptionMapper<WebApplicationException> {

	@Override
	public Response toResponse(
		WebApplicationException webApplicationException) {

		_log.error(webApplicationException, webApplicationException);

		Response response = webApplicationException.getResponse();

		return Response.status(
			response.getStatus()
		).entity(
			webApplicationException.getMessage()
		).type(
			MediaType.TEXT_PLAIN
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebApplicationExceptionMapper.class);

}