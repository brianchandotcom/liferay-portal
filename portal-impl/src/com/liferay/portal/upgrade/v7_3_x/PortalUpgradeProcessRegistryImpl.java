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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

import java.util.TreeMap;

/**
 * @author Alicia García
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(
		TreeMap<Version, UpgradeProcess> upgradeProcesses) {

		upgradeProcesses.put(new Version(6, 0, 0), new UpgradeMVCCVersion());

		upgradeProcesses.put(new Version(6, 0, 1), new DummyUpgradeProcess());

		upgradeProcesses.put(new Version(6, 0, 2), new UpgradeLayoutSet());

		upgradeProcesses.put(new Version(6, 0, 3), new UpgradeClusterGroup());

		upgradeProcesses.put(new Version(6, 0, 4), new UpgradeAssetCategory());

		upgradeProcesses.put(new Version(7, 0, 0), new UpgradeRatingsStats());

		upgradeProcesses.put(
			new Version(7, 1, 0),
			new CTModelUpgradeProcess(
				"AssetCategory", "AssetCategoryProperty", "AssetEntry",
				"AssetLink", "AssetTag", "AssetVocabulary", "Layout",
				"LayoutFriendlyURL", "PortletPreferences",
				"ResourcePermission"));

		upgradeProcesses.put(new Version(8, 0, 0), new UpgradeSchema());

		upgradeProcesses.put(
			new Version(8, 1, 0),
			new CTModelUpgradeProcess(
				"AssetEntries_AssetCategories", "AssetEntries_AssetTags"));

		upgradeProcesses.put(
			new Version(8, 1, 1), new UpgradeAssetCategoryName());

		upgradeProcesses.put(
			new Version(8, 2, 0), new UpgradeAssetEntryMappingTables());

		upgradeProcesses.put(
			new Version(8, 3, 0), new UpgradeUserGroupGroupRole());

		upgradeProcesses.put(new Version(8, 4, 0), new UpgradeUserGroupRole());

		upgradeProcesses.put(
			new Version(8, 5, 0),
			new CTModelUpgradeProcess(
				"Group_", "Groups_Orgs", "Groups_Roles", "Groups_UserGroups",
				"Image", "LayoutSet", "Organization_", "Role_", "Team", "User_",
				"UserGroup", "UserGroupGroupRole", "UserGroupRole",
				"UserGroups_Teams", "Users_Groups", "Users_Orgs", "Users_Roles",
				"Users_Teams", "Users_UserGroups", "VirtualHost"));

		upgradeProcesses.put(
			new Version(8, 6, 0),
			new CTModelUpgradeProcess(
				"DLFileEntry", "DLFileEntryMetadata", "DLFileEntryType",
				"DLFileEntryTypes_DLFolders", "DLFileShortcut", "DLFileVersion",
				"DLFolder"));

		upgradeProcesses.put(
			new Version(8, 7, 0), new UpgradeSocialMVCCVersion());

		upgradeProcesses.put(
			new Version(8, 8, 0), new UpgradeExpandoMVCCVersion());

		upgradeProcesses.put(
			new Version(8, 9, 0), new UpgradeRatingsMVCCVersion());

		upgradeProcesses.put(
			new Version(8, 10, 0), new UpgradeResourceAction());

		upgradeProcesses.put(
			new Version(8, 11, 0),
			new CTModelUpgradeProcess(
				"ExpandoColumn", "ExpandoRow", "ExpandoTable", "ExpandoValue"));

		upgradeProcesses.put(
			new Version(8, 12, 0),
			new CTModelUpgradeProcess("RatingsEntry", "RatingsStats"));

		upgradeProcesses.put(
			new Version(8, 13, 0),
			new CTModelUpgradeProcess(
				"WorkflowDefinitionLink", "WorkflowInstanceLink"));

		upgradeProcesses.put(
			new Version(8, 14, 0),
			new CTModelUpgradeProcess(
				"SocialActivity", "SocialActivityAchievement",
				"SocialActivityCounter", "SocialActivityLimit",
				"SocialActivitySet", "SocialActivitySetting", "SocialRelation",
				"SocialRequest"));

		upgradeProcesses.put(
			new Version(8, 15, 0), new CTModelUpgradeProcess("SystemEvent"));

		upgradeProcesses.put(
			new Version(8, 16, 0), new UpgradeDLFileEntryType());

		upgradeProcesses.put(
			new Version(8, 17, 0), new UpgradeAssetVocabulary());

		upgradeProcesses.put(
			new Version(8, 18, 0), new UpgradeLayoutStyleBookEntry());

		upgradeProcesses.put(new Version(8, 18, 1), new UpgradeModules());

		upgradeProcesses.put(new Version(8, 18, 2), new UpgradeLayout());

		upgradeProcesses.put(new Version(8, 18, 3), new DummyUpgradeProcess());

		upgradeProcesses.put(new Version(8, 18, 4), new DummyUpgradeProcess());
	}

}