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

package com.liferay.portal.security.sso.opensso.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.sso.SSO;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.security.sso.opensso.configuration.OpenSSOConfiguration;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Enables the OpenSSO module to participate in significant portal session
 * lifecycle changes.
 *
 * @author Michael C. Han
 */
@Component(immediate = true, service = SSO.class)
public class SSOImpl implements SSO {

	@Override
	public String getSessionExpirationRedirectUrl(long companyId) {
		OpenSSOConfiguration openSSOConfiguration = _getOpenSSOConfiguration(
			companyId);

		if (_isSessionRedirectOnExpire(openSSOConfiguration)) {
			return openSSOConfiguration.logoutURL();
		}

		return null;
	}

	@Override
	public String getSignInURL(long companyId, String defaultSigninURL) {
		OpenSSOConfiguration openSSOConfiguration = _getOpenSSOConfiguration(
			companyId);

		if (!openSSOConfiguration.enabled()) {
			return null;
		}

		return openSSOConfiguration.loginURL();
	}

	@Override
	public boolean isLoginRedirectRequired(long companyId) {
		OpenSSOConfiguration openSSOConfiguration = _getOpenSSOConfiguration(
			companyId);

		return openSSOConfiguration.enabled();
	}

	@Override
	public boolean isRedirectRequired(long companyId) {
		return false;
	}

	@Override
	public boolean isSessionRedirectOnExpire(long companyId) {
		OpenSSOConfiguration openSSOConfiguration = _getOpenSSOConfiguration(
			companyId);

		return _isSessionRedirectOnExpire(openSSOConfiguration);
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private OpenSSOConfiguration _getOpenSSOConfiguration(long companyId) {
		try {
			return _configurationProvider.getConfiguration(
				OpenSSOConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, OpenSSOConstants.SERVICE_NAME));
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get OpenSSO configuration", ce);
		}

		return null;
	}

	private boolean _isSessionRedirectOnExpire(
		OpenSSOConfiguration openSSOConfiguration) {

		if (openSSOConfiguration.enabled()) {
			return openSSOConfiguration.logoutOnSessionExpiration();
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(SSOImpl.class);

	private ConfigurationProvider _configurationProvider;

}