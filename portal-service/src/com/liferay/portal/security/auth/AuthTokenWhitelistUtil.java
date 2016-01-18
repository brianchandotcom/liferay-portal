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
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static AuthTokenWhitelist getAuthTokenWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		if (_authTokenWhitelists.size() > 0) {
			return _authTokenWhitelists.get(0);
		}

		return null;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<String> getPortletCSRFWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> portletCSRFWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletCSRFWhitelist.addAll(
				authTokenWhitelist.getPortletCSRFWhitelist());
		}

		return portletCSRFWhitelist;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<String> getPortletCSRFWhitelistActions() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> portletCSRFWhitelistActions = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletCSRFWhitelistActions.addAll(
				authTokenWhitelist.getPortletCSRFWhitelistActions());
		}

		return portletCSRFWhitelistActions;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<String> getPortletInvocationWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> portletInvocationWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletInvocationWhitelist.addAll(
				authTokenWhitelist.getPortletInvocationWhitelist());
		}

		return portletInvocationWhitelist;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<String> getPortletInvocationWhitelistActions() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> portletInvocationWhitelistActions = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletInvocationWhitelistActions.addAll(
				authTokenWhitelist.getPortletInvocationWhitelistActions());
		}

		return portletInvocationWhitelistActions;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #isOriginCSRFWhitelisted(long, String)}
	 */
	@Deprecated
	public static boolean isCSRFOrigintWhitelisted(
		long companyId, String origin) {

		return isOriginCSRFWhitelisted(companyId, origin);
	}

	public static boolean isOriginCSRFWhitelisted(
		long companyId, String origin) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isOriginCSRFWhitelisted(companyId, origin)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isPortletCSRFWhitelisted(
		HttpServletRequest request, Portlet portlet) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
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
	public static boolean isPortletCSRFWhitelisted(
		long companyId, String portletId, String strutsAction) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

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

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
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
	public static boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction) {

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

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

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

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

		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isPortletURLPortletInvocationWhitelisted(
					liferayPortletURL)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isValidSharedSecret(String sharedSecret) {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			if (authTokenWhitelist.isValidSharedSecret(sharedSecret)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<String> resetOriginCSRFWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> originCSRFWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			originCSRFWhitelist.addAll(
				authTokenWhitelist.resetOriginCSRFWhitelist());
		}

		return originCSRFWhitelist;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<String> resetPortletCSRFWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> portletCSRFWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletCSRFWhitelist.addAll(
				authTokenWhitelist.resetPortletCSRFWhitelist());
		}

		return portletCSRFWhitelist;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<String> resetPortletInvocationWhitelist() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> portletInvocationWhitelist = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletInvocationWhitelist.addAll(
				authTokenWhitelist.resetPortletInvocationWhitelist());
		}

		return portletInvocationWhitelist;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static Set<String> resetPortletInvocationWhitelistActions() {
		PortalRuntimePermission.checkGetBeanProperty(AuthTokenWhitelist.class);

		Set<String> portletInvocationWhitelistActions = new HashSet<>();

		for (AuthTokenWhitelist authTokenWhitelist : _authTokenWhitelists) {
			portletInvocationWhitelistActions.addAll(
				authTokenWhitelist.resetPortletInvocationWhitelistActions());
		}

		return portletInvocationWhitelistActions;
	}

	/**
	 * @deprecated As of 7.0.0, replaced with no direct replacement
	 */
	@Deprecated
	public void setAuthTokenWhitelist(AuthTokenWhitelist authTokenWhitelist) {
	}

	private static final ServiceTrackerList<AuthTokenWhitelist>
		_authTokenWhitelists = ServiceTrackerCollections.openList(
			AuthTokenWhitelist.class);

}