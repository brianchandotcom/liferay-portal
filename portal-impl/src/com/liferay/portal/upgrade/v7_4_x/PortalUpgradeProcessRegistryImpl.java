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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

import java.util.TreeMap;

/**
 * @author Pei-Jung Lan
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(
		TreeMap<Version, UpgradeProcess> upgradeProcesses) {

		upgradeProcesses.put(new Version(9, 0, 0), new UpgradeAddress());

		upgradeProcesses.put(new Version(9, 0, 1), new UpgradeModules());

		upgradeProcesses.put(new Version(9, 1, 0), new UpgradeRegion());

		upgradeProcesses.put(new Version(9, 2, 0), new UpgradeCountry());

		upgradeProcesses.put(new Version(9, 2, 1), new UpgradeListType());

		upgradeProcesses.put(
			new Version(10, 0, 0), new UpgradePortletPreferences());

		upgradeProcesses.put(new Version(11, 0, 0), new UpgradeAssetEntry());

		upgradeProcesses.put(
			new Version(12, 0, 0), new UpgradePortalPreferences());

		upgradeProcesses.put(
			new Version(12, 0, 1), new UpgradeResourceAction());

		upgradeProcesses.put(
			new Version(12, 0, 2), new UpgradeDLFileEntryType());

		upgradeProcesses.put(new Version(12, 1, 0), new UpgradeDLFileEntry());

		upgradeProcesses.put(new Version(12, 1, 1), new UpgradeDLFileVersion());

		upgradeProcesses.put(new Version(12, 2, 0), new UpgradeCompanyId());

		upgradeProcesses.put(
			new Version(12, 2, 1), new UpgradeAssetEntryTitle());

		upgradeProcesses.put(
			new Version(12, 2, 2), new UpgradePortalPreferenceValue());

		upgradeProcesses.put(new Version(13, 0, 0), new UpgradeAccount());

		upgradeProcesses.put(new Version(13, 0, 1), new UpgradeLayout());

		upgradeProcesses.put(
			new Version(13, 1, 0), new UpgradeAssetVocabulary());

		upgradeProcesses.put(new Version(13, 2, 0), new UpgradeAssetCategory());

		upgradeProcesses.put(
			new Version(13, 3, 0),
			new CTModelUpgradeProcess("Repository", "RepositoryEntry"));

		upgradeProcesses.put(new Version(13, 3, 1), new UpgradeRepository());

		upgradeProcesses.put(new Version(13, 3, 2), new UpgradeMappingTables());
	}

}