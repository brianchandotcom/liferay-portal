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
taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/trash" prefix="liferay-trash" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.asset.kernel.model.AssetRendererFactory" %><%@
page import="com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.persistence.AssetEntryQuery" %><%@
page import="com.liferay.bookmarks.configuration.BookmarksGroupServiceOverriddenConfiguration" %><%@
page import="com.liferay.bookmarks.constants.BookmarksConstants" %><%@
page import="com.liferay.bookmarks.constants.BookmarksPortletKeys" %><%@
page import="com.liferay.bookmarks.constants.BookmarksWebKeys" %><%@
page import="com.liferay.bookmarks.exception.EntryURLException" %><%@
page import="com.liferay.bookmarks.exception.FolderNameException" %><%@
page import="com.liferay.bookmarks.exception.NoSuchEntryException" %><%@
page import="com.liferay.bookmarks.exception.NoSuchFolderException" %><%@
page import="com.liferay.bookmarks.model.BookmarksEntry" %><%@
page import="com.liferay.bookmarks.model.BookmarksFolder" %><%@
page import="com.liferay.bookmarks.model.BookmarksFolderConstants" %><%@
page import="com.liferay.bookmarks.search.BookmarksSearcher" %><%@
page import="com.liferay.bookmarks.service.BookmarksEntryServiceUtil" %><%@
page import="com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil" %><%@
page import="com.liferay.bookmarks.service.BookmarksFolderServiceUtil" %><%@
page import="com.liferay.bookmarks.service.permission.BookmarksEntryPermissionChecker" %><%@
page import="com.liferay.bookmarks.service.permission.BookmarksFolderPermissionChecker" %><%@
page import="com.liferay.bookmarks.web.internal.dao.search.BookmarksResultRowSplitter" %><%@
page import="com.liferay.bookmarks.web.internal.portlet.toolbar.contributor.BookmarksPortletToolbarContributor" %><%@
page import="com.liferay.bookmarks.web.internal.portlet.util.BookmarksUtil" %><%@
page import="com.liferay.bookmarks.web.internal.search.EntriesChecker" %><%@
page import="com.liferay.bookmarks.web.internal.search.EntriesMover" %><%@
page import="com.liferay.frontend.taglib.servlet.taglib.AddMenuItem" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchEntry" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortalPreferences" %><%@
page import="com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLUtil" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.GroupPermissionUtil" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.Menu" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem" %><%@
page import="com.liferay.portal.kernel.settings.GroupServiceSettingsLocator" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.CharPool" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.SetUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.trash.kernel.util.TrashUtil" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />