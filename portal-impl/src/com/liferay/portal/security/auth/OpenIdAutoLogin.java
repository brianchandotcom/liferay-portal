/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.OpenIdUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Jorge Ferrer
 */
public class OpenIdAutoLogin implements AutoLogin {

	public String[] login(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (!OpenIdUtil.isEnabled(companyId)) {
				return null;
			}

			HttpSession session = request.getSession();

			Long userId = (Long)session.getAttribute(WebKeys.OPEN_ID_LOGIN);

			if (userId == null) {
				return null;
			}

			session.removeAttribute(WebKeys.OPEN_ID_LOGIN);

			User user = UserLocalServiceUtil.getUserById(userId);

			String[] credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();

			return credentials;
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OpenIdAutoLogin.class);

}