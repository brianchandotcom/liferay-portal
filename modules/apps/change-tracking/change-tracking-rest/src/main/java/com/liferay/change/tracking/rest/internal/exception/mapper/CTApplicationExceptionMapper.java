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

package com.liferay.change.tracking.rest.internal.exception.mapper;

import com.liferay.change.tracking.rest.internal.model.GenericErrorModel;
import com.liferay.portal.kernel.exception.SystemException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=Liferay.ChangeTracking.Rest)",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true"
	},
	service = ExceptionMapper.class
)
public class CTApplicationExceptionMapper
	implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		Response.Status status = Response.Status.BAD_REQUEST;

		if (exception instanceof SystemException) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
		}

		return Response.status(
			status
		).entity(
			new GenericErrorModel(exception.getMessage())
		).build();
	}

}