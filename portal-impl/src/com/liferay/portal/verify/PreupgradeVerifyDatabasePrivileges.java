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
				db.runSQL("drop table " + tempTableName);
			}

			db.runSQL(
				StringBundler.concat(
					"create table ", tempTableName, " (column1 long not null, ",
					"column2 long, primary key (column1))"));

			db.updateIndexes(
				connection, tempTableName,
				StringBundler.concat(
					"create index ix_temp on ", tempTableName, " (column2)"),
				true);

			alterTableAddColumn(tempTableName, "column3", "long");

			db.runSQL(
				StringBundler.concat(
					"insert into ", tempTableName,
					" (column1, column2, column3) values (1,1,1)"));

			db.runSQL(
				StringBundler.concat(
					"update ", tempTableName,
					" set column2 = 2 where column1 = 1"));

			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select 1 from ", tempTableName, " where column1 = 1"));

			preparedStatement.executeQuery();

			db.runSQL(
				StringBundler.concat(
					"delete from ", tempTableName, " where column1 = 1"));

			if (dbInspector.hasTable(tempTableName)) {
				db.runSQL("drop table " + tempTableName);
			}
		}
		catch (Exception exception) {
			throw new VerifyException(
				"Database user is missing privileges", exception);
		}
	}

}