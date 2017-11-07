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

package com.liferay.vulcan.message.json;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.vulcan.result.APIError;

import javax.ws.rs.core.HttpHeaders;

/**
 * Represents errors in a different format. Instances of this interface work
 * like events. The {@link APIError} writer calls each of the {@code
 * ErrorMessageMapper} methods. In each method, developers should only map the
 * provided part of the error to its representation in a JSON object. To enable
 * this, each method receives a {@link JSONObjectBuilder}.
 *
 * Besides {@link #onStart(JSONObjectBuilder, APIError, HttpHeaders)} and
 * {@link #onFinish(JSONObjectBuilder, APIError, HttpHeaders)}, which are
 * respectively called when the writer starts and finishes the error, the
 * methods aren't called in a particular order.
 *
 * @author Alejandro Hernández
 */
@ConsumerType
@SuppressWarnings("unused")
public interface ErrorMessageMapper {

	/**
	 * Returns the mapper's media type.
	 *
	 * @return the mapper's media type.
	 */
	public String getMediaType();

	/**
	 * Maps an error description to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the error
	 * @param description the error's description
	 */
	public default void mapDescription(
		JSONObjectBuilder jsonObjectBuilder, String description) {
	}

	/**
	 * Maps an error status code to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the error
	 * @param statusCode the error's status code
	 */
	public default void mapStatusCode(
		JSONObjectBuilder jsonObjectBuilder, Integer statusCode) {
	}

	/**
	 * Maps an error title to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the error
	 * @param title the error's title
	 */
	public default void mapTitle(
		JSONObjectBuilder jsonObjectBuilder, String title) {
	}

	/**
	 * Maps an error type to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the error
	 * @param type the error's type
	 */
	public default void mapType(
		JSONObjectBuilder jsonObjectBuilder, String type) {
	}

	/**
	 * Finishes the API error.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the API error
	 * @param apiError the API error
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onFinish(
		JSONObjectBuilder jsonObjectBuilder, APIError apiError,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Starts the API error.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the API error
	 * @param apiError the API error
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onStart(
		JSONObjectBuilder jsonObjectBuilder, APIError apiError,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Returns {@code true} if the mapper can map all things related to the
	 * current request.
	 *
	 * @param  apiError the API error.
	 * @param  httpHeaders the current request's HTTP headers.
	 * @return {@code true} if the mapper can map the request; {@code false}>
	 *         otherwise.
	 */
	public default boolean supports(
		APIError apiError, HttpHeaders httpHeaders) {

		return true;
	}

}