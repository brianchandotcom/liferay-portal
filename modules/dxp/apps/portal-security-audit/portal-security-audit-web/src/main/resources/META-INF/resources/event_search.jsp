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

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

DisplayTerms displayTerms = searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_audit_event_search"
>
	<aui:input label="user-id" name="userId" value="<%= (userId != 0) ? String.valueOf(userId) : StringPool.BLANK %>" />

	<aui:input label="user-name" name="userName" value="<%= userName %>" />

	<aui:input label="resource-id" name="classPK" value="<%= classPK %>" />

	<aui:input label="class-name" name="className" value="<%= className %>" />

	<aui:input label="resource-action" name="eventType" value="<%= eventType %>" />

	<aui:input label="session-id" name="sessionID" value="<%= sessionID %>" />

	<aui:input label="client-ip" name="clientIP" value="<%= clientIP %>" />

	<aui:input label="client-host" name="clientHost" value="<%= clientHost %>" />

	<aui:input label="server-name" name="serverName" value="<%= serverName %>" />

	<aui:input label="server-port" name="serverPort" value="<%= (serverPort != 0) ? String.valueOf(serverPort) : StringPool.BLANK %>" />

	<aui:field-wrapper label="start-date">
		<liferay-ui:input-date
			dayParam='<%= "startDateDay" %>'
			dayValue="<%= startDateDay %>"
			monthParam='<%= "startDateMonth" %>'
			monthValue="<%= startDateMonth %>"
			yearParam='<%= "startDateYear" %>'
			yearValue="<%= startDateYear %>"
		/>

		<liferay-ui:input-time
			amPmParam='<%= "startDateAmPm" %>'
			amPmValue="<%= startDateAmPm %>"
			hourParam='<%= "startDateHour" %>'
			hourValue="<%= startDateHour %>"
			minuteParam='<%= "startDateMinute" %>'
			minuteValue="<%= startDateMinute %>"
		/>
	</aui:field-wrapper>

	<aui:field-wrapper label="end-date">
		<liferay-ui:input-date
			dayParam='<%= "endDateDay" %>'
			dayValue="<%= endDateDay %>"
			monthParam='<%= "endDateMonth" %>'
			monthValue="<%= endDateMonth %>"
			yearParam='<%= "endDateYear" %>'
			yearValue="<%= endDateYear %>"
		/>

		<liferay-ui:input-time
			amPmParam='<%= "endDateAmPm" %>'
			amPmValue="<%= endDateAmPm %>"
			hourParam='<%= "endDateHour" %>'
			hourValue="<%= endDateHour %>"
			minuteParam='<%= "endDateMinute" %>'
			minuteValue="<%= endDateMinute %>"
		/>
	</aui:field-wrapper>
</liferay-ui:search-toggle>