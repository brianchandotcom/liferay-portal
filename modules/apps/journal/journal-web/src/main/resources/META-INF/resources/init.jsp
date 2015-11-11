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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.dynamic.data.mapping.exception.NoSuchStructureException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException" %><%@
page import="com.liferay.dynamic.data.mapping.exception.StorageFieldRequiredException" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMForm" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormField" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMTemplate" %><%@
page import="com.liferay.dynamic.data.mapping.model.LocalizedValue" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil" %><%@
page import="com.liferay.dynamic.data.mapping.service.permission.DDMStructurePermission" %><%@
page import="com.liferay.dynamic.data.mapping.service.permission.DDMTemplatePermission" %><%@
page import="com.liferay.dynamic.data.mapping.storage.DDMFormValues" %><%@
page import="com.liferay.item.selector.ItemSelector" %><%@
page import="com.liferay.item.selector.ItemSelectorReturnType" %><%@
page import="com.liferay.item.selector.criteria.UUIDItemSelectorReturnType" %><%@
page import="com.liferay.journal.configuration.JournalGroupServiceConfiguration" %><%@
page import="com.liferay.journal.configuration.JournalServiceConfigurationValues" %><%@
page import="com.liferay.journal.constants.JournalConstants" %><%@
page import="com.liferay.journal.constants.JournalPortletKeys" %><%@
page import="com.liferay.journal.constants.JournalWebKeys" %><%@
page import="com.liferay.journal.exception.ArticleContentException" %><%@
page import="com.liferay.journal.exception.ArticleContentSizeException" %><%@
page import="com.liferay.journal.exception.ArticleDisplayDateException" %><%@
page import="com.liferay.journal.exception.ArticleExpirationDateException" %><%@
page import="com.liferay.journal.exception.ArticleIdException" %><%@
page import="com.liferay.journal.exception.ArticleSmallImageNameException" %><%@
page import="com.liferay.journal.exception.ArticleSmallImageSizeException" %><%@
page import="com.liferay.journal.exception.ArticleTitleException" %><%@
page import="com.liferay.journal.exception.ArticleVersionException" %><%@
page import="com.liferay.journal.exception.DuplicateArticleIdException" %><%@
page import="com.liferay.journal.exception.DuplicateFeedIdException" %><%@
page import="com.liferay.journal.exception.DuplicateFolderNameException" %><%@
page import="com.liferay.journal.exception.FeedContentFieldException" %><%@
page import="com.liferay.journal.exception.FeedIdException" %><%@
page import="com.liferay.journal.exception.FeedNameException" %><%@
page import="com.liferay.journal.exception.FeedTargetLayoutFriendlyUrlException" %><%@
page import="com.liferay.journal.exception.FeedTargetPortletIdException" %><%@
page import="com.liferay.journal.exception.FolderNameException" %><%@
page import="com.liferay.journal.exception.InvalidDDMStructureException" %><%@
page import="com.liferay.journal.exception.NoSuchArticleException" %><%@
page import="com.liferay.journal.exception.NoSuchFolderException" %><%@
page import="com.liferay.journal.model.JournalArticle" %><%@
page import="com.liferay.journal.model.JournalArticleConstants" %><%@
page import="com.liferay.journal.model.JournalArticleDisplay" %><%@
page import="com.liferay.journal.model.JournalFeed" %><%@
page import="com.liferay.journal.model.JournalFeedConstants" %><%@
page import="com.liferay.journal.model.JournalFolder" %><%@
page import="com.liferay.journal.model.JournalFolderConstants" %><%@
page import="com.liferay.journal.service.JournalArticleLocalServiceUtil" %><%@
page import="com.liferay.journal.service.JournalArticleServiceUtil" %><%@
page import="com.liferay.journal.service.JournalFeedLocalServiceUtil" %><%@
page import="com.liferay.journal.service.JournalFolderLocalServiceUtil" %><%@
page import="com.liferay.journal.service.JournalFolderServiceUtil" %><%@
page import="com.liferay.journal.service.permission.JournalArticlePermission" %><%@
page import="com.liferay.journal.service.permission.JournalFeedPermission" %><%@
page import="com.liferay.journal.service.permission.JournalFolderPermission" %><%@
page import="com.liferay.journal.service.permission.JournalPermission" %><%@
page import="com.liferay.journal.util.JournalContent" %><%@
page import="com.liferay.journal.util.comparator.ArticleVersionComparator" %><%@
page import="com.liferay.journal.util.comparator.FolderArticleDisplayDateComparator" %><%@
page import="com.liferay.journal.util.comparator.FolderArticleModifiedDateComparator" %><%@
page import="com.liferay.journal.util.impl.JournalUtil" %><%@
page import="com.liferay.journal.web.asset.JournalArticleAssetRenderer" %><%@
page import="com.liferay.journal.web.configuration.JournalWebConfiguration" %><%@
page import="com.liferay.journal.web.dao.search.JournalResultRowSplitter" %><%@
page import="com.liferay.journal.web.display.context.JournalDisplayContext" %><%@
page import="com.liferay.journal.web.display.context.JournalMoveEntriesDisplayContext" %><%@
page import="com.liferay.journal.web.display.context.util.JournalWebRequestHelper" %><%@
page import="com.liferay.journal.web.portlet.JournalPortlet" %><%@
page import="com.liferay.journal.web.portlet.action.ActionUtil" %><%@
page import="com.liferay.journal.web.search.ArticleDisplayTerms" %><%@
page import="com.liferay.journal.web.search.ArticleSearch" %><%@
page import="com.liferay.journal.web.search.ArticleSearchTerms" %><%@
page import="com.liferay.journal.web.search.EntriesChecker" %><%@
page import="com.liferay.journal.web.search.EntriesMover" %><%@
page import="com.liferay.journal.web.search.FeedDisplayTerms" %><%@
page import="com.liferay.journal.web.search.FeedSearch" %><%@
page import="com.liferay.journal.web.search.FeedSearchTerms" %><%@
page import="com.liferay.journal.web.search.JournalSearcher" %><%@
page import="com.liferay.journal.web.util.JournalPortletUtil" %><%@
page import="com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion" %><%@
page import="com.liferay.portal.LocaleException" %><%@
page import="com.liferay.portal.NoSuchWorkflowDefinitionLinkException" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.RowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletRequestModel" %><%@
page import="com.liferay.portal.kernel.search.Field" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.search.SearchResult" %><%@
page import="com.liferay.portal.kernel.search.SearchResultUtil" %><%@
page import="com.liferay.portal.kernel.search.Summary" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.upload.LiferayFileItemException" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.LocalizationUtil" %><%@
page import="com.liferay.portal.kernel.util.MathUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.webdav.WebDAVUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinition" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil" %><%@
page import="com.liferay.portal.model.*" %><%@
page import="com.liferay.portal.model.impl.*" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.service.*" %><%@
page import="com.liferay.portal.upload.LiferayFileItem" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PrefsPropsUtil" %><%@
page import="com.liferay.portlet.PortalPreferences" %><%@
page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portlet.PortletURLImpl" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.RequestBackedPortletURLFactoryUtil" %><%@
page import="com.liferay.portlet.admin.util.PortalProductMenuApplicationType" %><%@
page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFileEntryException" %><%@
page import="com.liferay.portlet.documentlibrary.FileSizeException" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntry" %><%@
page import="com.liferay.portlet.trash.model.TrashEntry" %><%@
page import="com.liferay.portlet.trash.util.TrashUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.util.RSSUtil" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.LinkedHashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Locale" %><%@
page import="java.util.Map" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.ResourceURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

JournalWebConfiguration journalWebConfiguration = (JournalWebConfiguration)request.getAttribute(JournalWebConfiguration.class.getName());

JournalDisplayContext journalDisplayContext = new JournalDisplayContext(request, liferayPortletResponse, portletPreferences);

JournalWebRequestHelper journalWebRequestHelper = new JournalWebRequestHelper(request);

JournalGroupServiceConfiguration journalGroupServiceConfiguration = journalWebRequestHelper.getJournalGroupServiceConfiguration();

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>