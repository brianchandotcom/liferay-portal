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

package com.liferay.oauth2.provider.rest.internal.endpoint.authorize.exception.mapper;

import com.liferay.petra.string.StringBundler;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=OAuthBadRequestExceptionMapper"
	},
	service = ExceptionMapper.class
)
@Produces("text/html")
@Provider
public class OAuthBadRequestExceptionMapper
	implements ExceptionMapper<BadRequestException> {

	@Override
	public Response toResponse(BadRequestException badRequestException) {
		return Response.status(
			Response.Status.BAD_REQUEST
		).entity(
			StringBundler.concat(
				"<html><body>", badRequestException.getMessage(),
				"</body></html>")
		).build();
	}

}