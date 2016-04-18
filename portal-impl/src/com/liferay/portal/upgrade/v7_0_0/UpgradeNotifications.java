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

import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.v7_0_0.util.UserNotificationEventTable;

import java.lang.reflect.Field;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class UpgradeNotifications extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasType(
				UserNotificationEventTable.class, "type_",
				"VARCHAR(200) null")) {

			alter(
				UserNotificationEventTable.class,
				new AlterColumnType("type_", "VARCHAR(200) null"));
		}
	}

	protected int getColumnType(Class<?> tableClass, String columnName)
		throws Exception {

		Field tableColumnsField = tableClass.getField("TABLE_COLUMNS");

		Object[][] tableColumns = (Object[][])tableColumnsField.get(null);

		for (Object[] tableColumn : tableColumns) {
			if (tableColumn[0].equals(columnName)) {
				return (int)tableColumn[1];
			}
		}

		throw new UpgradeException(
			"Table class " + tableClass +
				" has no row in TABLE_COLUMNS for column " + columnName);
	}

	protected boolean hasType(
			Class<?> tableClass, String columnName, String typeName)
		throws Exception {

		Field tableNameField = tableClass.getField("TABLE_NAME");

		String tableName = (String)tableNameField.get(null);

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		try (ResultSet rs = databaseMetaData.getColumns(
				null, null, tableName, columnName)) {

			if (!rs.next()) {
				return false;
			}

			int columnType = getColumnType(tableClass, columnName);
			int columnTypeSize = parseColumnTypeSize(typeName);
			boolean columnTypeNullable = isColumnTypeNullable(typeName);

			int columnSize = rs.getInt("COLUMN_SIZE");
			int dataType = rs.getInt("DATA_TYPE");
			int nullable = rs.getInt("NULLABLE");

			if ((columnTypeSize != -1) && (columnTypeSize != columnSize)) {
				return false;
			}

			if (dataType != columnType) {
				return false;
			}

			if ((columnTypeNullable &&
				 (nullable != DatabaseMetaData.columnNullable)) ||
				(!columnTypeNullable &&
				 (nullable != DatabaseMetaData.columnNoNulls))) {

				return false;
			}

			return true;
		}
	}

	protected boolean isColumnTypeNullable(String typeName) {
		typeName = typeName.trim();

		int i = typeName.indexOf("null");

		if (i == -1) {
			return false;
		}

		if ((i > 0) && !Character.isSpaceChar(typeName.charAt(i - 1))) {
			return false;
		}

		if ((i + 4) < typeName.length()) {
			return false;
		}

		return true;
	}

	protected int parseColumnTypeSize(String typeName) throws UpgradeException {
		Matcher matcher = _columnTypeSizePattern.matcher(typeName);

		if (!matcher.matches()) {
			return -1;
		}

		String size = matcher.group(1);

		if (Validator.isNotNull(size)) {
			try {
				return Integer.parseInt(size);
			}
			catch (NumberFormatException nfe) {
				throw new UpgradeException(
					"Invalid SQL type " + typeName +
						". Type size must be a non negative integer.",
					nfe);
			}
		}

		return -1;
	}

	private static final Pattern _columnTypeSizePattern = Pattern.compile(
		"^\\w+(?:\\((\\d+)\\))?.*", Pattern.CASE_INSENSITIVE);

}