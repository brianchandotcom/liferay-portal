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
import com.liferay.portal.upgrade.v7_0_0.util.ExpandoColumnTable;
import com.liferay.portal.upgrade.v7_0_0.util.ExpandoValueTable;

import java.sql.SQLException;

/**
 * @author Tibor Lipusz
 */
public class UpgradeExpando extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type ExpandoColumn defaultData TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				ExpandoColumnTable.TABLE_NAME, ExpandoColumnTable.TABLE_COLUMNS,
				ExpandoColumnTable.TABLE_SQL_CREATE,
				ExpandoColumnTable.TABLE_SQL_ADD_INDEXES);
		}

		try {
			runSQL("alter_column_type ExpandoValue data_ TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				ExpandoValueTable.TABLE_NAME, ExpandoValueTable.TABLE_COLUMNS,
				ExpandoValueTable.TABLE_SQL_CREATE,
				ExpandoValueTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}