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

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

import java.util.TreeMap;

/**
 * @author José Ángel Jiménez
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(
		TreeMap<Version, UpgradeProcess> upgradeProcesses) {

		upgradeProcesses.put(
			new Version(3, 0, 0), new UpgradeSQLServerDatetime());

		upgradeProcesses.put(new Version(4, 0, 0), new UpgradeSchema());

		upgradeProcesses.put(new Version(5, 0, 0), new UpgradeBadColumnNames());

		upgradeProcesses.put(new Version(5, 0, 1), new UpgradePersonalMenu());

		upgradeProcesses.put(new Version(5, 0, 2), new UpgradeCountry());

		upgradeProcesses.put(new Version(5, 0, 3), new UpgradeModules());

		upgradeProcesses.put(new Version(5, 0, 4), new UpgradeLayout());

		upgradeProcesses.put(new Version(5, 0, 5), new UpgradeThemeId());

		upgradeProcesses.put(new Version(5, 1, 0), new UpgradeMVCCVersion());

		upgradeProcesses.put(new Version(5, 1, 1), new UpgradeVirtualHost());
	}

}