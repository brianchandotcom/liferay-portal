/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
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
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select distinct buildNumber from Release_ where " +
					"schemaVersion is null or schemaVersion = ''");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String buildNumber = resultSet.getString("buildNumber");

				String schemaVersion = toSchemaVersion(buildNumber);

				runSQL(
					StringBundler.concat(
						"update Release_ set schemaVersion = '", schemaVersion,
						"' where buildNumber = ", buildNumber,
						" and (schemaVersion is null or schemaVersion = '')"));
			}
		}
	}

}