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

package com.liferay.portal.upgrade.v7_0_0;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeCompanyId
	extends com.liferay.portal.upgrade.util.UpgradeCompanyId {

	@Override
	protected String[] getTableNames() {
		return new String[] {
			"AnnouncementsFlag", "AssetEntries_AssetCategories",
			"AssetEntries_AssetTags", "AssetTagStats", "BrowserTracker",
			"DLFileEntryMetadata", "DLFileEntryTypes_DLFolders", "DLSyncEvent",
			"Groups_Orgs", "Groups_Roles", "Groups_UserGroups", "Image",
			"MBStatsUser", "OrgGroupRole", "OrgLabor", "PasswordPolicyRel",
			"PasswordTracker", "PortletPreferences", "RatingsStats",
			"ResourceBlockPermission", "SCFrameworkVersi_SCProductVers",
			"SCLicense", "SCLicenses_SCProductEntries", "TrashVersion",
			"UserGroupGroupRole", "UserGroupRole", "UserGroups_Teams",
			"UserIdMapper", "Users_Groups", "Users_Orgs", "Users_Roles",
			"Users_Teams", "Users_UserGroups", "UserTrackerPath"
		};
	}

}