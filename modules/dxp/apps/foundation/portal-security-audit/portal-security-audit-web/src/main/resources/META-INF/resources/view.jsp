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

<liferay-portlet:renderURL varImpl="searchURL" />

<aui:form action="<%= searchURL %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="searchURL" />

	<liferay-portlet:renderURL varImpl="iteratorURL">
		<portlet:param name="className" value="<%= className %>" />
		<portlet:param name="classPK" value="<%= classPK %>" />
		<portlet:param name="clientHost" value="<%= clientHost %>" />
		<portlet:param name="clientIP" value="<%= clientIP %>" />
		<portlet:param name="endDateAmPm" value="<%= String.valueOf(endDateAmPm) %>" />
		<portlet:param name="endDateDay" value="<%= String.valueOf(endDateDay) %>" />
		<portlet:param name="endDateHour" value="<%= String.valueOf(endDateHour) %>" />
		<portlet:param name="endDateMinute" value="<%= String.valueOf(endDateMinute) %>" />
		<portlet:param name="endDateMonth" value="<%= String.valueOf(endDateMonth) %>" />
		<portlet:param name="endDateYear" value="<%= String.valueOf(endDateYear) %>" />
		<portlet:param name="eventType" value="<%= eventType %>" />
		<portlet:param name="serverName" value="<%= serverName %>" />
		<portlet:param name="serverPort" value="<%= String.valueOf(serverPort) %>" />
		<portlet:param name="sessionID" value="<%= sessionID %>" />
		<portlet:param name="startDateAmPm" value="<%= String.valueOf(startDateAmPm) %>" />
		<portlet:param name="startDateDay" value="<%= String.valueOf(startDateDay) %>" />
		<portlet:param name="startDateHour" value="<%= String.valueOf(startDateHour) %>" />
		<portlet:param name="startDateMinute" value="<%= String.valueOf(startDateMinute) %>" />
		<portlet:param name="startDateMonth" value="<%= String.valueOf(startDateMonth) %>" />
		<portlet:param name="startDateYear" value="<%= String.valueOf(startDateYear) %>" />
		<portlet:param name="userId" value="<%= String.valueOf(userId) %>" />
		<portlet:param name="userName" value="<%= userName %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:search-container
		displayTerms="<%= new DisplayTerms(renderRequest) %>"
		emptyResultsMessage="there-are-no-events"
		headerNames="user-id,user-name,resource-id,resource-name,resource-action,client-ip,create-date"
		iteratorURL="<%= iteratorURL %>"
	>
		<liferay-ui:search-form
			page="/event_search.jsp"
			servletContext="<%= application %>"
		/>

		<%
		int endDateDayHour = (endDateAmPm != Calendar.PM) ? endDateHour : endDateHour + 12;
		int startDateDayHour = (startDateAmPm != Calendar.PM) ? startDateHour : startDateHour + 12;

		Date endDate = PortalUtil.getDate(endDateMonth, endDateDay, endDateYear, endDateDayHour, endDateMinute, timeZone, null);
		Date startDate = PortalUtil.getDate(startDateMonth, startDateDay, startDateYear, startDateDayHour, startDateMinute, timeZone, null);

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		if (displayTerms.isAdvancedSearch()) {
			total = AuditEventManagerUtil.getAuditEventsCount(themeDisplay.getCompanyId(), userId, userName, startDate, endDate, eventType, className, classPK, clientHost, clientIP, serverName, serverPort, sessionID, displayTerms.isAndOperator());

			searchContainer.setTotal(total);

			searchContainer.setResults(AuditEventManagerUtil.getAuditEvents(themeDisplay.getCompanyId(), userId, userName, startDate, endDate, eventType, className, classPK, clientHost, clientIP, serverName, serverPort, sessionID, displayTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), new AuditEventCreateDateComparator()));
		}
		else {
			String keywords = displayTerms.getKeywords();
			String number = Validator.isNumber(keywords) ? keywords : String.valueOf(0);

			total = AuditEventManagerUtil.getAuditEventsCount(themeDisplay.getCompanyId(), Long.valueOf(number), keywords, null, null, keywords, keywords, keywords, keywords, keywords, keywords, Integer.valueOf(number), keywords, false);

			searchContainer.setTotal(total);

			searchContainer.setResults(AuditEventManagerUtil.getAuditEvents(themeDisplay.getCompanyId(), Long.valueOf(number), keywords, null, null, keywords, keywords, keywords, keywords, keywords, keywords, Integer.valueOf(number), keywords, false, searchContainer.getStart(), searchContainer.getEnd(), new AuditEventCreateDateComparator()));
		}
		%>

		<liferay-ui:search-container-row
			className="com.liferay.portal.security.audit.AuditEvent"
			escapedModel="<%= true %>"
			keyProperty="auditEventId"
			modelVar="event"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="mvcPath" value="/view_audit_event.jsp" />
				<portlet:param name="auditEventId" value="<%= String.valueOf(event.getAuditEventId()) %>" />
			</liferay-portlet:renderURL>

			<%@ include file="/search_columns.jspf" %>
		</liferay-ui:search-container-row>

		<div class="separator"><!-- --></div>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</aui:form>