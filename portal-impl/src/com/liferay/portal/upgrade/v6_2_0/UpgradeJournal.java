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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.v6_2_0.util.JournalFeedTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.util.JournalConverterUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Bruno Basto
 */
public class UpgradeJournal extends BaseUpgradePortletPreferences {

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
			ps.setString(14, JournalConverterUtil.getDDMXSD(xsd));
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

	protected void addDDMTemplate(
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

		super.doUpgrade();
	}

	protected long getDDMStructureId(long groupId, String structureId) {
		if (Validator.isNull(structureId)) {
			return 0;
		}

		Long ddmStructureId = _ddmStructureIds.get(groupId + "#" + structureId);

		if (ddmStructureId == null) {
			_log.error(
				"Unable to find the DDM structure ID with the key {groupId=" +
					groupId + ", structureKey=" + structureId + "}");

			return 0;
		}

		return ddmStructureId;
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			"56_INSTANCE_%", "62_INSTANCE_%", "101_INSTANCE_%"
		};
	}

	protected void updatePreferencesClassPKs(
			PortletPreferences preferences, String key)
		throws Exception {

		String[] oldValues = preferences.getValues(key, null);

		if (oldValues == null) {
			return;
		}

		String[] newValues = new String[oldValues.length];

		for (int i = 0; i < oldValues.length; i++) {
			String oldValue = oldValues[i];

			String newValue = oldValue;

			String[] oldPrimaryKeys = StringUtil.split(oldValue);

			for (String oldPrimaryKey : oldPrimaryKeys) {
				if (!Validator.isNumber(oldPrimaryKey)) {
					break;
				}

				Long newPrimaryKey = _ddmStructurePKs.get(
					GetterUtil.getLong(oldPrimaryKey));

				if (Validator.isNotNull(newPrimaryKey)) {
					newValue = StringUtil.replace(
						newValue, oldPrimaryKey, String.valueOf(newPrimaryKey));
				}
			}

			newValues[i] = newValue;
		}

		preferences.setValues(key, newValues);
	}

	protected void updateResourcePermission(
			long companyId, String oldClassName, String newClassName,
			long oldPrimKey, long newPrimKey)
		throws Exception {

		StringBundler sb = new StringBundler(11);

		sb.append("update ResourcePermission set name = '");
		sb.append(newClassName);
		sb.append("', primKey = '");
		sb.append(newPrimKey);
		sb.append("' where companyId = ");
		sb.append(companyId);
		sb.append(" and name = '");
		sb.append(oldClassName);
		sb.append("' and primKey = '");
		sb.append(oldPrimKey);
		sb.append("'");

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
				_ddmStructurePKs.put(id_, ddmStructureId);
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

				long ddmTemplateId = increment();

				long classNameId = PortalUtil.getClassNameId(
					DDMStructure.class.getName());

				long classPK = getDDMStructureId(groupId, structureId);

				addDDMTemplate(
					uuid_, ddmTemplateId, groupId, companyId, userId, userName,
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

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences preferences = PortletPreferencesFactoryUtil.fromXML(
			companyId, ownerId, ownerType, plid, portletId, xml);

		if (portletId.startsWith(PortletKeys.ASSET_PUBLISHER)) {
			updatePreferencesClassPKs(
				preferences, "anyClassTypeJournalArticleAssetRendererFactory");
			updatePreferencesClassPKs(preferences, "classTypeIds");
			updatePreferencesClassPKs(
				preferences, "classTypeIdsJournalArticleAssetRendererFactory");
		}
		else if (portletId.startsWith(PortletKeys.JOURNAL_CONTENT)) {
			String templateId = preferences.getValue(
				"templateId", StringPool.BLANK);

			if (Validator.isNotNull(templateId)) {
				preferences.reset("templateId");

				preferences.setValue("ddmTemplateKey", templateId);
			}
		}
		else if (portletId.startsWith(PortletKeys.JOURNAL_CONTENT_LIST)) {
			String structureId = preferences.getValue(
				"structureId", StringPool.BLANK);

			if (Validator.isNotNull(structureId)) {
				preferences.reset("structureId");

				preferences.setValue("ddmStructureKey", structureId);
			}
		}

		return PortletPreferencesFactoryUtil.toXML(preferences);
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeJournal.class);

	private Map<String, Long> _ddmStructureIds = new HashMap<String, Long>();
	private Map<Long, Long> _ddmStructurePKs = new HashMap<Long, Long>();

}