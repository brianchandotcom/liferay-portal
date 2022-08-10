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

package com.liferay.portal.upgrade.v7_1_x;

import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.upgrade.util.VersionTreeMap;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

/**
 * @author Alberto Chaparro
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(VersionTreeMap versionTreeMap) {
		versionTreeMap.put(new Version(1, 0, 0), new UpgradeSchema());

		versionTreeMap.put(
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

		versionTreeMap.put(
			new Version(1, 1, 1),
			UpgradeProcessFactory.alterColumnType(
				"Counter", "name", "VARCHAR(150) not null"));

		versionTreeMap.put(new Version(1, 1, 2), new UpgradeDB2());

		versionTreeMap.put(
			new Version(2, 0, 0), new UpgradeAssetTagsPermission());

		versionTreeMap.put(
			new Version(2, 0, 1),
			UpgradeProcessFactory.alterColumnType(
				"DLFileEntryType", "fileEntryTypeKey", "VARCHAR(75) null"));

		versionTreeMap.put(
			new Version(2, 0, 2),
			UpgradeProcessFactory.alterColumnType(
				"PasswordPolicy", "regex", "STRING null"));

		versionTreeMap.put(
			new Version(2, 0, 3), new UpgradePortalPreferences());

		versionTreeMap.put(
			new Version(2, 0, 4),
			UpgradeProcessFactory.alterColumnType(
				"UserGroup", "name", "VARCHAR(255) null"));

		versionTreeMap.put(
			new Version(2, 0, 5), new UpgradeAnnouncementsPortletId());

		versionTreeMap.put(
			new Version(2, 0, 6), new UpgradeAnnouncementsPortletPreferences());

		versionTreeMap.put(
			new Version(2, 0, 7),
			new UpgradeCalendarTimeFormatPortletPreferences());

		versionTreeMap.put(
			new Version(2, 0, 8),
			new UpgradeCalendarClassNameIdsPortletPreferences());

		versionTreeMap.put(new Version(2, 0, 9), new DummyUpgradeProcess());

		versionTreeMap.put(new Version(2, 0, 10), new DummyUpgradeProcess());

		versionTreeMap.put(new Version(2, 0, 11), new DummyUpgradeProcess());
	}

}