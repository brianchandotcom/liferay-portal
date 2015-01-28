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

package com.liferay.portal.sso.cas;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.SSO;
import com.liferay.portal.sso.cas.configuration.CASConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SSO.class)
public class SSOImpl implements SSO {

	@Override
	public String getSessionExpirationRedirectUrl(long companyId) {
		if (isSessionRedirectOnExpire(companyId)) {
			return PrefsPropsUtil.getString(
				companyId, PropsKeys.CAS_LOGOUT_URL,
				_casConfiguration.logoutURL());
		}

		return null;
	}

	@Override
	public String getSignInURL(long companyId, String defaultSigninURL) {
		if (!isCASAuthEnabled(companyId)) {
			return null;
		}

		if (Validator.isNotNull(_casConfiguration.loginURL())) {
			defaultSigninURL = _casConfiguration.loginURL();
		}

		return PrefsPropsUtil.getString(
			companyId, PropsKeys.CAS_LOGIN_URL, defaultSigninURL);
	}

	@Override
	public boolean isLoginRedirectRequired(long companyId) {
		return isCASAuthEnabled(companyId);
	}

	@Override
	public boolean isRedirectRequired(long companyId) {
		return isCASAuthEnabled(companyId);
	}

	@Override
	public boolean isSessionRedirectOnExpire(long companyId) {
		if (isCASAuthEnabled(companyId)) {
			return _casConfiguration.logoutOnSessionExpiration();
		}

		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_casConfiguration = Configurable.createConfigurable(
			CASConfiguration.class, properties);
	}

	protected boolean isCASAuthEnabled(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.CAS_AUTH_ENABLED,
			_casConfiguration.enabled())) {

			return true;
		}

		return false;
	}

	private volatile CASConfiguration _casConfiguration;

}