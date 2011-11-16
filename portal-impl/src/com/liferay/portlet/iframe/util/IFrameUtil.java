/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.iframe.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * @author Amos Fong
 */
public class IFrameUtil {

	public static String getPassword(
			PortletRequest portletRequest, String password)
		throws PortalException, SystemException {

		if (!isPasswordTokenEnabled(portletRequest)) {
			return StringPool.BLANK;
		}

		if (Validator.isNull(password) || password.equals("@password@")) {
			password = PortalUtil.getUserPassword(portletRequest);

			if (password == null) {
				password = StringPool.BLANK;
			}
		}

		return password;
	}

	public static String getUserName(
			PortletRequest portletRequest, String userName)
		throws PortalException, SystemException {

		User user = PortalUtil.getUser(portletRequest);

		if (user == null) {
			return userName;
		}

		if (Validator.isNull(userName) || userName.equals("@user_id@")) {
			userName = portletRequest.getRemoteUser();
		}
		else if (userName.equals("@email_address@")) {
			userName = user.getEmailAddress();
		}
		else if (userName.equals("@screen_name@")) {
			userName = user.getScreenName();
		}

		return userName;
	}

	public static boolean isPasswordTokenEnabled(PortletRequest portletRequest)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String roleName = PropsValues.IFRAME_PASSWORD_PASSWORD_TOKEN_ROLE;

		if (Validator.isNull(roleName)) {
			return true;
		}

		if (layout.isPrivateLayout() && layout.getGroup().isUser()) {
			return true;
		}

		try {
			Role role = RoleLocalServiceUtil.getRole(
				themeDisplay.getCompanyId(), roleName);

			if (UserLocalServiceUtil.hasRoleUser(
					role.getRoleId(), themeDisplay.getUserId())) {

				return true;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Error getting role " + roleName + ". The password token " +
						"will be disabled.");
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(IFrameUtil.class);

}