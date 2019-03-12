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

<%@ include file="/document_library/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DLFileEntryType fileEntryType = (DLFileEntryType)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message='<%= LanguageUtil.get(request, "actions") %>'
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= DLFileEntryTypePermission.contains(permissionChecker, fileEntryType, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_entry_type" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= DLFileEntryTypePermission.contains(permissionChecker, fileEntryType, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DLFileEntryType.class.getName() %>"
			modelResourceDescription="<%= fileEntryType.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= DLFileEntryTypePermission.contains(permissionChecker, fileEntryType, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/document_library/edit_file_entry_type" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>

	<c:if test="<%= portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN) %>">
		<liferay-export-import-changeset:publish-entity-menu-item
			className="<%= DLFileEntryType.class.getName() %>"
			groupId="<%= fileEntryType.getGroupId() %>"
			uuid="<%= fileEntryType.getUuid() %>"
		/>
	</c:if>
</liferay-ui:icon-menu>