/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @author Raymond Augé
 */
public class UriInfoUtil {

	public static String getBasePath(UriInfo uriInfo) {
		return String.valueOf(
			getBaseUriBuilder(
				uriInfo
			).build());
	}

	public static UriBuilder getBaseUriBuilder(
		HttpServletRequest httpServletRequest, UriInfo uriInfo) {

		UriBuilder uriBuilder = getBaseUriBuilder(uriInfo);

		uriBuilder.host(PortalUtil.getForwardedHost(httpServletRequest));
		uriBuilder.port(PortalUtil.getForwardedPort(httpServletRequest));

		if (PortalUtil.isSecure(httpServletRequest)) {
			uriBuilder.scheme(Http.HTTPS);
		}

		return uriBuilder;
	}

	public static UriBuilder getBaseUriBuilder(
		String applicationPath, UriInfo uriInfo) {

		String basePath = getBasePath(uriInfo);

		if (basePath.endsWith(StringPool.FORWARD_SLASH)) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}

		basePath = basePath.substring(0, basePath.lastIndexOf("/") + 1);

		if (basePath.endsWith("/c/")) {
			basePath = StringUtil.removeLast(basePath, "c/");
		}

		basePath = basePath + applicationPath;

		return UriBuilder.fromPath(basePath);
	}

	public static UriBuilder getBaseUriBuilder(UriInfo uriInfo) {
		return _updateUriBuilder(uriInfo.getBaseUriBuilder());
	}

	private static String _getHost(UriBuilder uriBuilder) {
		try {
			if (_uriBuilderHostField == null) {
				_uriBuilderHostField = ReflectionUtil.getDeclaredField(
					uriBuilder.getClass(), "host");
			}

			return (String)_uriBuilderHostField.get(uriBuilder);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private static boolean _isHttpsEnabled() {
		if (Http.HTTPS.equals(
				PropsUtil.get(PropsKeys.PORTAL_INSTANCE_PROTOCOL)) ||
			Http.HTTPS.equals(PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL))) {

			return true;
		}

		return false;
	}

	private static UriBuilder _updateUriBuilder(UriBuilder uriBuilder) {
		if (!Validator.isBlank(PortalUtil.getPathContext())) {
			URI uri = uriBuilder.build();

			uriBuilder.replacePath(PortalUtil.getPathContext(uri.getPath()));
		}

		if (Validator.isNotNull(_getHost(uriBuilder)) && _isHttpsEnabled()) {
			uriBuilder.scheme(Http.HTTPS);
		}

		return uriBuilder;
	}

	private static volatile Field _uriBuilderHostField;

}