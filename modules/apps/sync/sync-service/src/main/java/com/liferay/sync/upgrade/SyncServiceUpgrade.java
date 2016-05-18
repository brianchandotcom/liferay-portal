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

package com.liferay.sync.upgrade;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.sync.service.SyncDLObjectLocalService;
import com.liferay.sync.upgrade.v1_0_2.UpgradeSchema;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SyncServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.sync.service", "0.0.1", "1.0.0",
			new com.liferay.sync.upgrade.v1_0_0.UpgradeIndex(),
			new com.liferay.sync.upgrade.v1_0_0.UpgradeSyncDLObject(
				_syncDLObjectLocalService));

		registry.register(
			"com.liferay.sync.service", "1.0.0", "1.0.1",
			new com.liferay.sync.upgrade.v1_0_1.UpgradeSyncDLObject());

		registry.register(
			"com.liferay.sync.service", "1.0.1", "1.0.2", new UpgradeSchema(),
			new com.liferay.sync.upgrade.v1_0_2.UpgradeSyncDLObject(
				_syncDLObjectLocalService));
	}

	@Reference(unbind = "-")
	protected void setSyncDLObjectLocalService(
		SyncDLObjectLocalService syncDLObjectLocalService) {

		_syncDLObjectLocalService = syncDLObjectLocalService;
	}

	private SyncDLObjectLocalService _syncDLObjectLocalService;

}