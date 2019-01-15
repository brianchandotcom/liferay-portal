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

package com.liferay.portal.background.task.internal.upgrade;

import com.liferay.portal.background.task.internal.upgrade.v1_0_0.UpgradeBackgroundTask;
import com.liferay.portal.background.task.internal.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.portal.background.task.internal.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.portal.background.task.internal.upgrade.v2_0_0.util.BackgroundTaskTable;
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina Rodríguez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class BackgroundTaskServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "0.0.2", new UpgradeSchema(), new UpgradeKernelPackage());

		registry.register("0.0.2", "1.0.0", new UpgradeBackgroundTask());

		registry.register(
			"1.0.0", "2.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {BackgroundTaskTable.class}));
	}

}