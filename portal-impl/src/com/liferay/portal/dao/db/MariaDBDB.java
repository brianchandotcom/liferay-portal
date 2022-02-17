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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.sql.Connection;

import java.util.List;

/**
 * @author Preston Crary
 */
public class MariaDBDB extends MySQLDB {

	public MariaDBDB(int majorVersion, int minorVersion) {
		super(DBType.MARIADB, majorVersion, minorVersion);
	}

	@Override
	public void alterTableDropColumn(
			Connection connection, String tableName, String columnName)
		throws Exception {

		String[] primaryKeyColumnNames = getPrimaryKeyColumnNames(
			connection, tableName);

		boolean primaryKey = ArrayUtil.contains(
			primaryKeyColumnNames, columnName);

		if (primaryKey && (primaryKeyColumnNames.length > 1)) {
			removePrimaryKey(connection, tableName);

			addPrimaryKey(
				connection, tableName,
				ArrayUtil.remove(primaryKeyColumnNames, columnName));
		}

		List<IndexMetadata> uniqueIndexes = getIndexes(
			connection, tableName, columnName, true);

		for (IndexMetadata uniqueIndex : uniqueIndexes) {
			String[] columnNames = uniqueIndex.getColumnNames();

			if (columnNames.length > 1) {
				runSQL(uniqueIndex.getDropSQL());
			}
		}
	}

}