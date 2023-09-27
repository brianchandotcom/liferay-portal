/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Amos Fong
 * @author Peter Fellwock
 * @author Raymond Augé
 */
public class AuthTokenUtil {

	public static void addCSRFToken(
		HttpServletRequest httpServletRequest,
		LiferayPortletURL liferayPortletURL) {

		AuthToken authToken = _authToken;

		if (authToken != null) {
			authToken.addCSRFToken(httpServletRequest, liferayPortletURL);
		}
	}

	public static void addPortletInvocationToken(
		HttpServletRequest httpServletRequest,
		LiferayPortletURL liferayPortletURL) {

		AuthToken authToken = _authToken;

		if (authToken != null) {
			authToken.addPortletInvocationToken(
				httpServletRequest, liferayPortletURL);
		}
	}

	public static void checkCSRFToken(
			HttpServletRequest httpServletRequest, String origin)
		throws PrincipalException {

		AuthToken authToken = _authToken;

		if (authToken != null) {
			authToken.checkCSRFToken(httpServletRequest, origin);
		}
	}

	public static String getToken(HttpServletRequest httpServletRequest) {
		AuthToken authToken = _authToken;

		if (authToken == null) {
			return null;
		}

		return authToken.getToken(httpServletRequest);
	}

	public static String getToken(
		HttpServletRequest httpServletRequest, long plid, String portletId) {

		AuthToken authToken = _authToken;

		if (authToken == null) {
			return null;
		}

		return authToken.getToken(httpServletRequest, plid, portletId);
	}

	public static boolean isValidPortletInvocationToken(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {

		AuthToken authToken = _authToken;

		if (authToken == null) {
			return false;
		}

		return authToken.isValidPortletInvocationToken(
			httpServletRequest, layout, portlet);
	}

	private static volatile AuthToken _authToken =
		ServiceProxyFactory.newServiceTrackedInstance(
			AuthToken.class, AuthTokenUtil.class, "_authToken", false);

}