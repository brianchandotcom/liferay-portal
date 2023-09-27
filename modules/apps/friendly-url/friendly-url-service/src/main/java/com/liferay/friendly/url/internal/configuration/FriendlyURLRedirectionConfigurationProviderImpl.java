/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.configuration;

import com.liferay.friendly.url.configuration.FriendlyURLRedirectionConfiguration;
import com.liferay.friendly.url.configuration.FriendlyURLRedirectionConfigurationProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Mijarra
 */
@Component(service = FriendlyURLRedirectionConfigurationProvider.class)
public class FriendlyURLRedirectionConfigurationProviderImpl
	implements FriendlyURLRedirectionConfigurationProvider {

	@Override
	public FriendlyURLRedirectionConfiguration
		getCompanyFriendlyURLRedirectionConfiguration(long companyId) {

		return _getFriendlyURLRedirectionConfiguration(companyId);
	}

	@Override
	public FriendlyURLRedirectionConfiguration
		getSystemFriendlyURLRedirectionConfiguration() {

		return _getFriendlyURLRedirectionConfiguration(CompanyConstants.SYSTEM);
	}

	private FriendlyURLRedirectionConfiguration
		_getFriendlyURLRedirectionConfiguration(long companyId) {

		try {
			if (companyId > CompanyConstants.SYSTEM) {
				return _configurationProvider.getCompanyConfiguration(
					FriendlyURLRedirectionConfiguration.class, companyId);
			}

			return _configurationProvider.getSystemConfiguration(
				FriendlyURLRedirectionConfiguration.class);
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}