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
				StringBundler.concat(
					"update RatingsStats set RatingsStats.createDate = ",
					"(SELECT createDate FROM RatingsEntry WHERE ",
					"RatingsStats.classNameId = RatingsEntry.classNameId and ",
					"RatingsStats.classPK = RatingsEntry.classPK and ",
					"RatingsEntry.entryId = (SELECT MIN(entryId) from ",
					"RatingsEntry TEMP_TABLE where RatingsEntry.classNameId = ",
					"TEMP_TABLE.classNameId and RatingsEntry.classPK = ",
					"TEMP_TABLE.classPK))"))) {

			ps.executeUpdate();
		}

		try (PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"update RatingsStats set RatingsStats.modifiedDate = ",
					"(SELECT modifiedDate FROM RatingsEntry WHERE ",
					"RatingsStats.classNameId = RatingsEntry.classNameId and ",
					"RatingsStats.classPK = RatingsEntry.classPK and ",
					"RatingsEntry.entryId = (SELECT MAX(entryId) from ",
					"RatingsEntry TEMP_TABLE where RatingsEntry.classNameId = ",
					"TEMP_TABLE.classNameId and RatingsEntry.classPK = ",
					"TEMP_TABLE.classPK))"))) {

			ps.executeUpdate();
		}
	}

}