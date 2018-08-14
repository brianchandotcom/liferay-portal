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

package com.liferay.portal.kernel.security.auto.login;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mate Thurzo
 */
public abstract class BaseAutoLogin implements AutoLogin {

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String[] handleException(
			HttpServletRequest request, HttpServletResponse response,
			Exception e)
		throws AutoLoginException {

		return doHandleException(request, response, e);
	}

	@Override
	public String[] login(
			HttpServletRequest request, HttpServletResponse response)
		throws AutoLoginException {

		try {
			return doLogin(request, response);
		}
		catch (Exception e) {
			return doHandleException(request, response, e);
		}
	}

	protected void addRedirect(HttpServletRequest request) {
		String redirect = ParamUtil.getString(request, "redirect");

		if (Validator.isNotNull(redirect)) {
			request.setAttribute(
				AUTO_LOGIN_REDIRECT_AND_CONTINUE,
				PortalUtil.escapeRedirect(redirect));
		}
	}

	protected String[] doHandleException(
			HttpServletRequest request, HttpServletResponse response,
			Exception e)
		throws AutoLoginException {

		if (request.getAttribute(AUTO_LOGIN_REDIRECT) == null) {
			throw new AutoLoginException(e);
		}

		_log.error(e, e);

		return null;
	}

	protected abstract String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(BaseAutoLogin.class);

}