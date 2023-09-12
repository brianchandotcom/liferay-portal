/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.configuration;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.configuration.SemanticSearchConfigurationProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(enabled = false, service = SemanticSearchConfigurationProvider.class)
public class SemanticSearchConfigurationProviderImpl
	implements SemanticSearchConfigurationProvider {

	@Override
	public SemanticSearchConfiguration getCompanyConfiguration(long companyId) {
		return _getSemanticSearchConfiguration(companyId);
	}

	@Override
	public SemanticSearchConfiguration getSystemConfiguration() {
		return _getSemanticSearchConfiguration(CompanyConstants.SYSTEM);
	}

	private SemanticSearchConfiguration _getSemanticSearchConfiguration(
		long companyId) {

		try {
			if (companyId > CompanyConstants.SYSTEM) {
				return _configurationProvider.getCompanyConfiguration(
					SemanticSearchConfiguration.class, companyId);
			}

			return _configurationProvider.getSystemConfiguration(
				SemanticSearchConfiguration.class);
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}