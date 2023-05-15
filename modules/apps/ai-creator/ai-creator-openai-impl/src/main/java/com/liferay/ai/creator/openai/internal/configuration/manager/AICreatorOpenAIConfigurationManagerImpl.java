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

package com.liferay.ai.creator.openai.internal.configuration.manager;

import com.liferay.ai.creator.openai.configuration.AICreatorOpenAICompanyConfiguration;
import com.liferay.ai.creator.openai.configuration.AICreatorOpenAIGroupConfiguration;
import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = AICreatorOpenAIConfigurationManager.class)
public class AICreatorOpenAIConfigurationManagerImpl
	implements AICreatorOpenAIConfigurationManager {

	@Override
	public String getAICreatorCompanyApiKey(long companyId)
		throws ConfigurationException {

		AICreatorOpenAICompanyConfiguration
			aiCreatorOpenAICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AICreatorOpenAICompanyConfiguration.class, companyId);

		return aiCreatorOpenAICompanyConfiguration.apiKey();
	}

	@Override
	public String getAICreatorGroupApiKey(long companyId, long groupId)
		throws ConfigurationException {

		AICreatorOpenAIGroupConfiguration aiCreatorOpenAIGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				AICreatorOpenAIGroupConfiguration.class, groupId);

		if (Validator.isNotNull(aiCreatorOpenAIGroupConfiguration.apiKey())) {
			return aiCreatorOpenAIGroupConfiguration.apiKey();
		}

		AICreatorOpenAICompanyConfiguration
			aiCreatorOpenAICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AICreatorOpenAICompanyConfiguration.class, companyId);

		return aiCreatorOpenAICompanyConfiguration.apiKey();
	}

	@Override
	public boolean isAICreatorCompanyEnabled(long companyId)
		throws ConfigurationException {

		AICreatorOpenAICompanyConfiguration
			aiCreatorOpenAICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AICreatorOpenAICompanyConfiguration.class, companyId);

		if (aiCreatorOpenAICompanyConfiguration.enableOpenAIToCreateContent()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAICreatorGroupEnabled(long companyId, long groupId)
		throws ConfigurationException {

		if (!isAICreatorCompanyEnabled(companyId)) {
			return false;
		}

		AICreatorOpenAIGroupConfiguration aiCreatorOpenAIGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				AICreatorOpenAIGroupConfiguration.class, groupId);

		if (aiCreatorOpenAIGroupConfiguration.enableOpenAIToCreateContent()) {
			return true;
		}

		return false;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}