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

import com.liferay.portal.kernel.upgrade.UpgradeCTModel;
import com.liferay.portal.kernel.upgrade.UpgradePrimaryKeyCompanyId;
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

		upgradeProcesses.put(new Version(6, 0, 1), new UpgradeLayout());

		upgradeProcesses.put(new Version(6, 0, 2), new UpgradeLayoutSet());

		upgradeProcesses.put(new Version(6, 0, 3), new UpgradeClusterGroup());

		upgradeProcesses.put(new Version(6, 0, 4), new UpgradeAssetCategory());

		upgradeProcesses.put(new Version(7, 0, 0), new UpgradeRatingsStats());

		upgradeProcesses.put(
			new Version(7, 1, 0),
			new UpgradeCTModel(
				"AssetCategory", "AssetCategoryProperty", "AssetEntry",
				"AssetLink", "AssetTag", "AssetVocabulary", "Layout",
				"LayoutFriendlyURL", "PortletPreferences",
				"ResourcePermission"));

		upgradeProcesses.put(new Version(8, 0, 0), new UpgradeSchema());

		upgradeProcesses.put(
			new Version(8, 1, 0),
			new UpgradePrimaryKeyCompanyId(
				"Account_", "Address", "AnnouncementsDelivery",
				"AnnouncementsEntry", "AnnouncementsFlag", "AssetCategory",
				"AssetEntries_AssetCategories", "AssetEntries_AssetTags",
				"AssetEntry", "AssetLink", "AssetTag", "AssetVocabulary",
				"BrowserTracker", "Contact_", "DLFileEntry",
				"DLFileEntryMetadata", "DLFileEntryType",
				"DLFileEntryTypes_DLFolders", "DLFileShortcut", "DLFileVersion",
				"DLFolder", "EmailAddress", "ExpandoColumn", "ExpandoRow",
				"ExpandoTable", "ExpandoValue", "ExportImportConfiguration",
				"Group_", "Groups_Orgs", "Groups_Roles", "Groups_UserGroups",
				"Image", "Layout", "LayoutBranch", "LayoutFriendlyURL",
				"LayoutPrototype", "LayoutRevision", "LayoutSet",
				"LayoutSetBranch", "LayoutSetPrototype", "MembershipRequest",
				"Organization_", "OrgGroupRole", "OrgLabor", "PasswordPolicy",
				"PasswordPolicyRel", "PasswordTracker", "Phone",
				"PluginSetting", "Portlet", "PortletItem", "PortletPreferences",
				"RatingsEntry", "RatingsStats", "RecentLayoutBranch",
				"RecentLayoutRevision", "RecentLayoutSetBranch", "Repository",
				"RepositoryEntry", "ResourcePermission", "Role_",
				"SocialActivity", "SocialActivityAchievement",
				"SocialActivityCounter", "SocialActivityLimit",
				"SocialActivitySet", "SocialActivitySetting", "SocialRelation",
				"SocialRequest", "SystemEvent", "Team", "Ticket",
				"UserNotificationDelivery", "User_", "UserGroup",
				"UserGroupGroupRole", "UserGroupRole", "UserGroups_Teams",
				"UserIdMapper", "UserNotificationEvent", "Users_Groups",
				"Users_Orgs", "Users_Roles", "Users_Teams", "Users_UserGroups",
				"UserTracker", "UserTrackerPath", "VirtualHost", "WebDAVProps",
				"Website", "WorkflowDefinitionLink", "WorkflowInstanceLink"));
	}

}