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

package com.liferay.apio.architect.jaxrs.json.internal.filter;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.error.ApioDeveloperError.MustHaveExceptionConverter;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.wiring.osgi.manager.ErrorMessageMapperManager;
import com.liferay.apio.architect.wiring.osgi.manager.ExceptionConverterManager;
import com.liferay.apio.architect.writer.ErrorWriter;

import java.io.IOException;

import java.util.Optional;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * Filters and converts a {@link Try.Failure} entity to its corresponding {@link
 * APIError}, and writes that error to the response.
 *
 * @author Alejandro Hernández
 */
@Component(
	immediate = true,
	property = "liferay.apio.architect.container.response.filter=true"
)
public class FailureFilter implements ContainerResponseFilter {

	@Override
	public void filter(
			ContainerRequestContext containerRequestContext,
			ContainerResponseContext containerResponseContext)
		throws IOException {

		Object entity = containerResponseContext.getEntity();

		if (entity instanceof Try.Failure) {
			Try.Failure failure = (Try.Failure)entity;

			Exception exception = failure.getException();

			Optional<APIError> optional = _exceptionConverterManager.convert(
				exception);

			APIError apiError = optional.orElseThrow(
				() -> new MustHaveExceptionConverter(exception.getClass()));

			if (_apioLogger != null) {
				_apioLogger.error(apiError);
			}

			ErrorMessageMapper errorMessageMapper =
				_errorMessageMapperManager.getErrorMessageMapper(
					apiError, _httpHeaders);

			String result = ErrorWriter.writeError(
				errorMessageMapper, apiError, _httpHeaders);

			MultivaluedMap<String, Object> headers =
				containerResponseContext.getHeaders();

			headers.remove("Content-Type");
			headers.add("Content-Type", errorMessageMapper.getMediaType());

			containerResponseContext.setEntity(result);
			containerResponseContext.setStatus(apiError.getStatusCode());
		}
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private ApioLogger _apioLogger;

	@Reference
	private ErrorMessageMapperManager _errorMessageMapperManager;

	@Reference
	private ExceptionConverterManager _exceptionConverterManager;

	@Context
	private HttpHeaders _httpHeaders;

}