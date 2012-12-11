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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.v6_2_0.util.JournalFeedTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.journal.model.JournalArticle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class UpgradeJournal extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL(
				"alter_column_name JournalFeed feedType feedFormat " +
					"VARCHAR(75) null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				JournalFeedTable.TABLE_NAME, JournalFeedTable.TABLE_COLUMNS,
				JournalFeedTable.TABLE_SQL_CREATE,
				JournalFeedTable.TABLE_SQL_ADD_INDEXES);
		}

		updateJournalStructures();
	}

	private long addDDMStructure(
		String uuid_, long groupId, long companyId, long userId,
		String userName, Date createDate, Date modifiedDate,
		String parentStructureId, String name, String description,
		String xsd)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		long newStructureId = increment();

		long parentStructureIdValue = 0;

		if (Validator.isNotNull(parentStructureId)) {
			parentStructureIdValue = updateJournalStructure(parentStructureId);
		}

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into DDMStructure(uuid_, structureId, groupId, ");
			sb.append("companyId, userId, userName, createDate, modifiedDate,");
			sb.append(" parentStructureId, classNameId, structureKey, name,");
			sb.append(" description, xsd, storageType, type_) values (?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			long classNameId = PortalUtil.getClassNameId(
				JournalArticle.class.getName());

			String structureKey = String.valueOf(increment());
			String storageType = PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE;
			int type_ = DDMStructureConstants.TYPE_DEFAULT;

			ps.setString(1, uuid_);
			ps.setLong(2, increment());
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setDate(7, createDate);
			ps.setDate(8, modifiedDate);
			ps.setLong(9, parentStructureIdValue);
			ps.setLong(10, classNameId);
			ps.setString(11, structureKey);
			ps.setString(12, name);
			ps.setString(13, description);
			ps.setString(14, xsd);
			ps.setString(15, storageType);
			ps.setInt(16, type_);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return newStructureId;
	}

	private long updateJournalStructure(String structureId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long newStructureId = 0;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from JournalStructure where structureId = " +
					structureId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String uuid_ = rs.getString("uuid_");
				long id_ = rs.getLong("id_");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				String parentStructureId = rs.getString("parentStructureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String xsd = rs.getString("xsd");

				if (!structureIdsMap.containsKey(id_)) {
					newStructureId = addDDMStructure(
						uuid_, groupId, companyId, userId, userName, createDate,
						modifiedDate, parentStructureId, name, description,
						xsd);

					structureIdsMap.put(id_, newStructureId);
				}
				else {
					newStructureId = structureIdsMap.get(id_);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return newStructureId;
	}

	private void updateJournalStructures() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		structureIdsMap = new HashMap<Long, Long>();

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from JournalStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				String uuid_ = rs.getString("uuid_");
				long id_ = rs.getLong("id_");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				String parentStructureId = rs.getString("parentStructureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String xsd = rs.getString("xsd");

				long newStructureId = addDDMStructure(
					uuid_, groupId, companyId, userId, userName, createDate,
					modifiedDate, parentStructureId, name, description, xsd);

				structureIdsMap.put(id_, newStructureId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private Map<Long, Long> structureIdsMap = null;

}