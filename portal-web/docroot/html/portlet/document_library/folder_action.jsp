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
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_folder_action") + StringPool.UNDERLINE;

String redirect = currentURL;

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Folder folder = null;

long folderId = 0;

long repositoryId = 0;

if (row != null) {
	folder = (Folder)row.getObject();

	folderId = folder.getFolderId();

	repositoryId = folder.getRepositoryId();
}
else {
	if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY)) {
		folder = (Folder)request.getAttribute("view.jsp-folder");

		folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

		repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));
	}
	else {
		folder = (Folder)request.getAttribute("view_entries.jsp-folder");

		folderId = GetterUtil.getLong((String)request.getAttribute("view_entries.jsp-folderId"));

		repositoryId = GetterUtil.getLong((String)request.getAttribute("view_entries.jsp-repositoryId"));
	}
}

String modelResource = null;
String modelResourceDescription = null;
String resourcePrimKey = null;

boolean showPermissionsURL = false;

if (folder != null) {
	modelResource = DLFolderConstants.getClassName();
	modelResourceDescription = folder.getName();
	resourcePrimKey = String.valueOf(folderId);

	showPermissionsURL = DLFolderPermission.contains(permissionChecker, folder, ActionKeys.PERMISSIONS);
}
else {
	modelResource = "com.liferay.portlet.documentlibrary";
	modelResourceDescription = themeDisplay.getScopeGroupName();
	resourcePrimKey = String.valueOf(scopeGroupId);

	showPermissionsURL = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}

boolean view = false;

if (row == null && portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY)) {
	view = true;
}
%>

<liferay-util:buffer var="iconMenu">
	<liferay-ui:icon-menu align='<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? "right" : "auto" %>' direction='<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? null : "down" %>' extended="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? true : false %>" icon="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? null : StringPool.BLANK %>" message='<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? "actions" : StringPool.BLANK %>' showExpanded="<%= view %>" showWhenSingleIcon="<%= view %>">
		<c:choose>
			<c:when test="<%= folder != null %>">
				<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) && !folder.isMountPoint() %>">
					<portlet:renderURL var="editURL">
						<portlet:param name="struts_action" value="/document_library/edit_folder" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						image="edit"
						url="<%= editURL %>"
					/>
				</c:if>

				<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) && folder.isMountPoint() %>">
					<portlet:renderURL var="editURL">
						<portlet:param name="struts_action" value="/document_library/edit_repository" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						image="edit"
						url="<%= editURL %>"
					/>
				</c:if>

				<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) && !folder.isMountPoint() %>">
					<portlet:renderURL var="moveURL">
						<portlet:param name="struts_action" value="/document_library/move_folder" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						image="submit"
						message="move"
						url="<%= moveURL %>"
					/>
				</c:if>

				<c:if test="<%= showActions && showPermissionsURL %>">
					<liferay-security:permissionsURL
						modelResource="<%= modelResource %>"
						modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
						redirect="<%= currentURL %>"
						resourcePrimKey="<%= resourcePrimKey %>"
						var="permissionsURL"
					/>

					<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
				</c:if>

				<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.DELETE) && !folder.isMountPoint() %>">
					<portlet:renderURL var="redirectURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(folder.getParentFolderId()) %>" />
					</portlet:renderURL>

					<portlet:actionURL var="deleteURL">
						<portlet:param name="struts_action" value="/document_library/edit_folder" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
						<portlet:param name="redirect" value="<%= view ? redirectURL : redirect %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete url="<%= deleteURL %>" />
				</c:if>

				<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.DELETE) && folder.isMountPoint() %>">
					<portlet:renderURL var="redirectURL">
						<portlet:param name="struts_action" value="/document_library/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(folder.getParentFolderId()) %>" />
					</portlet:renderURL>

					<portlet:actionURL var="deleteURL">
						<portlet:param name="struts_action" value="/document_library/edit_repository" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
						<portlet:param name="redirect" value="<%= view ? redirectURL : redirect %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete url="<%= deleteURL %>" />
				</c:if>

				<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
					<portlet:renderURL var="addFolderURL">
						<portlet:param name="struts_action" value="/document_library/edit_folder" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="add_folder" message='<%= (folder != null) ? "add-subfolder" : "add-folder" %>' url="<%= addFolderURL %>" />
				</c:if>

				<c:if test="<%= showActions && folder.isDefaultRepository() && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
					<portlet:renderURL var="addRepositoryURL">
						<portlet:param name="struts_action" value="/document_library/edit_repository" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="add_drive" message="add-repository" url="<%= addRepositoryURL %>" />
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="<%= showActions && showPermissionsURL %>">
					<liferay-security:permissionsURL
						modelResource="<%= modelResource %>"
						modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
						redirect="<%= currentURL %>"
						resourcePrimKey="<%= resourcePrimKey %>"
						var="permissionsURL"
					/>

					<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
				</c:if>

				<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
					<portlet:renderURL var="addFolderURL">
						<portlet:param name="struts_action" value="/document_library/edit_folder" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="add_folder" message='<%= (folder != null) ? "add-subfolder" : "add-folder" %>' url="<%= addFolderURL %>" />
				</c:if>

				<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
					<portlet:renderURL var="addRepositoryURL">
						<portlet:param name="struts_action" value="/document_library/edit_repository" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon image="add_drive" message="add-repository" url="<%= addRepositoryURL %>" />
				</c:if>
			</c:otherwise>
		</c:choose>

		<c:if test="<%= portletDisplay.isWebDAVEnabled() && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.VIEW) %>">
			<liferay-ui:icon cssClass='<%= randomNamespace + "-webdav-action" %>' image="desktop" message="access-from-desktop" url="javascript:;" />
		</c:if>

		<c:if test="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) %>">
			<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">
				<portlet:renderURL var="editFileEntryURL">
					<portlet:param name="struts_action" value="/document_library/upload_multiple_file_entries" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</portlet:renderURL>

				<liferay-ui:icon cssClass="aui-helper-hidden upload-multiple-documents" image="../document_library/add_multiple_documents" message="multiple-documents" url="<%= editFileEntryURL %>" />
			</c:if>

			<%
			int fileEntryTypesCount = DLFileEntryTypeServiceUtil.getFileEntryTypesCount(scopeGroupId);
			%>

			<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">
				<liferay-portlet:renderURL var="editFileEntryURL" windowState="<%= fileEntryTypesCount > 0 ? LiferayWindowState.POP_UP.toString() : WindowState.NORMAL.toString() %>">
					<portlet:param name="struts_action" value='<%= fileEntryTypesCount > 0 ? "/document_library_display/select_file_entry_type" : "/document_library_display/edit_file_entry" %>' />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</liferay-portlet:renderURL>

				<%
				String taglibEditURL = "javascript:Liferay.Util.openWindow({dialog: {width: 420}, id: '" + renderResponse.getNamespace() + "selectFileEntryType', title: '" + LanguageUtil.get(pageContext, "select-document-type") + "', uri:'" + editFileEntryURL.toString() + "'});";
				%>

				<liferay-ui:icon image="../document_library/add_document" message="add-document" url="<%= fileEntryTypesCount > 0 ? taglibEditURL : editFileEntryURL %>" />
			</c:if>

			<c:if test="<%= showActions && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_SHORTCUT) && ((folder == null) || folder.isSupportsShortcuts()) %>">
				<portlet:renderURL var="editFileShortcutURL">
					<portlet:param name="struts_action" value="/document_library_display/edit_file_shortcut" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</portlet:renderURL>

				<liferay-ui:icon image="add_instance" message="add-shortcut" url="<%= editFileShortcutURL %>" />
			</c:if>
		</c:if>
	</liferay-ui:icon-menu>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) %>">

		<%= iconMenu %>

	</c:when>
	<c:otherwise>
		<span class="overlay document-action">

			<%= iconMenu %>

		</span>
	</c:otherwise>
</c:choose>

<div id="<%= randomNamespace %>webDav" style="display: none;">
	<div class="portlet-document-library">

		<%
		String webDavHelpMessage = null;

		if (BrowserSnifferUtil.isWindows(request)) {
			webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"});
		}
		else {
			webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV");
		}
		%>

		<liferay-ui:message key="<%= webDavHelpMessage %>" />

		<br /><br />

		<div class="file-entry-field">
			<label><liferay-ui:message key="webdav-url" /></label>

			<%
			StringBuilder sb = new StringBuilder();

			if (folder != null) {
				Folder curFolder = folder;

				while (true) {
					sb.insert(0, HttpUtil.encodeURL(curFolder.getName(), true));
					sb.insert(0, StringPool.SLASH);

					if (curFolder.getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
						break;
					}
					else {
						curFolder = DLAppLocalServiceUtil.getFolder(curFolder.getParentFolderId());
					}
				}
			}

			Group group = themeDisplay.getScopeGroup();
			%>

			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav" + group.getFriendlyURL() + "/document_library" + sb.toString() %>'
			/>
		</div>
	</div>
</div>

<aui:script use="aui-dialog,aui-swf">
	if (A.SWF.isFlashVersionAtLeast(9)) {
		var uploadMultipleDocumentsIcon = A.all('.upload-multiple-documents:hidden');

		uploadMultipleDocumentsIcon.show();
	}

	var webdavAction = A.one('.<%= randomNamespace %>-webdav-action');

	if (webdavAction) {
		webdavAction.on(
			'click',
			function(event) {
				event.preventDefault();

				new A.Dialog(
					{
						bodyContent: A.one('#<%= randomNamespace %>webDav').html(),
						centered: true,
						destroyOnClose: true,
						modal: true,
						title: '<liferay-ui:message key="access-from-desktop" />',
						width: 500
					}
				).render();
			}
		);
	}
</aui:script>