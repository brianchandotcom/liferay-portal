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
import com.liferay.portal.kernel.upgrade.RenameUpgradePortletPreferences;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.v6_2_0.util.JournalFeedTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Bruno Basto
 */
public class UpgradeJournal extends RenameUpgradePortletPreferences {

	public UpgradeJournal() {
		_preferenceNamesMap.put("templateId", "ddmTemplateKey");
	}

	protected void addDDMStructure(
			String uuid_, long ddmStructureId, long groupId, long companyId,
			long userId, String userName, Date createDate, Date modifiedDate,
			long parentDDMStructureId, long classNameId, String ddmStructureKey,
			String name, String description, String xsd, String storageType,
			int type)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMStructure(uuid_, structureId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, parentStructureId, classNameId, ");
			sb.append("structureKey, name, description, xsd, storageType, ");
			sb.append("type_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, uuid_);
			ps.setLong(2, ddmStructureId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setDate(7, createDate);
			ps.setDate(8, modifiedDate);
			ps.setLong(9, parentDDMStructureId);
			ps.setLong(10, classNameId);
			ps.setString(11, ddmStructureKey);
			ps.setString(12, name);
			ps.setString(13, description);
			ps.setString(14, xsd);
			ps.setString(15, storageType);
			ps.setInt(16, type);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addDDMStructure(
			String uuid_, long ddmStructureId, long groupId, long companyId,
			long userId, String userName, Date createDate, Date modifiedDate,
			String parentStructureId, String ddmStructureKey, String name,
			String description, String xsd)
		throws Exception {

		long parentDDMStructureId = 0;

		if (Validator.isNotNull(parentStructureId)) {
			parentDDMStructureId = updateStructure(parentStructureId);
		}

		addDDMStructure(
			uuid_, ddmStructureId, groupId, companyId, userId, userName,
			createDate, modifiedDate, parentDDMStructureId,
			PortalUtil.getClassNameId(JournalArticle.class.getName()),
			ddmStructureKey, name, description, xsd,
			PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE,
			DDMStructureConstants.TYPE_DEFAULT);
	}

	protected long addDDMTemplate(
			String uuid_, long ddmTemplateId, long groupId, long companyId,
			long userId, String userName, Date createDate, Date modifiedDate,
			long classNameId, long classPK, String templateKey, String name,
			String description, String type, String mode, String language,
			String script, boolean cacheable, boolean smallImage,
			long smallImageId, String smallImageURL)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMTemplate(uuid_, templateId, groupId, ");
			sb.append("companyId, userId, userName, createDate, modifiedDate,");
			sb.append("classNameId, classPK , templateKey, name, description,");
			sb.append("type_, mode_, language, script, cacheable, smallImage,");
			sb.append("smallImageId, smallImageURL) values (?, ?, ?, ?, ?, ?,");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, uuid_);
			ps.setLong(2, ddmTemplateId);
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

		return ddmTemplateId;
	}

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

		updateStructures();
		updateTemplates();

		updatePortletPreferences();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"56_INSTANCE_%"};
	}

	@Override
	protected Map<String, String> getPreferenceNamesMap() {
		return _preferenceNamesMap;
	}

	protected void updateResourcePermission(
			long companyId, String oldClassName, String newClassName,
			long oldPrimKey, long newPrimKey)
		throws Exception {

		StringBundler sb = new StringBundler(10);

		sb.append("update ResourcePermission set name = '");
		sb.append(newClassName);
		sb.append("', primKey = ");
		sb.append(newPrimKey);
		sb.append(" where companyId = ");
		sb.append(companyId);
		sb.append(" and name = '");
		sb.append(oldClassName);
		sb.append("' and primKey = ");
		sb.append(oldPrimKey);

		runSQL(sb.toString());
	}

	protected long updateStructure(String structureId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from JournalStructure where structureId = " +
					structureId);

			rs = ps.executeQuery();

			if (rs.next()) {
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

				Long ddmStructureId = _ddmStructureIds.get(
					groupId + "#" + structureId);

				if (ddmStructureId != null) {
					return ddmStructureId;
				}

				ddmStructureId = increment();

				addDDMStructure(
					uuid_, ddmStructureId, groupId, companyId, userId, userName,
					createDate, modifiedDate, parentStructureId, structureId,
					name, description, xsd);

				updateResourcePermission(
					companyId, JournalStructure.class.getName(),
					DDMStructure.class.getName(), id_, ddmStructureId);

				_ddmStructureIds.put(
					groupId + "#" + structureId, ddmStructureId);
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateStructures() throws Exception {
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
				String structureId = rs.getString("structureId");
				String parentStructureId = rs.getString("parentStructureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String xsd = rs.getString("xsd");

				long ddmStructureId = increment();

				addDDMStructure(
					uuid_, ddmStructureId, groupId, companyId, userId, userName,
					createDate, modifiedDate, parentStructureId, structureId,
					name, description, xsd);

				updateResourcePermission(
					companyId, JournalStructure.class.getName(),
					DDMStructure.class.getName(), id_, ddmStructureId);

				_ddmStructureIds.put(
					groupId + "#" + structureId, ddmStructureId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("drop table JournalStructure");
	}

	protected void updateTemplates() throws Exception {
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
				String templateId = rs.getString("templateId");
				String structureId = rs.getString("structureId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String language = rs.getString("langType");
				String script = rs.getString("xsl");
				boolean cacheable = rs.getBoolean("cacheable");
				boolean smallImage = rs.getBoolean("smallImage");
				long smallImageId = rs.getLong("smallImageId");
				String smallImageURL = rs.getString("smallImageURL");

				long classNameId = PortalUtil.getClassNameId(
					DDMStructure.class.getName());

				long classPK = 0;

				if (Validator.isNotNull(structureId)) {
					classPK = _ddmStructureIds.get(structureId);
				}

				long ddmTemplateId = addDDMTemplate(
					uuid_, increment(), groupId, companyId, userId, userName,
					createDate, modifiedDate, classNameId, classPK, templateId,
					name, description,
					DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
					DDMTemplateConstants.TEMPLATE_MODE_CREATE, language, script,
					cacheable, smallImage, smallImageId, smallImageURL);

				updateResourcePermission(
					companyId, JournalTemplate.class.getName(),
					DDMTemplate.class.getName(), id_, ddmTemplateId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("drop table JournalTemplate");
	}

	private Map<String, Long> _ddmStructureIds = new HashMap<String, Long>();
	private Map<String, String> _preferenceNamesMap =
		new HashMap<String, String>();

}