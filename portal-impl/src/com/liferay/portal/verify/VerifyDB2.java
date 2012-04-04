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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Igor Beslic
 */
public class VerifyDB2 extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_DB2)) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"SELECT tbname, name, coltype, length " +
				"FROM sysibm.syscolumns " +
				"WHERE tbcreator = " +
				"(SELECT DISTINCT CURRENT SCHEMA FROM sysibm.sysschemata) " +
				"AND coltype = 'VARCHAR' and length = 500");

			rs = ps.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString(1);
				String columnName = rs.getString(2);

				runSQL(
					"alter table " + tableName + " ALTER COLUMN " + columnName +
					" SET DATA TYPE VARCHAR(600)");
			}
	  }
	  finally {
	  	DataAccess.cleanUp(con, ps, rs);
	  }
	}

}