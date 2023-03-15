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

import com.github.scribejava.core.exceptions.OAuthException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Shinn Lok
 */
public class OAuthExceptionMapper implements ExceptionMapper<OAuthException> {

	@Override
	public Response toResponse(OAuthException oAuthException) {
		Response.ResponseBuilder responseBuilder = Response.serverError();

		responseBuilder.entity(oAuthException);

		return responseBuilder.build();
	}

}