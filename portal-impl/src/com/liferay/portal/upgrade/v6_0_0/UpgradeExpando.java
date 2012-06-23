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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeExpando extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateTables();
	}

	protected void updateColumns(
			long scTableId, long snTableId, long wolTableId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from ExpandoColumn where tableId = ?");

			ps.setLong(1, wolTableId);

			rs = ps.executeQuery();

			long scColumnId = 0;
			long snColumnId = 0;

			while (rs.next()) {
				long wolColumnId = rs.getLong("columnId");
				String name = rs.getString("name");

				long newTableId = 0;

				if (name.equals("aboutMe")) {
					newTableId = snTableId;
					snColumnId = wolColumnId;
				}
				else if (name.equals("jiraUserId")) {
					newTableId = scTableId;
					scColumnId = wolColumnId;
				}

				runSQL(
					"update ExpandoColumn set tableId = " + newTableId +
						" where tableId = " + wolTableId + " and name = '" +
							name + "'");
			}

			updateRows(
				scColumnId, scTableId, snColumnId, snTableId, wolTableId);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateRows(
			long scColumnId, long scTableId, long snColumnId, long snTableId,
			long wolTableId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from ExpandoRow where tableId = ?");

			ps.setLong(1, wolTableId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long wolRowId = rs.getLong("rowId_");
				long companyId = rs.getLong("companyId");
				long classPK = rs.getLong("classPK");

				long scRowId = increment();

				runSQL(
					"insert into ExpandoRow (rowId_, companyId, tableId, " +
						"classPK) values (" + scRowId + ", " + companyId +
							", " + scTableId + ", " + classPK + ")");

				long snRowId = increment();

				runSQL(
					"insert into ExpandoRow (rowId_, companyId, tableId, " +
						"classPK) values (" + snRowId + ", " + companyId +
							", " + snTableId + ", " + classPK + ")");

				runSQL("delete from ExpandoRow where tableId = " + wolTableId);

				updateValues(
					scColumnId, scRowId, scTableId, snColumnId, snRowId,
					snTableId, wolRowId, wolTableId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateTables() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from ExpandoTable where name = ?");

			ps.setString(1, "WOL");

			rs = ps.executeQuery();

			while (rs.next()) {
				long wolTableId = rs.getLong("tableId");
				long companyId = rs.getLong("companyId");
				long classNameId = rs.getLong("classNameId");

				long scTableId = increment();

				runSQL(
					"insert into ExpandoTable (tableId, companyId, " +
						"classNameId, name) values (" + scTableId + ", " +
							companyId + ", " + classNameId + ", 'SC')");

				long snTableId = increment();

				runSQL(
					"insert into ExpandoTable (tableId, companyId, " +
						"classNameId, name) values (" + snTableId + ", " +
							companyId + ", " + classNameId + ", 'SN')");

				runSQL(
					"delete from ExpandoTable where tableId = " + wolTableId);

				updateColumns(scTableId, snTableId, wolTableId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateValues(
			long scColumnId, long scRowId, long scTableId, long snColumnId,
			long snRowId, long snTableId, long wolRowId, long wolTableId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from ExpandoValue where tableId = ? and rowId_ = ?");

			ps.setLong(1, wolTableId);
			ps.setLong(2, wolRowId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long valueId = rs.getLong("valueId");
				long columnId = rs.getLong("columnId");

				long newTableId = 0;
				long newRowId = 0;

				if (columnId == scColumnId) {
					newRowId = scRowId;
					newTableId = scTableId;
				}
				else if (columnId == snColumnId) {
					newRowId = snRowId;
					newTableId = snTableId;
				}

				runSQL(
					"update ExpandoValue set tableId = " + newTableId +
						", rowId_ = " + newRowId + " where " + "valueId = " +
							valueId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}