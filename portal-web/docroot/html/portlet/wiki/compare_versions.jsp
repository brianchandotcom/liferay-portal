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

<liferay-util:include page="/html/portlet/wiki/history_navigation.jsp" />

<%
String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);
%>

<liferay-ui:diff-html diffHtmlResults="<%= diffHtmlResults %>" />