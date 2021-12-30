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

package com.liferay.exportimport.internal.upgrade.v1_0_2;

import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.staging.configuration.StagingConfiguration;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Tamas Molnar
 */
public class UpgradeExportImportServiceConfiguration extends UpgradeProcess {

	public UpgradeExportImportServiceConfiguration(
		ConfigurationAdmin configurationAdmin,
		ConfigurationProvider configurationProvider) {

		_configurationAdmin = configurationAdmin;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeExportImportServiceConfiguration();
	}

	private void _upgradeExportImportServiceConfiguration() throws Exception {
		Configuration[] exportImportServiceConfigurations =
			_configurationAdmin.listConfigurations(
				"(service.pid=" +
					ExportImportServiceConfiguration.class.getName() + "*)");

		if (exportImportServiceConfigurations == null) {
			return;
		}

		for (Configuration exportImportServiceConfiguration :
				exportImportServiceConfigurations) {

			Dictionary<String, Object> exportImportProperties =
				exportImportServiceConfiguration.getProperties();

			if (exportImportProperties == null) {
				continue;
			}

			Dictionary<String, Object> stagingProperties =
				HashMapDictionaryBuilder.<String, Object>put(
					"publishParentLayoutsByDefault",
					GetterUtil.getBoolean(
						exportImportProperties.remove(
							"publishParentLayoutsByDefault"),
						true)
				).put(
					"stagingDeleteTempLAROnFailure",
					GetterUtil.getBoolean(
						exportImportProperties.remove(
							"stagingDeleteTempLAROnFailure"),
						true)
				).put(
					"stagingDeleteTempLAROnSuccess",
					GetterUtil.getBoolean(
						exportImportProperties.remove(
							"stagingDeleteTempLarOnSuccess"),
						true)
				).put(
					"stagingUseVirtualHostForRemoteSite",
					GetterUtil.getBoolean(
						exportImportProperties.remove(
							"stagingUseVirtualHostForRemoteSite"))
				).build();

			exportImportServiceConfiguration.update(exportImportProperties);

			long companyId = GetterUtil.getLong(
				exportImportProperties.get("companyId"));

			if (companyId != 0) {
				stagingProperties.put("companyId", companyId);

				_configurationProvider.saveCompanyConfiguration(
					StagingConfiguration.class, companyId, stagingProperties);
			}
			else {
				_configurationProvider.saveSystemConfiguration(
					StagingConfiguration.class, stagingProperties);
			}
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final ConfigurationProvider _configurationProvider;

}