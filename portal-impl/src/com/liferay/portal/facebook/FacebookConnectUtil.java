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

package com.liferay.portal.facebook;

import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.portlet.PortletRequest;

/**
 * @author Wilson Man
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public class FacebookConnectUtil {

	public static String getAccessToken(
		long companyId, String redirect, String code) {

		return _facebookConnect.getAccessToken(companyId, redirect, code);
	}

	public static String getAccessTokenURL(long companyId) {
		return _facebookConnect.getAccessTokenURL(companyId);
	}

	public static String getAppId(long companyId) {
		return _facebookConnect.getAppId(companyId);
	}

	public static String getAppSecret(long companyId) {
		return _facebookConnect.getAppSecret(companyId);
	}

	public static String getAuthURL(long companyId) {
		return _facebookConnect.getAuthURL(companyId);
	}

	public static FacebookConnect getFacebookConnect() {
		return _facebookConnect;
	}

	public static JSONObject getGraphResources(
		long companyId, String path, String accessToken, String fields) {

		return _facebookConnect.getGraphResources(
			companyId, path, accessToken, fields);
	}

	public static String getGraphURL(long companyId) {
		return _facebookConnect.getGraphURL(companyId);
	}

	public static String getProfileImageURL(PortletRequest portletRequest) {
		return _facebookConnect.getProfileImageURL(portletRequest);
	}

	public static String getRedirectURL(long companyId) {
		return _facebookConnect.getRedirectURL(companyId);
	}

	public static boolean isEnabled(long companyId) {
		return _facebookConnect.isEnabled(companyId);
	}

	public static boolean isVerifiedAccountRequired(long companyId) {
		return _facebookConnect.isVerifiedAccountRequired(companyId);
	}

	private FacebookConnectUtil() {
	}

	private static volatile FacebookConnect _facebookConnect =
		ServiceProxyFactory.newServiceTrackedInstance(
			FacebookConnect.class, FacebookConnectUtil.class,
			"_facebookConnect", false, true);

}