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

package com.liferay.apio.architect.jaxrs.json.internal.exception.mapper;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.error.ApioDeveloperError.MustHaveExceptionConverter;
import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.wiring.osgi.manager.ErrorMessageMapperManager;
import com.liferay.apio.architect.wiring.osgi.manager.ExceptionConverterManager;
import com.liferay.apio.architect.writer.ErrorWriter;

import java.util.Optional;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * Captures and converts an exception to its corresponding {@link APIError}, and
 * writes that error to the response.
 *
 * @author Alejandro Hernández
 */
@Component(
	immediate = true, property = "liferay.apio.architect.exception.mapper=true"
)
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		Optional<APIError> optional = _exceptionConverterManager.convert(
			exception);

		APIError apiError = optional.orElseThrow(
			() -> new MustHaveExceptionConverter(exception.getClass()));

		if (_apioLogger != null) {
			_apioLogger.error(apiError);
		}

		Response.ResponseBuilder responseBuilder = Response.status(
			apiError.getStatusCode());

		ErrorMessageMapper errorMessageMapper =
			_errorMessageMapperManager.getErrorMessageMapper(
				apiError, _httpHeaders);

		return responseBuilder.entity(
			ErrorWriter.writeError(errorMessageMapper, apiError, _httpHeaders)
		).type(
			errorMessageMapper.getMediaType()
		).build();
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