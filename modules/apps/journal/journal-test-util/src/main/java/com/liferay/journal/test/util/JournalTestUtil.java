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

package com.liferay.journal.test.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFeedConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.rss.util.RSSUtil;

import java.lang.reflect.Method;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Juan Fernández
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
public class JournalTestUtil {

	public static JournalArticle addArticle(long groupId, long folderId)
		throws Exception {

		return addArticle(groupId, folderId, StringPool.BLANK, true);
	}

	public static JournalArticle addArticle(
			long userId, long groupId, long folderId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addArticle(
			groupId, folderId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(), false,
			false, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, long classNameId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> contentMap, Locale defaultLocale,
			boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, classNameId, titleMap, descriptionMap,
			contentMap, defaultLocale, null, workflowEnabled, approved,
			serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, long classNameId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> contentMap, Locale defaultLocale,
			Date expirationDate, boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, classNameId, titleMap, descriptionMap,
			contentMap, null, defaultLocale, expirationDate, workflowEnabled,
			approved, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, long classNameId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> contentMap, String layoutUuid,
			Locale defaultLocale, Date expirationDate, boolean workflowEnabled,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, classNameId, StringPool.BLANK, true, titleMap,
			descriptionMap, contentMap, layoutUuid, defaultLocale,
			expirationDate, workflowEnabled, approved, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, long classNameId, String articleId,
			boolean autoArticleId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> contentMap,
			String layoutUuid, Locale defaultLocale, Date expirationDate,
			boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, classNameId, articleId, autoArticleId, titleMap,
			descriptionMap, contentMap, layoutUuid, defaultLocale, null,
			expirationDate, workflowEnabled, approved, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, long classNameId, String articleId,
			boolean autoArticleId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> contentMap,
			String layoutUuid, Locale defaultLocale, Date displayDate,
			Date expirationDate, boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			contentMap, LocaleUtil.toLanguageId(defaultLocale));

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			_locales, defaultLocale);

		long ddmGroupId = GetterUtil.getLong(
			serviceContext.getAttribute("ddmGroupId"), groupId);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			ddmGroupId, JournalArticle.class.getName(), ddmForm, defaultLocale);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			ddmGroupId, ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		boolean neverExpire = true;

		int expirationDateDay = 0;
		int expirationDateMonth = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;

		User user = TestPropsValues.getUser();

		if (expirationDate != null) {
			neverExpire = false;

			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR_OF_DAY);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);
		}

		Calendar displayCal = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		if (displayDate != null) {
			displayCal.setTime(displayDate);
		}

		int displayDateDay = displayCal.get(Calendar.DATE);
		int displayDateMonth = displayCal.get(Calendar.MONTH);
		int displayDateYear = displayCal.get(Calendar.YEAR);
		int displayDateHour = displayCal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = displayCal.get(Calendar.MINUTE);

		if (workflowEnabled) {
			serviceContext = (ServiceContext)serviceContext.clone();

			if (approved) {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);
			}
			else {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);
			}
		}

		return JournalArticleLocalServiceUtil.addArticle(
			serviceContext.getUserId(), groupId, folderId, classNameId, 0,
			articleId, autoArticleId, JournalArticleConstants.VERSION_DEFAULT,
			titleMap, descriptionMap, content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), layoutUuid, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, 0, 0, 0, 0,
			0, true, true, false, null, null, null, null, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, long classNameId, String title,
			String description, String content, Locale defaultLocale,
			boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, classNameId, title, description, content,
			defaultLocale, null, workflowEnabled, approved, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, long classNameId, String title,
			String description, String content, Locale defaultLocale,
			Date expirationDate, boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, classNameId, _getLocalizedMap(title),
			_getLocalizedMap(description), _getLocalizedMap(content),
			defaultLocale, expirationDate, workflowEnabled, approved,
			serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			StringPool.BLANK, true,
			_getLocalizedMap(RandomTestUtil.randomString()),
			_getLocalizedMap(RandomTestUtil.randomString()),
			_getLocalizedMap(RandomTestUtil.randomString()), null,
			LocaleUtil.getSiteDefault(), null, false, false, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String articleId,
			boolean autoArticleId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addArticle(
			groupId, folderId, articleId, autoArticleId, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String articleId,
			boolean autoArticleId, ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			articleId, autoArticleId,
			_getLocalizedMap(RandomTestUtil.randomString()),
			_getLocalizedMap(RandomTestUtil.randomString()),
			_getLocalizedMap(RandomTestUtil.randomString()), null,
			LocaleUtil.getSiteDefault(), null, false, false, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String title, String content)
		throws Exception {

		return addArticle(
			groupId, folderId, title, title, content,
			LocaleUtil.getSiteDefault(), false, false);
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addArticle(
			groupId, folderId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			title, description, content, defaultLocale, workflowEnabled,
			approved, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, String title, String content)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			title, content, LocaleUtil.getSiteDefault(), false, false);
	}

	public static JournalArticle addArticle(
			long groupId, String title, String content, Date expirationDate,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, title, title, content,
			LocaleUtil.getSiteDefault(), expirationDate, false, false,
			serviceContext);
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
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, title, title, content,
			LocaleUtil.getSiteDefault(), false, false, serviceContext);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, boolean approved)
		throws Exception {

		return addArticleWithWorkflow(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, long folderId, boolean approved)
		throws Exception {

		return addArticleWithWorkflow(
			groupId, folderId, "title", "content", approved);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, long folderId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> contentMap,
			boolean workflowEnabled, boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addArticleWithWorkflow(
			groupId, folderId, titleMap, descriptionMap, contentMap,
			workflowEnabled, approved, serviceContext);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, long folderId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> contentMap,
			boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			titleMap, descriptionMap, contentMap, LocaleUtil.getSiteDefault(),
			workflowEnabled, approved, serviceContext);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, long folderId, String title, String content,
			boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addArticleWithWorkflow(
			groupId, folderId, title, content, approved, serviceContext);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, long folderId, String title, String content,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		return addArticleWithWorkflow(
			groupId, folderId, _getLocalizedMap(title),
			_getLocalizedMap(RandomTestUtil.randomString(50)),
			_getLocalizedMap(content), true, approved, serviceContext);
	}

	public static JournalArticle addArticleWithWorkflow(
			long groupId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> contentMap,
			boolean approved)
		throws Exception {

		return addArticleWithWorkflow(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, titleMap,
			descriptionMap, contentMap, true, approved);
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, long folderId, long classNameId, long classPK,
			String xml, String ddmStructureKey, String ddmTemplateKey,
			Locale defaultLocale)
		throws Exception {

		return addArticleWithXMLContent(
			folderId, classNameId, classPK, xml, ddmStructureKey,
			ddmTemplateKey, defaultLocale, null,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, long folderId, long classNameId, String xml,
			String ddmStructureKey, String ddmTemplateKey)
		throws Exception {

		return addArticleWithXMLContent(
			groupId, folderId, classNameId, xml, ddmStructureKey,
			ddmTemplateKey, LocaleUtil.getSiteDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, long folderId, long classNameId, String xml,
			String ddmStructureKey, String ddmTemplateKey, Locale defaultLocale)
		throws Exception {

		return addArticleWithXMLContent(
			groupId, folderId, classNameId, 0, xml, ddmStructureKey,
			ddmTemplateKey, defaultLocale);
	}

	public static JournalArticle addArticleWithXMLContent(
			long folderId, long classNameId, long classPK, String xml,
			String ddmStructureKey, String ddmTemplateKey, Locale defaultLocale,
			Map<String, byte[]> images, ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> titleMap = HashMapBuilder.put(
			defaultLocale, "Test Article"
		).build();

		return JournalArticleLocalServiceUtil.addArticle(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			folderId, classNameId, classPK, StringPool.BLANK, true, 0, titleMap,
			null, xml, ddmStructureKey, ddmTemplateKey, null, 1, 1, 1965, 0, 0,
			0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false, null, null,
			images, null, serviceContext);
	}

	public static JournalArticle addArticleWithXMLContent(
			long folderId, long classNameId, String xml, String ddmStructureKey,
			String ddmTemplateKey, Locale defaultLocale,
			Map<String, byte[]> images, ServiceContext serviceContext)
		throws Exception {

		return addArticleWithXMLContent(
			folderId, classNameId, 0, xml, ddmStructureKey, ddmTemplateKey,
			defaultLocale, images, serviceContext);
	}

	public static JournalArticle addArticleWithXMLContent(
			long folderId, long classNameId, String xml, String ddmStructureKey,
			String ddmTemplateKey, Locale defaultLocale,
			ServiceContext serviceContext)
		throws Exception {

		return addArticleWithXMLContent(
			folderId, classNameId, xml, ddmStructureKey, ddmTemplateKey,
			defaultLocale, null, serviceContext);
	}

	public static JournalArticle addArticleWithXMLContent(
			long groupId, String xml, String ddmStructureKey,
			String ddmTemplateKey)
		throws Exception {

		return addArticleWithXMLContent(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml, ddmStructureKey,
			ddmTemplateKey, LocaleUtil.getSiteDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			long parentFolderId, String xml, String ddmStructureKey,
			String ddmTemplateKey, Map<String, byte[]> images,
			ServiceContext serviceContext)
		throws Exception {

		return addArticleWithXMLContent(
			parentFolderId, JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml,
			ddmStructureKey, ddmTemplateKey, LocaleUtil.getSiteDefault(),
			images, serviceContext);
	}

	public static JournalArticle addArticleWithXMLContent(
			long parentFolderId, String xml, String ddmStructureKey,
			String ddmTemplateKey, ServiceContext serviceContext)
		throws Exception {

		return addArticleWithXMLContent(
			serviceContext.getScopeGroupId(), parentFolderId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml, ddmStructureKey,
			ddmTemplateKey, LocaleUtil.getSiteDefault());
	}

	public static JournalArticle addArticleWithXMLContent(
			String xml, String ddmStructureKey, String ddmTemplateKey)
		throws Exception {

		return addArticleWithXMLContent(
			TestPropsValues.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xml, ddmStructureKey,
			ddmTemplateKey, LocaleUtil.getSiteDefault());
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return JournalFeedLocalServiceUtil.addFeed(
			userId, groupId, feedId, autoFeedId, name, description,
			ddmStructureKey, ddmTemplateKey, rendererTemplateKey, delta,
			orderByCol, orderByType, friendlyURL, targetPortletId, contentField,
			feedFormat, feedVersion, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             JournalFolderFixture#addFolder(long, long, long, String)}
	 */
	@Deprecated
	public static JournalFolder addFolder(
			long userId, long groupId, long parentFolderId, String name)
		throws Exception {

		JournalFolderFixture journalFolderFixture = new JournalFolderFixture(
			JournalFolderLocalServiceUtil.getService());

		return journalFolderFixture.addFolder(
			userId, groupId, parentFolderId, name);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             JournalFolderFixture#addFolder(long, long, String)}
	 */
	@Deprecated
	public static JournalFolder addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		JournalFolderFixture journalFolderFixture = new JournalFolderFixture(
			JournalFolderLocalServiceUtil.getService());

		return journalFolderFixture.addFolder(groupId, parentFolderId, name);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             JournalFolderFixture#addFolder(long, String)}
	 */
	@Deprecated
	public static JournalFolder addFolder(long groupId, String name)
		throws Exception {

		JournalFolderFixture journalFolderFixture = new JournalFolderFixture(
			JournalFolderLocalServiceUtil.getService());

		return journalFolderFixture.addFolder(groupId, name);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             JournalFolderFixture#addFolder(long, String, ServiceContext)}
	 */
	@Deprecated
	public static JournalFolder addFolder(
			long parentFolderId, String name, ServiceContext serviceContext)
		throws Exception {

		JournalFolderFixture journalFolderFixture = new JournalFolderFixture(
			JournalFolderLocalServiceUtil.getService());

		return journalFolderFixture.addFolder(
			parentFolderId, name, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             JournalFolderFixture#addFolder(long, String, String, ServiceContext)}
	 */
	@Deprecated
	public static JournalFolder addFolder(
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws Exception {

		JournalFolderFixture journalFolderFixture = new JournalFolderFixture(
			JournalFolderLocalServiceUtil.getService());

		return journalFolderFixture.addFolder(
			parentFolderId, name, description, serviceContext);
	}

	public static Element addMetadataElement(
		Element element, String locale, String label) {

		Element metadataElement = element.addElement("meta-data");

		metadataElement.addAttribute("locale", locale);

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", "label");

		entryElement.addCDATA(label);

		return entryElement;
	}

	public static void expireArticle(long groupId, JournalArticle article)
		throws PortalException {

		JournalArticleLocalServiceUtil.expireArticle(
			article.getUserId(), article.getGroupId(), article.getArticleId(),
			null, ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static JournalArticle expireArticle(
			long groupId, JournalArticle article, double version)
		throws PortalException {

		return JournalArticleLocalServiceUtil.expireArticle(
			article.getUserId(), article.getGroupId(), article.getArticleId(),
			version, null, ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static Method getJournalUtilGetTokensMethod() {
		return ReflectionTestUtil.getMethod(
			_JOURNAL_UTIL_CLASS, "getTokens", long.class,
			PortletRequestModel.class, ThemeDisplay.class);
	}

	public static Method getJournalUtilTransformMethod() {
		return ReflectionTestUtil.getMethod(
			_JOURNAL_UTIL_CLASS, "transform", ThemeDisplay.class, Map.class,
			String.class, String.class, Document.class,
			PortletRequestModel.class, String.class, String.class);
	}

	public static String getSampleTemplateXSL() {
		return "$name.getData()";
	}

	public static Hits getSearchArticles(long companyId, long groupId)
		throws Exception {

		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(StringPool.BLANK);

		return indexer.search(searchContext);
	}

	public static int getSearchArticlesCount(long companyId, long groupId)
		throws Exception {

		Hits results = getSearchArticles(companyId, groupId);

		return results.getLength();
	}

	public static Map<String, Map<String, String>> getXsdMap(String xsd)
		throws Exception {

		Map<String, Map<String, String>> map = new HashMap<>();

		Document document = UnsecureSAXReaderUtil.read(xsd);

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-element");

		List<Node> nodes = xPathSelector.selectNodes(document);

		for (Node node : nodes) {
			Element dynamicElementElement = (Element)node;

			String type = dynamicElementElement.attributeValue("type");

			if (Objects.equals(type, "selection_break")) {
				continue;
			}

			String name = dynamicElementElement.attributeValue("name");

			map.put(name, _getMap(dynamicElementElement));
		}

		return map;
	}

	public static JournalArticle updateArticle(JournalArticle article)
		throws Exception {

		return updateArticle(article, RandomTestUtil.randomString());
	}

	public static JournalArticle updateArticle(
			JournalArticle article, Map<Locale, String> titleMap,
			String content, boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return updateArticle(
			article.getUserId(), article, titleMap, content, workflowEnabled,
			approved, serviceContext);
	}

	public static JournalArticle updateArticle(
			JournalArticle article, String title)
		throws Exception {

		return updateArticle(
			article, title, article.getContent(), false, false,
			ServiceContextTestUtil.getServiceContext());
	}

	public static JournalArticle updateArticle(
			JournalArticle article, String title, String content)
		throws Exception {

		return updateArticle(
			article, title, content, false, false,
			ServiceContextTestUtil.getServiceContext());
	}

	public static JournalArticle updateArticle(
			JournalArticle article, String title, String content,
			boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return updateArticle(
			article, _getLocalizedMap(title), content, workflowEnabled,
			approved, serviceContext);
	}

	public static JournalArticle updateArticle(
			long userId, JournalArticle article, Map<Locale, String> titleMap,
			String content, boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return updateArticle(
			userId, article, titleMap, content, null, workflowEnabled, approved,
			serviceContext);
	}

	public static JournalArticle updateArticle(
			long userId, JournalArticle article, Map<Locale, String> titleMap,
			String content, Date displayDate, boolean workflowEnabled,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		if (workflowEnabled) {
			serviceContext = (ServiceContext)serviceContext.clone();

			if (approved) {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);
			}
			else {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);
			}
		}

		if (displayDate == null) {
			displayDate = article.getDisplayDate();
		}

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			User user = TestPropsValues.getUser();

			Calendar displayCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR_OF_DAY);
			displayDateMinute = displayCal.get(Calendar.MINUTE);
		}

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		return JournalArticleLocalServiceUtil.updateArticle(
			userId, article.getGroupId(), article.getFolderId(),
			article.getArticleId(), article.getVersion(), titleMap,
			article.getDescriptionMap(), content, article.getDDMStructureKey(),
			article.getDDMTemplateKey(), article.getLayoutUuid(),
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true,
			article.isIndexable(), article.isSmallImage(),
			article.getSmallImageURL(), null, null, null, serviceContext);
	}

	public static JournalArticle updateArticleWithWorkflow(
			JournalArticle article, boolean approved)
		throws Exception {

		return updateArticle(
			article, RandomTestUtil.randomString(), article.getContent(), false,
			approved, ServiceContextTestUtil.getServiceContext());
	}

	public static JournalArticle updateArticleWithWorkflow(
			long userId, JournalArticle article, boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				article.getGroupId(), userId);

		return updateArticle(
			article, RandomTestUtil.randomString(), article.getContent(), false,
			approved, serviceContext);
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

	private static Map<Locale, String> _getLocalizedMap(String value) {
		Map<Locale, String> valuesMap = new HashMap<>();

		for (Locale locale : _locales) {
			valuesMap.put(locale, value);
		}

		return valuesMap;
	}

	private static Map<String, String> _getMap(Element dynamicElementElement) {
		Map<String, String> map = new HashMap<>();

		Element parentElement = dynamicElementElement.getParent();

		String parentType = parentElement.attributeValue("type");

		// Attributes

		for (Attribute attribute : dynamicElementElement.attributes()) {

			// Option element should not contain index type atribute

			if ((Objects.equals(parentType, "list") ||
				 Objects.equals(parentType, "multi-list")) &&
				Objects.equals(attribute.getName(), "index-type")) {

				continue;
			}

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

	private static final Class<?> _JOURNAL_UTIL_CLASS;

	private static final Locale[] _locales = {
		LocaleUtil.US, LocaleUtil.GERMANY, LocaleUtil.SPAIN
	};

	static {
		Bundle testBundle = FrameworkUtil.getBundle(JournalTestUtil.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		Bundle journalServiceBundle = null;

		for (Bundle bundle : bundleContext.getBundles()) {
			String symbolicName = bundle.getSymbolicName();

			if (symbolicName.equals("com.liferay.journal.service")) {
				journalServiceBundle = bundle;

				break;
			}
		}

		if (journalServiceBundle == null) {
			throw new ExceptionInInitializerError(
				"Unable to find com.liferay.journal.service bundle");
		}

		BundleWiring bundleWiring = journalServiceBundle.adapt(
			BundleWiring.class);

		ClassLoader classLoader = bundleWiring.getClassLoader();

		try {
			_JOURNAL_UTIL_CLASS = classLoader.loadClass(
				"com.liferay.journal.internal.util.JournalUtil");
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}