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

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/document-library" prefix="liferay-document-library" %><%@
taglib uri="http://liferay.com/tld/editor" prefix="liferay-editor" %><%@
taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/ratings" prefix="liferay-ratings" %><%@
taglib uri="http://liferay.com/tld/rss" prefix="liferay-rss" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/social-bookmarks" prefix="liferay-social-bookmarks" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.admin.kernel.util.PortalSearchApplicationType" %><%@
page import="com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.asset.kernel.model.AssetCategory" %><%@
page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.asset.kernel.model.AssetRendererFactory" %><%@
page import="com.liferay.asset.kernel.model.AssetTag" %><%@
page import="com.liferay.asset.kernel.model.AssetVocabulary" %><%@
page import="com.liferay.asset.kernel.model.AssetVocabularyConstants" %><%@
page import="com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil" %><%@
page import="com.liferay.blogs.model.BlogsEntry" %><%@
page import="com.liferay.document.library.kernel.exception.FileNameException" %><%@
page import="com.liferay.document.library.kernel.exception.FileSizeException" %><%@
page import="com.liferay.document.library.kernel.exception.NoSuchFileException" %><%@
page import="com.liferay.journal.model.JournalArticle" %><%@
page import="com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration" %><%@
page import="com.liferay.knowledge.base.constants.KBActionKeys" %><%@
page import="com.liferay.knowledge.base.constants.KBArticleConstants" %><%@
page import="com.liferay.knowledge.base.constants.KBCommentConstants" %><%@
page import="com.liferay.knowledge.base.constants.KBConstants" %><%@
page import="com.liferay.knowledge.base.constants.KBFolderConstants" %><%@
page import="com.liferay.knowledge.base.constants.KBPortletKeys" %><%@
page import="com.liferay.knowledge.base.exception.DuplicateKBFolderNameException" %><%@
page import="com.liferay.knowledge.base.exception.InvalidKBFolderNameException" %><%@
page import="com.liferay.knowledge.base.exception.KBArticleContentException" %><%@
page import="com.liferay.knowledge.base.exception.KBArticleImportException" %><%@
page import="com.liferay.knowledge.base.exception.KBArticlePriorityException" %><%@
page import="com.liferay.knowledge.base.exception.KBArticleSourceURLException" %><%@
page import="com.liferay.knowledge.base.exception.KBArticleStatusException" %><%@
page import="com.liferay.knowledge.base.exception.KBArticleTitleException" %><%@
page import="com.liferay.knowledge.base.exception.KBArticleUrlTitleException" %><%@
page import="com.liferay.knowledge.base.exception.KBCommentContentException" %><%@
page import="com.liferay.knowledge.base.exception.KBTemplateContentException" %><%@
page import="com.liferay.knowledge.base.exception.KBTemplateTitleException" %><%@
page import="com.liferay.knowledge.base.exception.NoSuchArticleException" %><%@
page import="com.liferay.knowledge.base.exception.NoSuchCommentException" %><%@
page import="com.liferay.knowledge.base.exception.NoSuchTemplateException" %><%@
page import="com.liferay.knowledge.base.model.KBArticle" %><%@
page import="com.liferay.knowledge.base.model.KBComment" %><%@
page import="com.liferay.knowledge.base.model.KBFolder" %><%@
page import="com.liferay.knowledge.base.model.KBTemplate" %><%@
page import="com.liferay.knowledge.base.service.KBArticleLocalServiceUtil" %><%@
page import="com.liferay.knowledge.base.service.KBArticleServiceUtil" %><%@
page import="com.liferay.knowledge.base.service.KBCommentLocalServiceUtil" %><%@
page import="com.liferay.knowledge.base.service.KBCommentServiceUtil" %><%@
page import="com.liferay.knowledge.base.service.KBFolderLocalServiceUtil" %><%@
page import="com.liferay.knowledge.base.service.KBFolderServiceUtil" %><%@
page import="com.liferay.knowledge.base.util.KnowledgeBaseUtil" %><%@
page import="com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator" %><%@
page import="com.liferay.knowledge.base.web.internal.KBUtil" %><%@
page import="com.liferay.knowledge.base.web.internal.application.dao.search.KBCommentResultRowSplitter" %><%@
page import="com.liferay.knowledge.base.web.internal.application.dao.search.KBResultRowSplitter" %><%@
page import="com.liferay.knowledge.base.web.internal.configuration.KBArticlePortletInstanceConfiguration" %><%@
page import="com.liferay.knowledge.base.web.internal.configuration.KBDisplayPortletInstanceConfiguration" %><%@
page import="com.liferay.knowledge.base.web.internal.configuration.KBSearchPortletInstanceConfiguration" %><%@
page import="com.liferay.knowledge.base.web.internal.configuration.KBSectionPortletInstanceConfiguration" %><%@
page import="com.liferay.knowledge.base.web.internal.constants.KBWebKeys" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBAdminManagementToolbarDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBAdminNavigationDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBAdminViewDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBNavigationDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBSearchDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBSelectParentDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBSuggestionListDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBSuggestionListManagementToolbarDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBTemplatesManagementToolbarDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.KBViewPrpArticlesDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.internal.display.context.helper.KBArticleURLHelper" %><%@
page import="com.liferay.knowledge.base.web.internal.search.KBCommentsChecker" %><%@
page import="com.liferay.knowledge.base.web.internal.search.KBObjectsSearch" %><%@
page import="com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission" %><%@
page import="com.liferay.knowledge.base.web.internal.security.permission.resource.DisplayPermission" %><%@
page import="com.liferay.knowledge.base.web.internal.security.permission.resource.KBArticlePermission" %><%@
page import="com.liferay.knowledge.base.web.internal.security.permission.resource.KBCommentPermission" %><%@
page import="com.liferay.knowledge.base.web.internal.security.permission.resource.KBFolderPermission" %><%@
page import="com.liferay.knowledge.base.web.internal.security.permission.resource.KBTemplatePermission" %><%@
page import="com.liferay.knowledge.base.web.internal.social.SocialBookmarksUtil" %><%@
page import="com.liferay.knowledge.base.web.internal.util.AdminUtil" %><%@
page import="com.liferay.knowledge.base.web.internal.util.KBArticleAssetEntriesUtil" %><%@
page import="com.liferay.knowledge.base.web.internal.util.comparator.KBOrderByComparatorAdapter" %><%@
page import="com.liferay.message.boards.model.MBMessage" %><%@
page import="com.liferay.petra.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.configuration.metatype.util.ParameterMapUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanPropertiesUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.RowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.PortalException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.ModelHintsUtil" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortalPreferences" %><%@
page import="com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.ClassNameLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.kernel.servlet.HttpHeaders" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.settings.GroupServiceSettingsLocator" %><%@
page import="com.liferay.portal.kernel.upload.UploadRequestSizeException" %><%@
page import="com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatConstants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LinkedHashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.OrderByComparator" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.ratings.kernel.RatingsType" %><%@
page import="com.liferay.ratings.kernel.definition.PortletRatingsDefinitionUtil" %><%@
page import="com.liferay.subscription.exception.NoSuchSubscriptionException" %><%@
page import="com.liferay.subscription.service.SubscriptionLocalServiceUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %><%@
page import="com.liferay.wiki.model.WikiPage" %>

<%@ page import="java.math.BigDecimal" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Collections" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.TreeMap" %>

<%@ page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String redirect = PortalUtil.escapeRedirect(ParamUtil.getString(request, "redirect", currentURL));

String rootPortletId = portletDisplay.getRootPortletId();

String templatePath = portletConfig.getInitParameter("template-path");

KBGroupServiceConfiguration kbGroupServiceConfiguration = ConfigurationProviderUtil.getConfiguration(KBGroupServiceConfiguration.class, new GroupServiceSettingsLocator(themeDisplay.getScopeGroupId(), KBConstants.SERVICE_NAME));

KBSectionPortletInstanceConfiguration kbSectionPortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(KBSectionPortletInstanceConfiguration.class);

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(FastDateFormatConstants.LONG, locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(FastDateFormatConstants.LONG, FastDateFormatConstants.SHORT, locale, timeZone);
%>