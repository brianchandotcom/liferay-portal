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

SACPEntry sacpEntry = (SACPEntry)row.getObject();
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= SACPEntryPermission.contains(permissionChecker, sacpEntry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_entry.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sacpEntryId" value="<%= String.valueOf(sacpEntry.getSacpEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= SACPEntryPermission.contains(permissionChecker, sacpEntry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= SACPEntry.class.getName() %>"
			modelResourceDescription="<%= sacpEntry.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(sacpEntry.getSacpEntryId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= SACPEntryPermission.contains(permissionChecker, sacpEntry, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteSACPEntry" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="sacpEntryId" value="<%= String.valueOf(sacpEntry.getSacpEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>