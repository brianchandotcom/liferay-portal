/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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