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

package com.liferay.friendly.url.internal.configuration;

import com.liferay.friendly.url.configuration.FriendlyURLRedirectionConfiguration;
import com.liferay.friendly.url.configuration.FriendlyURLRedirectionConfigurationProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Mijarra
 */
@Component(
	enabled = false, service = FriendlyURLRedirectionConfigurationProvider.class
)
public class FriendlyURLRedirectionConfigurationProviderImpl
	implements FriendlyURLRedirectionConfigurationProvider {

	@Override
	public FriendlyURLRedirectionConfiguration getCompanyConfiguration(
		long companyId) {

		return _getConfiguration(companyId);
	}

	@Override
	public FriendlyURLRedirectionConfiguration getSystemConfiguration() {
		return _getConfiguration(CompanyConstants.SYSTEM);
	}

	private FriendlyURLRedirectionConfiguration _getConfiguration(
		long companyId) {

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