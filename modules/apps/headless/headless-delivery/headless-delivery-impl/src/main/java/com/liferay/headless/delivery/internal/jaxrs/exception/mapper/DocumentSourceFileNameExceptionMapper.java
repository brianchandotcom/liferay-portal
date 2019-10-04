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

package com.liferay.headless.delivery.internal.jaxrs.exception.mapper;

import com.liferay.document.library.kernel.exception.SourceFileNameException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code SourceFileNameException} to a {@code 400} error.
 *
 * @author Alejandro Hernández
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Delivery)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Delivery.DocumentSourceFileNameExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DocumentSourceFileNameExceptionMapper
	implements ExceptionMapper<SourceFileNameException> {

	@Override
	public Response toResponse(
		SourceFileNameException sourceFileNameException) {

		return Response.status(
			400
		).entity(
			sourceFileNameException.getMessage()
		).type(
			MediaType.TEXT_PLAIN
		).build();
	}

}