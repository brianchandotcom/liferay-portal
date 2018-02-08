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

package com.liferay.portal.security.sso.ntlm.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.sso.SSO;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Enables the NTLM module to participate in significant portal session
 * lifecycle changes.
 *
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration",
	immediate = true, service = SSO.class
)
public class SSOImpl implements SSO {

	@Override
	public String getSessionExpirationRedirectUrl(long companyId) {
		return null;
	}

	@Override
	public String getSignInURL(long companyId, String defaultSigninURL) {
		return defaultSigninURL;
	}

	@Override
	public boolean isLoginRedirectRequired(long companyId) {
		try {
			NtlmConfiguration ntlmConfiguration =
				_configurationProvider.getConfiguration(
					NtlmConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, NtlmConstants.SERVICE_NAME));

			return ntlmConfiguration.enabled();
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get NTLM configuration", ce);
		}

		return false;
	}

	@Override
	public boolean isRedirectRequired(long companyId) {
		return false;
	}

	@Override
	public boolean isSessionRedirectOnExpire(long companyId) {
		return false;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(SSOImpl.class);

	private ConfigurationProvider _configurationProvider;

}