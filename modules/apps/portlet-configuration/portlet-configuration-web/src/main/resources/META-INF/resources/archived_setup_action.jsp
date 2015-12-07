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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ArchivedSettings archivedSettings = (ArchivedSettings)row.getObject();
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<portlet:actionURL name="restoreArchivedSetup" var="restoreArchivedSetupURL">
		<portlet:param name="mvcPath" value="/edit_archived_setups.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
		<portlet:param name="name" value="<%= archivedSettings.getName() %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="restore"
		url="<%= restoreArchivedSetupURL %>"
	/>

	<portlet:actionURL name="deleteArchivedSetup" var="deleteArchivedBackupURL">
		<portlet:param name="mvcPath" value="/edit_archived_setups.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="portletConfiguration" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
		<portlet:param name="name" value="<%= archivedSettings.getName() %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteArchivedBackupURL %>"
	/>
</liferay-ui:icon-menu>