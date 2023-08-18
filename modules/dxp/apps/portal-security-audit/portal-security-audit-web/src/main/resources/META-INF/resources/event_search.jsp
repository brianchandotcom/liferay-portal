<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AuditDisplayContext auditDisplayContext = (AuditDisplayContext)request.getAttribute(AuditDisplayContext.class.getName());

SearchContainer<?> searchContainer = (SearchContainer<?>)request.getAttribute("liferay-ui:search:searchContainer");
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= searchContainer.getDisplayTerms() %>"
	id="toggle_id_audit_event_search"
>
	<aui:input label="user-id" name="userId" value="<%= (auditDisplayContext.getUserId() != 0) ? String.valueOf(auditDisplayContext.getUserId()) : StringPool.BLANK %>" />

	<aui:input label="user-name" name="userName" value="<%= auditDisplayContext.getUserName() %>" />

	<aui:input label="group-id" name="groupId" value="<%= (auditDisplayContext.getGroupId() != 0) ? String.valueOf(auditDisplayContext.getGroupId()) : StringPool.BLANK %>" />

	<aui:input label="resource-id" name="classPK" value="<%= auditDisplayContext.getClassPK() %>" />

	<aui:input label="class-name" name="className" value="<%= auditDisplayContext.getClassName() %>" />

	<aui:input label="resource-action" name="eventType" value="<%= auditDisplayContext.getEventType() %>" />

	<aui:input label="client-ip" name="clientIP" value="<%= auditDisplayContext.getClientIP() %>" />

	<aui:input label="client-host" name="clientHost" value="<%= auditDisplayContext.getClientHost() %>" />

	<aui:input label="server-name" name="serverName" value="<%= auditDisplayContext.getServerName() %>" />

	<aui:input label="server-port" name="serverPort" value="<%= (auditDisplayContext.getServerPort() != 0) ? String.valueOf(auditDisplayContext.getServerPort()) : StringPool.BLANK %>" />

	<aui:field-wrapper label="start-date">
		<liferay-ui:input-date
			dayParam="startDateDay"
			dayValue="<%= auditDisplayContext.getStartDateDay() %>"
			monthParam="startDateMonth"
			monthValue="<%= auditDisplayContext.getStartDateMonth() %>"
			name="startDate"
			useNamespace="<%= false %>"
			yearParam="startDateYear"
			yearValue="<%= auditDisplayContext.getStartDateYear() %>"
		/>

		<liferay-ui:input-time
			amPmParam="startDateAmPm"
			amPmValue="<%= auditDisplayContext.getStartDateAmPm() %>"
			hourParam="startDateHour"
			hourValue="<%= auditDisplayContext.getStartDateHour() %>"
			minuteParam="startDateMinute"
			minuteValue="<%= auditDisplayContext.getStartDateMinute() %>"
			name="startDateTime"
			useNamespace="<%= false %>"
		/>
	</aui:field-wrapper>

	<aui:field-wrapper label="end-date">
		<liferay-ui:input-date
			dayParam="endDateDay"
			dayValue="<%= auditDisplayContext.getEndDateDay() %>"
			monthParam="endDateMonth"
			monthValue="<%= auditDisplayContext.getEndDateMonth() %>"
			name="endDate"
			useNamespace="<%= false %>"
			yearParam="endDateYear"
			yearValue="<%= auditDisplayContext.getEndDateYear() %>"
		/>

		<liferay-ui:input-time
			amPmParam="endDateAmPm"
			amPmValue="<%= auditDisplayContext.getEndDateAmPm() %>"
			hourParam="endDateHour"
			hourValue="<%= auditDisplayContext.getEndDateHour() %>"
			minuteParam="endDateMinute"
			minuteValue="<%= auditDisplayContext.getEndDateMinute() %>"
			name="endDateTime"
			useNamespace="<%= false %>"
		/>
	</aui:field-wrapper>
</liferay-ui:search-toggle>