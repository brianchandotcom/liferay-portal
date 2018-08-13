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

import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.v6_2_0.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.upgrade.v6_2_0.util.JournalFeedTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.List;
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
			String uuid, long ddmStructureId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, long parentDDMStructureId, long classNameId,
			String ddmStructureKey, String name, String description, String xsd,
			String storageType, int type)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into DDMStructure (uuid_, structureId, groupId, ");
		sb.append("companyId, userId, userName, createDate, modifiedDate, ");
		sb.append("parentStructureId, classNameId, structureKey, name, ");
		sb.append("description, xsd, storageType, type_) values (?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, uuid);
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
			ps.setString(14, getDDMXSD(xsd, getDefaultLocale(companyId)));
			ps.setString(15, storageType);
			ps.setInt(16, type);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure with UUID " +
					uuid);

			throw e;
		}
	}

	protected void addDDMStructure(
			String uuid, long ddmStructureId, long groupId, long companyId,
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
				uuid, ddmStructureId, groupId, companyId, userId, userName,
				createDate, modifiedDate, parentDDMStructureId,
				PortalUtil.getClassNameId(
					"com.liferay.portlet.journal.model.JournalArticle"),
				ddmStructureKey, name, description, xsd, "xml",
				_DDM_STRUCTURE_TYPE_DEFAULT);
		}
	}

	protected void addDDMTemplate(
			String uuid, long ddmTemplateId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, long classNameId, long classPK,
			String templateKey, String name, String description, String type,
			String mode, String language, String script, boolean cacheable,
			boolean smallImage, long smallImageId, String smallImageURL)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("insert into DDMTemplate (uuid_, templateId, groupId, ");
		sb.append("companyId, userId, userName, createDate, modifiedDate,");
		sb.append("classNameId, classPK , templateKey, name, description,");
		sb.append("type_, mode_, language, script, cacheable, smallImage,");
		sb.append("smallImageId, smallImageURL) values (?, ?, ?, ?, ?, ?,?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, uuid);
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
					uuid);

			throw e;
		}
	}

	protected void addMetadataEntry(
		Element metadataElement, String name, String value) {

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", name);
		entryElement.addCDATA(value);
	}

	protected void addResourcePermission(
		PreparedStatement ps, long companyId, String primKey, long roleId) {

		try {
			ps.setLong(1, increment());
			ps.setLong(2, companyId);
			ps.setString(3, "com.liferay.portlet.journal");
			ps.setInt(4, ResourceConstants.SCOPE_INDIVIDUAL);
			ps.setString(5, primKey);
			ps.setLong(6, roleId);
			ps.setLong(7, 0);
			ps.setLong(8, 1);

			ps.addBatch();
		}
		catch (Exception e) {
			_log.error("Unable to insert ResourcePermission", e);
		}
	}

	protected String decodeURL(String url) {
		try {
			return HttpUtil.decodeURL(url);
		}
		catch (IllegalArgumentException iae) {
			return url;
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			JournalFeedTable.class,
			new AlterColumnName("feedType", "feedFormat VARCHAR(75) null"));

		setUpStrutureAttributesMappings();

		updateContentSearch();
		updateLinkToLayoutContent();
		updateStructures();
		updateTemplates();
		upgradeURLTitle();

		updateAssetEntryClassTypeId();
		updateJournalResourcePermission();

		super.doUpgrade();
	}

	protected Element fetchMetadataEntry(
		Element parentElement, String attributeName, String attributeValue) {

		StringBundler sb = new StringBundler(5);

		sb.append("entry[@");
		sb.append(attributeName);
		sb.append(StringPool.EQUAL);
		sb.append(HtmlUtil.escapeXPathAttribute(attributeValue));
		sb.append(StringPool.CLOSE_BRACKET);

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		return (Element)xPath.selectSingleNode(parentElement);
	}

	protected long getCompanyGroupId(long companyId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId from Group_ where classNameId = ? and " +
					"classPK = ?")) {

			ps.setLong(
				1,
				PortalUtil.getClassNameId("com.liferay.portal.model.Company"));
			ps.setLong(2, companyId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("groupId");
				}

				return 0;
			}
		}
	}

	protected long getDDMStructureClassNameId() {
		return PortalUtil.getClassNameId(
			"com.liferay.portlet.dynamicdatamapping.model.DDMStructure");
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
			StringBundler sb = new StringBundler(5);

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

	protected String getDDMXSD(String journalXSD, Locale defaultLocale)
		throws Exception {

		Document document = SAXReaderUtil.read(journalXSD);

		Element rootElement = document.getRootElement();

		rootElement.addAttribute("available-locales", defaultLocale.toString());
		rootElement.addAttribute("default-locale", defaultLocale.toString());

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateJournalXSDDynamicElement(
				dynamicElementElement, defaultLocale.toString());
		}

		return XMLUtil.formatXML(document);
	}

	protected Locale getDefaultLocale(long companyId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select languageId from User_ where companyId = ? and " +
					"defaultUser = ?")) {

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String languageId = rs.getString("languageId");

					return LocaleUtil.fromLanguageId(languageId);
				}
			}
		}

		return LocaleUtil.getSiteDefault();
	}

	protected long getJournalStructureClassNameId() {
		return PortalUtil.getClassNameId(
			"com.liferay.portlet.journal.model.JournalStructure");
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			"56_INSTANCE_%", "62_INSTANCE_%", "101_INSTANCE_%"
		};
	}

	protected long getRoleId(String roleName) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select roleId from Role_ where name = ?")) {

			ps.setString(1, roleName);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("roleId");
				}

				return 0;
			}
		}
	}

	protected void removeAttribute(Element element, String attributeName) {
		Attribute attribute = element.attribute(attributeName);

		if (attribute == null) {
			return;
		}

		element.remove(attribute);
	}

	protected void setUpStrutureAttributesMappings() {
		_ddmDataTypes.put("boolean", "boolean");
		_ddmDataTypes.put("document_library", "document-library");
		_ddmDataTypes.put("image", "image");
		_ddmDataTypes.put("link_to_layout", "link-to-page");
		_ddmDataTypes.put("list", "string");
		_ddmDataTypes.put("multi-list", "string");
		_ddmDataTypes.put("text", "string");
		_ddmDataTypes.put("text_area", "html");
		_ddmDataTypes.put("text_box", "string");

		_ddmMetadataAttributes.put("instructions", "tip");
		_ddmMetadataAttributes.put("label", "label");
		_ddmMetadataAttributes.put("predefinedValue", "predefinedValue");

		_journalTypesToDDMTypes.put("boolean", "checkbox");
		_journalTypesToDDMTypes.put("document_library", "ddm-documentlibrary");
		_journalTypesToDDMTypes.put("image", "ddm-image");
		_journalTypesToDDMTypes.put("image_gallery", "ddm-documentlibrary");
		_journalTypesToDDMTypes.put("link_to_layout", "ddm-link-to-page");
		_journalTypesToDDMTypes.put("list", "select");
		_journalTypesToDDMTypes.put("multi-list", "select");
		_journalTypesToDDMTypes.put("selection_break", "ddm-separator");
		_journalTypesToDDMTypes.put("text", "text");
		_journalTypesToDDMTypes.put("text_area", "ddm-text-html");
		_journalTypesToDDMTypes.put("text_box", "textarea");
	}

	protected void updateAssetEntryClassTypeId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select distinct companyId, groupId, resourcePrimKey, " +
					"structureId from JournalArticle where structureId != ''");
			ResultSet rs = ps1.executeQuery()) {

			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.journal.model.JournalArticle");

			try (PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update AssetEntry set classTypeId = ? where " +
							"classNameId = ? AND classPK = ?")) {

				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long resourcePrimKey = rs.getLong("resourcePrimKey");
					String structureId = rs.getString("structureId");

					long ddmStructureId = getDDMStructureId(
						groupId, getCompanyGroupId(companyId), structureId);

					ps2.setLong(1, ddmStructureId);

					ps2.setLong(2, classNameId);
					ps2.setLong(3, resourcePrimKey);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected void updateContentSearch() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select groupId, portletId from JournalContentSearch group " +
					"by groupId, portletId having count(groupId) > 1 and " +
						"count(portletId) > 1");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				String portletId = rs.getString("portletId");

				updateContentSearch(groupId, portletId);
			}
		}
	}

	protected void updateContentSearch(long groupId, String portletId)
		throws Exception {

		try (PreparedStatement selectPreferencesPS =
				connection.prepareStatement(
					"select preferences from PortletPreferences inner join " +
						"Layout on PortletPreferences.plid = Layout.plid " +
							"where groupId = ? and portletId = ?");
			PreparedStatement selectSearchPS = connection.prepareStatement(
				"select companyId, privateLayout, layoutId, portletId from " +
					"JournalContentSearch where JournalContentSearch.groupId " +
						"= ? and JournalContentSearch.articleId = ?");
			PreparedStatement deleteSearchPS = connection.prepareStatement(
				"delete from JournalContentSearch where " +
					"JournalContentSearch.groupId = ? and " +
						"JournalContentSearch.articleId = ?");
			PreparedStatement insertSearchPS = connection.prepareStatement(
				"insert into JournalContentSearch(contentSearchId, " +
					"companyId, groupId, privateLayout, layoutId, portletId, " +
						"articleId) values (?, ?, ?, ?, ?, ?, ?)")) {

			selectPreferencesPS.setLong(1, groupId);
			selectPreferencesPS.setString(2, portletId);

			try (ResultSet preferencesRS = selectPreferencesPS.executeQuery()) {
				while (preferencesRS.next()) {
					String xml = preferencesRS.getString("preferences");

					PortletPreferences portletPreferences =
						PortletPreferencesFactoryUtil.fromDefaultXML(xml);

					String articleId = portletPreferences.getValue(
						"articleId", null);

					selectSearchPS.setLong(1, groupId);
					selectSearchPS.setString(2, articleId);

					try (ResultSet searchRS = selectSearchPS.executeQuery()) {
						if (searchRS.next()) {
							long companyId = searchRS.getLong("companyId");
							boolean privateLayout = searchRS.getBoolean(
								"privateLayout");
							long layoutId = searchRS.getLong("layoutId");
							String journalContentSearchPortletId =
								searchRS.getString("portletId");

							deleteSearchPS.setLong(1, groupId);
							deleteSearchPS.setString(2, articleId);

							deleteSearchPS.executeUpdate();

							insertSearchPS.setLong(1, increment());
							insertSearchPS.setLong(2, companyId);
							insertSearchPS.setLong(3, groupId);
							insertSearchPS.setBoolean(4, privateLayout);
							insertSearchPS.setLong(5, layoutId);
							insertSearchPS.setString(
								6, journalContentSearchPortletId);
							insertSearchPS.setString(7, articleId);

							insertSearchPS.executeUpdate();
						}
					}
				}
			}
		}
	}

	protected void updateElement(long groupId, Element element) {
		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateElement(groupId, dynamicElementElement);
		}

		String type = element.attributeValue("type");

		if (type.equals("link_to_layout")) {
			updateLinkToLayoutElements(groupId, element);
		}
	}

	protected void updateJournalArticleClassNameIdAndClassPK(
			long journalStructureId, Long ddmStructureId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update JournalArticle set classNameId = ?, classPK = ? " +
					"where classNameId = ? and classPK = ?")) {

			ps.setLong(1, getDDMStructureClassNameId());
			ps.setLong(2, ddmStructureId);
			ps.setLong(3, getJournalStructureClassNameId());
			ps.setLong(4, journalStructureId);

			ps.execute();
		}
	}

	protected void updateJournalResourcePermission() throws Exception {
		long guestRoleId = getRoleId("Guest");
		long ownerRoleId = getRoleId("Owner");
		long siteMemberRoleId = getRoleId("Site Member");

		StringBundler updateSB = new StringBundler(10);

		updateSB.append("update ResourcePermission set actionIds = actionIds ");
		updateSB.append("+ 1 where name = 'com.liferay.portlet.journal' and ");
		updateSB.append("roleId in (");
		updateSB.append(guestRoleId);
		updateSB.append(",");
		updateSB.append(ownerRoleId);
		updateSB.append(",");
		updateSB.append(siteMemberRoleId);
		updateSB.append(") and ownerId = 0 and MOD(actionIds, 2) = 0 and ");
		updateSB.append("scope = 4");

		runSQL(updateSB.toString());

		StringBundler selectSB = new StringBundler(10);

		selectSB.append("select companyId, primKey, roleId from ");
		selectSB.append("ResourcePermission where name = ");
		selectSB.append("'com.liferay.portlet.journal' and ownerId = 0 and ");
		selectSB.append("scope = 4 and roleId in (");
		selectSB.append(guestRoleId);
		selectSB.append(",");
		selectSB.append(ownerRoleId);
		selectSB.append(",");
		selectSB.append(siteMemberRoleId);
		selectSB.append(") order by companyId, primKey, roleId");

		StringBundler insertSB = new StringBundler(4);

		insertSB.append("insert into ResourcePermission ");
		insertSB.append("(resourcePermissionId, companyId, name, scope, ");
		insertSB.append("primKey, roleId, ownerId, actionIds) values (?, ?, ");
		insertSB.append("?, ?, ?, ?, ?, ?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement selectPS = connection.prepareStatement(
				selectSB.toString());
			PreparedStatement insertPS =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(insertSB.toString()));
			ResultSet rs = selectPS.executeQuery()) {

			long currentCompanyId = 0;
			String currentPrimKey = null;
			boolean hasGuestResourcePermissions = false;
			boolean hasOwnerResourcePermissions = false;
			boolean hasSiteMemberResourcePermissions = false;

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String primKey = rs.getString("primKey");
				long roleId = rs.getLong("roleId");

				if ((currentPrimKey != null) &&
					!primKey.equals(currentPrimKey)) {

					if (!hasGuestResourcePermissions) {
						addResourcePermission(
							insertPS, currentCompanyId, currentPrimKey,
							guestRoleId);
					}

					if (!hasOwnerResourcePermissions) {
						addResourcePermission(
							insertPS, currentCompanyId, currentPrimKey,
							ownerRoleId);
					}

					if (!hasSiteMemberResourcePermissions) {
						addResourcePermission(
							insertPS, currentCompanyId, currentPrimKey,
							siteMemberRoleId);
					}

					currentPrimKey = primKey;
					currentCompanyId = companyId;
					hasGuestResourcePermissions = false;
					hasOwnerResourcePermissions = false;
					hasSiteMemberResourcePermissions = false;
				}

				if (currentPrimKey == null) {
					currentCompanyId = companyId;
					currentPrimKey = primKey;
				}

				if (guestRoleId == roleId) {
					hasGuestResourcePermissions = true;
				}
				else if (ownerRoleId == roleId) {
					hasOwnerResourcePermissions = true;
				}
				else if (siteMemberRoleId == roleId) {
					hasSiteMemberResourcePermissions = true;
				}
			}

			if (currentPrimKey != null) {
				if (!hasGuestResourcePermissions) {
					addResourcePermission(
						insertPS, currentCompanyId, currentPrimKey,
						guestRoleId);
				}

				if (!hasOwnerResourcePermissions) {
					addResourcePermission(
						insertPS, currentCompanyId, currentPrimKey,
						ownerRoleId);
				}

				if (!hasSiteMemberResourcePermissions) {
					addResourcePermission(
						insertPS, currentCompanyId, currentPrimKey,
						siteMemberRoleId);
				}
			}

			insertPS.executeBatch();
		}
	}

	protected void updateJournalXSDDynamicElement(
		Element element, String defaultLanguageId) {

		String name = element.attributeValue("name");
		String type = element.attributeValue("type");

		Element metadataElement = element.element("meta-data");

		if (metadataElement == null) {
			metadataElement = element.addElement("meta-data");
		}

		if (type.equals("multi-list")) {
			element.addAttribute("multiple", "true");
		}
		else {
			Element parentElement = element.getParent();

			String parentType = parentElement.attributeValue("type");

			if ((parentType != null) && parentType.equals("select")) {
				metadataElement.addAttribute("locale", defaultLanguageId);

				addMetadataEntry(metadataElement, "label", decodeURL(name));

				removeAttribute(element, "index-type");

				element.addAttribute("name", "option" + StringUtil.randomId());
				element.addAttribute("type", "option");
				element.addAttribute("value", decodeURL(type));

				return;
			}
		}

		String indexType = StringPool.BLANK;

		Attribute indexTypeAttribute = element.attribute("index-type");

		if (indexTypeAttribute != null) {
			indexType = indexTypeAttribute.getValue();

			element.remove(indexTypeAttribute);
		}

		element.remove(element.attribute("type"));

		if (!type.equals("selection_break")) {
			String dataType = _ddmDataTypes.get(type);

			if (dataType == null) {
				dataType = "string";
			}

			element.addAttribute("dataType", dataType);
		}

		element.addAttribute("indexType", indexType);

		String required = "false";

		Element requiredElement = fetchMetadataEntry(
			metadataElement, "name", "required");

		if (requiredElement != null) {
			required = requiredElement.getText();
		}

		element.addAttribute("required", required);

		element.addAttribute("showLabel", "true");

		String newType = _journalTypesToDDMTypes.get(type);

		if (newType == null) {
			newType = type;
		}

		element.addAttribute("type", newType);

		if (newType.startsWith("ddm")) {
			element.addAttribute("fieldNamespace", "ddm");
		}

		metadataElement.addAttribute("locale", defaultLanguageId);

		List<Element> entryElements = metadataElement.elements();

		if (entryElements.isEmpty()) {
			addMetadataEntry(metadataElement, "label", name);
		}
		else {
			for (Element entryElement : entryElements) {
				String oldEntryName = entryElement.attributeValue("name");

				String newEntryName = _ddmMetadataAttributes.get(oldEntryName);

				if (newEntryName == null) {
					metadataElement.remove(entryElement);
				}
				else {
					entryElement.addAttribute("name", newEntryName);
				}
			}
		}

		if (newType.equals("ddm-date") || newType.equals("ddm-decimal") ||
			newType.equals("ddm-integer") ||
			newType.equals("ddm-link-to-page") ||
			newType.equals("ddm-number") || newType.equals("ddm-text-html") ||
			newType.equals("text") || newType.equals("textarea")) {

			element.addAttribute("width", "25");
		}
		else if (newType.equals("ddm-image")) {
			element.addAttribute("fieldNamespace", "ddm");
			element.addAttribute("readOnly", "false");
		}

		element.add(metadataElement.detach());

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateJournalXSDDynamicElement(
				dynamicElementElement, defaultLanguageId);
		}
	}

	protected void updateLinkToLayoutContent() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement selectPS = connection.prepareStatement(
				"select id_, groupId, content from JournalArticle where " +
					"structureId != '' and content like '%link_to_layout%'");
			PreparedStatement updatePS =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update JournalArticle set content = ? where id_ = ?"));
			ResultSet rs = selectPS.executeQuery()) {

			while (rs.next()) {
				long id = rs.getLong("id_");
				long groupId = rs.getLong("groupId");
				String content = rs.getString("content");

				try {
					Document document = SAXReaderUtil.read(content);

					Element rootElement = document.getRootElement();

					for (Element element : rootElement.elements()) {
						updateElement(groupId, element);
					}

					updatePS.setString(1, document.asXML());
					updatePS.setLong(2, id);

					updatePS.addBatch();
				}
				catch (Exception e) {
					_log.error("Unable to update content for article " + id, e);
				}
			}

			updatePS.executeBatch();
		}
	}

	protected void updateLinkToLayoutElements(long groupId, Element element) {
		Element dynamicContentElement = element.element("dynamic-content");

		Node node = dynamicContentElement.node(0);

		String text = node.getText();

		if (!text.isEmpty() && !text.endsWith(StringPool.AT + groupId)) {
			node.setText(
				dynamicContentElement.getStringValue() + StringPool.AT +
					groupId);
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

		updateJournalArticleClassNameIdAndClassPK(id_, ddmStructureId);

		updateResourcePermission(
			companyId, "com.liferay.portlet.journal.model.JournalStructure",
			"com.liferay.portlet.dynamicdatamapping.model.DDMStructure", id_,
			ddmStructureId);

		_ddmStructureIds.put(groupId + "#" + structureId, ddmStructureId);
		_ddmStructurePKs.put(id_, ddmStructureId);

		return ddmStructureId;
	}

	protected long updateStructure(String structureId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select * from JournalStructure where structureId = ?")) {

			ps.setString(1, structureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return updateStructure(rs);
				}

				return 0;
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to update journal structure with structure ID " +
					structureId);

			throw e;
		}
	}

	protected void updateStructures() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select * from JournalStructure");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				updateStructure(rs);
			}

			runSQL("drop table JournalStructure");
		}
	}

	protected void updateTemplates() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select * from JournalTemplate");
			ResultSet rs = ps.executeQuery()) {

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
					"com.liferay.portlet.dynamicdatamapping.model.DDMTemplate",
					id_, ddmTemplateId);
			}

			runSQL("drop table JournalTemplate");
		}
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

	protected void upgradeURLTitle() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select distinct groupId, articleId, urlTitle from " +
					"JournalArticle");
			ResultSet rs = ps1.executeQuery()) {

			Map<String, String> processedArticleIds = new HashMap<>();

			try (PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update JournalArticle set urlTitle = ? where " +
								"urlTitle = ?"))) {

				while (rs.next()) {
					String urlTitle = GetterUtil.getString(
						rs.getString("urlTitle"));

					String normalizedURLTitle =
						FriendlyURLNormalizerUtil.
							normalizeWithPeriodsAndSlashes(urlTitle);

					if (urlTitle.equals(normalizedURLTitle)) {
						continue;
					}

					String articleId = rs.getString("articleId");
					long groupId = rs.getLong("groupId");

					normalizedURLTitle = _getUniqueUrlTitle(
						groupId, articleId, normalizedURLTitle,
						processedArticleIds);

					ps2.setString(1, normalizedURLTitle);

					ps2.setString(2, urlTitle);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private String _getUniqueUrlTitle(
			long groupId, String articleId, String urlTitle,
			Map<String, String> processedArticleIds)
		throws Exception {

		for (int i = 1;; i++) {
			String key = groupId + StringPool.UNDERLINE + urlTitle;

			String processedArticleId = processedArticleIds.get(key);

			if (((processedArticleId == null) ||
				 processedArticleId.equals(articleId)) &&
				_isValidUrlTitle(groupId, articleId, urlTitle)) {

				processedArticleIds.put(key, articleId);

				return urlTitle;
			}

			String suffix = StringPool.DASH + i;

			String prefix = urlTitle;

			if (urlTitle.length() > suffix.length()) {
				prefix = urlTitle.substring(
					0, urlTitle.length() - suffix.length());
			}

			urlTitle = prefix + suffix;
		}
	}

	private boolean _isValidUrlTitle(
			long groupId, String articleId, String urlTitle)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from JournalArticle where groupId = ? and " +
					"urlTitle = ? and articleId != ?")) {

			ps.setLong(1, groupId);
			ps.setString(2, urlTitle);
			ps.setString(3, articleId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return false;
					}
				}

				return true;
			}
		}
	}

	private static final int _DDM_STRUCTURE_TYPE_DEFAULT = 0;

	private static final String _DDM_TEMPLATE_MODE_CREATE = "create";

	private static final String _DDM_TEMPLATE_TYPE_DISPLAY = "display";

	private static final String _PORTLET_ID_ASSET_PUBLISHER = "101";

	private static final String _PORTLET_ID_JOURNAL_CONTENT = "56";

	private static final String _PORTLET_ID_JOURNAL_CONTENT_LIST = "62";

	private static final Log _log = LogFactoryUtil.getLog(UpgradeJournal.class);

	private final Map<String, String> _ddmDataTypes = new HashMap<>();
	private final Map<String, String> _ddmMetadataAttributes = new HashMap<>();
	private final Map<String, Long> _ddmStructureIds = new HashMap<>();
	private final Map<Long, Long> _ddmStructurePKs = new HashMap<>();
	private final Map<String, String> _journalTypesToDDMTypes = new HashMap<>();

}