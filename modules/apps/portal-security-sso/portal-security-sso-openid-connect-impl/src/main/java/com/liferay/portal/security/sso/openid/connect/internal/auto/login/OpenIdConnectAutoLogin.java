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

package com.liferay.portal.security.sso.openid.connect.internal.auto.login;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnect;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.internal.OpenIdConnectFlowState;
import com.liferay.portal.security.sso.openid.connect.internal.OpenIdConnectSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = AutoLogin.class)
public class OpenIdConnectAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = _portal.getCompanyId(request);

		if (!_openIdConnect.isEnabled(companyId)) {
			return null;
		}

		HttpSession httpSession = request.getSession(false);

		if (httpSession == null) {
			return null;
		}

		OpenIdConnectSession openIdConnectSession =
			(OpenIdConnectSession)httpSession.getAttribute(
				OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION);

		if (openIdConnectSession == null) {
			return null;
		}

		OpenIdConnectFlowState openIdConnectFlowState =
			openIdConnectSession.getOpenIdConnectFlowState();

		if (OpenIdConnectFlowState.AUTH_COMPLETE.equals(
				openIdConnectFlowState)) {

			long userId = openIdConnectSession.getLoginUserId();

			User user = _userLocalService.getUserById(userId);

			String[] credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();

			openIdConnectSession.setOpenIdConnectFlowState(
				OpenIdConnectFlowState.PORTAL_AUTH_COMPLETE);

			return credentials;
		}

		return null;
	}

	@Reference
	private OpenIdConnect _openIdConnect;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}