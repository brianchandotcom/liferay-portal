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

import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ContentUtil;
import com.liferay.util.xml.XMLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Gergely Mathe
 * @author Eudaldo Alonso
 */
public class UpgradeJournal extends UpgradeProcess {

	public UpgradeJournal(
		CompanyLocalService companyLocalService,
		DDMTemplateLinkLocalService ddmTemplateLinkLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		GroupLocalService groupLocalService,
		UserLocalService userLocalService) {

		_companyLocalService = companyLocalService;
		_ddmTemplateLinkLocalService = ddmTemplateLinkLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_groupLocalService = groupLocalService;
		_userLocalService = userLocalService;
	}

	protected String addBasicWebContentStructureAndTemplate(long companyId)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(companyId);

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		boolean addResource = PermissionThreadLocal.isAddResource();

		PermissionThreadLocal.setAddResource(false);

		try {
			_defaultDDMStructureHelper.addDDMStructures(
				defaultUserId, group.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class),
				getClass().getClassLoader(),
				"com/liferay/journal/upgrade/v1_0_0/dependencies" +
					"/basic-web-content-structure.xml",
				new ServiceContext());
		}
		finally {
			PermissionThreadLocal.setAddResource(addResource);
		}

		String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
			companyId);

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		List<Element> structureElements = getDDMStructures(defaultLocale);

		Element structureElement = structureElements.get(0);

		return structureElement.elementText("name");
	}

	protected void addDDMTemplateLinks() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			DDMStructure.class.getName());

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(6);

			sb.append("select DDMTemplate.templateId, JournalArticle.id_ ");
			sb.append("from JournalArticle inner join DDMTemplate on (");
			sb.append("DDMTemplate.groupId = JournalArticle.groupId and ");
			sb.append("DDMTemplate.templateKey = ");
			sb.append("JournalArticle.ddmTemplateKey and ");
			sb.append("JournalArticle.classNameId != ?)");

			ps = connection.prepareStatement(sb.toString());

			ps.setLong(1, classNameId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long templateId = rs.getLong("templateId");
				long id = rs.getLong("id_");

				_ddmTemplateLinkLocalService.addTemplateLink(
					classNameId, id, templateId);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
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
		updateJournalArticlesDateFieldValues();

		addDDMTemplateLinks();
	}

	protected String getContent(String fileName) {
		Class<?> clazz = getClass();

		return ContentUtil.get(
			clazz.getClassLoader(),
			"com/liferay/journal/upgrade/v1_0_0/dependencies/" + fileName);
	}

	protected DDMForm getDDMForm(String structureKey) throws Exception {
		DDMForm ddmForm = _ddmForms.get(structureKey);

		if (ddmForm != null) {
			return ddmForm;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select (select structureKey from DDMStructure where " +
					" structureId = parentStructureId) parentStructureKey, " +
					" definition from DDMStructure where structureKey = ?" );

			ps.setString(1, structureKey);

			rs = ps.executeQuery();

			if (rs.next()) {
				String parentStructureKey = rs.getString("parentStructureKey");
				String definition = rs.getString("definition");

				ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

				if (parentStructureKey != null) {
					DDMForm parentDDMForm = getDDMForm(parentStructureKey);

					List<DDMFormField> ddmFormFields =
						ddmForm.getDDMFormFields();

					ddmFormFields.addAll(parentDDMForm.getDDMFormFields());
				}

				_ddmForms.put(structureKey, ddmForm);

				return ddmForm;
			}

			throw new UpgradeException(
				"Unable to find dynamic data mapping structure with key " +
					structureKey);
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected Map<String, String> getDDMFormFieldNameMap(DDMForm ddmForm) {
		Map<String, String> ddmFormFieldNameMap = new HashMap<>();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			Object oldName = ddmFormField.getProperties().get("oldName");

			if (oldName != null) {
				ddmFormFieldNameMap.put(
					oldName.toString(), ddmFormField.getName());
			}
			else {
				ddmFormFieldNameMap.put(
					ddmFormField.getName(), ddmFormField.getName());
			}
		}

		return ddmFormFieldNameMap;
	}

	protected List<Element> getDDMStructures(Locale locale)
		throws DocumentException {

		String xml = getContent("basic-web-content-structure.xml");

		xml = StringUtil.replace(xml, "[$LOCALE_DEFAULT$]", locale.toString());

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		return rootElement.elements("structure");
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

		if ( (dynamicElementElements == null) ||
			dynamicElementElements.isEmpty() ) {

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
		Document document = SAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		transformDateFieldValues(dynamicElementElements);

		return XMLUtil.formatXML(document);
	}

	protected void updateBasicWebContentStructure() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			updateJournalArticles(company.getCompanyId());
		}
	}

	protected String updateDDMFormFieldNames(
		String value, Map<String, String> ddmFormFieldNameMap) {

		for (Map.Entry<String, String> entry : ddmFormFieldNameMap.entrySet()) {
			value = value.replaceAll(entry.getKey(), entry.getValue());
		}

		return value;
	}

	protected void updateJournalArticle(
			long id, String ddmStructureKey, String ddmTemplateKey,
			String content)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update JournalArticle set ddmStructureKey = ?, " +
					"ddmTemplateKey = ?, content = ? where id_ = ?");

			ps.setString(1, ddmStructureKey);
			ps.setString(2, ddmTemplateKey);

			DDMForm ddmForm = getDDMForm(ddmStructureKey);

			content = updateDDMFormFieldNames(
				content, getDDMFormFieldNameMap(ddmForm));

			ps.setString(3, content);
			ps.setLong(4, id);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void updateJournalArticles(long companyId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select id_, content, ddmStructureKey from JournalArticle " +
					" where companyId = " + companyId +
					" and ddmStructureKey is null or ddmStructureKey like ''");

			String name = addBasicWebContentStructureAndTemplate(companyId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("id_");
				String content = rs.getString("content");
				String ddmStructureKey = rs.getString("ddmStructureKey");

				DDMForm ddmForm = getDDMForm(ddmStructureKey);

				updateDDMFormFieldNames(
					content, getDDMFormFieldNameMap(ddmForm));

				updateJournalArticle(id, name, name, content);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void updateJournalArticlesDateFieldValues() throws Exception {
		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select id_, content from JournalArticle where content like " +
					"'%type=_ddm-date_%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("id_");
				String content = rs.getString("content");

				updateJournalArticlesDateFieldValues(id, content);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void updateJournalArticlesDateFieldValues(long id, String content)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update JournalArticle set content = ? where id_ = ?");

			ps.setString(1, transformDateFieldValues(content));
			ps.setLong(2, id);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	private static final DateFormat _dateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");

	private final CompanyLocalService _companyLocalService;
	private final Map<String, DDMForm> _ddmForms = new HashMap<>();
	private final DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final GroupLocalService _groupLocalService;
	private final UserLocalService _userLocalService;

}