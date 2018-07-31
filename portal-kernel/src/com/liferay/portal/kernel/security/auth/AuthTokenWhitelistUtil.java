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

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 */
public class AuthTokenWhitelistUtil {

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static AuthTokenWhitelist getAuthTokenWhitelist() {
		if (_authTokenWhitelists.size() > 0) {
			return _authTokenWhitelists.get(0);
		}

		return null;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static Set<String> getPortletCSRFWhitelist() {
		Set<String> portletCSRFWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletCSRFWhitelist.addAll(
				authTokenWhitelist.getPortletCSRFWhitelist());
		}

		return portletCSRFWhitelist;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static Set<String> getPortletCSRFWhitelistActions() {
		Set<String> portletCSRFWhitelistActions = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletCSRFWhitelistActions.addAll(
				authTokenWhitelist.getPortletCSRFWhitelistActions());
		}

		return portletCSRFWhitelistActions;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static Set<String> getPortletInvocationWhitelist() {
		Set<String> portletInvocationWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletInvocationWhitelist.addAll(
				authTokenWhitelist.getPortletInvocationWhitelist());
		}

		return portletInvocationWhitelist;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static Set<String> getPortletInvocationWhitelistActions() {
		Set<String> portletInvocationWhitelistActions = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletInvocationWhitelistActions.addAll(
				authTokenWhitelist.getPortletInvocationWhitelistActions());
		}

		return portletInvocationWhitelistActions;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #isOriginCSRFWhitelisted(long, String)}
	 */
	@Deprecated
	public static boolean isCSRFOrigintWhitelisted(
		long companyId, String origin) {

		return isOriginCSRFWhitelisted(companyId, origin);
	}

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
		HttpServletRequest request, Portlet portlet) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletCSRFWhitelisted(request, portlet)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #isPortletCSRFWhitelisted(HttpServletRequest, Portlet)}
	 */
	@Deprecated
	public static boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletCSRFWhitelisted(
					companyId, portletId, strutsAction)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isPortletInvocationWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletInvocationWhitelisted(
					request, portlet)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #isPortletInvocationWhitelisted(HttpServletRequest, Portlet)}
	 */
	@Deprecated
	public static boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction) {

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletInvocationWhitelisted(
					companyId, portletId, strutsAction)) {

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

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static Set<String> resetOriginCSRFWhitelist() {
		Set<String> originCSRFWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			originCSRFWhitelist.addAll(
				authTokenWhitelist.resetOriginCSRFWhitelist());
		}

		return originCSRFWhitelist;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static Set<String> resetPortletCSRFWhitelist() {
		Set<String> portletCSRFWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletCSRFWhitelist.addAll(
				authTokenWhitelist.resetPortletCSRFWhitelist());
		}

		return portletCSRFWhitelist;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static Set<String> resetPortletInvocationWhitelist() {
		Set<String> portletInvocationWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletInvocationWhitelist.addAll(
				authTokenWhitelist.resetPortletInvocationWhitelist());
		}

		return portletInvocationWhitelist;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static Set<String> resetPortletInvocationWhitelistActions() {
		Set<String> portletInvocationWhitelistActions = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletInvocationWhitelistActions.addAll(
				authTokenWhitelist.resetPortletInvocationWhitelistActions());
		}

		return portletInvocationWhitelistActions;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced with no direct
	 *             replacement
	 */
	@Deprecated
	public void setAuthTokenWhitelist(AuthTokenWhitelist authTokenWhitelist) {
	}

	private static final ServiceTrackerList<AuthTokenWhitelist>
		_authTokenWhitelists = ServiceTrackerCollections.openList(
			AuthTokenWhitelist.class);

}