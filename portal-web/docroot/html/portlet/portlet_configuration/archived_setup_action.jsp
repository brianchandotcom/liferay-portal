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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objects = (Object[])row.getObject();

ArchivedSettings archivedSettings = (ArchivedSettings)objects[0];
portletResource = (String)objects[1];
%>

<liferay-ui:icon-menu>
	<portlet:actionURL var="restoreURL">
		<portlet:param name="struts_action" value="/portlet_configuration/edit_archived_setups" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
		<portlet:param name="name" value="<%= archivedSettings.getName() %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		image="undo"
		message="restore"
		url="<%= restoreURL %>"
	/>

	<portlet:actionURL var="deleteURL">
		<portlet:param name="struts_action" value="/portlet_configuration/edit_archived_setups" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
		<portlet:param name="name" value="<%= archivedSettings.getName() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>