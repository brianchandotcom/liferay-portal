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
import com.liferay.portlet.journal.model.JournalTemplateConstants;
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

		String localizedContent = createLocalizedContent(
			content, defaultLocale);

		boolean indexable = true;

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId, folderId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, localizedContent, "general", null, null, null, 1, 1,
			1965, 0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, indexable,
			false, null, null, null, null, serviceContext);
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

	public static JournalArticle addArticleWithWorkflow(
			long groupId, String title, String content, boolean approved)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			content, LocaleUtil.getDefault(), true, approved);
	}

	public static JournalArticle addArticleWithXMLContent(
			String xml, String structureId, String templateId)
		throws Exception {

		return addArticleWithXMLContent(
			TestPropsValues.getGroupId(), xml, structureId, templateId,
			LocaleUtil.getDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			String xml, String structureId, String templateId,
			Locale defaultLocale)
		throws Exception {

		return addArticleWithXMLContent(
			TestPropsValues.getGroupId(), xml, structureId, templateId,
			defaultLocale);
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, String xml, String structureId, String templateId)
		throws Exception {

		return addArticleWithXMLContent(
			groupId, xml, structureId, templateId, LocaleUtil.getDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, String xml, String structureId, String templateId,
			Locale defaultLocale)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(defaultLocale, "Test Article");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(TestPropsValues.getGroupId());

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId, 0, 0, 0, StringPool.BLANK,
			true, 0, titleMap, null, xml, "general", structureId,
			templateId, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0,
			0, true, true, false, null, null, null, null, serviceContext);
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
		return addDDMStructure(groupId, getSampleStructureXSD(),
			LocaleUtil.getDefault());
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

	public static DDMStructure addDDMStructure(
		String xsd, Locale defaultLocale) throws Exception {

		return addDDMStructure(
			TestPropsValues.getGroupId(), xsd, defaultLocale);
	}

	public static DDMTemplate addDDMTemplate(long structureId)
		throws Exception {

		return addDDMTemplate(
			structureId, getSampleTemplateXSL(),
			JournalTemplateConstants.LANG_TYPE_VM, LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long structureId, Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			structureId, getSampleTemplateXSL(),
			JournalTemplateConstants.LANG_TYPE_VM, defaultLocale);
	}

	public static DDMTemplate addDDMTemplate(long groupId, long structureId)
		throws Exception {

		return addDDMTemplate(
			groupId, structureId, getSampleTemplateXSL(),
			JournalTemplateConstants.LANG_TYPE_VM, LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long groupId, long structureId, Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			groupId, structureId, getSampleTemplateXSL(),
			JournalTemplateConstants.LANG_TYPE_VM, defaultLocale);
	}

	public static DDMTemplate addDDMTemplate(
			long structureId, String xsl, String lang)
		throws Exception {

		return addDDMTemplate(
			TestPropsValues.getGroupId(), structureId, xsl, lang,
			LocaleUtil.getDefault());
	}

	public static DDMTemplate addDDMTemplate(
			long groupId, long structureId, String xsl, String lang,
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
			structureId, nameMap, null,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null, lang, xsl,
			serviceContext);
	}

	public static DDMTemplate addDDMTemplate(
			long structureId, String xsl, String lang, Locale defaultLocale)
		throws Exception {

		return addDDMTemplate(
			TestPropsValues.getGroupId(), structureId, xsl, lang,
			defaultLocale);
	}

	public static void addDynamicContent(
		Element dynamicElement, String languageId, String value) {

		Element dynamicContent = dynamicElement.addElement("dynamic-content");

		dynamicContent.addAttribute("language-id", languageId);

		dynamicContent.setText(value);
	}

	public static Element addDynamicElement(
		Element element, String type, String name) {

		Element dynamicElement = element.addElement("dynamic-element");

		dynamicElement.addAttribute("name", name);
		dynamicElement.addAttribute("type", type);

		return dynamicElement;
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

	public static void addLanguageIdElement(
		Element element, String languageId, String value) {

		Element staticContent = element.addElement("static-content");

		staticContent.addAttribute("language-id", languageId);

		staticContent.setText(value);
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

		StringBundler availableLocales =
			new StringBundler((2 * _locales.length) - 1);

		for (int i = 0; i < _locales.length; i++) {
			Locale locale = _locales[i];

			availableLocales.append(LocaleUtil.toLanguageId(locale));

			if (i < (_locales.length - 1)) {
				availableLocales.append(StringPool.COMMA);
			}
		}

		Document document = createDocument(
			availableLocales.toString(),
			LocaleUtil.toLanguageId(defaultLocale));

		for (Locale locale : _locales) {
			addLanguageIdElement(
				document.getRootElement(), LocaleUtil.toLanguageId(locale),
				content);
		}

		return document.asXML();
	}

	public static String getSampleStructuredContent() {
		Document document = createDocument("en_US", "en_US");

		Element dynamicElement = addDynamicElement(
			document.getRootElement(), "text", "name");

		addDynamicContent(dynamicElement, "en_US", "Joe Bloggs");

		return document.asXML();
	}

	public static String getSampleStructureXSD() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		addDynamicElement(rootElement, "text", "name");
		addDynamicElement(rootElement, "text", "link");

		return document.asXML();
	}

	public static String getSampleTemplateXSL() {
		return "$name.getData()";
	}

	public static JournalArticle updateArticle(
			JournalArticle journalArticle, String title, String content)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title);
		}

		return JournalArticleLocalServiceUtil.updateArticle(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			journalArticle.getFolderId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), titleMap,
			journalArticle.getDescriptionMap(), content,
			journalArticle.getLayoutUuid(),
			ServiceTestUtil.getServiceContext());
	}

	private static Locale[] _locales = {
		new Locale("en", "US"), new Locale("es", "ES"),
		new Locale("de", "DE")
	};

}