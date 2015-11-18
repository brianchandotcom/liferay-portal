/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Igor Beslic
 * @author Joshua Gok
 */
public class VerifyDB2 extends VerifyProcess {

	protected void alterVarcharColumns() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select tbname, name, coltype, length from ");
			sb.append("sysibm.syscolumns where tbcreator = (select distinct ");
			sb.append("current schema from sysibm.sysschemata) AND coltype = ");
			sb.append("'VARCHAR' and length = 500");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString(1);

				if (!isPortalTableName(tableName)) {
					continue;
				}

				String columnName = rs.getString(2);

				runSQL(
					"alter table " + tableName + " alter column " + columnName +
						" set data type varchar(600)");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void convertColumnToClob(String tableName, String columnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("select coltype from sysibm.syscolumns where tbname = '");
			sb.append(tableName);
			sb.append("' AND name = '");
			sb.append(columnName);
			sb.append(StringPool.APOSTROPHE);

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			if (!rs.next()) {
				if (_log.isWarnEnabled()) {
					sb = new StringBundler(4);

					sb.append("Unable to find column ");
					sb.append(columnName);
					sb.append(" in table ");
					sb.append(tableName);

					_log.warn(sb.toString());
				}

				return;
			}

			String dataType = rs.getString(1);

			if (dataType.equals("CLOB")) {
				return;
			}

			runSQL("alter table " + tableName + " add temp CLOB");

			sb = new StringBundler(4);

			sb.append("update ");
			sb.append(tableName);
			sb.append(" set temp = ");
			sb.append(columnName);

			runSQL(sb.toString());

			sb = new StringBundler(4);

			sb.append("alter table ");
			sb.append(tableName);
			sb.append(" drop column ");
			sb.append(columnName);

			runSQL(sb.toString());

			sb = new StringBundler(4);

			sb.append("alter table ");
			sb.append(tableName);
			sb.append(" rename column temp to ");
			sb.append(columnName);

			runSQL(sb.toString());
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_DB2)) {
			return;
		}

		alterVarcharColumns();

		convertColumnToClob("EXPANDOVALUE", "DATA_");
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyDB2.class);

}