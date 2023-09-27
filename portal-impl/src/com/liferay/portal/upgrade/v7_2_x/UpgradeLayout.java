/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Matthew Chan
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"create table TEMP_TABLE (plid LONG NOT NULL PRIMARY KEY, " +
				"parentPlid LONG)");

		runSQL(
			StringBundler.concat(
				"insert into TEMP_TABLE select layout1.plid as plid, ",
				"layout2.plid as parentPlid from Layout layout1 left join ",
				"Layout layout2 on layout1.groupId = layout2.groupId and ",
				"layout1.privateLayout = layout2.privateLayout and ",
				"layout1.parentLayoutId = layout2.layoutId"));

		runSQL(
			"update Layout set parentPlid = (select coalesce(parentPlid, 0) " +
				"from TEMP_TABLE where Layout.plid = TEMP_TABLE.plid)");
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropTables("TEMP_TABLE")
		};
	}

}