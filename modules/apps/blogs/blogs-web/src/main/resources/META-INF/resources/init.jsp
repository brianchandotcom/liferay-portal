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

<%@ page import="com.liferay.blogs.configuration.BlogsGroupServiceOverriddenConfiguration" %><%@
page import="com.liferay.blogs.web.configuration.BlogsPortletInstanceConfiguration" %><%@
page import="com.liferay.blogs.web.constants.BlogsPortletKeys" %><%@
page import="com.liferay.blogs.web.display.context.BlogsPortletInstanceSettingsHelper" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.comment.CommentManagerUtil" %><%@
page import="com.liferay.portal.kernel.comment.Discussion" %><%@
page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainerResults" %><%@
page import="com.liferay.portal.kernel.exception.PortalException" %><%@
page import="com.liferay.portal.kernel.exception.SystemException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.repository.model.Folder" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.Field" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.settings.GroupServiceSettingsLocator" %><%@
page import="com.liferay.portal.kernel.settings.ParameterMapSettingsLocator" %><%@
page import="com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator" %><%@
page import="com.liferay.portal.kernel.upload.LiferayFileItemException" %><%@
page import="com.liferay.portal.kernel.upload.UploadRequestSizeException" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.PropsUtil" %><%@
page import="com.liferay.portal.kernel.util.RSSUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.model.Organization" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil" %><%@
page import="com.liferay.portal.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.service.OrganizationLocalServiceUtil" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.service.ServiceContextFunction" %><%@
page import="com.liferay.portal.service.SubscriptionLocalServiceUtil" %><%@
page import="com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.upload.LiferayFileItem" %><%@
page import="com.liferay.portal.util.Portal" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portlet.PortalPreferences" %><%@
page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.model.AssetTag" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.portlet.blogs.BlogsGroupServiceSettings" %><%@
page import="com.liferay.portlet.blogs.EntryContentException" %><%@
page import="com.liferay.portlet.blogs.EntryCoverImageCropException" %><%@
page import="com.liferay.portlet.blogs.EntryDescriptionException" %><%@
page import="com.liferay.portlet.blogs.EntrySmallImageNameException" %><%@
page import="com.liferay.portlet.blogs.EntrySmallImageScaleException" %><%@
page import="com.liferay.portlet.blogs.EntryTitleException" %><%@
page import="com.liferay.portlet.blogs.NoSuchEntryException" %><%@
page import="com.liferay.portlet.blogs.constants.BlogsConstants" %><%@
page import="com.liferay.portlet.blogs.model.BlogsEntry" %><%@
page import="com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.blogs.service.BlogsEntryServiceUtil" %><%@
page import="com.liferay.portlet.blogs.service.permission.BlogsEntryPermission" %><%@
page import="com.liferay.portlet.blogs.service.permission.BlogsPermission" %><%@
page import="com.liferay.portlet.blogs.util.BlogsUtil" %><%@
page import="com.liferay.portlet.documentlibrary.FileSizeException" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLUtil" %><%@
page import="com.liferay.portlet.trash.util.TrashUtil" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>