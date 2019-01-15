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
	}

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.SQLSERVER) {
			return;
		}

		for (Class<?> tableClass : _tableClasses) {
			_upgradeTable(tableClass);
		}
	}

	private void _upgradeTable(Class<?> tableClass) throws Exception {
		DBInspector dbInspector = new DBInspector(connection);

		String catalog = dbInspector.getCatalog();
		String schema = dbInspector.getSchema();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

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

			String newTypeName = dbInspector.normalizeName(_NEW_TYPE);

			String newTypeDefinition = StringBundler.concat(
				newTypeName, "(", _NEW_SIZE, ")");

			Map<String, Integer> tableColumnMap = getTableColumnsMap(
				tableClass);

			for (Map.Entry<String, Integer> entry : tableColumnMap.entrySet()) {
				if (entry.getValue() != Types.TIMESTAMP) {
					continue;
				}

				String columnName = dbInspector.normalizeName(
					entry.getKey(), databaseMetaData);

				try (ResultSet columnRS = databaseMetaData.getColumns(
						null, null, tableName, columnName)) {

					if (!columnRS.next()) {
						_log.error(
							StringBundler.concat(
								"Column ", columnName,
								" does not exist in table ", tableName));

						continue;
					}

					if (newTypeName.equals(columnRS.getString("TYPE_NAME")) &&
						(_NEW_SIZE == columnRS.getInt("DECIMAL_DIGITS"))) {

						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Column ", columnName, " in table ",
									tableName, " already is ",
									newTypeDefinition));
						}

						continue;
					}

					alter(
						tableClass,
						new AlterColumnType(columnName, newTypeDefinition));
				}
			}
		}
	}

	private static final int _NEW_SIZE = 6;

	private static final String _NEW_TYPE = "datetime2";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeSQLServerDatetime.class);

	private final Class<?>[] _tableClasses;

}