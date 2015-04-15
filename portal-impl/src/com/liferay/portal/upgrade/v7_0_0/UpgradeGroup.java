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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.upgrade.v7_0_0.util.GroupTable;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeGroup extends UpgradeProcess {

	protected long addGroup(long companyId, long parentGroupId)
		throws Exception {

		long groupId = increment();
		String name = "Private Pages " + groupId;
		String friendlyURL = "/private-pages-" + groupId;

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into Group_ (mvccVersion, uuid_, groupId, ");
			sb.append("companyId, creatorUserId, classNameId, classPK, ");
			sb.append("parentGroupId, liveGroupId, treePath, name, ");
			sb.append("description, type_, typeSettings, manualMembership, ");
			sb.append("membershipRestriction, friendlyURL, site, ");
			sb.append("remoteStagingGroupCount, inheritContent, active_) ");
			sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, 0);
			ps.setString(2, PortalUUIDUtil.generate());
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, getDefaultUserId(companyId));
			ps.setLong(6, PortalUtil.getClassNameId(Group.class));
			ps.setLong(7, groupId);
			ps.setLong(8, parentGroupId);
			ps.setLong(9, GroupConstants.DEFAULT_LIVE_GROUP_ID);
			ps.setString(10, StringPool.BLANK);
			ps.setString(11, name);
			ps.setString(12, StringPool.BLANK);
			ps.setInt(13, GroupConstants.TYPE_SITE_OPEN);
			ps.setString(14, StringPool.BLANK);
			ps.setBoolean(15, true);
			ps.setBoolean(16, false);
			ps.setString(17, friendlyURL);
			ps.setBoolean(18, true);
			ps.setInt(19, 0);
			ps.setBoolean(20, true);
			ps.setBoolean(21, true);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return groupId;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type Group_ name STRING null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				GroupTable.TABLE_NAME, GroupTable.TABLE_COLUMNS,
				GroupTable.TABLE_SQL_CREATE, GroupTable.TABLE_SQL_ADD_INDEXES);
		}

		updateGroups();
		updateTable("Layout");
		updateTable("LayoutSet");
		updateTable("LayoutFriendlyURL");
		updateTable("LayoutSetBranch");
		updateTable("LayoutRevision");
	}

	protected long getDefaultUserId(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select userId from User_ where companyId = ? and " +
					"defaultUser = ?");

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("userId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected int getPagesCount(long groupId, boolean privateLayout)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select pageCount from LayoutSet where groupId = ? and " +
					"privateLayout = ?");

			ps.setLong(1, groupId);
			ps.setBoolean(2, privateLayout);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void removeLayoutSet(long groupId, boolean privateLayout)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"delete from LayoutSet where groupId = ? and " +
					"privateLayout = ?");

			ps.setLong(1, groupId);
			ps.setBoolean(2, privateLayout);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateGroups() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select companyId, groupId, pageCount from LayoutSet where " +
					"privateLayout = ?");

			ps.setBoolean(1, false);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");
				int publicPagesCount = rs.getInt("pageCount");

				int privatePagesCount = getPagesCount(groupId, true);

				if ((privatePagesCount > 0) && (publicPagesCount > 0)) {
					long newGroupId = addGroup(companyId, groupId);

					updateTable("Layout", groupId, newGroupId);
					updateTable("LayoutSet", groupId, newGroupId);
					updateTable("LayoutFriendlyURL", groupId, newGroupId);
					updateTable("LayoutSetBranch", groupId, newGroupId);
					updateTable("LayoutRevision", groupId, newGroupId);
				}
				else {
					if (privatePagesCount > 0) {
						removeLayoutSet(groupId, false);
					}
					else {
						removeLayoutSet(groupId, true);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateTable(String tableName) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update " + tableName + " set privateLayout = ?");

			ps.setBoolean(1, false);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateTable(
			String tableName, long oldGroupId, long newGroupId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update " + tableName + " set groupId = ? where groupId = ? " +
					"and privateLayout = ?");

			ps.setLong(1, newGroupId);
			ps.setLong(2, oldGroupId);
			ps.setBoolean(3, true);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}