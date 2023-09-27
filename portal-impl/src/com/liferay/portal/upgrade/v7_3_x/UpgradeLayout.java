/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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