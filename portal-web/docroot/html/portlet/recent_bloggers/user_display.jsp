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

<%@ include file="/html/portlet/recent_bloggers/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

BlogsStatsUser statsUser = (BlogsStatsUser)objArray[0];
String rowHREF = (String)objArray[1];
%>

<liferay-ui:user-display url="<%= rowHREF %>" userId="<%= statsUser.getUserId() %>">
	<div class="blogger-post-count">
		<span><liferay-ui:message key="posts" />:</span><aui:spacer /><%= statsUser.getEntryCount() %>
	</div>

	<div class="blogger-stars">
		<span><liferay-ui:message key="stars" />:</span><aui:spacer /><%= statsUser.getRatingsTotalEntries() %>
	</div>

	<div class="blogger-date">
		<span><liferay-ui:message key="date" />:</span><aui:spacer /><%= dateFormatDate.format(statsUser.getLastPostDate()) %>
	</div>
</liferay-ui:user-display>