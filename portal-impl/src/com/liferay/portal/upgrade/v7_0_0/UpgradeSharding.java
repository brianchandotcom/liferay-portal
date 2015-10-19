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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Cristina González
 */
public class UpgradeSharding extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("alter table AnnouncementsFlag add companyId LONG default 0");
		runSQL(
			"alter table AssetEntries_AssetCategories add companyId " +
				"LONG default 0");
		runSQL("alter table AssetEntries_AssetTags add companyId LONG;");
		runSQL("alter table AssetTagStats add companyId LONG default 0");
		runSQL("alter table BrowserTracker add companyId LONG default 0");
		runSQL("alter table DLFileEntryMetadata add companyId LONG default 0");
		runSQL(
			"alter table DLFileEntryTypes_DLFolders add companyId " +
				"LONG default 0");
		runSQL("alter table DLSyncEvent add companyId LONG default 0");
		runSQL("alter table Groups_Orgs add companyId LONG default 0");
		runSQL("alter table Groups_Roles add companyId LONG default 0");
		runSQL("alter table Groups_UserGroups add companyId LONG default 0");
		runSQL("alter table Image add companyId LONG default 0");
		runSQL("alter table Marketplace_Module add companyId LONG default 0");
		runSQL("alter table MBStatsUser add companyId LONG default 0");
		runSQL("alter table OrgGroupRole add companyId LONG default 0");
		runSQL("alter table OrgLabor add companyId LONG default 0");
		runSQL("alter table PasswordPolicyRel add companyId LONG default 0");
		runSQL("alter table PasswordTracker add companyId LONG default 0");
		runSQL("alter table PortletPreferences add companyId LONG default 0");
		runSQL("alter table RatingsStats add companyId LONG default 0");
		runSQL(
			"alter table ResourceBlockPermission add companyId LONG default 0");
		runSQL(
			"alter table SCFrameworkVersi_SCProductVers add companyId " +
				"LONG default 0");
		runSQL("alter table SCLicense add companyId LONG default 0");
		runSQL(
			"alter table SCLicenses_SCProductEntries add companyId " +
				"LONG default 0");
		runSQL("alter table ServiceComponent add companyId LONG default 0");
		runSQL("alter table TrashVersion add companyId LONG default 0");
		runSQL("alter table UserGroupGroupRole add companyId LONG default 0");
		runSQL("alter table UserGroupRole add companyId LONG default 0");
		runSQL("alter table UserGroups_Teams add companyId LONG default 0");
		runSQL("alter table UserIdMapper add companyId LONG default 0");
		runSQL("alter table Users_Groups add companyId LONG default 0");
		runSQL("alter table Users_Orgs add companyId LONG default 0");
		runSQL("alter table Users_Roles add companyId LONG default 0");
		runSQL("alter table Users_Teams add companyId LONG default 0");
		runSQL("alter table Users_UserGroups add companyId LONG default 0");
		runSQL("alter table UserTrackerPath add companyId LONG default 0");
	}

}