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
import com.liferay.portal.upgrade.v7_0_0.util.BackgroundTaskTable;

import java.sql.SQLException;

/**
 * @author Cristina González
 */
public class UpgradeBackgroundTask extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type BackgroundTask name VARCHAR(255) null");
			runSQL(
				"alter_column_name BackgroundTask taskContext taskContextMap " +
					"TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				BackgroundTaskTable.TABLE_NAME,
				BackgroundTaskTable.TABLE_COLUMNS,
				BackgroundTaskTable.TABLE_SQL_CREATE,
				BackgroundTaskTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}