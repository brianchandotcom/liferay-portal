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

package com.liferay.batch.engine.internal.upgrade;

import com.liferay.batch.engine.internal.upgrade.v4_0_0.VersionUpgradeProcess;
import com.liferay.batch.engine.internal.upgrade.v4_0_1.ClassNameUpgradeProcess;
import com.liferay.batch.engine.internal.upgrade.v4_1_0.TaskItemDelegateNameUpgradeProcess;
import com.liferay.batch.engine.internal.upgrade.v4_2_0.BatchEngineImportTaskUpgradeProcess;
import com.liferay.batch.engine.internal.upgrade.v4_3_0.BatchEngineExportTaskUpgradeProcess;
import com.liferay.batch.engine.internal.upgrade.v4_3_1.BatchEngineTaskUpgradeProcess;
import com.liferay.batch.engine.internal.upgrade.v4_5_0.util.BatchEngineImportTaskErrorTable;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class BatchEngineServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("2.0.0", "3.0.0", new DummyUpgradeStep());

		registry.register("3.0.0", "4.0.0", new VersionUpgradeProcess());

		registry.register("4.0.0", "4.0.1", new ClassNameUpgradeProcess());

		registry.register(
			"4.0.1", "4.1.0", new TaskItemDelegateNameUpgradeProcess());

		registry.register(
			"4.1.0", "4.2.0", new BatchEngineImportTaskUpgradeProcess());

		registry.register(
			"4.2.0", "4.3.0", new BatchEngineExportTaskUpgradeProcess());

		registry.register(
			"4.3.0", "4.3.1", new BatchEngineTaskUpgradeProcess());

		registry.register(
			"4.3.1", "4.4.0",
			new com.liferay.batch.engine.internal.upgrade.v4_4_0.
				BatchEngineExportTaskUpgradeProcess());

		registry.register(
			"4.4.0", "4.5.0", BatchEngineImportTaskErrorTable.create(),
			new com.liferay.batch.engine.internal.upgrade.v4_5_0.
				BatchEngineImportTaskUpgradeProcess());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}