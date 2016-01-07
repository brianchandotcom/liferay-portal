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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Portlet;
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
	 * @deprecated As of 7.0.0, replaced with no direct replacement
	 */
	@Deprecated
	public static AuthTokenWhitelist getAuthTokenWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		if (_authTokenWhiteLists.size() > 0) {
			return _authTokenWhiteLists.get(0);
		}

		return null;
	}

	public static Set<String> getPortletCSRFWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> result = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			result.addAll(authTokenWhitelist.getPortletCSRFWhitelist());
		}

		return result;
	}

	public static Set<String> getPortletCSRFWhitelistActions() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> result = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			result.addAll(authTokenWhitelist.getPortletCSRFWhitelistActions());
		}

		return result;
	}

	public static Set<String> getPortletInvocationWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> result = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			result.addAll(authTokenWhitelist.getPortletInvocationWhitelist());
		}

		return result;
	}

	public static Set<String> getPortletInvocationWhitelistActions() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> result = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			result.addAll(
				authTokenWhitelist.getPortletInvocationWhitelistActions());
		}

		return result;
	}

	public static boolean isCSRFOrigintWhitelisted(
		long companyId, String origin) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			if (authTokenWhitelist.isOriginCSRFWhitelisted(companyId, origin)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isPortletCSRFWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			if (authTokenWhitelist.isPortletCSRFWhitelisted(request, portlet)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #isPortletCSRFWhitelisted(HttpServletRequest, Portlet)}
	 */
	@Deprecated
	@SuppressWarnings("deprecation")
	public static boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			if (authTokenWhitelist.isPortletCSRFWhitelisted(
					companyId, portletId, strutsAction)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isPortletInvocationWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			if (authTokenWhitelist.isPortletInvocationWhitelisted(
					request, portlet)) {

				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #isPortletInvocationWhitelisted(HttpServletRequest, Portlet)}
	 */
	@Deprecated
	@SuppressWarnings("deprecation")
	public static boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			if (authTokenWhitelist.isPortletInvocationWhitelisted(
					companyId, portletId, strutsAction)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			if (authTokenWhitelist.isPortletURLCSRFWhitelisted(
					liferayPortletURL)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			if (authTokenWhitelist.isPortletURLPortletInvocationWhitelisted(
					liferayPortletURL)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isValidSharedSecret(String sharedSecret) {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			if (authTokenWhitelist.isValidSharedSecret(sharedSecret)) {
				return true;
			}
		}

		return false;
	}

	public static Set<String> resetOriginCSRFWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> result = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			result.addAll(authTokenWhitelist.resetOriginCSRFWhitelist());
		}

		return result;
	}

	public static Set<String> resetPortletCSRFWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> result = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			result.addAll(authTokenWhitelist.resetPortletCSRFWhitelist());
		}

		return result;
	}

	public static Set<String> resetPortletInvocationWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> result = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			result.addAll(authTokenWhitelist.resetPortletInvocationWhitelist());
		}

		return result;
	}

	public static Set<String> resetPortletInvocationWhitelistActions() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> result = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhiteLists) {
			result.addAll(
				authTokenWhitelist.resetPortletInvocationWhitelistActions());
		}

		return result;
	}

	/**
	 * @deprecated As of 7.0.0, replaced with no direct replacement
	 */
	@Deprecated
	public void setAuthTokenWhitelist(AuthTokenWhitelist authTokenWhitelist) {
	}

	private static final ServiceTrackerList<AuthTokenWhitelist>
		_authTokenWhiteLists = ServiceTrackerCollections.openList(
			AuthTokenWhitelist.class);

}