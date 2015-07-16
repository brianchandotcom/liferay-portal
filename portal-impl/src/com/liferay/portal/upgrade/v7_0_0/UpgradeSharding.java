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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Cristina González
 */
public class UpgradeSharding extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("alter table AnnouncementsFlag add companyId LONG;");

		initCompanyId("AnnouncementsFlag");

		runSQL("alter table AssetEntries_AssetCategories add companyId LONG;");

		initCompanyId("AssetEntries_AssetCategories");

		runSQL("alter table AssetEntries_AssetTags add companyId LONG;");

		initCompanyId("AssetEntries_AssetTags");

		runSQL("alter table AssetTagStats add companyId LONG;");

		initCompanyId("AssetTagStats");

		runSQL("alter table BrowserTracker add companyId LONG;");

		initCompanyId("BrowserTracker");

		runSQL("alter table DLFileEntryMetadata add companyId LONG;");

		initCompanyId("DLFileEntryMetadata");

		runSQL("alter table DLFileEntryTypes_DLFolders add companyId LONG;");

		initCompanyId("DLFileEntryTypes_DLFolders");

		runSQL("alter table DLSyncEvent add companyId LONG;");

		initCompanyId("DLSyncEvent");

		runSQL("alter table Groups_Orgs add companyId LONG;");

		initCompanyId("Groups_Orgs");

		runSQL("alter table Groups_Roles add companyId LONG;");

		initCompanyId("Groups_Roles");

		runSQL("alter table Groups_UserGroups add companyId LONG;");

		initCompanyId("Groups_UserGroups");

		runSQL("alter table Image add companyId LONG;");

		initCompanyId("Image");

		runSQL("alter table Marketplace_Module add companyId LONG;");

		initCompanyId("Marketplace_Module");

		runSQL("alter table MBStatsUser add companyId LONG;");

		initCompanyId("MBStatsUser");

		runSQL("alter table OrgGroupRole add companyId LONG;");

		initCompanyId("OrgGroupRole");

		runSQL("alter table OrgLabor add companyId LONG;");

		initCompanyId("OrgLabor");

		runSQL("alter table PasswordPolicyRel add companyId LONG;");

		initCompanyId("PasswordPolicyRel");

		runSQL("alter table PasswordTracker add companyId LONG;");

		initCompanyId("PasswordTracker");

		runSQL("alter table PortletPreferences add companyId LONG;");

		initCompanyId("PortletPreferences");

		runSQL("alter table RatingsStats add companyId LONG;");

		initCompanyId("RatingsStats");

		runSQL("alter table ResourceBlockPermission add companyId LONG;");

		initCompanyId("ResourceBlockPermission");

		runSQL(
			"alter table SCFrameworkVersi_SCProductVers add companyId LONG;");

		initCompanyId("SCFrameworkVersi_SCProductVers");

		runSQL("alter table SCLicense add companyId LONG;");

		initCompanyId("SCLicense");

		runSQL("alter table SCLicenses_SCProductEntries add companyId LONG;");

		initCompanyId("SCLicenses_SCProductEntries");

		runSQL("alter table ServiceComponent add companyId LONG;");

		initCompanyId("ServiceComponent");

		runSQL("alter table TrashVersion add companyId LONG;");

		initCompanyId("TrashVersion");

		runSQL("alter table UserGroupGroupRole add companyId LONG;");

		initCompanyId("UserGroupGroupRole");

		runSQL("alter table UserGroupRole add companyId LONG;");

		initCompanyId("UserGroupRole");

		runSQL("alter table UserGroups_Teams add companyId LONG;");

		initCompanyId("UserGroups_Teams");

		runSQL("alter table UserIdMapper add companyId LONG;");

		initCompanyId("UserIdMapper");

		runSQL("alter table Users_Groups add companyId LONG;");

		initCompanyId("Users_Groups");

		runSQL("alter table Users_Orgs add companyId LONG;");

		initCompanyId("Users_Orgs");

		runSQL("alter table Users_Roles add companyId LONG;");

		initCompanyId("Users_Roles");

		runSQL("alter table Users_Teams add companyId LONG;");

		initCompanyId("Users_Teams");

		runSQL("alter table Users_UserGroups add companyId LONG;");

		initCompanyId("Users_UserGroups");

		runSQL("alter table UserTrackerPath add companyId LONG;");

		initCompanyId("UserTrackerPath");
	}

	protected void initCompanyId(String tableName) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update " + tableName + " set companyId = ?");

			ps.setLong(1, 0);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}