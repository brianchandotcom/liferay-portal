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
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyMySQL extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_MYSQL)) {
			return;
		}

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
			Statement statement = connection.createStatement()) {

			verifyTableEngine(statement);
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