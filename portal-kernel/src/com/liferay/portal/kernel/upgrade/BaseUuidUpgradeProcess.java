/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

/**
 * @author Amos Fong
 * @author Brian Wing Shun Chan
 */
public abstract class BaseUuidUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String[] tableAndPrimaryKeyColumnName :
				getTableAndPrimaryKeyColumnNames()) {

			String tableName = tableAndPrimaryKeyColumnName[0];
			String primKeyColumnName = tableAndPrimaryKeyColumnName[1];

			upgradeUuid(tableName, primKeyColumnName);
		}
	}

	protected abstract String[][] getTableAndPrimaryKeyColumnNames();

	protected void upgradeUuid(String tableName, String primKeyColumnName)
		throws Exception {

		if (!hasTable(tableName)) {
			_log.error("Skip nonexistent table " + tableName);

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Upgrade table " + tableName);
		}

		if (!hasColumn(tableName, "uuid_")) {
			alterTableAddColumn(tableName, "uuid_", "VARCHAR(75) null");
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				StringBundler.concat(
					"select ", primKeyColumnName, " from ", tableName,
					" where uuid_ is null or uuid_ = ''"),
				StringBundler.concat(
					"update ", tableName, " set uuid_ = ? where ",
					primKeyColumnName, " = ?"),
				resultSet -> new Object[] {
					resultSet.getLong(primKeyColumnName)
				},
				(values, preparedStatement) -> {
					preparedStatement.setString(1, PortalUUIDUtil.generate());
					preparedStatement.setLong(2, (long)values[0]);

					preparedStatement.addBatch();
				},
				"Unable to update uuid");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUuidUpgradeProcess.class);

}