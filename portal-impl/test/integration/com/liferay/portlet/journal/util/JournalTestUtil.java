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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFeedConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.util.RSSUtil;

import java.util.HashMap;
import java.util.List;
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
			groupId, folderId, title, title, content, LocaleUtil.getDefault(),
			false, false);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String title, String content,
			Locale defaultLocale, boolean workflowEnabled, boolean approved)
		throws Exception {

		return addArticle(
			groupId, folderId, title, title, content, defaultLocale,
			workflowEnabled, approved);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String title, String description,
			String content, Locale defaultLocale, boolean workflowEnabled,
			boolean approved)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return addArticle(
			groupId, folderId, title, description, content, defaultLocale,
			workflowEnabled, approved, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String title, String description,
			String content, Locale defaultLocale, boolean workflowEnabled,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title);
		}

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			descriptionMap.put(locale, description);
		}

		if (workflowEnabled) {
			serviceContext = (ServiceContext)serviceContext.clone();

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
			title, content, LocaleUtil.getDefault(), false, false);
	}

	public static JournalArticle addArticle(
			long groupId, String title, String content, Locale defaultLocale)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			title, content, defaultLocale, false, false);
	}

	public static JournalArticle addArticle(
			long groupId, String title, String content,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			title, content, LocaleUtil.getDefault(), false, false,
			serviceContext);
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
			long groupId, long folderId, String title, String content,
			boolean approved)
		throws Exception {

		return addArticle(
			groupId, folderId, title, content, LocaleUtil.getDefault(), true,
			approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, String title, boolean approved)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			"description", "content", LocaleUtil.getDefault(), true, approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			long parentFolderId, String title, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			serviceContext.getScopeGroupId(), parentFolderId, title,
			"description", "content", LocaleUtil.getDefault(), true, approved,
			serviceContext);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, String title, String content, boolean approved)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			title, content, LocaleUtil.getDefault(), true, approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			String title, boolean approved)
		throws Exception {

		return addArticleWithWorkflow(title, "content", approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			String title, boolean approved, ServiceContext serviceContext)
		throws Exception {

		return addArticleWithWorkflow(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title, approved,
			serviceContext);
	}

	public static JournalArticle addArticleWithWorkflow(
			String title, String content, boolean approved)
		throws Exception {

		return addArticle(
			TestPropsValues.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title, title,
			content, LocaleUtil.getDefault(), true, approved);
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, long folderId, long classNameId, String xml,
			String ddmStructureKey, String ddmTemplateKey)
		throws Exception {

		return addArticleWithXMLContent(
			groupId, folderId, classNameId, xml, ddmStructureKey,
			ddmTemplateKey, LocaleUtil.getDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, long folderId, long classNameId, String xml,
			String ddmStructureKey, String ddmTemplateKey, Locale defaultLocale)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addArticleWithXMLContent(
			folderId, classNameId, xml, ddmStructureKey, ddmTemplateKey,
			defaultLocale, serviceContext);
	}

	public static JournalArticle addArticleWithXMLContent(
			long folderId, long classNameId, String xml, String ddmStructureKey,
			String ddmTemplateKey, Locale defaultLocale,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(defaultLocale, "Test Article");

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			folderId, classNameId, 0, StringPool.BLANK, true, 0, titleMap, null,
			xml, "general", ddmStructureKey, ddmTemplateKey, null, 1, 1, 1965,
			0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false, null,
			null, null, null, serviceContext);
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, String xml, String ddmStructureKey,
			String ddmTemplateKey)
		throws Exception {

		return addArticleWithXMLContent(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml, ddmStructureKey,
			ddmTemplateKey, LocaleUtil.getDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			long parentFolderId, String xml, String ddmStructureKey,
			String ddmTemplateKey, ServiceContext serviceContext)
		throws Exception {

		return addArticleWithXMLContent(
			serviceContext.getScopeGroupId(), parentFolderId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml, ddmStructureKey,
			ddmTemplateKey, LocaleUtil.getDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			String xml, String ddmStructureKey, String ddmTemplateKey)
		throws Exception {

		return addArticleWithXMLContent(
			TestPropsValues.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml, ddmStructureKey,
			ddmTemplateKey, LocaleUtil.getDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			String xml, String ddmStructureKey, String ddmTemplateKey,
			Locale defaultLocale)
		throws Exception {

		return addArticleWithXMLContent(
			TestPropsValues.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml, ddmStructureKey,
			ddmTemplateKey, defaultLocale);
	}

	public static JournalArticle addArticleWithXMLContent(
			String xml, String ddmStructureKey, String ddmTemplateKey,
			ServiceContext serviceContext)
		throws Exception {

		return addArticleWithXMLContent(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, xml,
			ddmStructureKey, ddmTemplateKey, serviceContext);
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

	public static JournalFeed addFeed(
			long groupId, long plid, String name, String ddmStructureKey,
			String ddmTemplateKey, String rendererTemplateKey)
		throws Exception {

		long userId = TestPropsValues.getUserId();
		String feedId = StringPool.BLANK;
		boolean autoFeedId = true;
		String description = StringPool.BLANK;
		String type = StringPool.BLANK;
		int delta = 0;
		String orderByCol = "modified-date";
		String orderByType = "asc";
		String friendlyURL = _getFeedFriendlyURL(groupId, plid);
		String targetPortletId = StringPool.BLANK;
		String contentField = JournalFeedConstants.WEB_CONTENT_DESCRIPTION;
		String feedFormat = RSSUtil.getFeedTypeFormat(
			RSSUtil.FEED_TYPE_DEFAULT);
		double feedVersion = RSSUtil.getFeedTypeVersion(
			RSSUtil.FEED_TYPE_DEFAULT);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return JournalFeedLocalServiceUtil.addFeed(
			userId, groupId, feedId, autoFeedId, name, description, type,
			ddmStructureKey, ddmTemplateKey, rendererTemplateKey, delta,
			orderByCol, orderByType, friendlyURL, targetPortletId, contentField,
			feedFormat, feedVersion, serviceContext);
	}

	public static JournalFolder addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addFolder(parentFolderId, name, serviceContext);
	}

	public static JournalFolder addFolder(long groupId, String name)
		throws Exception {

		return addFolder(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
	}

	public static JournalFolder addFolder(
			long parentFolderId, String name, ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = JournalFolderLocalServiceUtil.fetchFolder(
			serviceContext.getScopeGroupId(), name);

		if (folder != null) {
			return folder;
		}

		return JournalFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			parentFolderId, name, "This is a test folder.", serviceContext);
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

	public static String getSampleTemplateXSL() {
		return "$name.getData()";
	}

	public static Map<String, Map<String, String>> getXsdMap(String xsd)
		throws Exception {

		Map<String, Map<String, String>> map =
			new HashMap<String, Map<String, String>>();

		Document document = SAXReaderUtil.read(xsd);

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-element");

		List<Node> nodes = xPathSelector.selectNodes(document);

		for (Node node : nodes) {
			Element dynamicElementElement = (Element)node;

			String type = dynamicElementElement.attributeValue("type");

			if (Validator.equals(type, "selection_break")) {
				continue;
			}

			String name = dynamicElementElement.attributeValue("name");

			map.put(name, _getMap(dynamicElementElement));
		}

		return map;
	}

	public static JournalArticle updateArticle(
			JournalArticle article, String title, String content)
		throws Exception {

		return updateArticle(
			article, title, content, ServiceTestUtil.getServiceContext());
	}

	public static JournalArticle updateArticle(
			JournalArticle article, String title, String content,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		for (Locale locale : _locales) {
			titleMap.put(locale, title);
		}

		return JournalArticleLocalServiceUtil.updateArticle(
			article.getUserId(), article.getGroupId(), article.getFolderId(),
			article.getArticleId(), article.getVersion(), titleMap,
			article.getDescriptionMap(),
			createLocalizedContent(content, LocaleUtil.getDefault()),
			article.getLayoutUuid(), serviceContext);
	}

	private static String _getFeedFriendlyURL(long groupId, long plid)
		throws Exception {

		String friendlyURL = StringPool.BLANK;

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (layout.isPrivateLayout()) {
			if (group.isUser()) {
				friendlyURL = friendlyURL.concat(
					PortalUtil.getPathFriendlyURLPrivateUser());
			}
			else {
				friendlyURL = friendlyURL.concat(
					PortalUtil.getPathFriendlyURLPrivateGroup());
			}
		}
		else {
			friendlyURL = friendlyURL.concat(
				PortalUtil.getPathFriendlyURLPublic());
		}

		friendlyURL = friendlyURL.concat(group.getFriendlyURL());
		friendlyURL = friendlyURL.concat(layout.getFriendlyURL());

		return friendlyURL;
	}

	private static Map<String, String> _getMap(Element dynamicElementElement) {
		Map<String, String> map = new HashMap<String, String>();

		// Attributes

		for (Attribute attribute : dynamicElementElement.attributes()) {
			map.put(attribute.getName(), attribute.getValue());
		}

		// Metadata

		Element metadadataElement = dynamicElementElement.element("meta-data");

		if (metadadataElement == null) {
			return map;
		}

		List<Element> entryElements = metadadataElement.elements("entry");

		for (Element entryElement : entryElements) {
			map.put(
				entryElement.attributeValue("name"), entryElement.getText());
		}

		return map;
	}

	private static Locale[] _locales = {
		new Locale("en", "US"), new Locale("de", "DE"), new Locale("es", "ES")
	};

}