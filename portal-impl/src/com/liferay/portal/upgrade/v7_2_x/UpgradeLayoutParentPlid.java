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

/**
 * @author Matthew Chan
 */
public class UpgradeLayoutParentPlid extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		runSQL("create table TEMP (plid integer, parentPlid integer)");

		String sql = StringBundler.concat(
			"insert into TEMP select l1.plid as plid, l2.plid as parentPlid from " +
			"Layout l1 left join Layout l2 on l1.groupId = l2.groupId and " +
			"l1.privateLayout = l2.privateLayout and " +
			"l1.parentLayoutId = l2.layoutId");

		runSQL(sql);

		runSQL("create unique index IX_TEMP on TEMP (plid)");

		runSQL(
			"update Layout set parentPlid = (select coalesce(parentPlid, 0) " +
				"from TEMP where Layout.plid = TEMP.plid)");

		runSQL("drop table TEMP");
	}

}