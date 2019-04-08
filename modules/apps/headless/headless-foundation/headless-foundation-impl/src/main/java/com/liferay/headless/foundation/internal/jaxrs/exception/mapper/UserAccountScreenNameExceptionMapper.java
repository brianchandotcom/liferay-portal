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

package com.liferay.headless.foundation.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.exception.UserScreenNameException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code UserScreenNameException} to a {@code 400} error or to a
 * {@code 409} if the screen name is duplicated
 *
 * @author Víctor Galán
 * @review
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Foundation)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Headless.Foundation.UserAccountScreenNameExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class UserAccountScreenNameExceptionMapper
	implements ExceptionMapper<UserScreenNameException> {

	@Override
	public Response toResponse(UserScreenNameException usne) {
		int statusCode = 400;

		if (usne instanceof UserScreenNameException.MustNotBeDuplicate) {
			statusCode = 409;
		}

		return Response.status(
			statusCode
		).type(
			MediaType.TEXT_PLAIN
		).entity(
			usne.getMessage()
		).build();
	}

}