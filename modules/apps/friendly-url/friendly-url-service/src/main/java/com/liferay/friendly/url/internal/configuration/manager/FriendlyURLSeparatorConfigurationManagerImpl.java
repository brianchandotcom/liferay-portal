/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.configuration.manager;

import com.liferay.friendly.url.configuration.FriendlyURLSeparatorCompanyConfiguration;
import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = FriendlyURLSeparatorConfigurationManager.class)
public class FriendlyURLSeparatorConfigurationManagerImpl
	implements FriendlyURLSeparatorConfigurationManager {

	@Override
	public String getFriendlyURLSeparatorsJSON(long companyId)
		throws ConfigurationException {

		FriendlyURLSeparatorCompanyConfiguration
			friendlyURLSeparatorCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FriendlyURLSeparatorCompanyConfiguration.class, companyId);

		return friendlyURLSeparatorCompanyConfiguration.
			friendlyURLSeparatorsJSON();
	}

	@Override
	public void updateFriendlyURLSeparatorCompanyConfiguration(
			long companyId, String friendlyURLSeparatorsJSON)
		throws ConfigurationException {

		_configurationProvider.saveCompanyConfiguration(
			FriendlyURLSeparatorCompanyConfiguration.class, companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"friendlyURLSeparatorsJSON", friendlyURLSeparatorsJSON
			).build());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}