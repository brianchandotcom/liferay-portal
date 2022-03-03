package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luis Ortiz
 */
public class UpgradeCompanyIdNotNull extends UpgradeProcess {

	private static final String[] _AFFECTED_TABLES= {
		"AssetEntries_AssetTags", "DLFileEntryTypes_DLFolders", "Groups_Orgs",
		"Groups_Roles", "Groups_UserGroups", "UserGroups_Teams", "Users_Groups",
		"Users_Orgs", "Users_Roles", "Users_Teams", "Users_UserGroups"};
	
	@Override
	protected void doUpgrade() throws Exception {

		for(String tableName : _AFFECTED_TABLES) {
			if(!hasColumnType(tableName, "companyId", "LONG NOT NULL")) {
				alterColumnType(tableName, "companyId", "LONG NOT NULL");
			}
		}
	}
}
