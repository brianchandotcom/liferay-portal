/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v6_1_0.util.AssetEntryTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type AssetEntry title STRING null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				AssetEntryTable.TABLE_NAME, AssetEntryTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(AssetEntryTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(AssetEntryTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}

		upgradeIGImageClassName();
	}

	protected void upgradeIGImageClassName() throws Exception {
		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class.getName());
		long igImageClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.imagegallery.model.IGImage");

		runSQL(
			"update AssetEntry set classNameId = " + igImageClassNameId +
				" where classNameId = " + dlFileEntryClassNameId);
	}

}