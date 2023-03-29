/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.exception;

import com.liferay.osb.faro.engine.client.model.ErrorResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author André Miranda
 */
public class FaroValidationException extends WebApplicationException {

	public FaroValidationException() {
	}

	public FaroValidationException(String field, String localizedMessage) {
		this(field, localizedMessage, null);
	}

	public FaroValidationException(
		String field, String localizedMessage, String message) {

		this(field, localizedMessage, message, Response.Status.BAD_REQUEST);
	}

	public FaroValidationException(
		String field, String localizedMessage, String message,
		Response.StatusType statusType) {

		super(getResponse(field, localizedMessage, message, statusType));
	}

	protected static Response getResponse(
		String field, String localizedMessage, String message,
		Response.StatusType statusType) {

		Response.ResponseBuilder responseBuilder = Response.status(statusType);

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setError(statusType.getReasonPhrase());
		errorResponse.setField(field);
		errorResponse.setLocalizedMessage(localizedMessage);
		errorResponse.setStatus(statusType.getStatusCode());
		errorResponse.setMessage(message);

		responseBuilder.entity(errorResponse);

		responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);

		return responseBuilder.build();
	}

}