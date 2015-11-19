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

JournalFolder folder = null;

if (row != null) {
	folder = (JournalFolder)row.getObject();
}
else {
	folder = (JournalFolder)request.getAttribute("view_entries.jsp-folder");
}

boolean folderSelected = GetterUtil.getBoolean(request.getAttribute("view_entries.jsp-folderSelected"));

String modelResource = null;
String modelResourceDescription = null;
String resourcePrimKey = null;

boolean hasPermissionsPermission = false;

if (folder != null) {
	modelResource = JournalFolder.class.getName();
	modelResourceDescription = folder.getName();
	resourcePrimKey = String.valueOf(folder.getPrimaryKey());

	hasPermissionsPermission = JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.PERMISSIONS);
}
else {
	modelResource = "com.liferay.journal";
	modelResourceDescription = themeDisplay.getScopeGroupName();
	resourcePrimKey = String.valueOf(scopeGroupId);

	hasPermissionsPermission = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:choose>
		<c:when test="<%= folder != null %>">
			<c:if test="<%= JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE) %>">
				<portlet:renderURL var="editURL">
					<portlet:param name="mvcPath" value="/edit_folder.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(folder.getGroupId()) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
					<portlet:param name="mergeWithParentFolderDisabled" value="<%= String.valueOf(folderSelected) %>" />
				</portlet:renderURL>

			<liferay-ui:icon
				message="edit"
				url="<%= editURL %>"
			/>

			<portlet:renderURL var="moveURL">
				<portlet:param name="mvcPath" value="/move_entries.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="rowIdsJournalFolder" value="<%= String.valueOf(folder.getFolderId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				message="move"
				url="<%= moveURL %>"
			/>
		</c:if>
		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_FOLDER) %>">
			<portlet:renderURL var="addFolderURL">
				<portlet:param name="mvcPath" value="/edit_folder.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(folder.getGroupId()) %>" />
				<portlet:param name="parentFolderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				message='<%= (folder != null) ? "add-subfolder" : "add-folder" %>'
				url="<%= addFolderURL %>"
			/>
		</c:if>
		</c:when>
		<c:otherwise>

			<%
			boolean workflowEnabled = false;

			if (WorkflowEngineManagerUtil.isDeployed() && (WorkflowHandlerRegistryUtil.getWorkflowHandler(DLFileEntry.class.getName()) != null)) {
				workflowEnabled = true;
			}
			%>

			<c:if test="<%= workflowEnabled && JournalFolderPermission.contains(permissionChecker, scopeGroupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, ActionKeys.UPDATE) %>">
				<portlet:renderURL var="editURL">
					<portlet:param name="mvcPath" value="/edit_folder.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
					<portlet:param name="mergeWithParentFolderDisabled" value="<%= String.valueOf(folderSelected) %>" />
					<portlet:param name="rootFolder" value="true" />
				</portlet:renderURL>

				<liferay-ui:icon
					message="edit"
					url="<%= editURL %>"
				/>
			</c:if>
			<c:if test="<%= JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_FOLDER) %>">
				<portlet:renderURL var="addFolderURL">
					<portlet:param name="mvcPath" value="/edit_folder.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="parentFolderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					message='<%= (folder != null) ? "add-subfolder" : "add-folder" %>'
					url="<%= addFolderURL %>"
				/>
			</c:if>

		</c:otherwise>
	</c:choose>

	<c:if test="<%= hasPermissionsPermission %>">
		<liferay-security:permissionsURL
			modelResource="<%= modelResource %>"
			modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
			resourcePrimKey="<%= resourcePrimKey %>"
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

	<c:if test="<%= (folder != null) && JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) %>">
		<portlet:actionURL name='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "moveFolderToTrash" : "deleteFolder" %>' var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(folder.getGroupId()) %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>" url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>