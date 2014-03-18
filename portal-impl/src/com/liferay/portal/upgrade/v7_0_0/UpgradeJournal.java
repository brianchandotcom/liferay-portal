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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.upgrade.v7_0_0.util.JournalArticleTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.util.ContentUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Gergely Mathe
 * @author Eudaldo Alonso
 */
public class UpgradeJournal extends UpgradeProcess {

	protected String addBasicWebContentStructureAndTemplate(long companyId)
		throws Exception {

		long groupId = getCompanyGroupId(companyId);

		String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
			companyId);

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		List<Element> structureElements = getDDMStructures(defaultLocale);

		Element structureElement = structureElements.get(0);

		String name = structureElement.elementText("name");

		String description = structureElement.elementText("description");

		String localizedName = localize(groupId, name, defaultLanguageId);
		String localizedDescription = localize(
			groupId, description, defaultLanguageId);

		Element structureElementRootElement = structureElement.element("root");

		String xsd = structureElementRootElement.asXML();

		long ddmStructureId = addDDMStructure(
			increment(), groupId, companyId, name, localizedName,
			localizedDescription, xsd);

		String templateFileName = structureElement.elementText("template");

		addDDMTemplate(
			increment(), groupId, companyId, ddmStructureId, name,
			localizedName, localizedDescription, getContent(templateFileName));

		return name;
	}

	protected long addDDMStructure(
			long ddmStructureId, long groupId, long companyId,
			String ddmStructureKey, String localizedName,
			String localizedDescription, String xsd)
		throws Exception {

		Timestamp now = new Timestamp(System.currentTimeMillis());

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

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, ddmStructureId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, getDefaultUserId(companyId));
			ps.setString(6, StringPool.BLANK);
			ps.setTimestamp(7, now);
			ps.setTimestamp(8, now);
			ps.setLong(9, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);
			ps.setLong(10, PortalUtil.getClassNameId(JournalArticle.class));
			ps.setString(11, ddmStructureKey);
			ps.setString(12, localizedName);
			ps.setString(13, localizedDescription);
			ps.setString(14, xsd);
			ps.setString(15, "xml");
			ps.setInt(16, DDMStructureConstants.TYPE_DEFAULT);

			ps.executeUpdate();

			Map<String, Long> bitwiseValues = getBitwiseValues(
				DDMStructure.class.getName());

			List<String> actionIds = new ArrayList<String>();

			actionIds.add(ActionKeys.VIEW);

			long bitwiseValue = getBitwiseValue(bitwiseValues, actionIds);

			addResourcePermission(
				companyId, DDMStructure.class.getName(), ddmStructureId,
				getRoleId(companyId, RoleConstants.GUEST), bitwiseValue);
			addResourcePermission(
				companyId, DDMStructure.class.getName(), ddmStructureId,
				getRoleId(companyId, RoleConstants.SITE_MEMBER), bitwiseValue);
		}
		catch (Exception e) {
			_log.error("Unable to create the basic web content structure");

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return ddmStructureId;
	}

	protected long addDDMTemplate(
			long ddmTemplateId, long groupId, long companyId,
			long ddmStructureId, String templateKey, String localizedName,
			String localizedDescription, String script)
		throws Exception {

		Timestamp now = new Timestamp(System.currentTimeMillis());

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

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, ddmTemplateId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, getDefaultUserId(companyId));
			ps.setString(6, StringPool.BLANK);
			ps.setTimestamp(7, now);
			ps.setTimestamp(8, now);
			ps.setLong(9, PortalUtil.getClassNameId(DDMStructure.class));
			ps.setLong(10, ddmStructureId);
			ps.setString(11, templateKey);
			ps.setString(12, localizedName);
			ps.setString(13, localizedDescription);
			ps.setString(14, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY);
			ps.setString(15, DDMTemplateConstants.TEMPLATE_MODE_CREATE);
			ps.setString(16, TemplateConstants.LANG_TYPE_FTL);
			ps.setString(17, script);
			ps.setBoolean(18, false);
			ps.setBoolean(19, false);
			ps.setLong(20, 0);
			ps.setString(21, StringPool.BLANK);

			ps.executeUpdate();

			Map<String, Long> bitwiseValues = getBitwiseValues(
				DDMTemplate.class.getName());

			List<String> actionIds = new ArrayList<String>();

			actionIds.add(ActionKeys.VIEW);

			long bitwiseValue = getBitwiseValue(bitwiseValues, actionIds);

			addResourcePermission(
				companyId, DDMTemplate.class.getName(), ddmTemplateId,
				getRoleId(companyId, RoleConstants.GUEST), bitwiseValue);
			addResourcePermission(
				companyId, DDMTemplate.class.getName(), ddmTemplateId,
				getRoleId(companyId, RoleConstants.SITE_MEMBER), bitwiseValue);
		}
		catch (Exception e) {
			_log.error("Unable to create the basic web content template");

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return ddmTemplateId;
	}

	protected void addResourcePermission(
			long companyId, String className, long primKey, long roleId,
			long actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			long resourcePermissionId = increment(
				ResourcePermission.class.getName());

			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(3);

			sb.append("insert into ResourcePermission (resourcePermissionId, ");
			sb.append("companyId, name, scope, primKey, roleId, ownerId, ");
			sb.append("actionIds) values (?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, resourcePermissionId);
			ps.setLong(2, companyId);
			ps.setString(3, className);
			ps.setInt(4, ResourceConstants.SCOPE_INDIVIDUAL);
			ps.setLong(5, primKey);
			ps.setLong(6, roleId);
			ps.setLong(7, 0);
			ps.setLong(8, actionIds);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource permission " + className, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected String convertStaticContentToDynamic(String content)
		throws Exception {

		Document document = SAXReaderUtil.read(content);

		Document newDocument = SAXReaderUtil.createDocument();

		Element rootElement = document.getRootElement();

		String availableLocales = rootElement.attributeValue(
			"available-locales");
		String defaultLocale = rootElement.attributeValue("default-locale");

		Element newRootElement = SAXReaderUtil.createElement("root");

		newRootElement.addAttribute("available-locales", availableLocales);
		newRootElement.addAttribute("default-locale", defaultLocale);

		newDocument.add(newRootElement);

		Element dynamicElementElement = SAXReaderUtil.createElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("name", "content");
		dynamicElementElement.addAttribute("type", "text_area");
		dynamicElementElement.addAttribute("index-type", "keyword");
		dynamicElementElement.addAttribute("index", String.valueOf(0));

		newRootElement.add(dynamicElementElement);

		List<Element> staticContentElements = rootElement.elements(
			"static-content");

		for (Element staticContentElement : staticContentElements) {
			String languageId = staticContentElement.attributeValue(
				"language-id");
			String text = staticContentElement.getText();

			Element dynamicContentElement = SAXReaderUtil.createElement(
				"dynamic-content");

			dynamicContentElement.addAttribute("language-id", languageId);
			dynamicContentElement.addCDATA(text);

			dynamicElementElement.add(dynamicContentElement);
		}

		return DDMXMLUtil.formatXML(newDocument);
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateBasicWebContentStructure();

		try {
			runSQL("alter_column_type JournalArticle description TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				JournalArticleTable.TABLE_NAME,
				JournalArticleTable.TABLE_COLUMNS,
				JournalArticleTable.TABLE_SQL_CREATE,
				JournalArticleTable.TABLE_SQL_ADD_INDEXES);
		}
	}

	protected long getBitwiseValue(
		Map<String, Long> bitwiseValues, List<String> actionIds) {

		long bitwiseValue = 0;

		for (String actionId : actionIds) {
			Long actionIdBitwiseValue = bitwiseValues.get(actionId);

			if (actionIdBitwiseValue == null) {
				continue;
			}

			bitwiseValue |= actionIdBitwiseValue;
		}

		return bitwiseValue;
	}

	protected Map<String, Long> getBitwiseValues(String name) throws Exception {
		Map<String, Long> bitwiseValues = _bitwiseValues.get(name);

		if (bitwiseValues != null) {
			return bitwiseValues;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String currentShardName = null;

		try {
			currentShardName = ShardUtil.setTargetSource(
				PropsUtil.get(PropsKeys.SHARD_DEFAULT_NAME));

			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select actionId, bitwiseValue from ResourceAction " +
					"where name = ?");

			ps.setString(1, name);

			rs = ps.executeQuery();

			bitwiseValues = new HashMap<String, Long>();

			while (rs.next()) {
				String actionId = rs.getString("actionId");
				long bitwiseValue = rs.getLong("bitwiseValue");

				bitwiseValues.put(actionId, bitwiseValue);
			}

			_bitwiseValues.put(name, bitwiseValues);

			return bitwiseValues;
		}
		finally {
			if (Validator.isNotNull(currentShardName)) {
				ShardUtil.setTargetSource(currentShardName);
			}

			DataAccess.cleanUp(con, ps, rs);
		}
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

	protected String getContent(String fileName) {
		return ContentUtil.get(
			"com/liferay/portal/events/dependencies/" + fileName);
	}

	protected List<Element> getDDMStructures(Locale locale)
		throws DocumentException {

		String xml = getContent("basic-web-content-structure.xml");

		xml = StringUtil.replace(xml, "[$LOCALE_DEFAULT$]", locale.toString());

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		return rootElement.elements("structure");
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

	protected long getRoleId(long companyId, String name) throws Exception {
		String roleIdsKey = companyId + StringPool.POUND + name;

		Long roleId = _roleIds.get(roleIdsKey);

		if (roleId != null) {
			return roleId;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select roleId from Role_ where companyId = ? and name = ?");

			ps.setLong(1, companyId);
			ps.setString(2, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				roleId = rs.getLong("roleId");
			}

			_roleIds.put(roleIdsKey, roleId);

			return roleId;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String localize(
			long groupId, String key, String defaultLanguageId)
		throws Exception {

		Map<Locale, String> localizationMap = new HashMap<Locale, String>();

		Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

		for (Locale locale : locales) {
			localizationMap.put(locale, LanguageUtil.get(locale, key));
		}

		return LocalizationUtil.updateLocalization(
			localizationMap, StringPool.BLANK, key, defaultLanguageId);
	}

	protected void updateBasicWebContentStructure() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select companyId from Company");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				updateJournalArticles(companyId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateJournalArticle(
			long id_, String ddmStructureKey, String ddmTemplateKey,
			String content)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update JournalArticle set structureId = ?, templateId = ?, " +
					"content = ? where id_ = ?");

			ps.setString(1, ddmStructureKey);
			ps.setString(2, ddmTemplateKey);
			ps.setString(3, convertStaticContentToDynamic(content));
			ps.setLong(4, id_);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateJournalArticles(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select id_, content from JournalArticle where companyId = " +
					companyId + " and structureId is NULL or structureId " +
						"LIKE ''");

			String name = addBasicWebContentStructureAndTemplate(companyId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long id_ = rs.getLong("id_");
				String content = rs.getString("content");

				updateJournalArticle(id_, name, name, content);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeJournal.class);

	private Map<String, Map<String, Long>> _bitwiseValues =
		new HashMap<String, Map<String, Long>>();
	private Map<String, Long> _roleIds = new HashMap<String, Long>();

}