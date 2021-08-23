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

package com.liferay.translation.google.cloud.translator.internal.upgrade.v1_0_0;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.translation.google.cloud.translator.internal.configuration.GoogleCloudTranslatorConfiguration;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * @author Alicia García
 */
public class GoogleCloudTranslatorConfigurationUpgradeProcess
	extends UpgradeProcess {

	public GoogleCloudTranslatorConfigurationUpgradeProcess(
		CompanyLocalService companyLocalService,
		ConfigurationProvider configurationProvider) {

		_companyLocalService = companyLocalService;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeConfiguration();
	}

	private void _upgradeConfiguration() throws Exception {
		if (_googleCloudTranslatorConfiguration.enabled()) {
			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("enabled", Boolean.TRUE);
			properties.put(
				"serviceAccountPrivateKey",
				_googleCloudTranslatorConfiguration.serviceAccountPrivateKey());

			_companyLocalService.forEachCompanyId(
				companyId -> _configurationProvider.saveCompanyConfiguration(
					GoogleCloudTranslatorConfiguration.class, companyId,
					properties));
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final ConfigurationProvider _configurationProvider;
	private final GoogleCloudTranslatorConfiguration
		_googleCloudTranslatorConfiguration =
			ConfigurableUtil.createConfigurable(
				GoogleCloudTranslatorConfiguration.class,
				new HashMapDictionary<String, Object>());

}