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

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.upgrade.util.VersionTreeMap;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

/**
 * @author Alicia García
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(VersionTreeMap versionTreeMap) {
		versionTreeMap.put(new Version(6, 0, 0), new UpgradeMVCCVersion());

		versionTreeMap.put(new Version(6, 0, 1), new DummyUpgradeProcess());

		versionTreeMap.put(new Version(6, 0, 2), new UpgradeLayoutSet());

		versionTreeMap.put(new Version(6, 0, 3), new UpgradeClusterGroup());

		versionTreeMap.put(new Version(6, 0, 4), new UpgradeAssetCategory());

		versionTreeMap.put(new Version(7, 0, 0), new UpgradeRatingsStats());

		versionTreeMap.put(
			new Version(7, 1, 0),
			new CTModelUpgradeProcess(
				"AssetCategory", "AssetCategoryProperty", "AssetEntry",
				"AssetLink", "AssetTag", "AssetVocabulary", "Layout",
				"LayoutFriendlyURL", "PortletPreferences",
				"ResourcePermission"));

		versionTreeMap.put(new Version(8, 0, 0), new UpgradeSchema());

		versionTreeMap.put(
			new Version(8, 1, 0),
			new CTModelUpgradeProcess(
				"AssetEntries_AssetCategories", "AssetEntries_AssetTags"));

		versionTreeMap.put(
			new Version(8, 1, 1),
			UpgradeProcessFactory.alterColumnType(
				"AssetCategory", "name", "VARCHAR(255) null"));

		versionTreeMap.put(
			new Version(8, 2, 0),
			UpgradeProcessFactory.dropColumns(
				"AssetEntries_AssetCategories", "changeType"),
			UpgradeProcessFactory.dropColumns(
				"AssetEntries_AssetTags", "changeType"),
			UpgradeProcessFactory.addColumns(
				"AssetEntries_AssetCategories", "ctChangeType BOOLEAN"),
			UpgradeProcessFactory.addColumns(
				"AssetEntries_AssetTags", "ctChangeType BOOLEAN"));

		versionTreeMap.put(
			new Version(8, 3, 0), new UpgradeUserGroupGroupRole());

		versionTreeMap.put(new Version(8, 4, 0), new UpgradeUserGroupRole());

		versionTreeMap.put(
			new Version(8, 5, 0),
			new CTModelUpgradeProcess(
				"Group_", "Groups_Orgs", "Groups_Roles", "Groups_UserGroups",
				"Image", "LayoutSet", "Organization_", "Role_", "Team", "User_",
				"UserGroup", "UserGroupGroupRole", "UserGroupRole",
				"UserGroups_Teams", "Users_Groups", "Users_Orgs", "Users_Roles",
				"Users_Teams", "Users_UserGroups", "VirtualHost"));

		versionTreeMap.put(
			new Version(8, 6, 0),
			new CTModelUpgradeProcess(
				"DLFileEntry", "DLFileEntryMetadata", "DLFileEntryType",
				"DLFileEntryTypes_DLFolders", "DLFileShortcut", "DLFileVersion",
				"DLFolder"));

		versionTreeMap.put(
			new Version(8, 7, 0), new UpgradeSocialMVCCVersion());

		versionTreeMap.put(
			new Version(8, 8, 0), new UpgradeExpandoMVCCVersion());

		versionTreeMap.put(
			new Version(8, 9, 0), new UpgradeRatingsMVCCVersion());

		versionTreeMap.put(new Version(8, 10, 0), new UpgradeResourceAction());

		versionTreeMap.put(
			new Version(8, 11, 0),
			new CTModelUpgradeProcess(
				"ExpandoColumn", "ExpandoRow", "ExpandoTable", "ExpandoValue"));

		versionTreeMap.put(
			new Version(8, 12, 0),
			new CTModelUpgradeProcess("RatingsEntry", "RatingsStats"));

		versionTreeMap.put(
			new Version(8, 13, 0),
			new CTModelUpgradeProcess(
				"WorkflowDefinitionLink", "WorkflowInstanceLink"));

		versionTreeMap.put(
			new Version(8, 14, 0),
			new CTModelUpgradeProcess(
				"SocialActivity", "SocialActivityAchievement",
				"SocialActivityCounter", "SocialActivityLimit",
				"SocialActivitySet", "SocialActivitySetting", "SocialRelation",
				"SocialRequest"));

		versionTreeMap.put(
			new Version(8, 15, 0), new CTModelUpgradeProcess("SystemEvent"));

		versionTreeMap.put(new Version(8, 16, 0), new UpgradeDLFileEntryType());

		versionTreeMap.put(new Version(8, 17, 0), new UpgradeAssetVocabulary());

		versionTreeMap.put(
			new Version(8, 18, 0), new UpgradeLayoutStyleBookEntry());

		versionTreeMap.put(
			new Version(8, 18, 1),
			UpgradeModulesFactory.create(
				new String[] {"com.liferay.layout.service"}, null));

		versionTreeMap.put(new Version(8, 18, 2), new UpgradeLayout());

		versionTreeMap.put(new Version(8, 18, 3), new DummyUpgradeProcess());

		versionTreeMap.put(new Version(8, 18, 4), new DummyUpgradeProcess());
	}

}