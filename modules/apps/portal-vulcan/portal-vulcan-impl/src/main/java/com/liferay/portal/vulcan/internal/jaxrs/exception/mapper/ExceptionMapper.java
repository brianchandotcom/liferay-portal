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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Converts any {@code DuplicateFriendlyURLEntryException} to a {@code 422}
 * error.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-collaboration-application)",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Vulcan.ExceptionMapper"
	},
	service = javax.ws.rs.ext.ExceptionMapper.class
)
public class ExceptionMapper
	implements
	javax.ws.rs.ext.ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		return Response.status(
			500
		).type(
			MediaType.TEXT_PLAIN
		).entity(
			e.getMessage()
		).build();
	}

}