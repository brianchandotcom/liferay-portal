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
 */
public class JournalTestUtil {

	public static JournalArticle addArticle(
			long groupId, long folderId, String name, String content)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, name.concat(LocaleUtil.toLanguageId(locale)));
		}

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		StringBundler sb = new StringBundler(3 + 6 * _locales.length);

		sb.append("<?xml version=\"1.0\"?><root available-locales=");
		sb.append("\"en_US,es_ES,de_DE\" default-locale=\"en_US\">");

		for (Locale locale : _locales) {
			sb.append("<static-content language-id=\"");
			sb.append(LocaleUtil.toLanguageId(locale));
			sb.append("\"><![CDATA[<p>");
			sb.append(content);
			sb.append(LocaleUtil.toLanguageId(locale));
			sb.append("</p>]]></static-content>");
		}

		sb.append("</root>");

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId, folderId, 0, 0,
			StringPool.BLANK, true, 1, titleMap, descriptionMap, sb.toString(),
			"general", null, null, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0, true,
			0, 0, 0, 0, 0, true, false, false, null, null, null, null,
			serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, String name, String content)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, name,
			content);
	}

	public static JournalArticle addArticle(
			String content, String structureId, String templateId)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(Locale.US, "Test Article");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(TestPropsValues.getGroupId());

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(), 0, 0, 0,
			StringPool.BLANK, true, 0, titleMap, null, content, "general",
			structureId, templateId, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0,
			true, 0, 0, 0, 0, 0, true, false, false, null, null, null, null,
			serviceContext);
	}

	public static DDMStructure addDDMStructure() throws Exception {
		return addDDMStructure(getSampleStructureXSD());
	}

	public static DDMStructure addDDMStructure(String xsd) throws Exception {
		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, "Test Structure");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMStructureLocalServiceUtil.addStructure(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class.getName()), nameMap,
			null, xsd, serviceContext);
	}

	public static DDMTemplate addDDMTemplate(long structureId)
		throws Exception {

		return addDDMTemplate(
			structureId, getSampleTemplateXSL(),
			JournalTemplateConstants.LANG_TYPE_VM);
	}

	public static DDMTemplate addDDMTemplate(
			long structureId, String xsl, String lang)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(Locale.US, "Test Template");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DDMTemplateLocalServiceUtil.addTemplate(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class.getName()),
			structureId, nameMap, null,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null, lang, xsl,
			serviceContext);
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

	public static Document createDocument(
		String availableLocales, String defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
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
			titleMap.put(locale, title.concat(LocaleUtil.toLanguageId(locale)));
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