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
import com.liferay.portal.kernel.exception.NoSuchModelException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author André Miranda
 */
public class NoSuchModelExceptionMapper
	implements ExceptionMapper<NoSuchModelException> {

	@Override
	public Response toResponse(NoSuchModelException noSuchModelException) {
		Response.ResponseBuilder responseBuilder = Response.serverError();

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setMessage(noSuchModelException.getMessage());
		errorResponse.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
		errorResponse.setTimestamp(System.currentTimeMillis());

		responseBuilder.entity(errorResponse);

		responseBuilder.status(Response.Status.BAD_REQUEST);
		responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);

		return responseBuilder.build();
	}

}