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

package com.liferay.portal.security.sso.google.internal.auto.login;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.google.GoogleAuthorization;
import com.liferay.portal.security.sso.google.internal.constants.GoogleWebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = AutoLogin.class)
public class GoogleAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long companyId = _portal.getCompanyId(httpServletRequest);

		if (!_googleAuthorization.isEnabled(companyId)) {
			return null;
		}

		User user = getUser(httpServletRequest, companyId);

		if (user == null) {
			return null;
		}

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	protected User getUser(
			HttpServletRequest httpServletRequest, long companyId)
		throws Exception {

		HttpSession session = httpServletRequest.getSession();

		String emailAddress = GetterUtil.getString(
			session.getAttribute(GoogleWebKeys.GOOGLE_USER_EMAIL_ADDRESS));

		if (Validator.isNotNull(emailAddress)) {
			session.removeAttribute(GoogleWebKeys.GOOGLE_USER_EMAIL_ADDRESS);

			return _userLocalService.getUserByEmailAddress(
				companyId, emailAddress);
		}

		String googleUserId = GetterUtil.getString(
			(String)session.getAttribute(GoogleWebKeys.GOOGLE_USER_ID));

		if (Validator.isNotNull(googleUserId)) {
			return _userLocalService.getUserByGoogleUserId(
				companyId, googleUserId);
		}

		return null;
	}

	protected void setGoogleAuthorization(
		GoogleAuthorization googleAuthorization) {

		_googleAuthorization = googleAuthorization;
	}

	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference
	private GoogleAuthorization _googleAuthorization;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}