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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class VerifyMySQL extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.MYSQL) {
			return;
		}

		Statement statement = connection.createStatement();

		verifyTableEngine(statement);

		verifyDatetimePrecision(connection.getMetaData(), statement);
	}

	protected String getActualColumnType(
			Statement statement, String tableName, String columnName)
		throws SQLException {

		StringBundler sb = new StringBundler(5);

		sb.append("show columns from ");
		sb.append(tableName);
		sb.append(" like \"");
		sb.append(columnName);
		sb.append("\"");

		try (ResultSet rs = statement.executeQuery(sb.toString())) {
			if (!rs.next()) {
				throw new IllegalStateException(
					"Table " + tableName + " does not have column " +
						columnName);
			}

			return rs.getString("Type");
		}
	}

	protected void verifyDatetimePrecision(
			DatabaseMetaData databaseMetaData, Statement statement)
		throws Exception {

		try (ResultSet rs = databaseMetaData.getTables(
			null, null, null, null)) {

			while (rs.next()) {
				verifyDatetimePrecisionForTable(
					databaseMetaData, statement, rs.getString("TABLE_CAT"),
					rs.getString("TABLE_SCHEM"), rs.getString("TABLE_NAME"));
			}
		}
	}

	protected void verifyDatetimePrecisionForTable(
			DatabaseMetaData databaseMetaData, Statement statement,
			String catalog, String schemaPattern, String tableName)
		throws SQLException {

		try (ResultSet rs = databaseMetaData.getColumns(
				catalog, schemaPattern, tableName, null)) {

			while (rs.next()) {
				if (Types.TIMESTAMP != rs.getInt("DATA_TYPE")) {
					continue;
				}

				String columnName = rs.getString("COLUMN_NAME");

				String actualColumnType = getActualColumnType(
					statement, tableName, columnName);

				if (actualColumnType.equals("datetime(6)")) {
					continue;
				}

				StringBundler sb = new StringBundler(5);

				sb.append("ALTER TABLE ");
				sb.append(tableName);
				sb.append(" MODIFY ");
				sb.append(columnName);
				sb.append(" datetime(6)");

				String sql = sb.toString();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Updating table " + tableName + " column " +
							columnName + " to datetime(6)");
				}

				statement.executeUpdate(sql);
			}
		}
	}

	protected void verifyTableEngine(Statement statement) throws Exception {
		try (ResultSet rs = statement.executeQuery("show table status")) {
			while (rs.next()) {
				String tableName = rs.getString("Name");

				if (!isPortalTableName(tableName)) {
					continue;
				}

				String engine = GetterUtil.getString(rs.getString("Engine"));
				String comment = GetterUtil.getString(rs.getString("Comment"));

				if (StringUtil.equalsIgnoreCase(comment, "VIEW")) {
					continue;
				}

				if (StringUtil.equalsIgnoreCase(
						engine, PropsValues.DATABASE_MYSQL_ENGINE)) {

					continue;
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"Updating table " + tableName + " to use engine " +
							PropsValues.DATABASE_MYSQL_ENGINE);
				}

				statement.executeUpdate(
					"alter table " + tableName + " engine " +
						PropsValues.DATABASE_MYSQL_ENGINE);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(VerifyMySQL.class);

}