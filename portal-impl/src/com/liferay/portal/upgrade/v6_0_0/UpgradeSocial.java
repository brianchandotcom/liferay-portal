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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DateUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.upgrade.v6_0_0.util.SocialActivityTable;
import com.liferay.portal.upgrade.v6_0_0.util.SocialRelationTable;
import com.liferay.portal.upgrade.v6_0_0.util.SocialRequestTable;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Amos Fong
 * @author Brian Wing Shun Chan
 */
public class UpgradeSocial extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateGroupId();

		// SocialActivity

		UpgradeColumn createDateColumn = new DateUpgradeColumnImpl(
			"createDate");
		UpgradeColumn modifiedDateColumn = new DateUpgradeColumnImpl(
			"modifiedDate");

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialActivityTable.TABLE_NAME, SocialActivityTable.TABLE_COLUMNS,
			createDateColumn);

		upgradeTable.setCreateSQL(SocialActivityTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(SocialActivityTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// SocialRelation

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialRelationTable.TABLE_NAME, SocialRelationTable.TABLE_COLUMNS,
			createDateColumn);

		upgradeTable.setCreateSQL(SocialRelationTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(SocialRelationTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// SocialRequest

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialRequestTable.TABLE_NAME, SocialRequestTable.TABLE_COLUMNS,
			createDateColumn, modifiedDateColumn);

		upgradeTable.setCreateSQL(SocialRequestTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(SocialRequestTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();
	}

	protected Object[] getGroup(long groupId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(_GET_GROUP);

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");

				return new Object[] {classNameId, classPK};
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected Object[] getLayout(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(_GET_LAYOUT);

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			if (rs.next()) {
				long groupId = rs.getLong("groupId");

				return new Object[] {groupId};
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateGroupId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select distinct(groupId) from SocialActivity where groupId " +
					"> 0");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				try {
					updateGroupId(groupId);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateGroupId(long groupId) throws Exception {
		Object[] group = getGroup(groupId);

		if (group == null) {
			return;
		}

		long classNameId = (Long)group[0];

		if (classNameId != PortalUtil.getClassNameId(Layout.class.getName())) {
			return;
		}

		long classPK = (Long)group[1];

		Object[] layout = getLayout(classPK);

		if (layout == null) {
			return;
		}

		long layoutGroupId = (Long)layout[0];

		runSQL(
			"update SocialActivity set groupId = " + layoutGroupId +
				" where groupId = " + groupId);
	}

	private static final String _GET_GROUP =
		"select * from Group_ where groupId = ?";

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

	private static final Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

}