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

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class AuthTokenWhitelistUtil {

	public static boolean isOriginCSRFWhitelisted(
		long companyId, String origin) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isOriginCSRFWhitelisted(companyId, origin)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isPortletCSRFWhitelisted(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletCSRFWhitelisted(
					httpServletRequest, portlet)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isPortletInvocationWhitelisted(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletInvocationWhitelisted(
					httpServletRequest, portlet)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletURLCSRFWhitelisted(
					liferayPortletURL)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletURLPortletInvocationWhitelisted(
					liferayPortletURL)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isValidSharedSecret(String sharedSecret) {
		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isValidSharedSecret(sharedSecret)) {
				return true;
			}
		}

		return false;
	}

	private static final ServiceTrackerList<AuthTokenWhitelist>
		_authTokenWhitelists = ServiceTrackerCollections.openList(
			AuthTokenWhitelist.class);

}