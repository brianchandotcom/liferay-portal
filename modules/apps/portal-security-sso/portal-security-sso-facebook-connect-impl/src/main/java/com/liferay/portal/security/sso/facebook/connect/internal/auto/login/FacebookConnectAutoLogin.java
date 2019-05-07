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

package com.liferay.portal.security.sso.facebook.connect.internal.auto.login;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectWebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Participates in every unauthenticated HTTP request to Liferay Portal.
 *
 * <p>
 * This class looks for one of two HTTP session attributes:
 * <code>FACEBOOK_USER_ID</code> or <code>FACEBOOK_USER_EMAIL_ADDRESS</code>. If
 * either is found and can be matched with a Liferay Portal user on the
 * corresponding field, then the user is logged in without any further
 * challenge.
 * <p>
 *
 * @author Wilson Man
 */
@Component(immediate = true, service = AutoLogin.class)
public class FacebookConnectAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long companyId = _portal.getCompanyId(httpServletRequest);

		if (!_facebookConnect.isEnabled(companyId)) {
			return null;
		}

		User user = getUser(httpServletRequest, companyId);

		if (user == null) {
			return null;
		}

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.FALSE.toString();

		return credentials;
	}

	protected User getUser(
			HttpServletRequest httpServletRequest, long companyId)
		throws PortalException {

		HttpSession session = httpServletRequest.getSession();

		String emailAddress = (String)session.getAttribute(
			WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);

		if (Validator.isNotNull(emailAddress)) {
			session.removeAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);

			return _userLocalService.getUserByEmailAddress(
				companyId, emailAddress);
		}

		long facebookId = GetterUtil.getLong(
			(String)session.getAttribute(
				FacebookConnectWebKeys.FACEBOOK_USER_ID));

		if (facebookId > 0) {
			return _userLocalService.getUserByFacebookId(companyId, facebookId);
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

	private FacebookConnect _facebookConnect;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}