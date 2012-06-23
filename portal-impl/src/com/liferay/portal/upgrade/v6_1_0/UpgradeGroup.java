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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.upgrade.v6_1_0.util.GroupTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Hugo Huijser
 * @author Jorge Ferrer
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type Group_ name VARCHAR(150) null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				GroupTable.TABLE_NAME, GroupTable.TABLE_COLUMNS,
				GroupTable.TABLE_SQL_CREATE, GroupTable.TABLE_SQL_ADD_INDEXES);
		}

		updateName();
		updateSite();
	}

	protected long getClassNameId(String className) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select classNameId from ClassName_ where value = ?");

			ps.setString(1, className);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("classNameId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateName() throws Exception {
		long organizationClassNameId = getClassNameId(
			Organization.class.getName());

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select Group_.groupId, Group_.classPK, ");
			sb.append("Organization_.name from Group_ inner join ");
			sb.append("Organization_ on Organization_.organizationId = ");
			sb.append("Group_.classPK where classNameId = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, organizationClassNameId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long classPK = rs.getLong("classPK");
				String name = rs.getString("name");

				updateName(groupId, classPK, name);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateName(long groupId, long classPK, String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Group_ set name = ? where groupId = ?");

			StringBundler sb = new StringBundler(3);

			sb.append(classPK);
			sb.append(_ORGANIZATION_NAME_DELIMETER);
			sb.append(name);

			ps.setString(1, sb.toString());
			ps.setLong(2, groupId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateSite() throws Exception {
		long groupClassNameId = getClassNameId(Group.class.getName());

		runSQL(
			"update Group_ set site = TRUE where classNameId = " +
				groupClassNameId);

		long organizationClassNameId = getClassNameId(
			Organization.class.getName());

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String sql =
				"select distinct Group_.groupId from Group_ inner join " +
					"Layout on Layout.groupId = Group_.groupId where " +
						"classNameId = ?";

			ps = con.prepareStatement(sql);

			ps.setLong(1, organizationClassNameId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				runSQL(
					"update Group_ set site = TRUE where groupId = " + groupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _ORGANIZATION_NAME_DELIMETER =
		" LFR_ORGANIZATION ";

}