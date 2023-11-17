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
	public boolean isAICreatorChatGPTCompanyEnabled(long companyId)
		throws ConfigurationException {

		AICreatorOpenAICompanyConfiguration
			aiCreatorOpenAICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AICreatorOpenAICompanyConfiguration.class, companyId);

		if (aiCreatorOpenAICompanyConfiguration.
				enableChatGPTToCreateContent()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isAICreatorChatGPTGroupEnabled(long companyId, long groupId)
		throws ConfigurationException {

		if (!isAICreatorChatGPTCompanyEnabled(companyId)) {
			return false;
		}

		AICreatorOpenAIGroupConfiguration aiCreatorOpenAIGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				AICreatorOpenAIGroupConfiguration.class, groupId);

		if (aiCreatorOpenAIGroupConfiguration.enableChatGPTToCreateContent()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAICreatorDALLECompanyEnabled(long companyId)
		throws ConfigurationException {

		AICreatorOpenAICompanyConfiguration
			aiCreatorOpenAICompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AICreatorOpenAICompanyConfiguration.class, companyId);

		if (aiCreatorOpenAICompanyConfiguration.enableDALLEToCreateImages()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAICreatorDALLEGroupEnabled(long companyId, long groupId)
		throws ConfigurationException {

		if (!isAICreatorDALLECompanyEnabled(companyId)) {
			return false;
		}

		AICreatorOpenAIGroupConfiguration aiCreatorOpenAIGroupConfiguration =
			_configurationProvider.getGroupConfiguration(
				AICreatorOpenAIGroupConfiguration.class, groupId);

		if (aiCreatorOpenAIGroupConfiguration.enableDALLEToCreateImages()) {
			return true;
		}

		return false;
	}

	@Override
	public void saveAICreatorOpenAICompanyConfiguration(
			long companyId, String apiKey, boolean enableChatGPT,
			boolean enableDALLE)
		throws ConfigurationException {

		_configurationProvider.saveCompanyConfiguration(
			AICreatorOpenAICompanyConfiguration.class, companyId,
			HashMapDictionaryBuilder.<String, Object>put(
				"apiKey", apiKey
			).put(
				"enableChatGPTToCreateContent", enableChatGPT
			).put(
				"enableDALLEToCreateImages", enableDALLE
			).build());
	}

	@Override
	public void saveAICreatorOpenAIGroupConfiguration(
			long groupId, String apiKey, boolean enableChatGPT,
			boolean enableDALLE)
		throws ConfigurationException {

		_configurationProvider.saveGroupConfiguration(
			AICreatorOpenAIGroupConfiguration.class, groupId,
			HashMapDictionaryBuilder.<String, Object>put(
				"apiKey", apiKey
			).put(
				"enableChatGPTToCreateContent", enableChatGPT
			).put(
				"enableDALLEToCreateImages", enableDALLE
			).build());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}