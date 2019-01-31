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

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.BaseUpgradeBadColumnNames;
import com.liferay.portal.upgrade.v7_2_x.util.AssetCategoryTable;
import com.liferay.portal.upgrade.v7_2_x.util.AssetEntryTable;
import com.liferay.portal.upgrade.v7_2_x.util.AssetVocabularyTable;
import com.liferay.portal.upgrade.v7_2_x.util.CompanyTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFileEntryTypeTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFileVersionTable;
import com.liferay.portal.upgrade.v7_2_x.util.DLFolderTable;
import com.liferay.portal.upgrade.v7_2_x.util.ExportImportConfigurationTable;
import com.liferay.portal.upgrade.v7_2_x.util.GroupTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutBranchTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutPrototypeTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutRevisionTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutSetBranchTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutSetPrototypeTable;
import com.liferay.portal.upgrade.v7_2_x.util.LayoutTable;
import com.liferay.portal.upgrade.v7_2_x.util.PasswordPolicyTable;
import com.liferay.portal.upgrade.v7_2_x.util.RepositoryTable;
import com.liferay.portal.upgrade.v7_2_x.util.RoleTable;
import com.liferay.portal.upgrade.v7_2_x.util.TeamTable;
import com.liferay.portal.upgrade.v7_2_x.util.UserGroupTable;
import com.liferay.portal.upgrade.v7_2_x.util.UserIdMapperTable;

/**
 * @author Tina Tian
 */
public class UpgradeBadColumnNames extends BaseUpgradeBadColumnNames {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeBadColumnNames(AssetCategoryTable.class, "description");
		upgradeBadColumnNames(AssetEntryTable.class, "description", "visible");
		upgradeBadColumnNames(AssetVocabularyTable.class, "description");
		upgradeBadColumnNames(CompanyTable.class, "system");
		upgradeBadColumnNames(DLFileEntryTable.class, "description");
		upgradeBadColumnNames(DLFileEntryTypeTable.class, "description");
		upgradeBadColumnNames(DLFileVersionTable.class, "description");
		upgradeBadColumnNames(DLFolderTable.class, "description");
		upgradeBadColumnNames(
			ExportImportConfigurationTable.class, "description");
		upgradeBadColumnNames(GroupTable.class, "description");
		upgradeBadColumnNames(LayoutTable.class, "description", "system");
		upgradeBadColumnNames(LayoutBranchTable.class, "description");
		upgradeBadColumnNames(LayoutPrototypeTable.class, "description");
		upgradeBadColumnNames(LayoutRevisionTable.class, "description");
		upgradeBadColumnNames(LayoutSetBranchTable.class, "description");
		upgradeBadColumnNames(LayoutSetPrototypeTable.class, "description");
		upgradeBadColumnNames(
			PasswordPolicyTable.class, "description", "history");
		upgradeBadColumnNames(RepositoryTable.class, "description");
		upgradeBadColumnNames(RoleTable.class, "description");
		upgradeBadColumnNames(TeamTable.class, "description");
		upgradeBadColumnNames(UserGroupTable.class, "description");
		upgradeBadColumnNames(UserIdMapperTable.class, "description");
	}

}