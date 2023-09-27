<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	<c:if test="<%= Objects.equals(dlRequestHelper.getResourcePortletName(), DLPortletKeys.DOCUMENT_LIBRARY_ADMIN) %>">
		<liferay-export-import-changeset:publish-entity-menu-item
			className="<%= DLFileEntryType.class.getName() %>"
			groupId="<%= fileEntryType.getGroupId() %>"
			uuid="<%= fileEntryType.getUuid() %>"
		/>
	</c:if>
</liferay-ui:icon-menu>