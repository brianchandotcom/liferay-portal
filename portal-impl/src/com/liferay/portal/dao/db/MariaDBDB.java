/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		super.alterTableDropColumn(connection, tableName, columnName);
	}

}