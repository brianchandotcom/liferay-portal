<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.search.SearchResult" %><%@
page import="com.liferay.portal.kernel.xml.Document" %><%@
page import="com.liferay.portal.kernel.xml.Element" %><%@
page import="com.liferay.portal.kernel.xml.Node" %><%@
page import="com.liferay.portal.kernel.xml.SAXReaderUtil" %><%@
page import="com.liferay.portal.kernel.xml.XPath" %><%@
page import="com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.StorageFieldRequiredException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMStructurePermission" %><%@
page import="com.liferay.portlet.journal.ArticleContentException" %><%@
page import="com.liferay.portlet.journal.ArticleContentSizeException" %><%@
page import="com.liferay.portlet.journal.ArticleDisplayDateException" %><%@
page import="com.liferay.portlet.journal.ArticleExpirationDateException" %><%@
page import="com.liferay.portlet.journal.ArticleIdException" %><%@
page import="com.liferay.portlet.journal.ArticleSmallImageNameException" %><%@
page import="com.liferay.portlet.journal.ArticleSmallImageSizeException" %><%@
page import="com.liferay.portlet.journal.ArticleTitleException" %><%@
page import="com.liferay.portlet.journal.ArticleVersionException" %><%@
page import="com.liferay.portlet.journal.DuplicateArticleIdException" %><%@
page import="com.liferay.portlet.journal.DuplicateFeedIdException" %><%@
page import="com.liferay.portlet.journal.DuplicateFolderNameException" %><%@
page import="com.liferay.portlet.journal.FeedContentFieldException" %><%@
page import="com.liferay.portlet.journal.FeedIdException" %><%@
page import="com.liferay.portlet.journal.FeedNameException" %><%@
page import="com.liferay.portlet.journal.FeedTargetLayoutFriendlyUrlException" %><%@
page import="com.liferay.portlet.journal.FeedTargetPortletIdException" %><%@
page import="com.liferay.portlet.journal.FolderNameException" %><%@
page import="com.liferay.portlet.journal.InvalidDDMStructureException" %><%@
page import="com.liferay.portlet.journal.NoSuchFolderException" %><%@
page import="com.liferay.portlet.journal.asset.JournalArticleAssetRenderer" %><%@
page import="com.liferay.portlet.journal.context.JournalDisplayContext" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleResource" %><%@
page import="com.liferay.portlet.journal.model.JournalFeed" %><%@
page import="com.liferay.portlet.journal.model.JournalFeedConstants" %><%@
page import="com.liferay.portlet.journal.model.JournalFolder" %><%@
page import="com.liferay.portlet.journal.model.JournalFolderConstants" %><%@
page import="com.liferay.portlet.journal.search.ArticleDisplayTerms" %><%@
page import="com.liferay.portlet.journal.search.EntriesChecker" %><%@
page import="com.liferay.portlet.journal.search.FeedDisplayTerms" %><%@
page import="com.liferay.portlet.journal.search.FeedSearch" %><%@
page import="com.liferay.portlet.journal.search.FeedSearchTerms" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalFolderServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalArticlePermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalFeedPermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalFolderPermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalPermission" %><%@
page import="com.liferay.portlet.journal.util.JournalConverterUtil" %><%@
page import="com.liferay.portlet.journal.util.JournalSearcher" %><%@
page import="com.liferay.portlet.journal.util.JournalUtil" %><%@
page import="com.liferay.portlet.journal.util.comparator.ArticleVersionComparator" %><%@
page import="com.liferay.util.RSSUtil" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

JournalDisplayContext journalDisplayContext = new JournalDisplayContext(liferayPortletRequest, portletPreferences);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/journal/init-ext.jsp" %>