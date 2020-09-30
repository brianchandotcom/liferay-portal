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

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.LayoutTable;

/**
 * @author Preston Crary
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(LayoutTable.TABLE_NAME, "headId") ||
			hasColumn(LayoutTable.TABLE_NAME, "head")) {

			alter(
				LayoutTable.class, new AlterTableDropColumn("headId"),
				new AlterTableDropColumn("head"));
		}

		if (!hasColumnType(
				LayoutTable.TABLE_NAME, "description", "TEXT null")) {

			alter(
				LayoutTable.class,
				new UpgradeProcess.AlterColumnType("description", "TEXT null"));
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "masterLayoutPlid")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("masterLayoutPlid", "LONG"));

			runSQL("update Layout set masterLayoutPlid = 0");
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "status")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("status", "INTEGER"));

			runSQL("update Layout set status = 0");
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "statusByUserId")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("statusByUserId", "LONG"));
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "statusByUserName")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn(
					"statusByUserName", "VARCHAR(75) null"));
		}

		if (!hasColumn(LayoutTable.TABLE_NAME, "statusDate")) {
			alter(
				LayoutTable.class,
				new AlterTableAddColumn("statusDate", "DATE null"));
		}

		runSQL("DROP_TABLE_IF_EXISTS(LayoutVersion)");
	}

}