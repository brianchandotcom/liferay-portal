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

package com.liferay.portal.upgrade.internal.live;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.GetterUtil;
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

			try (AutoCloseable autoCloseable = db.syncTables(
					connection, tableName, tempTableName,
					resultColumnNamesMap)) {

				db.copyTableRows(
					connection, tableName, tempTableName, resultColumnNamesMap);
			}
		}
	}

	private String _getTempTableName(String tableName) {
		return _UPGRADE_LIVE_TABLE_NAME_PREFIX.concat(tableName);
	}

	private static final String _UPGRADE_LIVE_TABLE_NAME_PREFIX =
		GetterUtil.get(
			PropsUtil.get("upgrade.live.table.name.prefix"), "tmp_live_");

}