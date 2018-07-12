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

package com.liferay.apio.architect.impl.message.json;

import com.liferay.apio.architect.operation.HTTPMethod;
import com.liferay.apio.architect.operation.Operation;

/**
 * Maps {@link Operation} data to its representation in a JSON object. Instances
 * of this interface work like events. The different {@code
 * javax.ws.rs.ext.MessageBodyWriter} call the {@code OperationMapper} methods.
 * In each method, developers should only map the provided part of the resource
 * to its representation in a JSON object. To enable this, each method receives
 * a {@link JSONObjectBuilder}.
 *
 * <p>
 * The method {@link #onFinish} is called when the writer finishes writing the
 * operation. Otherwise, the operation message mapper's methods aren't called in
 * a particular order.
 * </p>
 *
 * @author Javier Gamarra
 * @review
 */
public interface OperationMapper {

	/**
	 * Maps a resource operation form's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the operation
	 * @param url the operation form's URL
	 */
	public default void mapFormURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps a resource operation's method to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the operation
	 * @param httpMethod the operation's method
	 */
	public default void mapHTTPMethod(
		JSONObjectBuilder jsonObjectBuilder, HTTPMethod httpMethod) {
	}

	/**
	 * Finishes the operation. This is the final operation-mapper method the
	 * writer calls.
	 *
	 * @param resourceJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param operation the operation
	 */
	public default void onFinish(
		JSONObjectBuilder resourceJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Operation operation) {
	}

}