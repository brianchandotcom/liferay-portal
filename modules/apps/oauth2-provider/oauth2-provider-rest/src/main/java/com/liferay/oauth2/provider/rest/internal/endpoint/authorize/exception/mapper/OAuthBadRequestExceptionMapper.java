/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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