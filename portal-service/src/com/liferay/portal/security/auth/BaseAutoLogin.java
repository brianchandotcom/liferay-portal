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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mate Thurzo
 */
public abstract class BaseAutoLogin implements AuthVerifier, AutoLogin {

	@Override
	public String getAuthType() {
		return this.getClass().getSimpleName();
	}

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
			return handleException(request, response, e);
		}
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		try {
			AuthVerifierResult authVerifierResult = new AuthVerifierResult();

			String[] credentials = login(
				accessControlContext.getRequest(),
				accessControlContext.getResponse());

			if (credentials != null) {
				authVerifierResult.setPassword(credentials[1]);
				authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
				authVerifierResult.setUserId(Long.valueOf(credentials[0]));
			}

			return authVerifierResult;
		}
		catch (AutoLoginException ale) {
			throw new AuthException(ale);
		}
	}

	protected void addRedirect(HttpServletRequest request) {
		String redirect = ParamUtil.getString(request, "redirect");

		if (Validator.isNotNull(redirect)) {
			request.setAttribute(
				AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE,
				PortalUtil.escapeRedirect(redirect));
		}
	}

	protected String[] doHandleException(
			HttpServletRequest request, HttpServletResponse response,
			Exception e)
		throws AutoLoginException {

		if (request.getAttribute(AutoLogin.AUTO_LOGIN_REDIRECT) == null) {
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