/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_1_x;

import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.upgrade.util.UpgradeVersionTreeMap;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

/**
 * @author Alberto Chaparro
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(
		UpgradeVersionTreeMap upgradeVersionTreeMap) {

		upgradeVersionTreeMap.put(new Version(1, 0, 0), new UpgradeSchema());

		upgradeVersionTreeMap.put(
			new Version(1, 1, 0),
			UpgradeModulesFactory.create(
				new String[] {
					"com.liferay.asset.category.property.service",
					"com.liferay.asset.entry.rel.service",
					"com.liferay.asset.tag.stats.service",
					"com.liferay.blogs.service",
					"com.liferay.document.library.content.service",
					"com.liferay.document.library.file.rank.service",
					"com.liferay.document.library.sync.service",
					"com.liferay.layout.page.template.service",
					"com.liferay.message.boards.service",
					"com.liferay.subscription.service",
					"com.liferay.trash.service"
				},
				null));

		upgradeVersionTreeMap.put(
			new Version(1, 1, 1),
			UpgradeProcessFactory.alterColumnType(
				"Counter", "name", "VARCHAR(150) not null"));

		upgradeVersionTreeMap.put(new Version(1, 1, 2), new UpgradeDB2());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 0), new UpgradeAssetTagsPermission());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 1),
			UpgradeProcessFactory.alterColumnType(
				"DLFileEntryType", "fileEntryTypeKey", "VARCHAR(75) null"));

		upgradeVersionTreeMap.put(
			new Version(2, 0, 2),
			UpgradeProcessFactory.alterColumnType(
				"PasswordPolicy", "regex", "STRING null"));

		upgradeVersionTreeMap.put(
			new Version(2, 0, 3), new UpgradePortalPreferences());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 4),
			UpgradeProcessFactory.alterColumnType(
				"UserGroup", "name", "VARCHAR(255) null"));

		upgradeVersionTreeMap.put(
			new Version(2, 0, 5), new UpgradeAnnouncementsPortletId());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 6), new UpgradeAnnouncementsPortletPreferences());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 7),
			new UpgradeCalendarTimeFormatPortletPreferences());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 8),
			new UpgradeCalendarClassNameIdsPortletPreferences());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 9), new DummyUpgradeProcess());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 10), new DummyUpgradeProcess());

		upgradeVersionTreeMap.put(
			new Version(2, 0, 11), new DummyUpgradeProcess());
	}

}