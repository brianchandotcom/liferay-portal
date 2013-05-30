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

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CalEvent event = null;

String cssClass = StringPool.BLANK;

boolean view = false;

if (row != null) {
	event = (CalEvent)row.getObject();
}
else {
	event = (CalEvent)request.getAttribute("view_event.jsp-event");

	cssClass = "nav nav-list unstyled well";

	view = true;
}
%>

<liferay-ui:icon-menu cssClass="<%= cssClass %>" showExpanded="<%= view %>" showWhenSingleIcon="<%= view %>">
	<c:if test="<%= CalEventPermission.contains(permissionChecker, event, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/calendar/edit_event" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="eventId" value="<%= String.valueOf(event.getEventId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= CalEventPermission.contains(permissionChecker, event, ActionKeys.VIEW) %>">
		<portlet:actionURL var="exportURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<portlet:param name="struts_action" value="/calendar/export_events" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="eventId" value="<%= String.valueOf(event.getEventId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="export"
			url="<%= exportURL %>"
		/>
	</c:if>

	<c:if test="<%= CalEventPermission.contains(permissionChecker, event, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= CalEvent.class.getName() %>"
			modelResourceDescription="<%= event.getTitle() %>"
			resourcePrimKey="<%= String.valueOf(event.getEventId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= CalEventPermission.contains(permissionChecker, event, ActionKeys.DELETE) %>">
		<portlet:renderURL var="redirectURL">
			<portlet:param name="struts_action" value="/calendar/view" />
		</portlet:renderURL>

		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/calendar/edit_event" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= view ? redirectURL : currentURL %>" />
			<portlet:param name="eventId" value="<%= String.valueOf(event.getEventId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>