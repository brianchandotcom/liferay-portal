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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v7_0_0.util.DDMContentTable;
import com.liferay.portal.upgrade.v7_0_0.util.DDMStructureTable;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataMapping extends UpgradeProcess {

	protected void addStructureVersion(
			long structureVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long structureId,
			String name, String description, String definition,
			String storageType, int type)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMStructureVersion (structureVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("structureId, version, name, description, definition, ");
			sb.append("storageType, type_) values (?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, structureVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, structureId);
			ps.setString(8, DDMStructureConstants.VERSION_DEFAULT);
			ps.setString(9, name);
			ps.setString(10, description);
			ps.setString(11, definition);
			ps.setString(12, storageType);
			ps.setInt(13, type);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure version " +
					"with structureId " + structureId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addStructureVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String definition = rs.getString("definition");
				String storageType = rs.getString("storageType");
				int type = rs.getInt("type_");

				long structureVersionId = increment();

				addStructureVersion(
					structureVersionId, groupId, companyId, userId, userName,
					modifiedDate, structureId, name, description, definition,
					storageType, type);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_name DDMContent xml data_ TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMContentTable.TABLE_NAME, DDMContentTable.TABLE_COLUMNS,
				DDMContentTable.TABLE_SQL_CREATE,
				DDMContentTable.TABLE_SQL_ADD_INDEXES);
		}

		try {
			runSQL("alter_column_name DDMStructure xsd definition TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMStructureTable.TABLE_NAME, DDMStructureTable.TABLE_COLUMNS,
				DDMStructureTable.TABLE_SQL_CREATE,
				DDMStructureTable.TABLE_SQL_ADD_INDEXES);
		}

		addStructureVersions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDynamicDataMapping.class);

}