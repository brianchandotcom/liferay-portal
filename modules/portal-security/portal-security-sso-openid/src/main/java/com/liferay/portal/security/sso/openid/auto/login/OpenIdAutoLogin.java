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

package com.liferay.portal.security.sso.openid.auto.login;

import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = AutoLogin.class)
public class OpenIdAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		if (!_openId.isEnabled(companyId)) {
			return null;
		}

		HttpSession session = request.getSession();

		Long userId = (Long)session.getAttribute(WebKeys.OPEN_ID_LOGIN);

		if (userId == null) {
			return null;
		}

		session.removeAttribute(WebKeys.OPEN_ID_LOGIN);

		User user = _userLocalService.getUserById(userId);

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	@Reference(unbind = "-")
	protected void setOpenId(OpenId openId) {
		_openId = openId;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private OpenId _openId;
	private UserLocalService _userLocalService;

}