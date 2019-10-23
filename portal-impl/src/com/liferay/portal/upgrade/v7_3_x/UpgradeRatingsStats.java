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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_3_x.util.RatingsStatsTable;

import java.sql.PreparedStatement;

/**
 * @author Javier Gamarra
 */
public class UpgradeRatingsStats extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("RatingsStats", "createDate")) {
			alter(
				RatingsStatsTable.class, new AlterTableAddColumn("createDate"));
		}

		if (!hasColumn("RatingsStats", "modifiedDate")) {
			alter(
				RatingsStatsTable.class,
				new AlterTableAddColumn("modifiedDate"));
		}

		try (PreparedStatement ps = connection.prepareStatement(
				_getUpdateSQL("createDate", "min"))) {

			ps.executeUpdate();
		}

		try (PreparedStatement ps = connection.prepareStatement(
				_getUpdateSQL("modifiedDate", "max"))) {

			ps.executeUpdate();
		}
	}

	private String _getUpdateSQL(String field, String function) {
		return StringBundler.concat(
			"update RatingsStats set RatingsStats.", field, " = (select ",
			function, "(", field, ") FROM RatingsEntry WHERE ",
			"RatingsStats.classNameId = RatingsEntry.classNameId and ",
			"RatingsStats.classPK = RatingsEntry.classPK)");
	}

}