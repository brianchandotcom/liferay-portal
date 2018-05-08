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

package com.liferay.apio.architect.writer;

import com.google.gson.JsonObject;

import com.liferay.apio.architect.error.APIError;
import com.liferay.apio.architect.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;

import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

/**
 * Writes an {@link APIError} by using an {@link ErrorMessageMapper}.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public final class ErrorWriter {

	/**
	 * Writes an {@link APIError} to a JSON object.
	 *
	 * @param  errorMessageMapper the {@link ErrorMessageMapper} that matches
	 *         the {@code apiError} and {@code httpHeaders} parameters
	 * @param  apiError the API error
	 * @param  httpHeaders the current request's HTTP headers
	 * @return the API error, as a JSON string
	 */
	public static String writeError(
		ErrorMessageMapper errorMessageMapper, APIError apiError,
		HttpHeaders httpHeaders) {

		JSONObjectBuilder jsonObjectBuilder = new JSONObjectBuilder();

		errorMessageMapper.onStart(jsonObjectBuilder, apiError, httpHeaders);

		Optional<String> optional = apiError.getDescription();

		optional.ifPresent(
			description -> errorMessageMapper.mapDescription(
				jsonObjectBuilder, description));

		errorMessageMapper.mapStatusCode(
			jsonObjectBuilder, apiError.getStatusCode());
		errorMessageMapper.mapTitle(jsonObjectBuilder, apiError.getTitle());
		errorMessageMapper.mapType(jsonObjectBuilder, apiError.getType());
		errorMessageMapper.onFinish(jsonObjectBuilder, apiError, httpHeaders);

		JsonObject jsonObject = jsonObjectBuilder.build();

		return jsonObject.toString();
	}

	private ErrorWriter() {
		throw new UnsupportedOperationException();
	}

}