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

<%@ page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.QueryConfig" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.search.Summary" %><%@
page import="com.liferay.portlet.journal.model.JournalArticle" %><%@
page import="com.liferay.portlet.journal.model.JournalArticleConstants" %><%@
page import="com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil" %><%@
page import="com.liferay.portlet.journalcontentsearch.util.ContentHits" %>

<%
boolean showListedDefault = true;

if (portletName.equals(PortletKeys.JOURNAL_CONTENT_SEARCH)) {
	showListedDefault = PropsValues.JOURNAL_CONTENT_SEARCH_SHOW_LISTED;
}

boolean showListed = PrefsParamUtil.getBoolean(portletPreferences, request, "showListed", showListedDefault);

String targetPortletId = StringPool.BLANK;

if (!showListed) {
	targetPortletId = PrefsParamUtil.getString(portletPreferences, request, "targetPortletId", StringPool.BLANK);
}

String type = PrefsParamUtil.getString(portletPreferences, request, "type", StringPool.BLANK);
%>

<%@ include file="/html/portlet/journal_content_search/init-ext.jsp" %>