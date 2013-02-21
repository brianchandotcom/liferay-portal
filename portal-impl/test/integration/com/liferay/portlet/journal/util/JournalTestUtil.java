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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Juan Fernández
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
public class JournalTestUtil {

	public static JournalArticle addArticle(
			long groupId, long folderId, String title, String content)
		throws Exception {

		return addArticle(
			groupId, folderId, title, content, LocaleUtil.getDefault(), false,
			false);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String title, String content,
			Locale defaultLocale, boolean workflowEnabled, boolean approved)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title);
		}

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			descriptionMap.put(locale, title);
		}

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		if (workflowEnabled) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			if (approved) {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);
			}
		}

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId, folderId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, createLocalizedContent(content, defaultLocale),
			"general", null, null, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0, true,
			0, 0, 0, 0, 0, true, true, false, null, null, null, null,
			serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, String title, String content)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			content, LocaleUtil.getDefault(), false, false);
	}

	public static JournalArticle addArticle(
			long groupId, String title, String content, Locale defaultLocale)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			content, defaultLocale, false, false);
	}

	public static JournalArticle addArticleWithWorkflow(boolean approved)
		throws Exception {

		return addArticleWithWorkflow("title", "content", approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, boolean approved)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title",
			"content", LocaleUtil.getDefault(), true, approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, String title, String content, boolean approved)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			content, LocaleUtil.getDefault(), true, approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			String title, boolean approved)
		throws Exception {

		return addArticleWithWorkflow(title, "content", approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			String title, String content, boolean approved)
		throws Exception {

		return addArticle(
			TestPropsValues.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title, content,
			LocaleUtil.getDefault(), true, approved);
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, String xml, String ddmStructureKey,
			String ddmTemplateKey)
		throws Exception {

		return addArticleWithXMLContent(
			groupId, xml, ddmStructureKey, ddmTemplateKey,
			LocaleUtil.getDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, String xml, String ddmStructureKey,
			String ddmTemplateKey, Locale defaultLocale)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(defaultLocale, "Test Article");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(TestPropsValues.getGroupId());

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId, 0, 0, 0, StringPool.BLANK,
			true, 0, titleMap, null, xml, "general", ddmStructureKey,
			ddmTemplateKey, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0, true, 0, 0,
			0, 0, 0, true, true, false, null, null, null, null, serviceContext);
	}

	public static JournalArticle addArticleWithXMLContent(
			String xml, String ddmStructureKey, String ddmTemplateKey)
		throws Exception {

		return addArticleWithXMLContent(
			TestPropsValues.getGroupId(), xml, ddmStructureKey, ddmTemplateKey,
			LocaleUtil.getDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			String xml, String ddmStructureKey, String ddmTemplateKey,
			Locale defaultLocale)
		throws Exception {

		return addArticleWithXMLContent(
			TestPropsValues.getGroupId(), xml, ddmStructureKey, ddmTemplateKey,
			defaultLocale);
	}

	public static DDMStructure addDDMStructure() throws Exception {
		return addDDMStructure(
			TestPropsValues.getGroupId(), getSampleStructureXSD(),
			LocaleUtil.getDefault());
	}

	public static DDMStructure addDDMStructure(Locale defaultLocale)
		throws Exception {

		return addDDMStructure(
			TestPropsValues.getGroupId(), getSampleStructureXSD(),
			defaultLocale);
	}

	public static DDMStructure addDDMStructure(long groupId) throws Exception {
		return addDDMStructure(
			groupId, getSampleStructureXSD(), LocaleUtil.getDefault());
	}

	public static DDMStructure addDDMStructure(
		long groupId, Locale defaultLocale) throws Exception {

		return addDDMStructure(
			groupId, getSampleStructureXSD(), LocaleUtil.getDefault());
	}

	public static DDMStructure addDDMStructure(
			long groupId, String xsd, Locale defaultLocale)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(defaultLocale, "Test Structure");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), groupId,
			PortalUtil.getClassNameId(JournalArticle.class.getName()), nameMap,
			null, xsd, serviceContext);
	}

	public static DDMStructure addDDMStructure(String xsd) throws Exception {
		return addDDMStructure(
			TestPropsValues.getGroupId(), xsd, LocaleUtil.getDefault());
	}

	public static DDMStructure addDDMStructure(String xsd, Locale defaultLocale)
		throws Exception {

		return addDDMStructure(
			TestPropsValues.getGroupId(), xsd, defaultLocale);
	}

	public static DDMTemplate addDDMTemplate(long ddmStructureId)
		throws Exception {

		return addDDMTemplate(
			ddmStructureId, TemplateConstants.LANG_TYPE_VM,
			getSampleTemplateXSL(), LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long ddmStructureId, Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			ddmStructureId, TemplateConstants.LANG_TYPE_VM,
			getSampleTemplateXSL(), defaultLocale);
	}

	public static DDMTemplate addDDMTemplate(long groupId, long ddmStructureId)
		throws Exception {

		return addDDMTemplate(
			groupId, ddmStructureId, TemplateConstants.LANG_TYPE_VM,
			getSampleTemplateXSL(), LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long groupId, long ddmStructureId, Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			groupId, ddmStructureId, TemplateConstants.LANG_TYPE_VM,
			getSampleTemplateXSL(), defaultLocale);
	}

	public static DDMTemplate addDDMTemplate(
			long groupId, long ddmStructureId, String language, String script,
			Locale defaultLocale)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(defaultLocale, "Test Template");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), groupId,
			PortalUtil.getClassNameId(DDMStructure.class.getName()),
			ddmStructureId, nameMap, null,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null, language, script,
			serviceContext);
	}

	public static DDMTemplate addDDMTemplate(
			long ddmStructureId, String language, String script)
		throws Exception {

		return addDDMTemplate(
			TestPropsValues.getGroupId(), ddmStructureId, language, script,
			LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long ddmStructureId, String language, String script,
			Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			TestPropsValues.getGroupId(), ddmStructureId, language, script,
			defaultLocale);
	}

	public static void addDynamicContentElement(
		Element dynamicElementElement, String languageId, String value) {

		Element dynamicContentElement = dynamicElementElement.addElement(
			"dynamic-content");

		dynamicContentElement.addAttribute("language-id", languageId);
		dynamicContentElement.setText(value);
	}

	public static Element addDynamicElementElement(
		Element element, String type, String name) {

		Element dynamicElementElement = element.addElement("dynamic-element");

		dynamicElementElement.addAttribute("name", name);
		dynamicElementElement.addAttribute("type", type);

		return dynamicElementElement;
	}

	public static JournalFolder addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		JournalFolder folder = JournalFolderLocalServiceUtil.fetchFolder(
			groupId, name);

		if (folder != null) {
			return folder;
		}

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return JournalFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), groupId, parentFolderId, name,
			"This is a test folder.", serviceContext);
	}

	public static JournalFolder addFolder(long groupId, String name)
		throws Exception {

		return addFolder(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
	}

	public static void addLanguageIdElement(
		Element element, String languageId, String value) {

		Element staticContentElement = element.addElement("static-content");

		staticContentElement.addAttribute("language-id", languageId);
		staticContentElement.setText(value);
	}

	public static Document createDocument(
		String availableLocales, String defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
	}

	public static String createLocalizedContent(
		String content, Locale defaultLocale) {

		StringBundler sb = new StringBundler((2 * _locales.length) - 1);

		for (int i = 0; i < _locales.length; i++) {
			Locale locale = _locales[i];

			sb.append(LocaleUtil.toLanguageId(locale));

			if (i < (_locales.length - 1)) {
				sb.append(StringPool.COMMA);
			}
		}

		Document document = createDocument(
			sb.toString(), LocaleUtil.toLanguageId(defaultLocale));

		for (Locale locale : _locales) {
			addLanguageIdElement(
				document.getRootElement(), LocaleUtil.toLanguageId(locale),
				content);
		}

		return document.asXML();
	}

	public static String getSampleStructuredContent() {
		Document document = createDocument("en_US", "en_US");

		Element dynamicElementElement = addDynamicElementElement(
			document.getRootElement(), "text", "name");

		addDynamicContentElement(dynamicElementElement, "en_US", "Joe Bloggs");

		return document.asXML();
	}

	public static String getSampleStructureXSD() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		addDynamicElementElement(rootElement, "text", "name");
		addDynamicElementElement(rootElement, "text", "link");

		return document.asXML();
	}

	public static String getSampleTemplateXSL() {
		return "$name.getData()";
	}

	public static JournalArticle updateArticle(
			JournalArticle article, String title, String content)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title);
		}

		return JournalArticleLocalServiceUtil.updateArticle(
			article.getUserId(), article.getGroupId(), article.getFolderId(),
			article.getArticleId(), article.getVersion(), titleMap,
			article.getDescriptionMap(), content, article.getLayoutUuid(),
			ServiceTestUtil.getServiceContext());
	}

	private static Locale[] _locales = {
		new Locale("en", "US"), new Locale("de", "DE"), new Locale("es", "ES")
	};

}