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

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.frontend.taglib.servlet.taglib.AddMenuItem" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.servlet.HttpHeaders" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.Menu" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem" %><%@
page import="com.liferay.wiki.configuration.WikiGroupServiceConfiguration" %><%@
page import="com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration" %><%@
page import="com.liferay.wiki.display.context.WikiListPagesDisplayContext" %><%@
page import="com.liferay.wiki.display.context.WikiNodeInfoPanelDisplayContext" %><%@
page import="com.liferay.wiki.display.context.WikiPageInfoPanelDisplayContext" %><%@
page import="com.liferay.wiki.engine.impl.WikiEngineRenderer" %><%@
page import="com.liferay.wiki.exception.DuplicateNodeNameException" %><%@
page import="com.liferay.wiki.exception.DuplicatePageException" %><%@
page import="com.liferay.wiki.exception.ImportFilesException" %><%@
page import="com.liferay.wiki.exception.NoSuchNodeException" %><%@
page import="com.liferay.wiki.exception.NoSuchPageException" %><%@
page import="com.liferay.wiki.exception.NodeNameException" %><%@
page import="com.liferay.wiki.exception.PageContentException" %><%@
page import="com.liferay.wiki.exception.PageTitleException" %><%@
page import="com.liferay.wiki.exception.PageVersionException" %><%@
page import="com.liferay.wiki.exception.RequiredNodeException" %><%@
page import="com.liferay.wiki.exception.WikiFormatException" %><%@
page import="com.liferay.wiki.importer.impl.WikiImporterKeys" %><%@
page import="com.liferay.wiki.importer.impl.WikiImporterTracker" %><%@
page import="com.liferay.wiki.model.impl.WikiPageImpl" %><%@
page import="com.liferay.wiki.service.permission.WikiNodePermissionChecker" %><%@
page import="com.liferay.wiki.service.permission.WikiPagePermissionChecker" %><%@
page import="com.liferay.wiki.service.permission.WikiResourcePermissionChecker" %><%@
page import="com.liferay.wiki.social.WikiActivityKeys" %><%@
page import="com.liferay.wiki.util.WikiPageAttachmentsUtil" %><%@
page import="com.liferay.wiki.util.WikiUtil" %><%@
page import="com.liferay.wiki.util.comparator.PageVersionComparator" %><%@
page import="com.liferay.wiki.validator.WikiPageTitleValidator" %><%@
page import="com.liferay.wiki.web.configuration.WikiPortletInstanceConfiguration" %><%@
page import="com.liferay.wiki.web.internal.display.context.WikiDisplayContextProvider" %><%@
page import="com.liferay.wiki.web.internal.display.context.logic.MailTemplatesHelper" %><%@
page import="com.liferay.wiki.web.internal.display.context.logic.WikiPortletInstanceSettingsHelper" %><%@
page import="com.liferay.wiki.web.internal.display.context.logic.WikiVisualizationHelper" %><%@
page import="com.liferay.wiki.web.internal.display.context.util.WikiRequestHelper" %><%@
page import="com.liferay.wiki.web.internal.display.context.util.WikiSocialActivityHelper" %><%@
page import="com.liferay.wiki.web.internal.display.context.util.WikiURLHelper" %><%@
page import="com.liferay.wiki.web.internal.portlet.toolbar.item.WikiPortletToolbarContributor" %><%@
page import="com.liferay.wiki.web.util.WikiWebComponentProvider" %>

<%
WikiRequestHelper wikiRequestHelper = new WikiRequestHelper(request);

WikiGroupServiceOverriddenConfiguration wikiGroupServiceOverriddenConfiguration = wikiRequestHelper.getWikiGroupServiceOverriddenConfiguration();

WikiPortletInstanceSettingsHelper wikiPortletInstanceSettingsHelper = new WikiPortletInstanceSettingsHelper(wikiRequestHelper);

WikiWebComponentProvider wikiWebComponentProvider = WikiWebComponentProvider.getWikiWebComponentProvider();

WikiDisplayContextProvider wikiDisplayContextProvider = wikiWebComponentProvider.getWikiDisplayContextProvider();

WikiGroupServiceConfiguration wikiGroupServiceConfiguration = wikiWebComponentProvider.getWikiGroupServiceConfiguration();

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/wiki/init-ext.jsp" %>