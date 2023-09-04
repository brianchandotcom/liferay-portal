/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.internal.configuration.manager;

import com.liferay.ai.creator.openai.configuration.AICreatorOpenAICompanyConfiguration;
import com.liferay.ai.creator.openai.configuration.AICreatorOpenAIGroupConfiguration;
import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
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
	public String getAICreatorOpenAICompanyAPIKey(long companyId)
		throws ConfigurationException {

		AICreatorOpenAICompanyConfiguration
			aiCreatorOpenAICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AICreatorOpenAICompanyConfiguration.class, companyId);

		return aiCreatorOpenAICompanyConfiguration.apiKey();
	}

	@Override
	public String getAICreatorOpenAIGroupAPIKey(long groupId)
		throws ConfigurationException {

		AICreatorOpenAIGroupConfiguration aiCreatorOpenAIGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				AICreatorOpenAIGroupConfiguration.class, groupId);

		return aiCreatorOpenAIGroupConfiguration.apiKey();
	}

	@Override
	public String getAICreatorOpenAIGroupAPIKey(long companyId, long groupId)
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
	public boolean isAICreatorOpenAICompanyEnabled(long companyId)
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
	public boolean isAICreatorOpenAIGroupEnabled(long companyId, long groupId)
		throws ConfigurationException {

		if (!isAICreatorOpenAICompanyEnabled(companyId)) {
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

	@Override
	public void saveAICreatorOpenAICompanyConfiguration(
			long companyId, String apiKey, boolean enabled)
		throws ConfigurationException {

		_configurationProvider.saveCompanyConfiguration(
			AICreatorOpenAICompanyConfiguration.class, companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"apiKey", apiKey
			).put(
				"enableOpenAIToCreateContent", enabled
			).build());
	}

	@Override
	public void saveAICreatorOpenAIGroupConfiguration(
			long groupId, String apiKey, boolean enabled)
		throws ConfigurationException {

		_configurationProvider.saveGroupConfiguration(
			AICreatorOpenAIGroupConfiguration.class, groupId,
			HashMapDictionaryBuilder.<String, Object>put(
				"apiKey", apiKey
			).put(
				"enableOpenAIToCreateContent", enabled
			).build());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}