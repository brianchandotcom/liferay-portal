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

package com.liferay.batch.engine.internal.upgrade.v4_6_1;

import com.liferay.batch.engine.configuration.BatchEngineTaskCompanyConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Matija Petanjek
 */
public class BatchEngineTaskConfigurationUpgradeProcess extends UpgradeProcess {

	public BatchEngineTaskConfigurationUpgradeProcess(
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin,
		ConfigurationProvider configurationProvider) {

		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			"com.liferay.batch.engine.configuration." +
				"BatchEngineTaskConfiguration",
			StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			return;
		}

		_createCompanyConfigurations(properties);

		properties.remove(_CSV_FILE_COLUMN_DELIMITER);
		properties.remove(_EXPORT_BATCH_SIZE);
		properties.remove(_IMPORT_BATCH_SIZE);

		configuration.update(properties);
	}

	private void _createCompanyConfigurations(
			Dictionary<String, Object> properties)
		throws Exception {

		_companyLocalService.forEachCompanyId(
			companyId -> _configurationProvider.saveCompanyConfiguration(
				BatchEngineTaskCompanyConfiguration.class, companyId,
				HashMapDictionaryBuilder.put(
					_CSV_FILE_COLUMN_DELIMITER,
					properties.get(_CSV_FILE_COLUMN_DELIMITER)
				).put(
					_EXPORT_BATCH_SIZE, properties.get(_EXPORT_BATCH_SIZE)
				).put(
					_IMPORT_BATCH_SIZE, properties.get(_IMPORT_BATCH_SIZE)
				).build()));
	}

	private static final String _CSV_FILE_COLUMN_DELIMITER =
		"csvFileColumnDelimiter";

	private static final String _EXPORT_BATCH_SIZE = "exportBatchSize";

	private static final String _IMPORT_BATCH_SIZE = "importBatchSize";

	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;
	private final ConfigurationProvider _configurationProvider;

}