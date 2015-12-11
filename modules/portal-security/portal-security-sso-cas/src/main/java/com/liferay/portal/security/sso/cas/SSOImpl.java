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

package com.liferay.portal.security.sso.cas;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.security.sso.SSO;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.security.sso.cas.constants.CASConstants;
import com.liferay.portal.security.sso.cas.module.configuration.CASConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Enables the CAS module to participate in significant portal session lifecycle
 * changes.
 * 
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.cas.module.configuration.CASConfiguration",
	immediate = true, service = SSO.class
)
public class SSOImpl implements SSO {

	@Override
	public String getSessionExpirationRedirectUrl(long companyId) {
		CASConfiguration casConfiguration = getCASConfiguration(companyId);

		if (casConfiguration.logoutOnSessionExpiration()) {
			return casConfiguration.logoutURL();
		}

		return null;
	}

	@Override
	public String getSignInURL(long companyId, String defaultSigninURL) {
		CASConfiguration casConfiguration = getCASConfiguration(companyId);

		if (!casConfiguration.enabled()) {
			return null;
		}

		return casConfiguration.loginURL();
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
		CASConfiguration casConfiguration = getCASConfiguration(companyId);

		return casConfiguration.logoutOnSessionExpiration();
	}

	protected boolean isCASAuthEnabled(long companyId) {
		CASConfiguration casConfiguration = getCASConfiguration(companyId);

		return casConfiguration.enabled();
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	private CASConfiguration getCASConfiguration(long companyId) {
		try {
			CASConfiguration casCompanyServiceSettings =
				_configurationFactory.getConfiguration(
					CASConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, CASConstants.SERVICE_NAME));

			return casCompanyServiceSettings;
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to get CAS configuration", ce);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(SSOImpl.class);

	private volatile ConfigurationFactory _configurationFactory;

}