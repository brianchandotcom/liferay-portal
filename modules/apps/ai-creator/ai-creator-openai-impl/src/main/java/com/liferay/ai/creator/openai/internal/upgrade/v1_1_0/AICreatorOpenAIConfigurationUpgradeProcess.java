/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.internal.upgrade.v1_1_0;

import com.liferay.ai.creator.openai.configuration.AICreatorOpenAICompanyConfiguration;
import com.liferay.ai.creator.openai.configuration.AICreatorOpenAIGroupConfiguration;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Roberto Díaz
 */
public class AICreatorOpenAIConfigurationUpgradeProcess extends UpgradeProcess {

	public AICreatorOpenAIConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin,
		ConfigurationProvider configurationProvider) {

		_configurationAdmin = configurationAdmin;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateCompanyConfigurations();
		_updateGroupConfigurations();
	}

	private Dictionary<String, Object> _createDictionary(
		Dictionary<String, Object> properties) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"apiKey", properties.get("apiKey")
		).put(
			"enableChatGPTToCreateContent",
			GetterUtil.getBoolean(
				properties.get("enableOpenAIToCreateContent"), true)
		).put(
			"enableDALLEToCreateImages",
			GetterUtil.getBoolean(
				properties.get("enableDALLEToCreateImages"), true)
		).build();
	}

	private Configuration[] _getScopedConfigurations(String className)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(%s=%s.scoped)", ConfigurationAdmin.SERVICE_FACTORYPID,
				className));

		if (configurations == null) {
			return new Configuration[0];
		}

		return configurations;
	}

	private void _updateCompanyConfigurations() throws Exception {
		Configuration[] aiCreatorOpenAICompanyConfigurations =
			_getScopedConfigurations(
				AICreatorOpenAICompanyConfiguration.class.getName());

		for (Configuration aiCreatorOpenAICompanyConfiguration :
				aiCreatorOpenAICompanyConfigurations) {

			Dictionary<String, Object> properties =
				aiCreatorOpenAICompanyConfiguration.getProperties();

			if (properties == null) {
				return;
			}

			_configurationProvider.saveCompanyConfiguration(
				AICreatorOpenAICompanyConfiguration.class,
				GetterUtil.getLong(
					properties.get(
						ExtendedObjectClassDefinition.Scope.COMPANY.
							getPropertyKey())),
				_createDictionary(properties));
		}
	}

	private void _updateGroupConfigurations() throws Exception {
		Configuration[] aiCreatorOpenAIGroupConfigurations =
			_getScopedConfigurations(
				AICreatorOpenAIGroupConfiguration.class.getName());

		for (Configuration aiCreatorOpenAIGroupConfiguration :
				aiCreatorOpenAIGroupConfigurations) {

			Dictionary<String, Object> properties =
				aiCreatorOpenAIGroupConfiguration.getProperties();

			if (properties == null) {
				return;
			}

			_configurationProvider.saveGroupConfiguration(
				AICreatorOpenAIGroupConfiguration.class,
				GetterUtil.getLong(
					properties.get(
						ExtendedObjectClassDefinition.Scope.GROUP.
							getPropertyKey())),
				_createDictionary(properties));
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final ConfigurationProvider _configurationProvider;

}