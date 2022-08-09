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
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Preston Crary
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("Layout", "masterLayoutPlid")) {
			alterTableAddColumn("Layout", "masterLayoutPlid", "LONG");

			runSQL("update Layout set masterLayoutPlid = 0");
		}

		if (!hasColumn("Layout", "status")) {
			alterTableAddColumn("Layout", "status", "INTEGER");

			runSQL("update Layout set status = 0");
		}

		runSQL("DROP_TABLE_IF_EXISTS(LayoutVersion)");
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"Layout", "statusByUserId LONG",
				"statusByUserName VARCHAR(75) null", "statusDate DATE null")
		};
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("Layout", "headId", "head"),
			UpgradeProcessFactory.alterColumnType(
				"Layout", "description", "TEXT null")
		};
	}

}