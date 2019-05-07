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

package com.liferay.portlet.social.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Jorge Ferrer
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class FacebookUtil {

	public static final String FACEBOOK_APPS_URL = "http://apps.facebook.com/";

	public static final String FACEBOOK_SERVLET_PATH = "/facebook/";

	public static String[] getFacebookData(
		HttpServletRequest httpServletRequest) {

		String path = GetterUtil.getString(httpServletRequest.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf(StringPool.SLASH, 1);

		if (pos == -1) {
			return null;
		}

		String facebookCanvasPageURL = path.substring(1, pos);

		if (_log.isDebugEnabled()) {
			_log.debug("Facebook canvas page URL " + facebookCanvasPageURL);
		}

		if (Validator.isNull(facebookCanvasPageURL)) {
			return null;
		}

		String redirect = path.substring(pos);

		if (_log.isDebugEnabled()) {
			_log.debug("Redirect " + redirect);
		}

		if (Validator.isNull(redirect)) {
			return null;
		}

		pos = path.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		String appPath = StringPool.BLANK;

		if (pos != -1) {
			pos = path.indexOf(CharPool.SLASH, pos + 3);

			if (pos != -1) {
				appPath = path.substring(pos);
			}
		}

		return new String[] {facebookCanvasPageURL, redirect, appPath};
	}

	public static boolean isFacebook(String currentURL) {
		String path = currentURL;

		if (currentURL.startsWith(Http.HTTP)) {
			int pos = currentURL.indexOf(
				CharPool.SLASH, Http.HTTPS_WITH_SLASH.length());

			path = currentURL.substring(pos);
		}

		if (path.startsWith(FACEBOOK_SERVLET_PATH)) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(FacebookUtil.class);

}