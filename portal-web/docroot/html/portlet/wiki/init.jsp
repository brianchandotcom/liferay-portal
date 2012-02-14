<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.portlet.documentlibrary.DuplicateFileException" %><%@
page import="com.liferay.portlet.documentlibrary.FileSizeException" %><%@
page import="com.liferay.portlet.documentlibrary.store.DLStoreUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DLUtil" %><%@
page import="com.liferay.portlet.documentlibrary.util.DocumentConversionUtil" %><%@
page import="com.liferay.portlet.wiki.DuplicateNodeNameException" %><%@
page import="com.liferay.portlet.wiki.DuplicatePageException" %><%@
page import="com.liferay.portlet.wiki.ImportFilesException" %><%@
page import="com.liferay.portlet.wiki.NoSuchNodeException" %><%@
page import="com.liferay.portlet.wiki.NoSuchPageException" %><%@
page import="com.liferay.portlet.wiki.NodeNameException" %><%@
page import="com.liferay.portlet.wiki.PageContentException" %><%@
page import="com.liferay.portlet.wiki.PageTitleException" %><%@
page import="com.liferay.portlet.wiki.PageVersionException" %><%@
page import="com.liferay.portlet.wiki.WikiFormatException" %><%@
page import="com.liferay.portlet.wiki.importers.WikiImporterKeys" %><%@
page import="com.liferay.portlet.wiki.model.WikiNode" %><%@
page import="com.liferay.portlet.wiki.model.WikiPage" %><%@
page import="com.liferay.portlet.wiki.model.WikiPageConstants" %><%@
page import="com.liferay.portlet.wiki.model.WikiPageDisplay" %><%@
page import="com.liferay.portlet.wiki.model.WikiPageResource" %><%@
page import="com.liferay.portlet.wiki.model.impl.WikiPageImpl" %><%@
page import="com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil" %><%@
page import="com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil" %><%@
page import="com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil" %><%@
page import="com.liferay.portlet.wiki.service.WikiPageServiceUtil" %><%@
page import="com.liferay.portlet.wiki.service.permission.WikiNodePermission" %><%@
page import="com.liferay.portlet.wiki.service.permission.WikiPagePermission" %><%@
page import="com.liferay.portlet.wiki.service.permission.WikiPermission" %><%@
page import="com.liferay.portlet.wiki.util.WikiCacheUtil" %><%@
page import="com.liferay.portlet.wiki.util.WikiUtil" %><%@
page import="com.liferay.portlet.wiki.util.comparator.PageVersionComparator" %><%@
page import="com.liferay.util.RSSUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean enableRelatedAssets = GetterUtil.getBoolean(preferences.getValue("enableRelatedAssets", null), true);
boolean enablePageRatings = PropsValues.WIKI_PAGE_RATINGS_ENABLED && GetterUtil.getBoolean(preferences.getValue("enablePageRatings", null), true);
boolean enableComments = PropsValues.WIKI_PAGE_COMMENTS_ENABLED && GetterUtil.getBoolean(preferences.getValue("enableComments", null), true);
boolean enableCommentRatings = GetterUtil.getBoolean(preferences.getValue("enableCommentRatings", null), true);

List<WikiNode> allNodes = WikiNodeLocalServiceUtil.getNodes(scopeGroupId);
List<String> allNodeNames = WikiUtil.getNodeNames(allNodes);

String[] visibleNodes = null;

String visibleNodesPreference = preferences.getValue("visibleNodes", null);

if (visibleNodesPreference != null) {
	visibleNodes = StringUtil.split(visibleNodesPreference);

	allNodes = WikiUtil.orderNodes(allNodes, visibleNodes);
}
else {
	visibleNodes = allNodeNames.toArray(new String[allNodeNames.size()]);
}

String[] hiddenNodes = StringUtil.split(preferences.getValue("hiddenNodes", null));

int rssDelta = GetterUtil.getInteger(preferences.getValue("rssDelta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = preferences.getValue("rssDisplayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);

StringBundler rssURLParams = new StringBundler(4);

if ((rssDelta != SearchContainer.DEFAULT_DELTA) || !rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
	if (rssDelta != SearchContainer.DEFAULT_DELTA) {
		rssURLParams.append("&max=");
		rssURLParams.append(rssDelta);
	}

	if (!rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
		rssURLParams.append("&displayStyle=");
		rssURLParams.append(rssDisplayStyle);
	}
}

String rssURLParam = rssURLParams.toString();

StringBundler rssURLAtomParams = new StringBundler(4);

rssURLAtomParams.append(rssURLParam);
rssURLAtomParams.append("&type=");
rssURLAtomParams.append(RSSUtil.ATOM);
rssURLAtomParams.append("&version=1.0");

StringBundler rssURLRSS10Params = new StringBundler(4);

rssURLRSS10Params.append(rssURLParam);
rssURLRSS10Params.append("&type=");
rssURLRSS10Params.append(RSSUtil.RSS);
rssURLRSS10Params.append("&version=1.0");

StringBundler rssURLRSS20Params = new StringBundler(4);

rssURLRSS20Params.append(rssURLParam);
rssURLRSS20Params.append("&type=");
rssURLRSS20Params.append(RSSUtil.RSS);
rssURLRSS20Params.append("&version=2.0");

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/wiki/init-ext.jsp" %>