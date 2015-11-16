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

package com.liferay.journal.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.DDMStructureManager;
import com.liferay.portlet.dynamicdatamapping.DDMTemplateManager;
import com.liferay.portlet.dynamicdatamapping.StorageEngineManager;
import com.liferay.util.ContentUtil;
import com.liferay.util.xml.XMLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Gergely Mathe
 * @author Eudaldo Alonso
 */
public class UpgradeJournal extends UpgradeProcess {

	public UpgradeJournal(
		CompanyLocalService companyLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLinkLocalService ddmTemplateLinkLocalService,
		DDMTemplateLocalService ddmTemplateLocalService,
		GroupLocalService groupLocalService,
		UserLocalService userLocalService) {

		_companyLocalService = companyLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmTemplateLinkLocalService = ddmTemplateLinkLocalService;
		_ddmTemplateLocalService = ddmTemplateLocalService;
		_groupLocalService = groupLocalService;
		_userLocalService = userLocalService;
	}

	protected String addBasicWebContentStructureAndTemplate(long companyId)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(companyId);

		long groupId = group.getGroupId();

		String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
			companyId);

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		List<Element> structureElements = getDDMStructures(defaultLocale);

		Element structureElement = structureElements.get(0);

		String name = structureElement.elementText("name");

		String description = structureElement.elementText("description");

		Set<Locale> locales = LanguageUtil.getAvailableLocales(groupId);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			locales, name);
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(locales, description);

		Element structureElementDefinitionElement = structureElement.element(
			"definition");

		String definition = structureElementDefinitionElement.getTextTrim();

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			groupId, PortalUtil.getClassNameId(JournalArticle.class.getName()),
			name);

		if (ddmStructure != null) {
			return name;
		}

		try {
			long ddmStructureId = addDDMStructure(
				groupId, companyId, name, nameMap, descriptionMap, definition);

			Element templateElement = structureElement.element("template");

			String fileName = templateElement.elementText("file-name");

			boolean cacheable = GetterUtil.getBoolean(
				templateElement.elementText("cacheable"));

			addDDMTemplate(
				groupId, companyId, ddmStructureId, name, nameMap,
				descriptionMap, getContent(fileName), cacheable);

			if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				ddmStructureId = addDDMStructure(
					stagingGroup.getGroupId(), companyId, name, nameMap,
					descriptionMap, definition);

				addDDMTemplate(
					stagingGroup.getGroupId(), companyId, ddmStructureId, name,
					nameMap, descriptionMap, getContent(fileName), cacheable);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to create the basic web content structure and " +
					"template");
		}

		return name;
	}

	protected long addDDMStructure(
			long groupId, long companyId, String ddmStructureKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String definition)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long userId = _userLocalService.getDefaultUserId(companyId);

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			userId, groupId,
			DDMStructureManager.STRUCTURE_DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(JournalArticle.class.getName()),
			ddmStructureKey, nameMap, descriptionMap, definition,
			StorageEngineManager.STORAGE_TYPE_DEFAULT,
			DDMStructureManager.STRUCTURE_TYPE_DEFAULT, serviceContext);

		return ddmStructure.getStructureId();
	}

	protected long addDDMTemplate(
			long groupId, long companyId, long ddmStructureId,
			String templateKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String script,
			boolean cacheable)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long userId = _userLocalService.getDefaultUserId(companyId);

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.addTemplate(
			userId, groupId,
			PortalUtil.getClassNameId(_CLASS_NAME_DDM_STRUCTURE),
			ddmStructureId,
			PortalUtil.getClassNameId(JournalArticle.class.getName()),
			templateKey, nameMap, descriptionMap,
			DDMTemplateManager.TEMPLATE_TYPE_DISPLAY,
			DDMTemplateManager.TEMPLATE_MODE_CREATE,
			TemplateConstants.LANG_TYPE_FTL, script, cacheable, false, null,
			null, serviceContext);

		return ddmTemplate.getTemplateId();
	}

	protected void addDDMTemplateLinks() throws Exception {
		long classNameId = PortalUtil.getClassNameId(_CLASS_NAME_DDM_STRUCTURE);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("select DDMTemplate.templateId, JournalArticle.id_ ");
			sb.append("from JournalArticle inner join DDMTemplate on (");
			sb.append("DDMTemplate.groupId = JournalArticle.groupId and ");
			sb.append("DDMTemplate.templateKey = ");
			sb.append("JournalArticle.ddmTemplateKey and ");
			sb.append("JournalArticle.classNameId != ?)");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, classNameId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long templateId = rs.getLong("templateId");
				long id_ = rs.getLong("id_");

				_ddmTemplateLinkLocalService.addTemplateLink(
					classNameId, id_, templateId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
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

		return XMLUtil.formatXML(newDocument);
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateBasicWebContentStructure();

		addDDMTemplateLinks();
	}

	protected String getContent(String fileName) {
		Class<?> clazz = getClass();

		return ContentUtil.get(
			clazz.getClassLoader(),
			"com/liferay/journal/upgrade/v1_0_0/dependencies/" + fileName);
	}

	protected List<Element> getDDMStructures(Locale locale)
		throws DocumentException {

		String xml = getContent("basic-web-content-structure.xml");

		xml = StringUtil.replace(xml, "[$LOCALE_DEFAULT$]", locale.toString());

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		return rootElement.elements("structure");
	}

	protected void updateBasicWebContentStructure() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			updateJournalArticles(company.getCompanyId());
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
				"update JournalArticle set ddmStructureKey = ?, " +
					"ddmTemplateKey = ?, content = ? where id_ = ?");

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
					companyId + " and ddmStructureKey is null or " +
						"ddmStructureKey like ''");

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

	private static final String _CLASS_NAME_DDM_STRUCTURE =
		"com.liferay.dynamic.data.mapping.model.DDMStructure";

	private static final Log _log = LogFactoryUtil.getLog(UpgradeJournal.class);

	private final CompanyLocalService _companyLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final GroupLocalService _groupLocalService;
	private final UserLocalService _userLocalService;

}