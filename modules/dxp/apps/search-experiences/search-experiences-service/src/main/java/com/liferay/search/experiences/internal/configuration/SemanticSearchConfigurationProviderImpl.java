/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.configuration;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
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