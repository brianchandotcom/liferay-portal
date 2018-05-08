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

package com.liferay.apio.architect.message.json;

import aQute.bnd.annotation.ConsumerType;

import javax.ws.rs.core.HttpHeaders;

/**
 * Maps message data to its representation in a JSON object. Instances of this
 * interface work like events. The message's {@code
 * javax.ws.rs.ext.MessageBodyWriter} calls the {@code MessageMapper} methods.
 * In each method, developers should only map the provided part of the resource
 * to its representation in a JSON object. To enable this, each method receives
 * a {@link JSONObjectBuilder}.
 *
 * @author Alejandro Hernández
 * @param  <T> The type of message this mapper handles
 */
@ConsumerType
public interface MessageMapper<T> {

	/**
	 * Returns the media type the mapper represents.
	 *
	 * @return the media type the mapper represents
	 */
	public String getMediaType();

	/**
	 * Finishes the mapping. This is the final mapper method the writer should
	 * call.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the message
	 * @param t the message
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onFinish(
		JSONObjectBuilder jsonObjectBuilder, T t, HttpHeaders httpHeaders) {
	}

	/**
	 * Starts the mapping. This is the first mapper method the writer should
	 * call.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the message
	 * @param t the message
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onStart(
		JSONObjectBuilder jsonObjectBuilder, T t, HttpHeaders httpHeaders) {
	}

}