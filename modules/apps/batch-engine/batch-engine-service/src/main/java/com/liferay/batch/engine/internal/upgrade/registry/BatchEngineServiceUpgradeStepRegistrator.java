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

package com.liferay.batch.engine.internal.upgrade.registry;

import com.liferay.batch.engine.internal.upgrade.v4_5_0.util.BatchEngineImportTaskErrorTable;
import com.liferay.batch.engine.internal.upgrade.v4_6_1.BatchEngineTaskConfigurationUpgradeProcess;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = UpgradeStepRegistrator.class)
public class BatchEngineServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("2.0.0", "3.0.0", new DummyUpgradeStep());

		registry.register(
			"3.0.0", "4.0.0",
			UpgradeProcessFactory.dropColumns(
				"BatchEngineExportTask", "version"),
			UpgradeProcessFactory.dropColumns(
				"BatchEngineImportTask", "version"));

		registry.register(
			"4.0.0", "4.0.1",
			UpgradeProcessFactory.alterColumnType(
				"BatchEngineExportTask", "className", "VARCHAR(255) null"),
			UpgradeProcessFactory.alterColumnType(
				"BatchEngineImportTask", "className", "VARCHAR(255) null"));

		registry.register(
			"4.0.1", "4.1.0",
			UpgradeProcessFactory.addColumns(
				"BatchEngineExportTask",
				"taskItemDelegateName VARCHAR(75) null"),
			UpgradeProcessFactory.addColumns(
				"BatchEngineImportTask",
				"taskItemDelegateName VARCHAR(75) null"));

		registry.register(
			"4.1.0", "4.2.0",
			UpgradeProcessFactory.addColumns(
				"BatchEngineImportTask", "processedItemsCount INTEGER",
				"totalItemsCount INTEGER"));

		registry.register(
			"4.2.0", "4.3.0",
			UpgradeProcessFactory.alterColumnType(
				"BatchEngineExportTask", "fieldNames", "VARCHAR(1000) null"));

		registry.register(
			"4.3.0", "4.3.1",
			UpgradeProcessFactory.alterColumnType(
				"BatchEngineExportTask", "errorMessage", "TEXT null"),
			UpgradeProcessFactory.alterColumnType(
				"BatchEngineImportTask", "errorMessage", "TEXT null"));

		registry.register(
			"4.3.1", "4.4.0",
			UpgradeProcessFactory.addColumns(
				"BatchEngineExportTask", "processedItemsCount INTEGER",
				"totalItemsCount INTEGER"));

		registry.register(
			"4.4.0", "4.5.0", BatchEngineImportTaskErrorTable.create(),
			UpgradeProcessFactory.addColumns(
				"BatchEngineImportTask", "importStrategy INTEGER"));

		registry.register(
			"4.5.0", "4.6.0",
			UpgradeProcessFactory.addColumns(
				"BatchEngineExportTask",
				"externalReferenceCode VARCHAR(75) null"),
			UpgradeProcessFactory.addColumns(
				"BatchEngineImportTask",
				"externalReferenceCode VARCHAR(75) null"));

		registry.register(
			"4.6.0", "4.6.1",
			new BatchEngineTaskConfigurationUpgradeProcess(
				_companyLocalService, _configurationAdmin,
				_configurationProvider));

		registry.register(
			"4.6.1", "4.6.2",
			UpgradeProcessFactory.alterColumnType(
				"BatchEngineExportTask", "fieldNames", "STRING null"));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}