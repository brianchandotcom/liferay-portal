/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.upgrade.util.UpgradeVersionTreeMap;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

/**
 * @author José Ángel Jiménez
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(
		UpgradeVersionTreeMap upgradeVersionTreeMap) {

		upgradeVersionTreeMap.put(new Version(3, 0, 0), new UpgradeSchema());

		upgradeVersionTreeMap.put(
			new Version(4, 0, 0), new UpgradeSQLServerDatetime());

		upgradeVersionTreeMap.put(
			new Version(5, 0, 0), new UpgradeBadColumnNames());

		upgradeVersionTreeMap.put(
			new Version(5, 0, 1), new UpgradePersonalMenu());

		upgradeVersionTreeMap.put(new Version(5, 0, 2), new UpgradeCountry());

		upgradeVersionTreeMap.put(
			new Version(5, 0, 3),
			UpgradeModulesFactory.create(
				new String[] {
					"com.liferay.asset.service",
					"com.liferay.document.library.repository.cmis.impl"
				},
				null));

		upgradeVersionTreeMap.put(new Version(5, 0, 4), new UpgradeLayout());

		upgradeVersionTreeMap.put(new Version(5, 0, 5), new UpgradeThemeId());

		upgradeVersionTreeMap.put(
			new Version(5, 1, 0), new UpgradeMVCCVersion());

		upgradeVersionTreeMap.put(
			new Version(5, 1, 1), new UpgradeVirtualHost());

		upgradeVersionTreeMap.put(
			new Version(5, 1, 2), new DummyUpgradeProcess());

		upgradeVersionTreeMap.put(
			new Version(5, 1, 3), new DummyUpgradeProcess());

		upgradeVersionTreeMap.put(
			new Version(5, 1, 4), new DummyUpgradeProcess());
	}

}