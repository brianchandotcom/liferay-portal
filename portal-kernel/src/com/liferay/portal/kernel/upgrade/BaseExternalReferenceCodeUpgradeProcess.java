/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Amos Fong
 * @author Brian Wing Shun Chan
 */
public abstract class BaseExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String[] tableAndPrimaryKeyColumnName :
				getTableAndPrimaryKeyColumnNames()) {

			String tableName = tableAndPrimaryKeyColumnName[0];
			String primKeyColumnName = tableAndPrimaryKeyColumnName[1];

			upgradeExternalReferenceCode(tableName, primKeyColumnName);
		}
	}

	protected abstract String[][] getTableAndPrimaryKeyColumnNames();

	protected boolean isUseUUID(String tableName) throws Exception {
		return hasColumn(tableName, "uuid_");
	}

	protected void upgradeExternalReferenceCode(
			String tableName, String primKeyColumnName)
		throws Exception {

		if (!hasTable(tableName)) {
			_log.error("Skip nonexistent table " + tableName);

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Upgrade table " + tableName);
		}

		if (!hasColumn(tableName, "externalReferenceCode")) {
			alterTableAddColumn(
				tableName, "externalReferenceCode", "VARCHAR(75)");
		}

		boolean useUUID = isUseUUID(tableName);

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				StringBundler.concat(
					"select ", useUUID ? "uuid_, " : StringPool.BLANK,
					primKeyColumnName, " from ", tableName,
					" where externalReferenceCode is null or ",
					"externalReferenceCode = ''"),
				StringBundler.concat(
					"update ", tableName,
					" set externalReferenceCode = ? where ", primKeyColumnName,
					" = ?"),
				resultSet -> new Object[] {
					useUUID ? resultSet.getString("uuid_") : null,
					resultSet.getLong(primKeyColumnName)
				},
				(values, preparedStatement) -> {
					if (useUUID) {
						preparedStatement.setString(1, (String)values[0]);
					}
					else {
						preparedStatement.setString(
							1, String.valueOf(values[1]));
					}

					preparedStatement.setLong(2, (long)values[1]);

					preparedStatement.addBatch();
				},
				null);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseExternalReferenceCodeUpgradeProcess.class);

}