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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.journal.kernel.util.JournalConverterManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.upgrade.v6_2_0.util.JournalFeedTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Locale;
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
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, long parentDDMStructureId, long classNameId,
			String ddmStructureKey, String name, String description, String xsd,
			String storageType, int type)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMStructure (uuid_, structureId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
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
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, modifiedDate);
			ps.setLong(9, parentDDMStructureId);
			ps.setLong(10, classNameId);
			ps.setString(11, ddmStructureKey);
			ps.setString(12, name);
			ps.setString(13, description);
			ps.setString(
				14,
				JournalConverterManagerUtil.getDDMXSD(
					xsd, getDefaultLocale(companyId)));
			ps.setString(15, storageType);
			ps.setInt(16, type);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure with UUID " +
					uuid_);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addDDMStructure(
			String uuid_, long ddmStructureId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, String parentStructureId,
			String ddmStructureKey, String name, String description, String xsd)
		throws Exception {

		long parentDDMStructureId = 0;

		if (Validator.isNotNull(parentStructureId)) {
			parentDDMStructureId = updateStructure(parentStructureId);
		}

		long insertedDDMStructureId = getDDMStructureId(
			groupId, ddmStructureKey, false);

		if (insertedDDMStructureId == 0) {
			addDDMStructure(
				uuid_, ddmStructureId, groupId, companyId, userId, userName,
				createDate, modifiedDate, parentDDMStructureId,
				PortalUtil.getClassNameId(
					"com.liferay.portlet.journal.model.JournalArticle"),
				ddmStructureKey, name, description, xsd,
				PropsUtil.get("journal.article.storage.type"),
				_DDM_STRUCTURE_TYPE_DEFAULT);
		}
	}

	protected void addDDMTemplate(
			String uuid_, long ddmTemplateId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, long classNameId, long classPK,
			String templateKey, String name, String description, String type,
			String mode, String language, String script, boolean cacheable,
			boolean smallImage, long smallImageId, String smallImageURL)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMTemplate (uuid_, templateId, groupId, ");
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
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, modifiedDate);
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
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping template with UUID " +
					uuid_);

			throw e;
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

		updateAssetEntryClassTypeId();

		super.doUpgrade();
	}

	protected long getCompanyGroupId(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from Group_ where classNameId = ? and " +
					"classPK = ?");

			ps.setLong(1, PortalUtil.getClassNameId(Company.class.getName()));
			ps.setLong(2, companyId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getDDMStructureClassNameId() {
		return PortalUtil.getClassNameId(
			"com.liferay.portlet.dynamicdatamapping.DDMStructure");
	}

	protected long getDDMStructureId(
		long groupId, long companyGroupId, String structureId) {

		return getDDMStructureId(groupId, companyGroupId, structureId, true);
	}

	protected long getDDMStructureId(
		long groupId, long companyGroupId, String structureId, boolean warn) {

		if (Validator.isNull(structureId)) {
			return 0;
		}

		Long ddmStructureId = _ddmStructureIds.get(groupId + "#" + structureId);

		if ((ddmStructureId == null) && (companyGroupId != 0)) {
			ddmStructureId = _ddmStructureIds.get(
				companyGroupId + "#" + structureId);
		}

		if (ddmStructureId != null) {
			return ddmStructureId;
		}

		if (warn && _log.isWarnEnabled()) {
			StringBundler sb = new StringBundler();

			sb.append("Unable to get the DDM structure ID for group ");
			sb.append(groupId);

			if (companyGroupId != 0) {
				sb.append(" or global group");
			}

			sb.append(" and journal structure ID ");
			sb.append(structureId);

			_log.warn(sb.toString());
		}

		return 0;
	}

	protected long getDDMStructureId(
		long groupId, String structureId, boolean warn) {

		return getDDMStructureId(groupId, 0, structureId, warn);
	}

	protected Locale getDefaultLocale(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select languageId from User_ where companyId = ? and " +
					"defaultUser = ?");

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			if (rs.next()) {
				String languageId = rs.getString("languageId");

				return LocaleUtil.fromLanguageId(languageId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return LocaleUtil.getSiteDefault();
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			"56_INSTANCE_%", "62_INSTANCE_%", "101_INSTANCE_%"
		};
	}

	protected void updateAssetEntryClassTypeId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select companyId, groupId, resourcePrimKey, structureId " +
					"from JournalArticle where structureId != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long resourcePrimKey = rs.getLong("resourcePrimKey");
				String structureId = rs.getString("structureId");

				long ddmStructureId = getDDMStructureId(
					groupId, getCompanyGroupId(companyId), structureId);

				runSQL(
					"update AssetEntry set classTypeId = " +
						ddmStructureId + " where classPK = " + resourcePrimKey);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
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

	protected long updateStructure(ResultSet rs) throws Exception {
		String uuid_ = rs.getString("uuid_");
		long id_ = rs.getLong("id_");
		long groupId = rs.getLong("groupId");
		long companyId = rs.getLong("companyId");
		long userId = rs.getLong("userId");
		String userName = rs.getString("userName");
		Timestamp createDate = rs.getTimestamp("createDate");
		Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
		String structureId = rs.getString("structureId");
		String parentStructureId = rs.getString("parentStructureId");
		String name = rs.getString("name");
		String description = rs.getString("description");
		String xsd = rs.getString("xsd");

		Long ddmStructureId = _ddmStructureIds.get(groupId + "#" + structureId);

		if (ddmStructureId != null) {
			return ddmStructureId;
		}

		ddmStructureId = increment();

		addDDMStructure(
			uuid_, ddmStructureId, groupId, companyId, userId, userName,
			createDate, modifiedDate, parentStructureId, structureId, name,
			description, xsd);

		updateResourcePermission(
			companyId, "com.liferay.portlet.journal.model.JournalStructure",
			"com.liferay.portlet.dynamicdatamapping.DDMStructure", id_,
			ddmStructureId);

		_ddmStructureIds.put(groupId + "#" + structureId, ddmStructureId);
		_ddmStructurePKs.put(id_, ddmStructureId);

		return ddmStructureId;
	}

	protected long updateStructure(String structureId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from JournalStructure where structureId = ?");

			ps.setString(1, structureId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return updateStructure(rs);
			}

			return 0;
		}
		catch (Exception e) {
			_log.error(
				"Unable to update journal structure with structure ID " +
					structureId);

			throw e;
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
				updateStructure(rs);
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
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
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

				long classNameId = getDDMStructureClassNameId();

				long classPK = getDDMStructureId(
					groupId, getCompanyGroupId(companyId), structureId);

				addDDMTemplate(
					uuid_, ddmTemplateId, groupId, companyId, userId, userName,
					createDate, modifiedDate, classNameId, classPK, templateId,
					name, description, _DDM_TEMPLATE_TYPE_DISPLAY,
					_DDM_TEMPLATE_MODE_CREATE, language, script, cacheable,
					smallImage, smallImageId, smallImageURL);

				updateResourcePermission(
					companyId,
					"com.liferay.portlet.journal.model.JournalTemplate",
					"com.liferay.portlet.dynamicdatamapping.DDMTemplate", id_,
					ddmTemplateId);
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

		if (portletId.startsWith(_PORTLET_ID_ASSET_PUBLISHER)) {
			updatePreferencesClassPKs(
				preferences, "anyClassTypeJournalArticleAssetRendererFactory");
			updatePreferencesClassPKs(preferences, "classTypeIds");
			updatePreferencesClassPKs(
				preferences, "classTypeIdsJournalArticleAssetRendererFactory");
		}
		else if (portletId.startsWith(_PORTLET_ID_JOURNAL_CONTENT)) {
			String templateId = preferences.getValue(
				"templateId", StringPool.BLANK);

			if (Validator.isNotNull(templateId)) {
				preferences.reset("templateId");

				preferences.setValue("ddmTemplateKey", templateId);
			}
		}
		else if (portletId.startsWith(_PORTLET_ID_JOURNAL_CONTENT_LIST)) {
			String structureId = preferences.getValue(
				"structureId", StringPool.BLANK);

			if (Validator.isNotNull(structureId)) {
				preferences.reset("structureId");

				preferences.setValue("ddmStructureKey", structureId);
			}
		}

		return PortletPreferencesFactoryUtil.toXML(preferences);
	}

	private static final int _DDM_STRUCTURE_TYPE_DEFAULT = 0;

	private static final String _DDM_TEMPLATE_MODE_CREATE = "create";

	private static final String _DDM_TEMPLATE_TYPE_DISPLAY = "display";

	private static final String _PORTLET_ID_ASSET_PUBLISHER = "101";

	private static final String _PORTLET_ID_JOURNAL_CONTENT = "56";

	private static final String _PORTLET_ID_JOURNAL_CONTENT_LIST = "62";

	private static final Log _log = LogFactoryUtil.getLog(UpgradeJournal.class);

	private final Map<String, Long> _ddmStructureIds = new HashMap<>();
	private final Map<Long, Long> _ddmStructurePKs = new HashMap<>();

}