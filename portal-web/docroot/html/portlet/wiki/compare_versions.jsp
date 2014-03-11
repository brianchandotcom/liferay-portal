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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);
long nodeId = (Long)request.getAttribute(WebKeys.WIKI_NODE_ID);
String title = (String)request.getAttribute(WebKeys.TITLE);
double sourceVersion = (Double)request.getAttribute(WebKeys.SOURCE_VERSION);
double targetVersion = (Double)request.getAttribute(WebKeys.TARGET_VERSION);

Object[] returnValue = WikiUtil.getWikiPageVersionsInfo(nodeId, title, sourceVersion, targetVersion, pageContext);
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
	<liferay-util:param name="tabs1" value="history" />
</liferay-util:include>

<liferay-util:include page="/html/portlet/wiki/page_tabs_history.jsp" />

<liferay-ui:header
	backURL="<%= backURL %>"
	title="compare-versions"
/>

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
</liferay-portlet:renderURL>

<liferay-ui:version-comparator
	diffHtmlResults="<%= diffHtmlResults %>"
	iteratorURL="<%= iteratorURL %>"
	nextVersion="<%= (Double)returnValue[2] %>"
	previousVersion="<%= (Double)returnValue[1] %>"
	sourceVersion="<%= sourceVersion %>"
	targetVersion="<%= targetVersion %>"
	versionsInfo="<%= (List<Tuple>)returnValue[0] %>"
/>