<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

List<DLFileEntryType> fileEntryTypes = Collections.emptyList();

boolean showAddFileEntryTypes = ((folder == null) || folder.isSupportsMetadata());

if (showAddFileEntryTypes) {
	fileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(PortalUtil.getSiteAndCompanyGroupIds(themeDisplay), folderId, true);
}

boolean fileEntryTypesIsEmpty = fileEntryTypes.isEmpty();

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

boolean showAddDocument = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) && (fileEntryTypesIsEmpty || showAddFileEntryTypes);

boolean showAddFolder = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER);

boolean showAddMultipleDocuments = ((folder == null) || folder.isSupportsMultipleUpload()) && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT);

boolean showAddRepository = (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_REPOSITORY));

boolean showAddShortcut = ((folder == null) || folder.isSupportsShortcuts()) && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_SHORTCUT);
%>

<c:if test="<%= (showAddFolder || showAddShortcut || showAddRepository || showAddMultipleDocuments || showAddDocument) %>">
	<aui:nav-item dropdown="<%= true %>" id="addButtonContainer" label="add">
		<c:if test="<%= showAddFolder %>">
			<portlet:renderURL var="addFolderURL">
				<portlet:param name="struts_action" value="/document_library/edit_folder" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
				<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addFolderURL %>" iconClass="icon-folder-open" label='<%= (folder != null) ? "subfolder" : "folder" %>' />
		</c:if>

		<c:if test="<%= showAddShortcut %>">
			<portlet:renderURL var="editFileShortcutURL">
				<portlet:param name="struts_action" value="/document_library/edit_file_shortcut" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= editFileShortcutURL %>" label="shortcut" />
		</c:if>

		<c:if test="<%= showAddRepository %>">
			<portlet:renderURL var="addRepositoryURL">
				<portlet:param name="struts_action" value="/document_library/edit_repository" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addRepositoryURL %>" iconClass="icon-hdd" label="repository" />
		</c:if>

		<c:if test="<%= showAddMultipleDocuments %>">
			<portlet:renderURL var="editFileEntryURL">
				<portlet:param name="struts_action" value="/document_library/upload_multiple_file_entries" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= editFileEntryURL %>" label="multiple-documents" />
		</c:if>

		<c:if test="<%= showAddDocument %>">
			<c:choose>
				<c:when test="<%= fileEntryTypesIsEmpty %>">
					<portlet:renderURL var="editFileEntryURL">
						<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="backURL" value="<%= currentURL %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:renderURL>

					<aui:nav-item href="<%= editFileEntryURL %>" iconClass="icon-file" label="basic-document" />
				</c:when>
				<c:when test="<%= showAddFileEntryTypes %>">
					<%
					for (DLFileEntryType fileEntryType : fileEntryTypes) {
					%>
						<portlet:renderURL var="addFileEntryTypeURL">
							<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
						</portlet:renderURL>

						<aui:nav-item href="<%= addFileEntryTypeURL %>" iconClass="icon-file" label="<%= HtmlUtil.escape(fileEntryType.getName(locale)) %>" />
					<%
					}
					%>
				</c:when>
			</c:choose>
		</c:if>
	</aui:nav-item>
</c:if>

<aui:script use="aui-base,uploader">
	if (!A.UA.ios && (A.Uploader.TYPE != 'none')) {
		var uploadMultipleDocumentsIcon = A.all('.upload-multiple-documents:hidden');

		uploadMultipleDocumentsIcon.show();
	}
</aui:script>