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

import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.model.ErrorResponse;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Matthew Kong
 */
public class FaroEngineClientExceptionMapper
	implements ExceptionMapper<FaroEngineClientException> {

	@Override
	public Response toResponse(
		FaroEngineClientException faroEngineClientException) {

		Response.ResponseBuilder responseBuilder = null;

		try {
			ErrorResponse errorResponse = null;

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				faroEngineClientException.getMessage());

			if (jsonObject.get("errorAttributes") != null) {
				JSONObject errorAttributesJSONObject =
					(JSONObject)jsonObject.get("errorAttributes");

				errorResponse = JSONUtil.readValue(
					errorAttributesJSONObject.toString(), ErrorResponse.class);
			}
			else {
				errorResponse = JSONUtil.readValue(
					faroEngineClientException.getMessage(),
					ErrorResponse.class);
			}

			if (errorResponse.getStatus() !=
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {

				responseBuilder = Response.serverError();

				ErrorResponse wrappedErrorResponse = new ErrorResponse();

				wrappedErrorResponse.setMessage(errorResponse.getMessage());
				wrappedErrorResponse.setMessageKey(
					errorResponse.getMessageKey());
				wrappedErrorResponse.setStatus(
					Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
				wrappedErrorResponse.setTimestamp(System.currentTimeMillis());

				responseBuilder.entity(wrappedErrorResponse);
			}
			else {
				responseBuilder = Response.status(errorResponse.getStatus());

				responseBuilder.entity(errorResponse);
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			responseBuilder = Response.serverError();
		}

		return responseBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaroEngineClientExceptionMapper.class);

}