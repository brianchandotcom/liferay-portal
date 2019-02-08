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

package com.liferay.headless.collaboration.internal.exception.mapper;

import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * Converts any {@code DuplicateFriendlyURLEntryException} to a {@code 422}
 * error.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-collaboration-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_EXTENSION + "=true",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Vulcan.DuplicateFriendlyURLEntryExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DuplicateFriendlyURLEntryExceptionMapper
	implements ExceptionMapper<DuplicateFriendlyURLEntryException> {

	@Override
	public Response toResponse(DuplicateFriendlyURLEntryException dfurlee) {
		return Response.status(
			422
		).type(
			MediaType.TEXT_PLAIN
		).entity(
			"Duplicate friendly URL"
		).build();
	}

}