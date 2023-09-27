/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luis Ortiz
 */
public class UpgradeMappingTables extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String tableName : _TABLE_NAMES) {
			alterColumnType(tableName, "companyId", "LONG NOT NULL");
		}
	}

	private static final String[] _TABLE_NAMES = {
		"AssetEntries_AssetTags", "DLFileEntryTypes_DLFolders", "Groups_Orgs",
		"Groups_Roles", "Groups_UserGroups", "UserGroups_Teams", "Users_Groups",
		"Users_Orgs", "Users_Roles", "Users_Teams", "Users_UserGroups"
	};

}