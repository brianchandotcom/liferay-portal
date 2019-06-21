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
BlogsEntry entry = (BlogsEntry)request.getAttribute("view_entry_content.jsp-entry");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

if (row != null) {
	entry = (BlogsEntry)row.getObject();
}
%>

<liferay-ui:icon-menu
	cssClass='<%= (row == null) ? "entry-options inline" : StringPool.BLANK %>'
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editEntryURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/blogs/edit_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			label="<%= true %>"
			message="edit"
			url="<%= editEntryURL %>"
		/>
	</c:if>

	<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= BlogsEntry.class.getName() %>"
			modelResourceDescription="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>"
			resourceGroupId="<%= String.valueOf(entry.getGroupId()) %>"
			resourcePrimKey="<%= String.valueOf(entry.getEntryId()) %>"
			var="permissionsEntryURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			label="<%= true %>"
			message="permissions"
			method="get"
			url="<%= permissionsEntryURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) %>">
		<portlet:renderURL var="viewEntriesURL" />

		<portlet:actionURL name="/blogs/edit_entry" var="deleteEntryURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= viewEntriesURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			label="<%= true %>"
			trash="<%= trashHelper.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteEntryURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>