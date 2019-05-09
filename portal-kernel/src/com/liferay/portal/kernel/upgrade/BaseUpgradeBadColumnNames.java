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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tina Tian
 */
public abstract class BaseUpgradeBadColumnNames extends UpgradeProcess {

	protected void upgradeBadColumnNames(
			Class<?> tableClass, String... columnNames)
		throws Exception {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String tableName = dbInspector.normalizeName(
			getTableName(tableClass), databaseMetaData);

		Map<String, String> columnSQLs = _getTableColumnSQLs(tableClass);

		List<AlterColumnName> alterColumnNames = new ArrayList<>(
			columnNames.length);

		for (String columnName : columnNames) {
			String newColumnName = columnName.concat(StringPool.UNDERLINE);

			try (ResultSet columnResultSet = databaseMetaData.getColumns(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					tableName,
					dbInspector.normalizeName(
						newColumnName, databaseMetaData))) {

				if (columnResultSet.next()) {
					continue;
				}
			}

			try (ResultSet columnResultSet = databaseMetaData.getColumns(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					tableName,
					dbInspector.normalizeName(columnName, databaseMetaData))) {

				if (!columnResultSet.next()) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to get bad column name ", columnName,
								" in table ", tableName));
					}

					continue;
				}
			}

			String columnSQL = columnSQLs.get(newColumnName);

			if (Validator.isNull(columnSQL)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to get SQL for column ", columnName,
							" in table ", tableName));
				}

				continue;
			}

			alterColumnNames.add(new AlterColumnName(columnName, columnSQL));
		}

		alter(tableClass, alterColumnNames.toArray(new AlterColumnName[0]));
	}

	private Map<String, String> _getTableColumnSQLs(Class<?> tableClass)
		throws ReflectiveOperationException {

		Field tableSQLCreateField = tableClass.getField("TABLE_SQL_CREATE");

		String createSQL = (String)tableSQLCreateField.get(null);

		int startIndex = createSQL.indexOf(CharPool.OPEN_PARENTHESIS);
		int endIndex = createSQL.lastIndexOf(CharPool.CLOSE_PARENTHESIS);

		if ((startIndex < 0) || (endIndex < 0) || (startIndex >= endIndex)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get column SQL from " + createSQL);
			}

			return Collections.emptyMap();
		}

		Map<String, String> columnSQLs = new HashMap<>();

		for (String columnSQL :
				StringUtil.split(
					createSQL.substring(startIndex + 1, endIndex))) {

			int index = columnSQL.indexOf(CharPool.SPACE);

			if (index <= 0) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to get column name from " + columnSQL);
				}

				continue;
			}

			columnSQLs.put(columnSQL.substring(0, index), columnSQL);
		}

		return columnSQLs;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeBadColumnNames.class);

}