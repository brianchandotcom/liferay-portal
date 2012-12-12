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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
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

		structureKeyStructureIdMap = new HashMap<String, Long>();
		templateIdsMap = new HashMap<Long, Long>();

		updateJournalStructures();

		updateJournalTemplates();
	}

	private long addDDMStructure(
			String uuid_, long groupId, long companyId, long userId,
			String userName, Date createDate, Date modifiedDate,
			String structureKey, String parentStructureId, String name,
			String description, String xsd)
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

			String storageType = PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE;
			int type_ = DDMStructureConstants.TYPE_DEFAULT;

			ps.setString(1, uuid_);
			ps.setLong(2, newStructureId);
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

	private long addDDMTemplate(
			String uuid_, long groupId, long companyId, long userId,
			String userName, Date createDate, Date modifiedDate, long classPK,
			String templateKey, String name, String description, String type,
			String mode, String language, String script, boolean cacheable,
			boolean smallImage, long smallImageId, String smallImageURL)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		long newTemplateId = increment();

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into DDMTemplate(uuid_, templateId, groupId, ");
			sb.append("companyId, userId, userName, createDate, modifiedDate,");
			sb.append("classNameId, classPK , templateKey, name, description,");
			sb.append("type_, mode_, language, script, cacheable, smallImage,");
			sb.append("smallImageId, smallImageURL) values (?, ?, ?, ?, ?, ?,");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			long classNameId = PortalUtil.getClassNameId(
				DDMStructure.class.getName());

			ps.setString(1, uuid_);
			ps.setLong(2, newTemplateId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setDate(7, createDate);
			ps.setDate(8, modifiedDate);
			ps.setLong(9, classNameId);
			ps.setLong(10, classPK);
			ps.setString(11, templateKey);
			ps.setString(12, name);
			ps.setString(13, description);
			ps.setString(14, type);
			ps.setString(15, mode);
			ps.setString(16, language);
			ps.setString(17, script);
			ps.setBoolean(18, cacheable);
			ps.setBoolean(19, smallImage);
			ps.setLong(20, smallImageId);
			ps.setString(21, smallImageURL);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return newTemplateId;
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
				String structureKey = rs.getString("structureId");
				String parentStructureId = rs.getString("parentStructureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String xsd = rs.getString("xsd");

				if (!structureKeyStructureIdMap.containsKey(structureKey)) {
					newStructureId = addDDMStructure(
						uuid_, groupId, companyId, userId, userName, createDate,
						modifiedDate, structureKey, parentStructureId, name,
						description, xsd);

					structureKeyStructureIdMap.put(
						structureKey, newStructureId);
				}
				else {
					newStructureId = structureKeyStructureIdMap.get(
						structureKey);
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
				String ddmStructureKey = rs.getString("structureId");
				String parentStructureId = rs.getString("parentStructureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String xsd = rs.getString("xsd");

				long newStructureId = addDDMStructure(
					uuid_, groupId, companyId, userId, userName, createDate,
					modifiedDate, ddmStructureKey, parentStructureId, name,
					description, xsd);

				structureKeyStructureIdMap.put(ddmStructureKey, newStructureId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void updateJournalTemplates() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from JournalTemplate");

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
				String templateKey = rs.getString("templateId");
				String structureKey = rs.getString("structureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String language = rs.getString("langType");
				String script = rs.getString("xsl");
				boolean cacheable = rs.getBoolean("cacheable");
				boolean smallImage = rs.getBoolean("smallImage");
				long smallImageId = rs.getLong("smallImageId");
				String smallImageURL = rs.getString("smallImageURL");

				long classPK = 0;

				if (Validator.isNotNull(structureKey)) {
					classPK = structureKeyStructureIdMap.get(structureKey);
				}

				long newTemplateId = addDDMTemplate(
					uuid_, groupId, companyId, userId, userName, createDate,
					modifiedDate, classPK, templateKey, name, description,
					DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
					DDMTemplateConstants.TEMPLATE_MODE_CREATE, language, script,
					cacheable, smallImage, smallImageId,
					smallImageURL);

				templateIdsMap.put(id_, newTemplateId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private Map<String, Long> structureKeyStructureIdMap = null;
	private Map<Long, Long> templateIdsMap = null;

}