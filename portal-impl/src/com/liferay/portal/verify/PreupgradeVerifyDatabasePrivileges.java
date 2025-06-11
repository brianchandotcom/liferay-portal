/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

import java.sql.PreparedStatement;

/**
 * @author Jorge Avalos
 */
public class PreupgradeVerifyDatabasePrivileges
	extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		String tempTableName = "temp_permission_check";

		DB db = DBManagerUtil.getDB();

		DBInspector dbInspector = new DBInspector(connection);

		try {
			if (dbInspector.hasTable(tempTableName)) {
				db.runSQL("DROP TABLE " + tempTableName);
			}

			db.runSQL(
				StringBundler.concat(
					"CREATE TABLE ", tempTableName, " (column1 LONG NOT NULL, ",
					"PRIMARY KEY (column1))"));

			alterTableAddColumn(tempTableName, "column2", "LONG");

			db.updateIndexes(
				connection, tempTableName,
				StringBundler.concat(
					"create index IX_TEMP on ", tempTableName, " (column2)"),
				true);

			db.runSQL(
				StringBundler.concat(
					"INSERT INTO ", tempTableName,
					" (column1, column2) VALUES (1,1)"));

			db.runSQL(
				StringBundler.concat(
					"UPDATE ", tempTableName,
					" SET column2 = 2 WHERE column1 = 1"));

			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"SELECT 1 FROM ", tempTableName, " WHERE column1 = 1"));

			preparedStatement.executeQuery();

			db.runSQL(
				StringBundler.concat(
					"DELETE FROM ", tempTableName, " WHERE column1 = 1"));
		}
		catch (Exception exception) {
			throw new VerifyException(
				"User is missing database privileges", exception);
		}
		finally {
			if (dbInspector.hasTable(tempTableName)) {
				db.runSQL("DROP TABLE " + tempTableName);
			}
		}
	}

}