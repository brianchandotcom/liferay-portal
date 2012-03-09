/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.*;

/**
 * @author Ivica Cardic
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateOracleVarchar2Length();
	}

	protected void updateOracleVarchar2Length() throws Exception {
		DB db = DBFactoryUtil.getDB();

		if (!db.getType().equals(DB.TYPE_ORACLE)) {
			return;
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			st = con.createStatement();
			rs = st.executeQuery(_SELECT_VARCHAR2_COLUMNS);

			while (rs.next()) {
				String tableName = rs.getString(1);
				String columnName = rs.getString(2);
				int dataLength = rs.getInt(3);

				if (dataLength != 4000) {
					dataLength = dataLength / 4;
				}

				try {
					StringBundler sb = new StringBundler(7);

					sb.append("alter table ");
					sb.append(tableName);
					sb.append(" modify ( ");
					sb.append(columnName);
					sb.append(" varchar2(");
					sb.append(dataLength);
					sb.append(" char))");

					runSQL(sb.toString());
				}
				catch (SQLException e) {
					if (e.getErrorCode() == 1441) {
						if (_log.isWarnEnabled()) {
							StringBundler sb = new StringBundler(8);

							sb.append("Cannot decrease column length ");
							sb.append("because some value is too big. ");
							sb.append("Skiping upgrade for table ");
							sb.append(tableName);
							sb.append(" and column ");
							sb.append(columnName);
							sb.append(". Read the Upgrade chapter in the ");
							sb.append("User Guide for more details.");

							_log.warn(sb.toString());
						}
					}
					else {
						throw e;
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, st, rs);
		}
	}

	private static final String _SELECT_VARCHAR2_COLUMNS =
		"select table_name, column_name, data_length from user_tab_columns " +
			"where data_type = 'VARCHAR2'";

	private static Log _log = LogFactoryUtil.getLog(UpgradeSchema.class);

}