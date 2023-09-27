/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.upgrade.util.UpgradeVersionTreeMap;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.util.PortalUpgradeProcessRegistry;

/**
 * @author Alicia García
 */
public class PortalUpgradeProcessRegistryImpl
	implements PortalUpgradeProcessRegistry {

	@Override
	public void registerUpgradeProcesses(
		UpgradeVersionTreeMap upgradeVersionTreeMap) {

		upgradeVersionTreeMap.put(
			new Version(6, 0, 0), new UpgradeMVCCVersion());

		upgradeVersionTreeMap.put(
			new Version(6, 0, 1), new DummyUpgradeProcess());

		upgradeVersionTreeMap.put(new Version(6, 0, 2), new UpgradeLayoutSet());

		upgradeVersionTreeMap.put(
			new Version(6, 0, 3), new UpgradeClusterGroup());

		upgradeVersionTreeMap.put(
			new Version(6, 0, 4), new UpgradeAssetCategory());

		upgradeVersionTreeMap.put(
			new Version(7, 0, 0), new UpgradeRatingsStats());

		upgradeVersionTreeMap.put(
			new Version(7, 1, 0),
			new CTModelUpgradeProcess(
				"AssetCategory", "AssetCategoryProperty", "AssetEntry",
				"AssetLink", "AssetTag", "AssetVocabulary", "Layout",
				"LayoutFriendlyURL", "PortletPreferences",
				"ResourcePermission"));

		upgradeVersionTreeMap.put(new Version(8, 0, 0), new UpgradeSchema());

		upgradeVersionTreeMap.put(
			new Version(8, 1, 0),
			new CTModelUpgradeProcess(
				"AssetEntries_AssetCategories", "AssetEntries_AssetTags"));

		upgradeVersionTreeMap.put(
			new Version(8, 1, 1),
			UpgradeProcessFactory.alterColumnType(
				"AssetCategory", "name", "VARCHAR(255) null"));

		upgradeVersionTreeMap.put(
			new Version(8, 2, 0),
			UpgradeProcessFactory.dropColumns(
				"AssetEntries_AssetCategories", "changeType"),
			UpgradeProcessFactory.dropColumns(
				"AssetEntries_AssetTags", "changeType"),
			UpgradeProcessFactory.addColumns(
				"AssetEntries_AssetCategories", "ctChangeType BOOLEAN"),
			UpgradeProcessFactory.addColumns(
				"AssetEntries_AssetTags", "ctChangeType BOOLEAN"));

		upgradeVersionTreeMap.put(
			new Version(8, 3, 0), new UpgradeUserGroupGroupRole());

		upgradeVersionTreeMap.put(
			new Version(8, 4, 0), new UpgradeUserGroupRole());

		upgradeVersionTreeMap.put(
			new Version(8, 5, 0),
			new CTModelUpgradeProcess(
				"Group_", "Groups_Orgs", "Groups_Roles", "Groups_UserGroups",
				"Image", "LayoutSet", "Organization_", "Role_", "Team", "User_",
				"UserGroup", "UserGroupGroupRole", "UserGroupRole",
				"UserGroups_Teams", "Users_Groups", "Users_Orgs", "Users_Roles",
				"Users_Teams", "Users_UserGroups", "VirtualHost"));

		upgradeVersionTreeMap.put(
			new Version(8, 6, 0),
			new CTModelUpgradeProcess(
				"DLFileEntry", "DLFileEntryMetadata", "DLFileEntryType",
				"DLFileEntryTypes_DLFolders", "DLFileShortcut", "DLFileVersion",
				"DLFolder"));

		upgradeVersionTreeMap.put(
			new Version(8, 7, 0), new UpgradeSocialMVCCVersion());

		upgradeVersionTreeMap.put(
			new Version(8, 8, 0), new UpgradeExpandoMVCCVersion());

		upgradeVersionTreeMap.put(
			new Version(8, 9, 0), new UpgradeRatingsMVCCVersion());

		upgradeVersionTreeMap.put(
			new Version(8, 10, 0), new UpgradeResourceAction());

		upgradeVersionTreeMap.put(
			new Version(8, 11, 0),
			new CTModelUpgradeProcess(
				"ExpandoColumn", "ExpandoRow", "ExpandoTable", "ExpandoValue"));

		upgradeVersionTreeMap.put(
			new Version(8, 12, 0),
			new CTModelUpgradeProcess("RatingsEntry", "RatingsStats"));

		upgradeVersionTreeMap.put(
			new Version(8, 13, 0),
			new CTModelUpgradeProcess(
				"WorkflowDefinitionLink", "WorkflowInstanceLink"));

		upgradeVersionTreeMap.put(
			new Version(8, 14, 0),
			new CTModelUpgradeProcess(
				"SocialActivity", "SocialActivityAchievement",
				"SocialActivityCounter", "SocialActivityLimit",
				"SocialActivitySet", "SocialActivitySetting", "SocialRelation",
				"SocialRequest"));

		upgradeVersionTreeMap.put(
			new Version(8, 15, 0), new CTModelUpgradeProcess("SystemEvent"));

		upgradeVersionTreeMap.put(
			new Version(8, 16, 0), new UpgradeDLFileEntryType());

		upgradeVersionTreeMap.put(
			new Version(8, 17, 0), new UpgradeAssetVocabulary());

		upgradeVersionTreeMap.put(
			new Version(8, 18, 0), new UpgradeLayoutStyleBookEntry());

		upgradeVersionTreeMap.put(
			new Version(8, 18, 1),
			UpgradeModulesFactory.create(
				new String[] {"com.liferay.layout.service"}, null));

		upgradeVersionTreeMap.put(new Version(8, 18, 2), new UpgradeLayout());

		upgradeVersionTreeMap.put(
			new Version(8, 18, 3), new DummyUpgradeProcess());

		upgradeVersionTreeMap.put(
			new Version(8, 18, 4), new DummyUpgradeProcess());
	}

}