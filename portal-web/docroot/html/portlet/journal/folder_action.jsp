<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

JournalFolder folder = null;

if (row != null) {
	folder = (JournalFolder)row.getObject();
}
else {
	folder = (JournalFolder)request.getAttribute("view_articles.jsp-folder");
}
%>

<span class="overlay folder-action">
	<liferay-ui:icon-menu align="auto" direction="down" extended="<%= false %>" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE) %>">
			<portlet:renderURL var="editURL">
				<portlet:param name="struts_action" value="/journal/edit_folder" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(folder.getGroupId()) %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="edit"
				url="<%= editURL %>"
			/>

			<portlet:renderURL var="moveURL">
				<portlet:param name="struts_action" value="/journal/move_folder" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="submit"
				message="move"
				url="<%= moveURL %>"
			/>
		</c:if>

		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.PERMISSIONS) %>">
			<liferay-security:permissionsURL
				modelResource="<%= JournalFolder.class.getName() %>"
				modelResourceDescription="<%= folder.getName() %>"
				resourcePrimKey="<%= String.valueOf(folder.getPrimaryKey()) %>"
				var="permissionsURL"
			/>

			<liferay-ui:icon
				image="permissions"
				url="<%= permissionsURL %>"
			/>
		</c:if>

		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) %>">
			<portlet:actionURL var="deleteURL">
				<portlet:param name="struts_action" value="/journal/edit_folder" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(folder.getGroupId()) %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete url="<%= deleteURL %>" />
		</c:if>

		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.ADD_FOLDER) %>">
			<portlet:renderURL var="addFolderURL">
				<portlet:param name="struts_action" value="/journal/edit_folder" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(folder.getGroupId()) %>" />
				<portlet:param name="parentFolderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="add_folder"
				message='<%= (folder != null) ? "add-subfolder" : "add-folder" %>'
				url="<%= addFolderURL %>"
			/>
		</c:if>
	</liferay-ui:icon-menu>
</span>