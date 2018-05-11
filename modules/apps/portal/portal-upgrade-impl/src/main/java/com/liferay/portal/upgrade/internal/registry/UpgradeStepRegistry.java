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

package com.liferay.portal.upgrade.internal.registry;

import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator.Registry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Preston Crary
 */
public class UpgradeStepRegistry implements Registry {

	public UpgradeStepRegistry(int buildNumber) {
		_buildNumber = buildNumber;
	}

	public List<UpgradeInfo> getUpgradeInfos() {
		return _upgradeInfos;
	}

	@Override
	public void register(
		String bundleSymbolicName, String fromSchemaVersionString,
		String toSchemaVersionString, UpgradeStep... upgradeSteps) {

		register(fromSchemaVersionString, toSchemaVersionString, upgradeSteps);
	}

	@Override
	public void register(
		String fromSchemaVersionString, String toSchemaVersionString,
		UpgradeStep... upgradeSteps) {

		_upgradeInfos.addAll(
			UpgradeStepRegistratorTracker.createUpgradeInfos(
				fromSchemaVersionString, toSchemaVersionString, _buildNumber,
				upgradeSteps));
	}

	private final int _buildNumber;
	private final List<UpgradeInfo> _upgradeInfos = new ArrayList<>();

}