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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Amos Fong
 * @author Peter Fellwock
 * @author Raymond Augé
 */
public class AuthTokenUtil {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #checkCSRFToken(HttpServletRequest, String)}
	 */
	@Deprecated
	public static void check(HttpServletRequest request)
		throws PortalException {

		_instance._check(request);
	}

	public static void checkCSRFToken(HttpServletRequest request, String origin)
		throws PrincipalException {

		_instance._checkCSRFToken(request, origin);
	}

	public static String getToken(HttpServletRequest request) {
		return _instance._getToken(request);
	}

	public static String getToken(
		HttpServletRequest request, long plid, String portletId) {

		return _instance._getToken(request, plid, portletId);
	}

	public static boolean isValidPortletInvocationToken(
		HttpServletRequest request, long plid, String portletId,
		String strutsAction, String tokenValue) {

		return _instance._isValidPortletInvocationToken(
			request, plid, portletId, strutsAction, tokenValue);
	}

	private AuthTokenUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(AuthToken.class.getName());

		_serviceTracker.open();
	}

	@SuppressWarnings("deprecation")
	private void _check(HttpServletRequest request) throws PortalException {
		if (_serviceTracker.isEmpty()) {
			return;
		}

		AuthToken authToken = _serviceTracker.getService();

		authToken.check(request);
	}

	private void _checkCSRFToken(HttpServletRequest request, String origin)
		throws PrincipalException {

		if (_serviceTracker.isEmpty()) {
			return;
		}

		AuthToken authToken = _serviceTracker.getService();

		authToken.checkCSRFToken(request, origin);
	}

	private String _getToken(HttpServletRequest request) {
		if (_serviceTracker.isEmpty()) {
			return null;
		}

		AuthToken authToken = _serviceTracker.getService();

		return authToken.getToken(request);
	}

	private String _getToken(
		HttpServletRequest request, long plid, String portletId) {

		if (_serviceTracker.isEmpty()) {
			return null;
		}

		AuthToken authToken = _serviceTracker.getService();

		return authToken.getToken(request, plid, portletId);
	}

	private boolean _isValidPortletInvocationToken(
		HttpServletRequest request, long plid, String portletId,
		String strutsAction, String tokenValue) {

		if (_serviceTracker.isEmpty()) {
			return false;
		}

		AuthToken authToken = _serviceTracker.getService();

		return authToken.isValidPortletInvocationToken(
			request, plid, portletId, strutsAction, tokenValue);
	}

	private static final AuthTokenUtil _instance = new AuthTokenUtil();

	private final ServiceTracker<?, AuthToken> _serviceTracker;

}