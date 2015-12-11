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

package com.liferay.portal.security.sso.opensso;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.security.sso.SSO;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.opensso.module.configuration.OpenSSOConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * Enables the OpenSSO module to participate in significant portal session
 * lifecycle changes.
 * 
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.opensso.module.configuration.OpenSSOConfiguration",
	immediate = true, service = SSO.class
)
public class SSOImpl implements SSO {

	@Override
	public String getSessionExpirationRedirectUrl(long companyId) {
		if (isSessionRedirectOnExpire(companyId)) {
			return PrefsPropsUtil.getString(
				companyId, PropsKeys.OPEN_SSO_LOGOUT_URL,
				_openSSOConfiguration.logoutURL());
		}

		return null;
	}

	@Override
	public String getSignInURL(long companyId, String defaultSigninURL) {
		if (!isOpenSSOEnabled(companyId)) {
			return null;
		}

		if (Validator.isNotNull(_openSSOConfiguration.loginURL())) {
			defaultSigninURL = _openSSOConfiguration.loginURL();
		}

		return PrefsPropsUtil.getString(
			companyId, PropsKeys.OPEN_SSO_LOGIN_URL, defaultSigninURL);
	}

	@Override
	public boolean isLoginRedirectRequired(long companyId) {
		return isOpenSSOEnabled(companyId);
	}

	@Override
	public boolean isRedirectRequired(long companyId) {
		return false;
	}

	@Override
	public boolean isSessionRedirectOnExpire(long companyId) {
		if (isOpenSSOEnabled(companyId)) {
			return _openSSOConfiguration.logoutOnSessionExpiration();
		}

		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_openSSOConfiguration = Configurable.createConfigurable(
			OpenSSOConfiguration.class, properties);
	}

	protected boolean isOpenSSOEnabled(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.OPEN_SSO_AUTH_ENABLED,
				_openSSOConfiguration.enabled())) {

			return true;
		}

		return false;
	}

	private volatile OpenSSOConfiguration _openSSOConfiguration;

}