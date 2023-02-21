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