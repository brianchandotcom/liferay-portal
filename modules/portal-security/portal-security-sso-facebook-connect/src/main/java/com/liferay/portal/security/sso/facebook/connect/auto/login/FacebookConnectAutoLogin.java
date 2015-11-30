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

package com.liferay.portal.security.sso.facebook.connect.auto.login;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectWebKeys;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wilson Man
 */
@Component(immediate = true, service = AutoLogin.class)
public class FacebookConnectAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		if (!_facebookConnect.isEnabled(companyId)) {
			return null;
		}

		User user = getUser(request, companyId);

		if (user == null) {
			return null;
		}

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.FALSE.toString();

		return credentials;
	}

	protected User getUser(HttpServletRequest request, long companyId)
		throws PortalException {

		HttpSession session = request.getSession();

		String emailAddress = (String)session.getAttribute(
			WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);

		if (Validator.isNotNull(emailAddress)) {
			session.removeAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);

			return _userLocalService.getUserByEmailAddress(
				companyId, emailAddress);
		}
		else {
			long facebookId = GetterUtil.getLong(
				(String)session.getAttribute(
					FacebookConnectWebKeys.FACEBOOK_USER_ID));

			if (facebookId > 0) {
				return _userLocalService.getUserByFacebookId(
					companyId, facebookId);
			}
		}

		return null;
	}

	@Reference(unbind = "-")
	protected void setFacebookConnect(FacebookConnect facebookConnect) {
		_facebookConnect = facebookConnect;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile FacebookConnect _facebookConnect;
	private volatile UserLocalService _userLocalService;

}