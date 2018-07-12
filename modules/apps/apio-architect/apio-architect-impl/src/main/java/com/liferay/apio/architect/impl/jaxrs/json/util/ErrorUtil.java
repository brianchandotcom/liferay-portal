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

package com.liferay.apio.architect.impl.jaxrs.json.util;

import static com.liferay.apio.architect.impl.writer.ErrorWriter.writeError;

import static org.slf4j.LoggerFactory.getLogger;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.impl.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.impl.wiring.osgi.manager.exception.mapper.ExceptionMapperManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.message.json.ErrorMessageMapperManager;

import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;

/**
 * Provides utility methods for transforming exceptions into responses.
 *
 * @author Alejandro Hernández
 */
@Component(service = ErrorUtil.class)
public class ErrorUtil {

	/**
	 * Transforms an exception into a {@code Response}.
	 *
	 * @param  exception the exception
	 * @param  request the current request
	 * @return the response
	 */
	public Response getErrorResponse(Exception exception, Request request) {
		Optional<APIError> apiErrorOptional = _exceptionMapperManager.map(
			exception);

		if (!apiErrorOptional.isPresent()) {
			Class<? extends Exception> clazz = exception.getClass();

			_logger.warn("No exception mapper found for {}", clazz);

			if (clazz.isAssignableFrom(WebApplicationException.class)) {
				WebApplicationException webApplicationException =
					(WebApplicationException)exception;

				return webApplicationException.getResponse();
			}

			return Response.serverError().build();
		}

		APIError apiError = apiErrorOptional.get();

		_logger.error(apiError.getMessage(), apiError.getException());

		int statusCode = apiError.getStatusCode();

		Optional<ErrorMessageMapper> errorMessageMapperOptional =
			_errorMessageMapperManager.getErrorMessageMapperOptional(request);

		return errorMessageMapperOptional.map(
			errorMessageMapper -> Response.status(
				statusCode
			).type(
				errorMessageMapper.getMediaType()
			).entity(
				writeError(errorMessageMapper, apiError)
			).build()
		).orElseGet(
			() -> Response.status(
				statusCode
			).build()
		);
	}

	@Reference
	private ErrorMessageMapperManager _errorMessageMapperManager;

	@Reference
	private ExceptionMapperManager _exceptionMapperManager;

	private final Logger _logger = getLogger(getClass());

}