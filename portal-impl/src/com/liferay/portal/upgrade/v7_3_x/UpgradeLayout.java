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

/**
 * @author Preston Crary
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("Layout", "headId") || hasColumn("Layout", "head")) {
			alterTableDropColumn("Layout", "headId");
			alterTableDropColumn("Layout", "head");
		}

		if (!hasColumnType("Layout", "description", "TEXT null")) {
			alterColumnType("Layout", "description", "TEXT null");
		}

		if (!hasColumn("Layout", "masterLayoutPlid")) {
			alterTableAddColumn("Layout", "masterLayoutPlid", "LONG");

			runSQL("update Layout set masterLayoutPlid = 0");
		}

		if (!hasColumn("Layout", "status")) {
			alterTableAddColumn("Layout", "status", "INTEGER");

			runSQL("update Layout set status = 0");
		}

		if (!hasColumn("Layout", "statusByUserId")) {
			alterTableAddColumn("Layout", "statusByUserId", "LONG");
		}

		if (!hasColumn("Layout", "statusByUserName")) {
			alterTableAddColumn(
				"Layout", "statusByUserName", "VARCHAR(75) null");
		}

		if (!hasColumn("Layout", "statusDate")) {
			alterTableAddColumn("Layout", "statusDate", "DATE null");
		}

		runSQL("DROP_TABLE_IF_EXISTS(LayoutVersion)");
	}

}