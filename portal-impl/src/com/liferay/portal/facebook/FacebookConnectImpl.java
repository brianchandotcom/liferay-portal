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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.facebook.FacebookConnect;
import com.liferay.portal.kernel.facebook.FacebookConnectUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Wilson Man
 * @author Mika Koivisto
 */
@DoPrivileged
public class FacebookConnectImpl implements FacebookConnect {

	@Override
	public String getAccessToken(long companyId, String redirect, String code) {
		String url = HttpUtil.addParameter(
			getAccessTokenURL(companyId), "client_id", getAppId(companyId));

		url = HttpUtil.addParameter(
			url, "redirect_uri", FacebookConnectUtil.getRedirectURL(companyId));

		String facebookConnectRedirectURL = getRedirectURL(companyId);

		facebookConnectRedirectURL = HttpUtil.addParameter(
			facebookConnectRedirectURL, "redirect", redirect);

		url = HttpUtil.addParameter(
			url, "redirect_uri", facebookConnectRedirectURL);
		url = HttpUtil.addParameter(
			url, "client_secret", getAppSecret(companyId));
		url = HttpUtil.addParameter(url, "code", code);

		Http.Options options = new Http.Options();

		options.setLocation(url);
		options.setPost(true);

		try {
			String content = HttpUtil.URLtoString(options);

			if (Validator.isNotNull(content)) {
				int x = content.indexOf("access_token=");

				if (x >= 0) {
					int y = content.indexOf(CharPool.AMPERSAND, x);

					if (y < x) {
						y = content.length();
					}

					return content.substring(x + 13, y);
				}
			}
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to retrieve Facebook access token", e);
		}

		return null;
	}

	@Override
	public String getAccessTokenURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_TOKEN_URL,
			PropsValues.FACEBOOK_CONNECT_OAUTH_TOKEN_URL);
	}

	@Override
	public String getAppId(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_APP_ID,
			PropsValues.FACEBOOK_CONNECT_APP_ID);
	}

	@Override
	public String getAppSecret(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_APP_SECRET,
			PropsValues.FACEBOOK_CONNECT_APP_SECRET);
	}

	@Override
	public String getAuthURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_AUTH_URL,
			PropsValues.FACEBOOK_CONNECT_OAUTH_AUTH_URL);
	}

	@Override
	public JSONObject getGraphResources(
		long companyId, String path, String accessToken, String fields) {

		try {
			String url = HttpUtil.addParameter(
				getGraphURL(companyId).concat(path), "access_token",
				accessToken);

			if (Validator.isNotNull(fields)) {
				url = HttpUtil.addParameter(url, "fields", fields);
			}

			Http.Options options = new Http.Options();

			options.setLocation(url);

			String json = HttpUtil.URLtoString(options);

			return JSONFactoryUtil.createJSONObject(json);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	@Override
	public String getGraphURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_GRAPH_URL,
			PropsValues.FACEBOOK_CONNECT_GRAPH_URL);
	}

	@Override
	public String getProfileImageURL(PortletRequest portletRequest) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		request = PortalUtil.getOriginalServletRequest(request);

		HttpSession session = request.getSession();

		String facebookId = (String)session.getAttribute(
			WebKeys.FACEBOOK_USER_ID);

		if (Validator.isNull(facebookId)) {
			return null;
		}

		long companyId = PortalUtil.getCompanyId(request);

		String token = (String)session.getAttribute(
			WebKeys.FACEBOOK_ACCESS_TOKEN);

		JSONObject jsonObject = getGraphResources(
			companyId, "/me", token, "id,picture");

		return jsonObject.getString("picture");
	}

	@Override
	public String getRedirectURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, PropsKeys.FACEBOOK_CONNECT_OAUTH_REDIRECT_URL,
			PropsValues.FACEBOOK_CONNECT_OAUTH_REDIRECT_URL);
	}

	@Override
	public boolean isEnabled(long companyId) {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.FACEBOOK_CONNECT_AUTH_ENABLED,
			PropsValues.FACEBOOK_CONNECT_AUTH_ENABLED);
	}

	@Override
	public boolean isVerifiedAccountRequired(long companyId) {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.FACEBOOK_CONNECT_VERIFIED_ACCOUNT_REQUIRED,
			PropsValues.FACEBOOK_CONNECT_VERIFIED_ACCOUNT_REQUIRED);
	}

	private static Log _log = LogFactoryUtil.getLog(FacebookConnectImpl.class);

}