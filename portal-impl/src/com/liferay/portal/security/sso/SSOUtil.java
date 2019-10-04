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

import com.liferay.portal.kernel.security.sso.SSO;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Raymond Augé
 */
public class SSOUtil {

	public static String getSessionExpirationRedirectURL(
		long companyId, String sessionExpirationRedirectURL) {

		String ssoSessionExpirationRedirectURL =
			_ssoUtil._getSessionExpirationRedirectURL(companyId);

		if (_ssoUtil._ssoMap.isEmpty() ||
			Validator.isNull(ssoSessionExpirationRedirectURL)) {

			return sessionExpirationRedirectURL;
		}

		return ssoSessionExpirationRedirectURL;
	}

	public static String getSignInURL(long companyId, String signInURL) {
		if (_ssoUtil._ssoMap.isEmpty()) {
			return null;
		}

		return _ssoUtil._getSignInURL(companyId, signInURL);
	}

	public static boolean isLoginRedirectRequired(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LOGIN_DIALOG_DISABLED,
				PropsValues.LOGIN_DIALOG_DISABLED)) {

			return true;
		}

		if (_ssoUtil._ssoMap.isEmpty()) {
			return false;
		}

		return _ssoUtil._isLoginRedirectRequired(companyId);
	}

	public static boolean isRedirectRequired(long companyId) {
		if (_ssoUtil._ssoMap.isEmpty()) {
			return false;
		}

		return _ssoUtil._isRedirectRequired(companyId);
	}

	public static boolean isSessionRedirectOnExpire(long companyId) {
		boolean sessionRedirectOnExpire =
			PropsValues.SESSION_TIMEOUT_REDIRECT_ON_EXPIRE;

		if (_ssoUtil._ssoMap.isEmpty() || sessionRedirectOnExpire) {
			return sessionRedirectOnExpire;
		}

		return _ssoUtil._isSessionRedirectOnExpire(companyId);
	}

	private SSOUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			SSO.class, new SSOServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private String _getSessionExpirationRedirectURL(long companyId) {
		for (SSO sso : _ssoMap.values()) {
			String sessionExpirationRedirectURL =
				sso.getSessionExpirationRedirectUrl(companyId);

			if (sessionExpirationRedirectURL != null) {
				return sessionExpirationRedirectURL;
			}
		}

		return null;
	}

	private String _getSignInURL(long companyId, String defaultSignInURL) {
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

	private static final SSOUtil _ssoUtil = new SSOUtil();

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