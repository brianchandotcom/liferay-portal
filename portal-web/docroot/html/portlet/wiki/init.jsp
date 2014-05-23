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

<%@ page import="com.liferay.portal.NoSuchModelException" %><%@
page import="com.liferay.portal.kernel.sanitizer.SanitizerException" %><%@
page import="com.liferay.portal.kernel.sanitizer.SanitizerUtil" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFileException" %><%@
page import="com.liferay.portlet.documentlibrary.FileNameException" %><%@
page import="com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException" %><%@
page import="com.liferay.portlet.social.model.SocialActivity" %><%@
page import="com.liferay.portlet.social.model.SocialActivityConstants" %><%@
page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %><%@
page import="com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.wiki.DuplicateNodeNameException" %><%@
page import="com.liferay.portlet.wiki.DuplicatePageException" %><%@
page import="com.liferay.portlet.wiki.ImportFilesException" %><%@
page import="com.liferay.portlet.wiki.NoSuchNodeException" %><%@
page import="com.liferay.portlet.wiki.NoSuchPageException" %><%@
page import="com.liferay.portlet.wiki.NodeNameException" %><%@
page import="com.liferay.portlet.wiki.PageContentException" %><%@
page import="com.liferay.portlet.wiki.PageTitleException" %><%@
page import="com.liferay.portlet.wiki.PageVersionException" %><%@
page import="com.liferay.portlet.wiki.RequiredNodeException" %><%@
page import="com.liferay.portlet.wiki.WikiFormatException" %><%@
page import="com.liferay.portlet.wiki.WikiPortletInstanceSettings" %><%@
page import="com.liferay.portlet.wiki.WikiSettings" %><%@
page import="com.liferay.portlet.wiki.importers.WikiImporterKeys" %><%@
page import="com.liferay.portlet.wiki.model.WikiNode" %><%@
page import="com.liferay.portlet.wiki.model.WikiPage" %><%@
page import="com.liferay.portlet.wiki.model.WikiPageConstants" %><%@
page import="com.liferay.portlet.wiki.model.WikiPageDisplay" %><%@
page import="com.liferay.portlet.wiki.model.WikiPageResource" %><%@
page import="com.liferay.portlet.wiki.model.impl.WikiPageImpl" %><%@
page import="com.liferay.portlet.wiki.service.WikiNodeServiceUtil" %><%@
page import="com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil" %><%@
page import="com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil" %><%@
page import="com.liferay.portlet.wiki.service.WikiPageServiceUtil" %><%@
page import="com.liferay.portlet.wiki.service.permission.WikiNodePermission" %><%@
page import="com.liferay.portlet.wiki.service.permission.WikiPagePermission" %><%@
page import="com.liferay.portlet.wiki.service.permission.WikiPermission" %><%@
page import="com.liferay.portlet.wiki.social.WikiActivityKeys" %><%@
page import="com.liferay.portlet.wiki.util.WikiCacheUtil" %><%@
page import="com.liferay.portlet.wiki.util.WikiConstants" %><%@
page import="com.liferay.portlet.wiki.util.WikiPageAttachmentsUtil" %><%@
page import="com.liferay.portlet.wiki.util.WikiUtil" %><%@
page import="com.liferay.portlet.wiki.util.comparator.PageVersionComparator" %>

<%
String portletId = portletDisplay.getId();

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	 portletId = ParamUtil.getString(request, "portletResource");
}

WikiSettings wikiSettings = WikiUtil.getWikiSettings(scopeGroupId);
WikiPortletInstanceSettings wikiPortletInstanceSettings = WikiUtil.getWikiPortletInstanceSettings(layout, portletId);

String displayStyle = wikiPortletInstanceSettings.getDisplayStyle();
long displayStyleGroupId = wikiPortletInstanceSettings.getDisplayStyleGroupId(themeDisplay.getScopeGroupId());
boolean enableRelatedAssets = wikiPortletInstanceSettings.getEnableRelatedAssets();
boolean enablePageRatings = wikiPortletInstanceSettings.getEnablePageRatings();
boolean enableComments = wikiPortletInstanceSettings.getEnableComments();
boolean enableCommentRatings = wikiPortletInstanceSettings.getEnableCommentRatings();

List<WikiNode> allNodes = WikiNodeServiceUtil.getNodes(scopeGroupId);
List<String> allNodeNames = WikiUtil.getNodeNames(allNodes);

String[] visibleNodes = wikiPortletInstanceSettings.getVisibleNodes();

if (ArrayUtil.isNotEmpty(visibleNodes)) {
	allNodes = WikiUtil.orderNodes(allNodes, visibleNodes);
}
else {
	visibleNodes = allNodeNames.toArray(new String[allNodeNames.size()]);
}

String[] hiddenNodes = wikiPortletInstanceSettings.getHiddenNodes();

boolean enableRSS = wikiPortletInstanceSettings.getEnableRSS();
int rssDelta = wikiPortletInstanceSettings.getRssDelta();
String rssDisplayStyle = wikiPortletInstanceSettings.getRssDisplayStyle();
String rssFeedType = wikiPortletInstanceSettings.getRssFeedType();

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/wiki/init-ext.jsp" %>