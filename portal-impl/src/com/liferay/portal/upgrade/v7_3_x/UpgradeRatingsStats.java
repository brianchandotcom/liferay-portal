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

import java.sql.PreparedStatement;

/**
 * @author Javier Gamarra
 */
public class UpgradeRatingsStats extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("RatingsStats", "createDate")) {
			alterTableAddColumn("RatingsStats", "createDate", "DATE null");
		}

		if (!hasColumn("RatingsStats", "modifiedDate")) {
			alterTableAddColumn("RatingsStats", "modifiedDate", "DATE null");
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				_getUpdateSQL("createDate", "min"))) {

			preparedStatement.executeUpdate();
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				_getUpdateSQL("modifiedDate", "max"))) {

			preparedStatement.executeUpdate();
		}
	}

	private String _getUpdateSQL(String columnName, String function) {
		return StringBundler.concat(
			"update RatingsStats set ", columnName, " = (select ", function,
			"(", columnName,
			") FROM RatingsEntry WHERE RatingsStats.classNameId = ",
			"RatingsEntry.classNameId and RatingsStats.classPK = ",
			"RatingsEntry.classPK)");
	}

}