<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.LocaleException" %><%@
page import="com.liferay.portal.NoSuchLayoutException" %><%@
page import="com.liferay.portal.kernel.editor.EditorUtil" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.QueryConfig" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.search.SearchResult" %><%@
page import="com.liferay.portal.kernel.search.SearchResultUtil" %><%@
page import="com.liferay.portal.kernel.search.Summary" %><%@
page import="com.liferay.portal.kernel.xml.Document" %><%@
page import="com.liferay.portal.kernel.xml.Element" %><%@
page import="com.liferay.portal.kernel.xml.Node" %><%@
page import="com.liferay.portal.kernel.xml.SAXReaderUtil" %><%@
page import="com.liferay.portal.kernel.xml.XPath" %><%@
page import="com.liferay.portal.layoutconfiguration.util.RuntimePageUtil" %><%@
page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.portlet.asset.NoSuchEntryException" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.JournalSearcher" %><%@
page import="com.liferay.portlet.dynamicdatamapping.NoSuchStructureException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMStructure" %><%@
page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %><%@
page import="com.liferay.portlet.dynamicdatamapping.storage.Fields" %><%@
page import="com.liferay.portlet.journal.ArticleContentException" %><%@
page import="com.liferay.portlet.journal.ArticleContentSizeException" %><%@
page import="com.liferay.portlet.journal.ArticleDisplayDateException" %><%@
page import="com.liferay.portlet.journal.ArticleExpirationDateException" %><%@
page import="com.liferay.portlet.journal.ArticleIdException" %><%@
page import="com.liferay.portlet.journal.ArticleSmallImageNameException" %><%@
page import="com.liferay.portlet.journal.ArticleSmallImageSizeException" %><%@
page import="com.liferay.portlet.journal.ArticleTitleException" %><%@
page import="com.liferay.portlet.journal.ArticleTypeException" %><%@
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
page import="com.liferay.portlet.journal.NoSuchArticleException" %><%@
page import="com.liferay.portlet.journal.NoSuchFolderException" %><%@
page import="com.liferay.portlet.journal.action.EditArticleAction" %><%@
page import="com.liferay.portlet.journal.model.JournalArticle" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleConstants" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleDisplay" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleResource" %><%@
page import="com.liferay.portlet.journal.model.JournalFeed" %><%@
page import="com.liferay.portlet.journal.model.JournalFeedConstants" %><%@
page import="com.liferay.portlet.journal.model.JournalFolder" %><%@
page import="com.liferay.portlet.journal.model.JournalFolderConstants" %><%@
page import="com.liferay.portlet.journal.model.JournalSearchConstants" %><%@
page import="com.liferay.portlet.journal.model.impl.JournalArticleImpl" %><%@
page import="com.liferay.portlet.journal.search.ArticleDisplayTerms" %><%@
page import="com.liferay.portlet.journal.search.ArticleSearch" %><%@
page import="com.liferay.portlet.journal.search.ArticleSearchTerms" %><%@
page import="com.liferay.portlet.journal.search.EntriesChecker" %><%@
page import="com.liferay.portlet.journal.search.FeedDisplayTerms" %><%@
page import="com.liferay.portlet.journal.search.FeedSearch" %><%@
page import="com.liferay.portlet.journal.search.FeedSearchTerms" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalArticleServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.JournalFolderServiceUtil" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalArticlePermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalFeedPermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalFolderPermission" %><%@
page import="com.liferay.portlet.journal.service.permission.JournalPermission" %><%@
page import="com.liferay.portlet.journal.util.JournalConverterUtil" %><%@
page import="com.liferay.portlet.journal.util.JournalUtil" %><%@
page import="com.liferay.portlet.journalcontent.util.JournalContentUtil" %><%@
page import="com.liferay.portlet.trash.util.TrashUtil" %><%@
page import="com.liferay.util.RSSUtil" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

String[] displayViews = StringUtil.split(PrefsParamUtil.getString(portletPreferences, liferayPortletRequest, "displayViews", StringUtil.merge(PropsValues.JOURNAL_DISPLAY_VIEWS)));

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/journal/init-ext.jsp" %>