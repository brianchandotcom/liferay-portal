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

package com.liferay.dispatch.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.BaseUuidUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(service = UpgradeStepRegistrator.class)
public class DispatchServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			UpgradeProcessFactory.addColumns(
				"DispatchTrigger", "endDate DATE null", "startDate DATE null"),
			UpgradeProcessFactory.alterColumnName(
				"DispatchTrigger", "typeSettings", "taskSettings TEXT null"),
			UpgradeProcessFactory.alterColumnName(
				"DispatchTrigger", "type_", "taskType VARCHAR(75) null"));

		registry.register(
			"2.0.0", "2.1.0",
			UpgradeProcessFactory.addColumns(
				"DispatchTrigger", "overlapAllowed BOOLEAN"));

		registry.register(
			"2.1.0", "3.0.0",
			UpgradeProcessFactory.alterColumnName(
				"DispatchTrigger", "taskType",
				"taskExecutorType VARCHAR(75) null"));

		registry.register(
			"3.0.0", "3.1.0",
			UpgradeProcessFactory.addColumns(
				"DispatchTrigger", "taskClusterMode INTEGER"));

		registry.register(
			"3.1.0", "3.1.1",
			new com.liferay.dispatch.internal.upgrade.v3_1_1.
				DispatchTriggerModelResourcePermissionUpgradeProcess());

		registry.register(
			"3.1.1", "4.0.0",
			UpgradeProcessFactory.alterColumnName(
				"DispatchTrigger", "taskClusterMode",
				"dispatchTaskClusterMode INTEGER null"),
			UpgradeProcessFactory.alterColumnName(
				"DispatchTrigger", "taskExecutorType",
				"dispatchTaskExecutorType VARCHAR(75) null"),
			UpgradeProcessFactory.alterColumnName(
				"DispatchTrigger", "taskSettings",
				"dispatchTaskSettings TEXT null"));

		registry.register(
			"4.0.0", "4.0.1",
			UpgradeProcessFactory.alterColumnType(
				"DispatchTrigger", "dispatchTaskExecutorType",
				"VARCHAR(75) null"),
			UpgradeProcessFactory.alterColumnType(
				"DispatchTrigger", "dispatchTaskSettings", "TEXT null"));

		registry.register(
			"4.0.1", "4.1.0",
			UpgradeProcessFactory.addColumns(
				"DispatchTrigger", "externalReferenceCode VARCHAR(75) null"));

		registry.register(
			"4.1.0", "4.2.0",
			new BaseUuidUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"DispatchTrigger", "dispatchTriggerId"}
					};
				}

			});

		registry.register(
			"4.2.0", "4.3.0",
			new com.liferay.dispatch.internal.upgrade.v4_3_0.
				DispatchTriggerUpgradeProcess());
	}

}