/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.opensso.internal;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
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
@Component(service = SSO.class)
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
		return _isSessionRedirectOnExpire(_getOpenSSOConfiguration(companyId));
	}

	private OpenSSOConfiguration _getOpenSSOConfiguration(long companyId) {
		try {
			return _configurationProvider.getConfiguration(
				OpenSSOConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, OpenSSOConstants.SERVICE_NAME));
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get OpenSSO configuration", configurationException);
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

	@Reference
	private ConfigurationProvider _configurationProvider;

}