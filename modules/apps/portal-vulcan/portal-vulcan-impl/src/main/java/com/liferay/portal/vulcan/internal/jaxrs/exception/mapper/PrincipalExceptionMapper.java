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

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Converts any {@code PrincipalException} to a {@code 404} error in case it is
 * a GET request, otherwise return a {@code 403}
 *
 * @author Brian Wing Shun Chan
 * @review
 */
public class PrincipalExceptionMapper
	extends BaseExceptionMapper<PrincipalException> {

	@Override
	protected Problem getProblem(PrincipalException principalException) {
		Response.Status status = Response.Status.FORBIDDEN;

		String method = _httpServletRequest.getMethod();

		if (method.equals(HttpMethods.GET)) {
			status = Response.Status.NOT_FOUND;
		}

		return new Problem(status, principalException.getMessage());
	}

	@Context
	private HttpServletRequest _httpServletRequest;

}