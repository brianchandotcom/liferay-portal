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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Luis Ortiz
 */
public class UpgradeMappingTables extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String tableName : _TABLE_NAMES) {
			if (!hasColumnType(tableName, "companyId", "LONG NOT NULL")) {
				alterColumnType(tableName, "companyId", "LONG NOT NULL");
			}
		}
	}

	private static final String[] _TABLE_NAMES = {
		"AssetEntries_AssetTags", "DLFileEntryTypes_DLFolders", "Groups_Orgs",
		"Groups_Roles", "Groups_UserGroups", "UserGroups_Teams", "Users_Groups",
		"Users_Orgs", "Users_Roles", "Users_Teams", "Users_UserGroups"
	};

}