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

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.Map;

/**
 * @author José Ángel Jiménez
 */
public class BaseUpgradeSQLServerDatetime extends UpgradeProcess {

	public BaseUpgradeSQLServerDatetime(Class<?>[] tableClasses) {
		_tableClasses = tableClasses;
		_newType = "datetime2";
		_newSize = 6;
	}

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.SQLSERVER) {
			try (LoggingTimer loggingTimer = new LoggingTimer();) {
				for (Class<?> tableClass : _tableClasses) {
					_upgradeTable(tableClass);
				}
			}
		}
	}

	private void _upgradeTable(Class<?> tableClass) throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		DBInspector dbInspector = new DBInspector(connection);

		String catalog = dbInspector.getCatalog();
		String schema = dbInspector.getSchema();

		String tableName = dbInspector.normalizeName(
			getTableName(tableClass), databaseMetaData);

		try (ResultSet tableRS = databaseMetaData.getTables(
				catalog, schema, tableName, null)) {

			if (!tableRS.next()) {
				_log.error(
					StringBundler.concat(
						"Table ", tableName, " does not exist"));

				return;
			}

			String newTypeName = dbInspector.normalizeName(_newType);

			String newTypeDefinition = StringBundler.concat(
				newTypeName, "(", _newSize, ")");

			for (Map.Entry<String, Integer> tableColumnEntry :
					getTableColumnsMap(tableClass).entrySet()) {

				String columnName = dbInspector.normalizeName(
					tableColumnEntry.getKey(), databaseMetaData);

				if (tableColumnEntry.getValue() != Types.TIMESTAMP) {
					continue;
				}

				try (ResultSet columnRS = databaseMetaData.getColumns(
						null, null, tableName, columnName)) {

					if (!columnRS.next()) {
						_log.error(
							StringBundler.concat(
								"Column ", columnName,
								" does not exist in Table ", tableName));

						continue;
					}

					if (newTypeName.equals(columnRS.getString("TYPE_NAME")) &&
						(_newSize == columnRS.getInt("DECIMAL_DIGITS"))) {

						_log.warn(
							StringBundler.concat(
								"Column ", columnName, " in Table ", tableName,
								" already is ", newTypeDefinition));

						continue;
					}

					alter(
						tableClass,
						new AlterColumnType(columnName, newTypeDefinition));
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeSQLServerDatetime.class);

	private final int _newSize;
	private final String _newType;
	private final Class<?>[] _tableClasses;

}