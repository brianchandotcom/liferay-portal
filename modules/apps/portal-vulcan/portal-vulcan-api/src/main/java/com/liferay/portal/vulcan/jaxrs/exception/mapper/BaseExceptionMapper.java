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

package com.liferay.portal.vulcan.jaxrs.exception.mapper;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Base class that returns objects that follow the Problem+JSON specification
 *
 * @author Javier Gamarra
 * @review
 */
public abstract class BaseExceptionMapper<T extends Throwable>
	implements ExceptionMapper<T> {

	@Override
	public Response toResponse(T exception) {
		Problem problem = getProblem(exception);

		return Response.status(
			problem.getStatus()
		).entity(
			problem
		).type(
			getMediaType()
		).build();
	}

	protected MediaType getMediaType() {
		List<MediaType> mediaTypes = httpHeaders.getAcceptableMediaTypes();

		MediaType mediaType = mediaTypes.get(0);

		if (mediaType.equals(MediaType.valueOf(MediaType.TEXT_HTML)) ||
			mediaType.equals(MediaType.valueOf(MediaType.WILDCARD))) {

			return MediaType.valueOf(MediaType.APPLICATION_JSON);
		}

		return mediaType;
	}

	protected abstract Problem getProblem(T exception);

	@Context
	protected HttpHeaders httpHeaders;

}