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

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Javier Gamarra
 */
public class ExceptionMapper
	implements javax.ws.rs.ext.ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		_log.error(exception, exception);

		return Response.status(
			500
		).entity(
			exception.getMessage()
		).type(
			MediaType.TEXT_PLAIN
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExceptionMapper.class);

}