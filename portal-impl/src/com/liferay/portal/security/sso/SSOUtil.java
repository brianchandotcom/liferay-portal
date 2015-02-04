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

package com.liferay.portal.security.sso;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 */
public class SSOUtil {

	public static String getSessionExpirationRedirectURL(
		long companyId, String sessionExpirationRedirectURL) {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
					PropsValues.OPEN_SSO_AUTH_ENABLED) &&
			PropsValues.OPEN_SSO_LOGOUT_ON_SESSION_EXPIRATION) {

			return PrefsPropsUtil.getString(
					companyId, PropsKeys.OPEN_SSO_LOGOUT_URL,
					PropsValues.OPEN_SSO_LOGOUT_URL);
		}

		if (_instance._ssoMap.isEmpty()) {
			return sessionExpirationRedirectURL;
		}

		return _instance._getSessionExpirationRedirectUrl(companyId);
	}

	public static String getSignInURL(long companyId, String signInURL) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
				PropsValues.OPEN_SSO_AUTH_ENABLED)) {

			return signInURL;
		}

		if (_instance._ssoMap.isEmpty()) {
			return null;
		}

		return _instance._getSignInUrl(companyId, signInURL);
	}

	public static boolean isAccessAllowed(
		HttpServletRequest request, Set<String> hostsAllowed) {

		if (hostsAllowed.isEmpty()) {
			return true;
		}

		String remoteAddr = request.getRemoteAddr();

		if (hostsAllowed.contains(remoteAddr)) {
			return true;
		}

		String computerAddress = PortalUtil.getComputerAddress();

		if (computerAddress.equals(remoteAddr) &&
			hostsAllowed.contains(_SERVER_IP)) {

			return true;
		}

		return false;
	}

	public static boolean isLoginRedirectRequired(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LOGIN_DIALOG_DISABLED,
				PropsValues.LOGIN_DIALOG_DISABLED) ||
			PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
				PropsValues.OPEN_SSO_AUTH_ENABLED)) {

			return true;
		}

		if (_instance._ssoMap.isEmpty()) {
			return false;
		}

		return _instance._isLoginRedirectRequired(companyId);
	}

	public static boolean isRedirectRequired(long companyId) {
		if (_instance._ssoMap.isEmpty()) {
			return false;
		}

		return _instance._isRedirectRequired(companyId);
	}

	public static boolean isSessionRedirectOnExpire(long companyId) {
		boolean sessionRedirectOnExpire =
			PropsValues.SESSION_TIMEOUT_REDIRECT_ON_EXPIRE;

		if (_instance._ssoMap.isEmpty()) {
			return sessionRedirectOnExpire;
		}

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
					PropsValues.OPEN_SSO_AUTH_ENABLED) &&
			PropsValues.OPEN_SSO_LOGOUT_ON_SESSION_EXPIRATION) {

			return true;
		}

		return _instance._isSessionRedirectOnExpire(companyId);
	}

	private SSOUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			SSO.class, new SSOServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private String _getSessionExpirationRedirectUrl(long companyId) {
		for (SSO sso : _ssoMap.values()) {
			String sessionExpirationRedirectUrl =
				sso.getSessionExpirationRedirectUrl(companyId);

			if (sessionExpirationRedirectUrl != null) {
				return sessionExpirationRedirectUrl;
			}
		}

		return null;
	}

	private String _getSignInUrl(long companyId, String defaultSignInURL) {
		for (SSO sso : _ssoMap.values()) {
			String signInURL = sso.getSignInURL(companyId, defaultSignInURL);

			if (signInURL != null) {
				return signInURL;
			}
		}

		return null;
	}

	private boolean _isLoginRedirectRequired(long companyId) {
		for (SSO sso : _ssoMap.values()) {
			if (sso.isLoginRedirectRequired(companyId)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isRedirectRequired(long companyId) {
		for (SSO sso : _ssoMap.values()) {
			if (sso.isRedirectRequired(companyId)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isSessionRedirectOnExpire(long companyId) {
		for (SSO sso : _ssoMap.values()) {
			if (sso.isSessionRedirectOnExpire(companyId)) {
				return true;
			}
		}

		return false;
	}

	private static final String _SERVER_IP = "SERVER_IP";

	private static final SSOUtil _instance = new SSOUtil();

	private final ServiceTracker<SSO, SSO> _serviceTracker;
	private final Map<ServiceReference<SSO>, SSO> _ssoMap =
		new ConcurrentSkipListMap<>(Collections.reverseOrder());

	private class SSOServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<SSO, SSO> {

		@Override
		public SSO addingService(ServiceReference<SSO> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			SSO sso = registry.getService(serviceReference);

			_ssoMap.put(serviceReference, sso);

			return sso;
		}

		@Override
		public void modifiedService(
			ServiceReference<SSO> serviceReference, SSO sso) {
		}

		@Override
		public void removedService(
			ServiceReference<SSO> serviceReference, SSO sso) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_ssoMap.remove(serviceReference);
		}

	}

}