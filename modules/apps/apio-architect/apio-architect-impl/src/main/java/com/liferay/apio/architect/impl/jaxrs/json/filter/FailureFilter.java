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

package com.liferay.apio.architect.impl.jaxrs.json.filter;

import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.functional.Try.Failure;
import com.liferay.apio.architect.impl.jaxrs.json.util.ErrorUtil;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Filters and converts a {@link Failure} entity to its corresponding {@code
 * Response};
 *
 * @author Alejandro Hernández
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(liferay.apio.architect.application=true)",
		"osgi.jaxrs.extension=true"
	}
)
public class FailureFilter implements ContainerResponseFilter {

	@Override
	public void filter(
			ContainerRequestContext containerRequestContext,
			ContainerResponseContext containerResponseContext)
		throws IOException {

		Try.fromFallible(
			containerResponseContext::getEntity
		).map(
			Failure.class::cast
		).map(
			Failure::getException
		).map(
			exception -> _errorUtil.getErrorResponse(exception, _request)
		).ifSuccess(
			response -> _updateContext(containerResponseContext, response)
		);
	}

	private static void _updateContext(
		ContainerResponseContext containerResponseContext, Response response) {

		containerResponseContext.setStatus(response.getStatus());

		MultivaluedMap<String, Object> headers =
			containerResponseContext.getHeaders();

		headers.remove(CONTENT_TYPE);

		MediaType mediaType = response.getMediaType();

		if (mediaType != null) {
			headers.add(CONTENT_TYPE, mediaType.toString());
		}

		containerResponseContext.setEntity(response.getEntity());
	}

	@Reference
	private ErrorUtil _errorUtil;

	@Context
	private Request _request;

}