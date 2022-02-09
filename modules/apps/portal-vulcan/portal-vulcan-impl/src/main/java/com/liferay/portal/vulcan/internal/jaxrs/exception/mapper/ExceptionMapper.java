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
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Providers;

/**
 * @author Javier Gamarra
 */
public class ExceptionMapper extends BaseExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		Class<? extends Exception> exceptionClass = exception.getClass();

		String exceptionClassName = exceptionClass.getSimpleName();

		if (exceptionClassName.startsWith("Duplicate") &&
			exceptionClassName.endsWith("ExternalReferenceCodeException")) {

			return Response.status(
				Response.Status.BAD_REQUEST
			).entity(
				exception.getMessage()
			).type(
				getMediaType()
			).build();
		}

		Throwable throwable = exception.getCause();

		if (throwable == null) {
			return super.toResponse(exception);
		}

		javax.ws.rs.ext.ExceptionMapper<Throwable> exceptionMapper =
			_providers.getExceptionMapper(
				(Class<Throwable>)throwable.getClass());

		if (exceptionMapper != null) {
			return exceptionMapper.toResponse(throwable);
		}

		return super.toResponse(exception);
	}

	@Override
	protected Problem getProblem(Exception exception) {
		_log.error(exception);

		return new Problem(
			Response.Status.INTERNAL_SERVER_ERROR, exception.getMessage());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExceptionMapper.class);

	@Context
	private Providers _providers;

}