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

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Cristina González
 */
public class UpgradeSharding extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addCompanyIdColumn("AnnouncementsFlag");
		addCompanyIdColumn("AssetEntries_AssetCategories");
		addCompanyIdColumn("AssetEntries_AssetTags add companyId LONG;");
		addCompanyIdColumn("AssetTagStats");
		addCompanyIdColumn("BrowserTracker");
		addCompanyIdColumn("DLFileEntryMetadata");
		addCompanyIdColumn("DLFileEntryTypes_DLFolders");
		addCompanyIdColumn("DLSyncEvent");
		addCompanyIdColumn("Groups_Orgs");
		addCompanyIdColumn("Groups_Roles");
		addCompanyIdColumn("Groups_UserGroups");
		addCompanyIdColumn("Image");
		addCompanyIdColumn("Marketplace_Module");
		addCompanyIdColumn("MBStatsUser");
		addCompanyIdColumn("OrgGroupRole");
		addCompanyIdColumn("OrgLabor");
		addCompanyIdColumn("PasswordPolicyRel");
		addCompanyIdColumn("PasswordTracker");
		addCompanyIdColumn("PortletPreferences");
		addCompanyIdColumn("RatingsStats");
		addCompanyIdColumn("ResourceBlockPermission");
		addCompanyIdColumn("SCFrameworkVersi_SCProductVers");
		addCompanyIdColumn("SCLicense");
		addCompanyIdColumn("SCLicenses_SCProductEntries");
		addCompanyIdColumn("ServiceComponent");
		addCompanyIdColumn("TrashVersion");
		addCompanyIdColumn("UserGroupGroupRole");
		addCompanyIdColumn("UserGroupRole");
		addCompanyIdColumn("UserGroups_Teams");
		addCompanyIdColumn("UserIdMapper");
		addCompanyIdColumn("Users_Groups");
		addCompanyIdColumn("Users_Orgs");
		addCompanyIdColumn("Users_Roles");
		addCompanyIdColumn("Users_Teams");
		addCompanyIdColumn("Users_UserGroups");
		addCompanyIdColumn("UserTrackerPath");
	}

	protected void addCompanyIdColumn(String tableName)
		throws IOException, SQLException {

		runSQL("alter table " + tableName + "add companyId LONG default 0");
	}

}