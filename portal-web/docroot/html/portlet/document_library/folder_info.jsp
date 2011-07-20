<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

int totalFolders = DLAppServiceUtil.getFoldersCount(repositoryId, folderId, false);
int totalEntries = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(repositoryId, folderId, status);

String folderName = "documents-home";

if (folder != null) {
	folderName = folder.getName();
}

String label = LanguageUtil.format(pageContext, "edit-x", folderName);
%>

<liferay-ui:icon
	cssClass="total-folders"
	image="folder"
	message='<%= LanguageUtil.format(pageContext, "x-folders", totalFolders) %>'
	label="<%= true %>"
/>

<liferay-ui:icon
	cssClass="total-documents"
	image="../document_library/add_document"
	message='<%= LanguageUtil.format(pageContext, "x-documents", totalEntries) %>'
	label="<%= true %>"
/>

<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>">
	<portlet:renderURL var="editFolderURL">
		<portlet:param name="struts_action" value="/document_library/edit_folder" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
	</portlet:renderURL>

	<portlet:renderURL var="editRepositoryURL">
		<portlet:param name="struts_action" value="/document_library/edit_repository" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		image="edit"
		label="<%= true %>"
		message="<%= label %>"
		url="<%= ((folder == null) || !folder.isMountPoint()) ? editFolderURL : editRepositoryURL %>"
	/>
</c:if>