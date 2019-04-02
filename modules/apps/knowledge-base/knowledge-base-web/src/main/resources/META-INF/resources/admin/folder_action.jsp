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

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(KBWebKeys.SEARCH_CONTAINER_RESULT_ROW);

KBFolder kbFolder = null;

if (row != null) {
	kbFolder = (KBFolder)row.getObject();
}
else {
	kbFolder = (KBFolder)request.getAttribute("info_panel.jsp-kbFolder");
}

String modelResource = null;
String modelResourceDescription = null;
String resourcePrimKey = null;

boolean showPermissionsURL = false;

if (kbFolder != null) {
	modelResource = KBFolder.class.getName();
	modelResourceDescription = kbFolder.getName();
	resourcePrimKey = String.valueOf(kbFolder.getKbFolderId());

	showPermissionsURL = KBFolderPermission.contains(permissionChecker, kbFolder, KBActionKeys.PERMISSIONS);
}
else {
	modelResource = KBConstants.RESOURCE_NAME_ADMIN;
	modelResourceDescription = themeDisplay.getScopeGroupName();
	resourcePrimKey = String.valueOf(scopeGroupId);

	showPermissionsURL = AdminPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS) && GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= kbFolder != null %>">
		<c:if test="<%= KBFolderPermission.contains(permissionChecker, kbFolder, KBActionKeys.UPDATE) %>">
			<liferay-portlet:renderURL var="editURL">
				<portlet:param name="mvcPath" value='<%= templatePath + "common/edit_folder.jsp" %>' />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="kbFolderId" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				message="edit"
				url="<%= editURL %>"
			/>
		</c:if>

		<c:if test="<%= KBFolderPermission.contains(permissionChecker, kbFolder, KBActionKeys.MOVE_KB_FOLDER) %>">
			<liferay-portlet:renderURL var="moveKBFolderURL">
				<portlet:param name="mvcPath" value='<%= templatePath + "move_object.jsp" %>' />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="resourceClassNameId" value="<%= String.valueOf(kbFolder.getClassNameId()) %>" />
				<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
				<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(kbFolder.getClassNameId()) %>" />
				<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbFolder.getParentKBFolderId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				message="move"
				url="<%= moveKBFolderURL %>"
			/>
		</c:if>

		<c:if test="<%= KBFolderPermission.contains(permissionChecker, kbFolder, KBActionKeys.DELETE) %>">
			<liferay-portlet:actionURL name="deleteKBFolder" var="deleteURL">
				<portlet:param name="mvcPath" value='<%= ParamUtil.getString(request, "mvcPath") %>' />
				<portlet:param name="redirect" value="<%= (row == null) ? redirect : currentURL %>" />
				<portlet:param name="kbFolderId" value="<%= String.valueOf(kbFolder.getKbFolderId()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon-delete
				url="<%= deleteURL %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= showPermissionsURL %>">
		<liferay-security:permissionsURL
			modelResource="<%= modelResource %>"
			modelResourceDescription="<%= modelResourceDescription %>"
			resourcePrimKey="<%= resourcePrimKey %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>
</liferay-ui:icon-menu>