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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Miguel Pastor
 */
public class UpgradeRelease extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchemaVersion();
	}

	protected String toSchemaVersion(String buildNumber) {
		StringBuilder sb = new StringBuilder(2 * buildNumber.length());

		for (int i = 0; i < buildNumber.length(); i++) {
			sb.append(buildNumber.charAt(i));
			sb.append(CharPool.PERIOD);
		}

		if (buildNumber.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	protected void upgradeSchemaVersion() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select distinct buildNumber from Release_ where " +
					"schemaVersion is null");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String buildNumber = rs.getString("buildNumber");

				String schemaVersion = toSchemaVersion(buildNumber);

				runSQL(
					"update Release_ set schemaVersion = '" + schemaVersion +
						"' where buildNumber = " + buildNumber +
							" and schemaVersion is null");
			}
		}
	}

}