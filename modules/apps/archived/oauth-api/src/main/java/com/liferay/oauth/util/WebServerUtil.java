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

package com.liferay.oauth.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Igor Beslic
 */
public class WebServerUtil {

	public static String getWebServerURL(StringBuffer requestURL)
		throws URISyntaxException {

		int webServerHttpPort = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.WEB_SERVER_HTTP_PORT));
		int webServerHttpsPort = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.WEB_SERVER_HTTPS_PORT));

		if ((webServerHttpPort == -1) && (webServerHttpsPort == -1)) {
			return null;
		}

		int webServerPort = webServerHttpPort;

		String webServerProtocol = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL);

		if (Validator.isNull(webServerProtocol)) {
			if (webServerHttpPort != -1) {
				webServerProtocol = Http.HTTP;
			}
			else {
				return null;
			}
		}

		if (StringUtil.equalsIgnoreCase(webServerProtocol, Http.HTTPS)) {
			if (webServerHttpsPort != -1) {
				webServerPort = webServerHttpsPort;
			}
			else {
				return null;
			}
		}

		StringBundler sb = new StringBundler(7);

		sb.append(webServerProtocol);
		sb.append(StringPool.COLON);
		sb.append(StringPool.DOUBLE_SLASH);

		URI uri = new URI(requestURL.toString());

		sb.append(_getAuthority(uri));

		if ((webServerProtocol.equals(Http.HTTP) &&
			 (webServerPort != Http.HTTP_PORT)) ||
			(webServerProtocol.equals(Http.HTTPS) &&
			 (webServerPort != Http.HTTPS_PORT))) {

			sb.append(StringPool.COLON);
			sb.append(webServerPort);
		}

		sb.append(_getPath(uri));

		return sb.toString();
	}

	private static String _getAuthority(URI uri) {
		String authority = StringUtil.toLowerCase(uri.getAuthority());

		int index = authority.lastIndexOf(StringPool.COLON);

		if (index >= 0) {
			authority = authority.substring(0, index);
		}

		return authority;
	}

	private static String _getPath(URI uri) {
		String path = uri.getRawPath();

		if ((path == null) || (path.length() == 0)) {
			path = StringPool.SLASH;
		}

		return path;
	}

}