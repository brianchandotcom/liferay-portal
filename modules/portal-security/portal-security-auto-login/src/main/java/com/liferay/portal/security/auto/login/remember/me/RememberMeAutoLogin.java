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

package com.liferay.portal.security.auto.login.remember.me;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.AutoLoginException;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = AutoLogin.class)
public class RememberMeAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doHandleException(
			HttpServletRequest request, HttpServletResponse response,
			Exception e)
		throws AutoLoginException {

		if (_log.isWarnEnabled()) {
			_log.warn(e, e);
		}

		removeCookies(request, response);

		throw new AutoLoginException(e);
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String autoUserId = CookieKeys.getCookie(request, CookieKeys.ID, false);
		String autoPassword = CookieKeys.getCookie(
			request, CookieKeys.PASSWORD, false);
		String rememberMe = CookieKeys.getCookie(
			request, CookieKeys.REMEMBER_ME, false);

		// LEP-5188

		String proxyPath = PortalUtil.getPathProxy();
		String contextPath = PortalUtil.getPathContext();

		if (proxyPath.equals(contextPath)) {
			if (Validator.isNotNull(request.getContextPath())) {
				rememberMe = Boolean.TRUE.toString();
			}
		}
		else {
			if (!contextPath.equals(request.getContextPath())) {
				rememberMe = Boolean.TRUE.toString();
			}
		}

		String[] credentials = null;

		if (Validator.isNotNull(autoUserId) &&
			Validator.isNotNull(autoPassword) &&
			Validator.isNotNull(rememberMe)) {

			Company company = PortalUtil.getCompany(request);

			KeyValuePair kvp = null;

			if (company.isAutoLogin()) {
				kvp = _userLocalService.decryptUserId(
					company.getCompanyId(), autoUserId, autoPassword);

				credentials = new String[3];

				credentials[0] = kvp.getKey();
				credentials[1] = kvp.getValue();
				credentials[2] = Boolean.FALSE.toString();
			}
		}

		// LPS-11218

		if (credentials != null) {
			Company company = PortalUtil.getCompany(request);

			User defaultUser = _userLocalService.getDefaultUser(
				company.getCompanyId());

			long userId = GetterUtil.getLong(credentials[0]);

			if (defaultUser.getUserId() == userId) {
				removeCookies(request, response);

				return null;
			}
		}

		return credentials;
	}

	protected void removeCookies(
		HttpServletRequest request, HttpServletResponse response) {

		Cookie cookie = new Cookie(CookieKeys.ID, StringPool.BLANK);

		cookie.setMaxAge(0);
		cookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(request, response, cookie);

		cookie = new Cookie(CookieKeys.PASSWORD, StringPool.BLANK);

		cookie.setMaxAge(0);
		cookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(request, response, cookie);
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RememberMeAutoLogin.class);

	private volatile UserLocalService _userLocalService;

}