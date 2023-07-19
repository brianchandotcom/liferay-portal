/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.internal.live;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.upgrade.live.LiveUpgradeExecutor;
import com.liferay.portal.upgrade.live.LiveUpgradeProcess;
import com.liferay.portal.upgrade.live.LiveUpgradeSchemaDiff;

import java.sql.Connection;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kevin Lee
 */
@Component(service = LiveUpgradeExecutor.class)
public class LiveUpgradeExecutorImpl implements LiveUpgradeExecutor {

	@Override
	public void upgrade(
			String tableName, LiveUpgradeProcess... liveUpgradeProcesses)
		throws Exception {

		if ((liveUpgradeProcesses == null) ||
			(liveUpgradeProcesses.length == 0)) {

			throw new IllegalArgumentException(
				"At least one live upgrade process is required");
		}

		try (Connection connection = DataAccess.getConnection()) {
			String tempTableName = _getTempTableName(tableName);

			DB db = DBManagerUtil.getDB();

			db.copyTableStructure(connection, tableName, tempTableName);

			LiveUpgradeSchemaDiff liveUpgradeSchemaDiff =
				new LiveUpgradeSchemaDiff(connection, tableName);

			for (LiveUpgradeProcess liveUpgradeProcess : liveUpgradeProcesses) {
				liveUpgradeProcess.upgrade(
					tempTableName, liveUpgradeSchemaDiff);
			}

			Map<String, String> resultColumnNamesMap =
				liveUpgradeSchemaDiff.getResultColumnNamesMap();

			Map<String, String> resultDefaultValuesMap =
				liveUpgradeSchemaDiff.getResultDefaultValuesMap();

			try (AutoCloseable autoCloseable = db.syncTables(
					connection, tableName, tempTableName, resultColumnNamesMap,
					resultDefaultValuesMap)) {

				db.copyTableRows(
					connection, tableName, tempTableName, resultColumnNamesMap,
					resultDefaultValuesMap);
			}

			db.renameTables(
				connection,
				new ObjectValuePair<>(
					tableName, _getArchiveTableName(tableName)),
				new ObjectValuePair<>(tempTableName, tableName));
		}
		catch (Exception exception) {
			_log.error("Live upgrade failed", exception);

			throw exception;
		}
	}

	private String _getArchiveTableName(String tableName) {
		return _UPGRADE_LIVE_ARCHIVE_TABLE_NAME_PREFIX.concat(tableName);
	}

	private String _getTempTableName(String tableName) {
		return _UPGRADE_LIVE_TEMP_TABLE_NAME_PREFIX.concat(tableName);
	}

	private static final String _UPGRADE_LIVE_ARCHIVE_TABLE_NAME_PREFIX =
		GetterUtil.get(
			PropsUtil.get("upgrade.live.archive.table.name.prefix"),
			"archive_live_");

	private static final String _UPGRADE_LIVE_TEMP_TABLE_NAME_PREFIX =
		GetterUtil.get(
			PropsUtil.get("upgrade.live.temp.table.name.prefix"), "tmp_live_");

	private static final Log _log = LogFactoryUtil.getLog(
		LiveUpgradeExecutorImpl.class);

}