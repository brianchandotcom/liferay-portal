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

package com.liferay.journal.upgrade.v0_0_5;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.content.ContentUtil;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gergely Mathe
 * @author Eudaldo Alonso
 */
public class UpgradeJournal extends UpgradeProcess {

	public UpgradeJournal(
		CompanyLocalService companyLocalService,
		DDMStorageLinkLocalService ddmStorageLinkLocalService,
		DDMTemplateLinkLocalService ddmTemplateLinkLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		GroupLocalService groupLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceActions resourceActions, UserLocalService userLocalService) {

		_companyLocalService = companyLocalService;
		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
		_ddmTemplateLinkLocalService = ddmTemplateLinkLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_groupLocalService = groupLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceActions = resourceActions;
		_userLocalService = userLocalService;
	}

	protected String addBasicWebContentStructureAndTemplate(long companyId)
		throws Exception {

		initJournalDDMCompositeModelsResourceActions();

		Group group = _groupLocalService.getCompanyGroup(companyId);

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		Class<?> clazz = getClass();

		_defaultDDMStructureHelper.addDDMStructures(
			defaultUserId, group.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			clazz.getClassLoader(),
			"com/liferay/journal/upgrade/v1_0_0/dependencies" +
				"/basic-web-content-structure.xml",
			new ServiceContext());

		String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
			companyId);

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		List<Element> structureElements = getDDMStructures(defaultLocale);

		Element structureElement = structureElements.get(0);

		return structureElement.elementText("name");
	}

	protected void addDDMStorageLinks() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long journalArticleClassNameId = PortalUtil.getClassNameId(
				JournalArticle.class.getName());

			StringBundler sb = new StringBundler(8);

			sb.append("select DDMStructure.structureId, JournalArticle.id_ ");
			sb.append("from JournalArticle inner join DDMStructure on (");
			sb.append("DDMStructure.groupId in (select distinct Group_.");
			sb.append("groupId from Group_ where (Group_.groupId = ");
			sb.append("JournalArticle.groupId) or (Group_.companyId = ");
			sb.append("JournalArticle.companyId and Group_.friendlyURL = ?)) ");
			sb.append("and DDMStructure.structureKey = JournalArticle.");
			sb.append("DDMStructureKey and JournalArticle.classNameId != ?)");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString())) {

				ps.setString(1, GroupConstants.GLOBAL_FRIENDLY_URL);
				ps.setLong(
					2, PortalUtil.getClassNameId(DDMStructure.class.getName()));

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						long structureId = rs.getLong("structureId");
						long id = rs.getLong("id_");

						_ddmStorageLinkLocalService.addStorageLink(
							journalArticleClassNameId, id, structureId,
							new ServiceContext());
					}
				}
			}
		}
	}

	protected void addDDMTemplateLinks() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				DDMStructure.class.getName());

			StringBundler sb = new StringBundler(6);

			sb.append("select DDMTemplate.templateId, JournalArticle.id_ ");
			sb.append("from JournalArticle inner join DDMTemplate on (");
			sb.append("DDMTemplate.groupId = JournalArticle.groupId and ");
			sb.append("DDMTemplate.templateKey = ");
			sb.append("JournalArticle.DDMTemplateKey and ");
			sb.append("JournalArticle.classNameId != ?)");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString())) {

				ps.setLong(1, classNameId);

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						long templateId = rs.getLong("templateId");
						long id = rs.getLong("id_");

						_ddmTemplateLinkLocalService.addTemplateLink(
							classNameId, id, templateId);
					}
				}
			}
		}
	}

	protected boolean containsDateFieldType(String content) {
		if (content.indexOf(_TYPE_ATTRIBUTE_DDM_DATE) != -1) {
			return true;
		}

		return false;
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
		updateJournalArticles();

		addDDMStorageLinks();
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

	protected Map<String, String> getInvalidDDMFormFieldNamesMap(
		String content) {

		Map<String, String> invalidDDMFormFieldNamesMap = new HashMap<>();

		Matcher matcher = _nameAttributePattern.matcher(content);

		while (matcher.find()) {
			String oldFieldName = matcher.group(1);

			String newFieldName = oldFieldName.replaceAll(
				_INVALID_FIELD_NAME_CHARS_REGEX, StringPool.BLANK);

			if (!oldFieldName.equals(newFieldName)) {
				invalidDDMFormFieldNamesMap.put(oldFieldName, newFieldName);
			}
		}

		return invalidDDMFormFieldNamesMap;
	}

	protected void initJournalDDMCompositeModelsResourceActions()
		throws Exception {

		_resourceActions.read(
			null, UpgradeJournal.class.getClassLoader(),
			"/META-INF/resource-actions/journal_ddm_composite_models.xml");

		List<String> modelNames = _resourceActions.getPortletModelResources(
			JournalPortletKeys.JOURNAL);

		for (String modelName : modelNames) {
			List<String> modelActions =
				_resourceActions.getModelResourceActions(modelName);

			_resourceActionLocalService.checkResourceActions(
				modelName, modelActions);
		}
	}

	protected void transformDateFieldValue(Element dynamicContentElement) {
		String value = dynamicContentElement.getText();

		if (!Validator.isNumber(value)) {
			return;
		}

		Date date = new Date(GetterUtil.getLong(value));

		dynamicContentElement.clearContent();

		dynamicContentElement.addCDATA(_dateFormat.format(date));
	}

	protected void transformDateFieldValues(
		List<Element> dynamicElementElements) {

		if ((dynamicElementElements == null) ||
			dynamicElementElements.isEmpty()) {

			return;
		}

		for (Element dynamicElementElement : dynamicElementElements) {
			String type = GetterUtil.getString(
				dynamicElementElement.attributeValue("type"));

			if (type.equals("ddm-date")) {
				List<Element> dynamicContentElements =
					dynamicElementElement.elements("dynamic-content");

				for (Element dynamicContentElement : dynamicContentElements) {
					transformDateFieldValue(dynamicContentElement);
				}
			}

			List<Element> childDynamicElementElements =
				dynamicElementElement.elements("dynamic-element");

			transformDateFieldValues(childDynamicElementElements);
		}
	}

	protected String transformDateFieldValues(String content) throws Exception {
		if (!containsDateFieldType(content)) {
			return content;
		}

		Document document = SAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		transformDateFieldValues(dynamicElementElements);

		return XMLUtil.formatXML(document);
	}

	protected String transformFieldNames(String content) {
		Map<String, String> invalidDDMFormFieldNamesMap =
			getInvalidDDMFormFieldNamesMap(content);

		for (Map.Entry<String, String> entry :
				invalidDDMFormFieldNamesMap.entrySet()) {

			content = StringUtil.replace(
				content, entry.getKey(), entry.getValue());
		}

		return content;
	}

	protected void updateJournalArticle(
			long id, String ddmStructureKey, String ddmTemplateKey,
			String content)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update JournalArticle set DDMStructureKey = ?, " +
					"DDMTemplateKey = ?, content = ? where id_ = ?")) {

			ps.setString(1, ddmStructureKey);
			ps.setString(2, ddmTemplateKey);
			ps.setString(3, content);
			ps.setLong(4, id);

			ps.executeUpdate();
		}
	}

	protected void updateJournalArticleContent(long id, String content)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update JournalArticle set content = ? where id_ = ?")) {

			ps.setString(1, content);
			ps.setLong(2, id);

			ps.executeUpdate();
		}
	}

	protected void updateJournalArticles() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				updateJournalArticles(company.getCompanyId());
			}
		}
	}

	protected void updateJournalArticles(long companyId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select id_, content, DDMStructureKey from " +
					"JournalArticle where companyId = " + companyId);
			ResultSet rs = ps.executeQuery()) {

			String name = addBasicWebContentStructureAndTemplate(companyId);

			while (rs.next()) {
				long id = rs.getLong("id_");
				String content = rs.getString("content");
				String ddmStructureKey = rs.getString("DDMStructureKey");

				if (Validator.isNull(ddmStructureKey)) {
					content = convertStaticContentToDynamic(content);

					updateJournalArticle(id, name, name, content);

					continue;
				}

				String updatedContent = transformDateFieldValues(content);

				updatedContent = transformFieldNames(updatedContent);

				if (!content.equals(updatedContent)) {
					updateJournalArticleContent(id, updatedContent);
				}
			}
		}
	}

	private static final String _INVALID_FIELD_NAME_CHARS_REGEX =
		"([\\p{Punct}&&[^_]]|\\p{Space})+";

	private static final String _TYPE_ATTRIBUTE_DDM_DATE = "type=\"ddm-date\"";

	private static final DateFormat _dateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

	private final CompanyLocalService _companyLocalService;
	private final DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private final DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final GroupLocalService _groupLocalService;
	private final Pattern _nameAttributePattern = Pattern.compile(
		"name=\"([^\"]+)\"");
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceActions _resourceActions;
	private final UserLocalService _userLocalService;

}