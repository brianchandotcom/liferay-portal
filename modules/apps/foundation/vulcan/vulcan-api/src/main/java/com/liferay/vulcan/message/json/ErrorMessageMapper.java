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
 * Instances of this interface can be used to add Vulcan the ability to
 * represent errors in a different format.
 *
 * ErrorMessageMappers works in an event like process. The
 * {@link APIError} writer will call each of the methods of
 * the mapper. In each method developers should only map the provided part of
 * the error, to its representation in a JSON object. For that, in all
 * methods, a {@link JSONObjectBuilder} is received.
 *
 * All methods are called in a not predefined order, except
 * {@link #onStart(JSONObjectBuilder, APIError, HttpHeaders)},
 * {@link #onFinish(JSONObjectBuilder, APIError, HttpHeaders)} (called when
 * the writer starts and finishes the error).
 *
 * @author Alejandro Hernández
 * @review
 */
@ConsumerType
@SuppressWarnings("unused")
public interface ErrorMessageMapper {

	/**
	 * Returns the media type that this mapper represents.
	 *
	 * @return the media type for this mapper.
	 * @review
	 */
	public String getMediaType();

	/**
	 * Maps an error description to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the actual error.
	 * @param  description the description of the error.
	 * @review
	 */
	public default void mapDescription(
		JSONObjectBuilder jsonObjectBuilder, String description) {
	}

	/**
	 * Maps an error status code to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the actual error.
	 * @param  statusCode the status code of the error.
	 * @review
	 */
	public default void mapStatusCode(
		JSONObjectBuilder jsonObjectBuilder, Integer statusCode) {
	}

	/**
	 * Maps an error title to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the actual error.
	 * @param  title the title of the error.
	 * @review
	 */
	public default void mapTitle(
		JSONObjectBuilder jsonObjectBuilder, String title) {
	}

	/**
	 * Maps an error type to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the actual error.
	 * @param  type the type of the error.
	 * @review
	 */
	public default void mapType(
		JSONObjectBuilder jsonObjectBuilder, String type) {
	}

	/**
	 * This method is called when the writer is finishing the apiError.
	 *
	 * @param  jsonObjectBuilder the json object builder for the apiError.
	 * @param  apiError the actual apiError.
	 * @param  httpHeaders the HTTP headers of the current request.
	 * @review
	 */
	public default void onFinish(
		JSONObjectBuilder jsonObjectBuilder, APIError apiError,
		HttpHeaders httpHeaders) {
	}

	/**
	 * This method is called when the writer is starting the apiError.
	 *
	 * @param  jsonObjectBuilder the json object builder for the apiError.
	 * @param  apiError the actual apiError.
	 * @param  httpHeaders the HTTP headers of the current request.
	 * @review
	 */
	public default void onStart(
		JSONObjectBuilder jsonObjectBuilder, APIError apiError,
		HttpHeaders httpHeaders) {
	}

	/**
	 * This method is called to check if the mapper supports mapping all things
	 * related to the current request.
	 *
	 * @param  apiError the actual apiError.
	 * @param  httpHeaders the HTTP headers of the current request.
	 * @return {@code true} if mapper supports mapping this request;
	 *         {@code false} otherwise.
	 * @review
	 */
	public default boolean supports(
		APIError apiError, HttpHeaders httpHeaders) {

		return true;
	}

}