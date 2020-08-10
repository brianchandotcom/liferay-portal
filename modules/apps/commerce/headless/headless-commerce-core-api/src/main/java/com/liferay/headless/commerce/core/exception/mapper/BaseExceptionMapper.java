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

package com.liferay.headless.commerce.core.exception.mapper;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Igor Beslic
 * @author Ivica Cardic
 * @author Zoltán Takács
 */
public abstract class BaseExceptionMapper<E extends Throwable>
	implements ExceptionMapper<E> {

	public abstract String getErrorDescription();

	public abstract Response.Status getStatus();

	@Override
	public Response toResponse(E e) {
		_log.error("General REST exception", e);

		Response.ResponseBuilder responseBuilder = Response.status(getStatus());

		Response.Status status = getStatus();

		responseBuilder.entity(toJSON(e, status.getStatusCode()));

		responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);

		return responseBuilder.build();
	}

	protected String toJSON(E e, int status) {
		return toJSON(e.getMessage(), status);
	}

	protected String toJSON(String message, int status, Object... args) {
		StringBuilder sb = new StringBuilder();

		sb.append("{\"errorDescription\": \"");
		sb.append(getErrorDescription());
		sb.append("\", \"message\": \"");
		sb.append(message);
		sb.append("\", \"status\": ");
		sb.append(status);

		if (args.length == 0) {
			sb.append("}");

			return sb.toString();
		}

		sb.append(", \"args\": {");

		for (int i = 0; i < (args.length - 1); i = i + 2) {
			sb.append("\"");
			sb.append(args[i]);
			sb.append("\"");
			sb.append(":");
			sb.append("\"");
			sb.append(args[i + 1]);
			sb.append("\"");

			if ((i + 2) < args.length) {
				sb.append(", ");
			}
		}

		sb.append("}}");

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseExceptionMapper.class);

}