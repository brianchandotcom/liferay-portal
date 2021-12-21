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

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author     Preston Crary
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *             BaseDBColumnSizeUpgradeProcess}
 */
@Deprecated
public abstract class BaseUpgradeDBColumnSize extends UpgradeProcess {

	public BaseUpgradeDBColumnSize(
		DBType dbType, String oldColumnType, int size) {

		_dbType = dbType;
		_oldColumnType = oldColumnType;
		_size = size;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == _dbType) {
			_upgradeTables();
		}
	}

	protected abstract void upgradeColumn(String tableName, String columnName)
		throws Exception;

	private void _upgradeTables() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		DB db = DBManagerUtil.getDB();

		DBInspector dbInspector = new DBInspector(connection);

		String catalog = dbInspector.getCatalog();
		String schema = dbInspector.getSchema();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			ResultSet tableResultSet = databaseMetaData.getTables(
				catalog, schema, null, new String[] {"TABLE"})) {

			while (tableResultSet.next()) {
				String tableName = dbInspector.normalizeName(
					tableResultSet.getString("TABLE_NAME"));

				Set<String> invalidColumnNames = new HashSet<>();

				try (ResultSet primaryKeyResultSet =
						databaseMetaData.getPrimaryKeys(
							catalog, schema, tableName)) {

					while (primaryKeyResultSet.next()) {
						String primaryKeyName = StringUtil.toUpperCase(
							primaryKeyResultSet.getString("COLUMN_NAME"));

						invalidColumnNames.add(primaryKeyName);
					}
				}

				ResultSet indexResultSet = null;

				if (db.getDBType() == DBType.ORACLE) {
					indexResultSet = databaseMetaData.getIndexInfo(
						catalog, schema, tableName, false, true);
				}
				else {
					indexResultSet = databaseMetaData.getIndexInfo(
						catalog, schema, tableName, false, false);
				}

				try {
					while (indexResultSet.next()) {
						invalidColumnNames.add(
							StringUtil.toUpperCase(
								indexResultSet.getString("COLUMN_NAME")));
					}
				}
				finally {
					if (indexResultSet != null) {
						indexResultSet.close();
					}
				}

				try (ResultSet columnResultSet = databaseMetaData.getColumns(
						catalog, schema, tableName, null)) {

					while (columnResultSet.next()) {
						int size = columnResultSet.getInt("COLUMN_SIZE");

						if ((size == _size) &&
							StringUtil.equalsIgnoreCase(
								_oldColumnType,
								columnResultSet.getString("TYPE_NAME"))) {

							String columnName = columnResultSet.getString(
								"COLUMN_NAME");

							if (invalidColumnNames.contains(
									StringUtil.toUpperCase(columnName))) {

								continue;
							}

							try {
								upgradeColumn(tableName, columnName);
							}
							catch (SQLException sqlException) {
								if (_log.isWarnEnabled()) {
									_log.warn(
										StringBundler.concat(
											"Unable to alter length of column ",
											columnName, " for table ",
											tableName),
										sqlException);
								}
							}
						}
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeDBColumnSize.class);

	private final DBType _dbType;
	private final String _oldColumnType;
	private final int _size;

}