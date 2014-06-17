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

package com.liferay.portal.kernel.facebook;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.portlet.PortletRequest;

/**
 * @author Wilson Man
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public class FacebookConnectUtil {

	public static String getAccessToken(
		long companyId, String redirect, String code) {

		return getFacebookConnect().getAccessToken(companyId, redirect, code);
	}

	public static String getAccessTokenURL(long companyId) {
		return getFacebookConnect().getAccessTokenURL(companyId);
	}

	public static String getAppId(long companyId) {
		return getFacebookConnect().getAppId(companyId);
	}

	public static String getAppSecret(long companyId) {
		return getFacebookConnect().getAppSecret(companyId);
	}

	public static String getAuthURL(long companyId) {
		return getFacebookConnect().getAuthURL(companyId);
	}

	public static FacebookConnect getFacebookConnect() {
		PortalRuntimePermission.checkGetBeanProperty(FacebookConnectUtil.class);

		return _facebookConnect;
	}

	public static JSONObject getGraphResources(
		long companyId, String path, String accessToken, String fields) {

		return getFacebookConnect().getGraphResources(
			companyId, path, accessToken, fields);
	}

	public static String getGraphURL(long companyId) {
		return getFacebookConnect().getGraphURL(companyId);
	}

	public static String getProfileImageURL(PortletRequest portletRequest) {
		return getFacebookConnect().getProfileImageURL(portletRequest);
	}

	public static String getRedirectURL(long companyId) {
		return getFacebookConnect().getRedirectURL(companyId);
	}

	public static boolean isEnabled(long companyId) {
		return getFacebookConnect().isEnabled(companyId);
	}

	public static boolean isVerifiedAccountRequired(long companyId) {
		return getFacebookConnect().isVerifiedAccountRequired(companyId);
	}

	public void setFacebookConnect(FacebookConnect facebookConnect) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_facebookConnect = facebookConnect;
	}

	private static FacebookConnect _facebookConnect;

}