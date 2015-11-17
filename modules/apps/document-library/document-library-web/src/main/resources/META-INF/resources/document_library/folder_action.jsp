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
String randomNamespace = null;

if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY)) {
	randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_folder_action") + StringPool.UNDERLINE;
}
else {
	randomNamespace = PortalUtil.generateRandomKey(request, "portlet_image_gallery_display_folder_action") + StringPool.UNDERLINE;
}

String redirect = currentURL;

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Folder folder = null;

long folderId = 0;

long repositoryId = 0;

if (row != null) {
	Object result = row.getObject();

	if (result instanceof Folder) {
		folder = (Folder)result;

		folderId = folder.getFolderId();

		repositoryId = folder.getRepositoryId();
	}
}
else {
	if (portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
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

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

boolean folderSelected = GetterUtil.getBoolean((String)request.getAttribute("view_entries.jsp-folderSelected"));

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

	showPermissionsURL = DLPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}

boolean showWhenSingleIcon = false;

DLVisualizationHelper dlVisualizationHelper = new DLVisualizationHelper(dlRequestHelper);

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);

if ((row == null) || dlVisualizationHelper.isShowWhenSingleIconActionButton()) {
	showWhenSingleIcon = true;
}

boolean view = false;

if ((row == null) && portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
	view = true;
}

String iconMenuId = null;
%>

<liferay-util:buffer var="iconMenu">
	<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">

		<%
		boolean hasViewPermission = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.VIEW);
		%>

		<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() %>">
			<c:if test="<%= hasViewPermission %>">
				<portlet:resourceURL id="/document_library/edit_folder" var="downloadURL">
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
				</portlet:resourceURL>

				<liferay-ui:icon
					message="download"
					method="get"
					url="<%= downloadURL %>"
				/>
			</c:if>

			<c:choose>
				<c:when test="<%= folder != null %>">

					<%
					boolean hasUpdatePermission = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE);
					%>

					<c:if test="<%= hasUpdatePermission && !folder.isMountPoint() %>">
						<portlet:renderURL var="editURL">
							<portlet:param name="mvcRenderCommandName" value="/document_library/edit_folder" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							message="edit"
							url="<%= editURL %>"
						/>
					</c:if>

					<c:if test="<%= hasUpdatePermission && folder.isMountPoint() %>">
						<portlet:renderURL var="editURL">
							<portlet:param name="mvcRenderCommandName" value="/document_library/edit_repository" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							message="edit"
							url="<%= editURL %>"
						/>
					</c:if>

					<c:if test="<%= hasUpdatePermission && !folder.isMountPoint() %>">
						<portlet:renderURL var="moveURL">
							<portlet:param name="mvcRenderCommandName" value="/document_library/move_entry" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="folderIds" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							message="move"
							url="<%= moveURL %>"
						/>
					</c:if>

					<c:if test="<%= showPermissionsURL %>">
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

					<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) && !folder.isMountPoint() %>">
						<portlet:renderURL var="addFolderURL">
							<portlet:param name="mvcRenderCommandName" value="/document_library/edit_folder" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							message="add-subfolder"
							url="<%= addFolderURL %>"
						/>
					</c:if>

					<c:if test="<%= folder.isMountPoint() %>">

						<%
						LocalRepository localRepository = null;

						try {
							localRepository = RepositoryProviderUtil.getLocalRepository(folder.getRepositoryId());
						}
						catch (UndeployedExternalRepositoryException uere) {
						}

						if ((localRepository != null) && localRepository.isCapabilityProvided(TemporaryFileEntriesCapability.class)) {
						%>

							<portlet:actionURL name="/document_library/edit_folder" var="deleteExpiredTemporaryFileEntriesURL">
								<portlet:param name="<%= Constants.CMD %>" value="deleteExpiredTemporaryFileEntries" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="repositoryId" value="<%= String.valueOf(folder.getRepositoryId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								message="delete-expired-temporary-files"
								url="<%= deleteExpiredTemporaryFileEntriesURL %>"
							/>

						<%
						}
						%>

					</c:if>
				</c:when>
				<c:otherwise>

					<%
					boolean workflowEnabled = WorkflowEngineManagerUtil.isDeployed() && (WorkflowHandlerRegistryUtil.getWorkflowHandler(DLFileEntry.class.getName()) != null);
					%>

					<c:if test="<%= workflowEnabled && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>">
						<portlet:renderURL var="editURL">
							<portlet:param name="mvcRenderCommandName" value="/document_library/edit_folder" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="rootFolder" value="<%= Boolean.TRUE.toString() %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							message="edit"
							url="<%= editURL %>"
						/>
					</c:if>

					<c:if test="<%= showPermissionsURL %>">
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

					<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
						<portlet:renderURL var="addFolderURL">
							<portlet:param name="mvcRenderCommandName" value="/document_library/edit_folder" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							message='<%= (folder != null) ? "add-subfolder" : "add-folder" %>'
							url="<%= addFolderURL %>"
						/>
					</c:if>

					<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_REPOSITORY) %>">
						<portlet:renderURL var="addRepositoryURL">
							<portlet:param name="mvcRenderCommandName" value="/document_library/edit_repository" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							message="add-repository"
							url="<%= addRepositoryURL %>"
						/>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:choose>
			<c:when test="<%= portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY) %>">
				<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) && ((folder == null) || !folder.isMountPoint()) %>">
					<c:if test="<%= (folder == null) || folder.isSupportsMultipleUpload() %>">
						<portlet:renderURL var="addMultipleFileEntriesURL">
							<portlet:param name="mvcPath" value="/document_library/upload_multiple_file_entries.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="backURL" value="<%= currentURL %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							cssClass="hide upload-multiple-documents"
							message='<%= portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY) ? "multiple-media" : "multiple-documents" %>'
							url="<%= addMultipleFileEntriesURL %>"
						/>
					</c:if>
				</c:if>

				<c:if test="<%= hasViewPermission && portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY) && (DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(repositoryId, folderId, status) > 0) %>">
					<liferay-ui:icon
						cssClass='<%= randomNamespace + "-slide-show" %>'
						message="view-slide-show"
						url="javascript:;"
					/>
				</c:if>

				<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() && ((folder == null) || (!folder.isMountPoint() && folder.isSupportsShortcuts())) && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_SHORTCUT) %>">
					<portlet:renderURL var="editFileShortcutURL">
						<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_shortcut" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						message="add-shortcut"
						url="<%= editFileShortcutURL %>"
					/>
				</c:if>
			</c:when>
		</c:choose>

		<c:if test="<%= hasViewPermission && portletDisplay.isWebDAVEnabled() && ((folder == null) || (folder.getRepositoryId() == scopeGroupId)) %>">

			<%
			iconMenuId = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:id"));
			%>

			<liferay-ui:icon
				cssClass='<%= randomNamespace + "-webdav-action" %>'
				message="access-from-desktop"
				url="javascript:;"
			/>
		</c:if>

		<c:if test="<%= dlPortletInstanceSettingsHelper.isShowActions() && (folder != null) %>">

			<%
			boolean hasDeletePermission = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.DELETE);
			%>

			<c:if test="<%= hasDeletePermission && !folder.isMountPoint() %>">
				<portlet:renderURL var="redirectURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
					<portlet:param name="folderId" value="<%= String.valueOf(folder.getParentFolderId()) %>" />
				</portlet:renderURL>

				<portlet:actionURL name="/document_library/edit_folder" var="deleteURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= ((folder.getModel() instanceof DLFolder) && TrashUtil.isTrashEnabled(scopeGroupId)) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= (view || folderSelected) ? redirectURL : redirect %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete trash="<%= ((folder.getModel() instanceof DLFolder) && TrashUtil.isTrashEnabled(scopeGroupId)) %>" url="<%= deleteURL %>" />
			</c:if>

			<c:if test="<%= hasDeletePermission && folder.isMountPoint() %>">
				<portlet:renderURL var="redirectURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
					<portlet:param name="folderId" value="<%= String.valueOf(folder.getParentFolderId()) %>" />
				</portlet:renderURL>

				<portlet:actionURL name="/document_library/edit_repository" var="deleteURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= (view || folderSelected) ? redirectURL : redirect %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete url="<%= deleteURL %>" />
			</c:if>
		</c:if>
	</liferay-ui:icon-menu>
</liferay-util:buffer>

<%= iconMenu %>

<div id="<%= randomNamespace %>webDav" style="display: none;">
	<div class="portlet-document-library">

		<%
		String webDavHelpMessage = null;

		if (BrowserSnifferUtil.isWindows(request)) {
			webDavHelpMessage = LanguageUtil.format(request, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"}, false);
		}
		else {
			webDavHelpMessage = LanguageUtil.format(request, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV", false);
		}
		%>

		<liferay-ui:message key="<%= webDavHelpMessage %>" />

		<br /><br />

		<aui:input cssClass="webdav-url-resource" name="webDavURL" type="resource" value="<%= DLUtil.getWebDavURL(themeDisplay, folder, null) %>" />
	</div>
</div>

<aui:script use="uploader,liferay-util-window">
	if (!A.UA.ios && (A.Uploader.TYPE != 'none')) {
		var uploadMultipleDocumentsIcon = A.all('.upload-multiple-documents:hidden');

		uploadMultipleDocumentsIcon.show();
	}

	var slideShow = A.one('.<%= randomNamespace %>-slide-show');

	if (slideShow) {
		slideShow.on(
			'click',
			function(event) {
				<portlet:renderURL var="viewSlideShowURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="/image_gallery_display/view_slide_show" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</portlet:renderURL>

				var slideShowWindow = window.open('<%= viewSlideShowURL %>', 'slideShow', 'directories=no,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no');

				slideShowWindow.focus();
			}
		);
	}

	var webdavAction = A.one('.<%= randomNamespace %>-webdav-action');

	if (webdavAction) {
		webdavAction.on(
			'click',
			function(event) {
				event.preventDefault();

				var webdavDialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: A.one('#<%= randomNamespace %>webDav').html(),
							destroyOnHide: true
						},
						title: '<%= UnicodeLanguageUtil.get(request, "access-from-desktop") %>'
					}
				);

				webdavDialog.after(
					'render',
					function(event) {
						var webdavURLInput = webdavDialog.get('boundingBox').one('.webdav-url-resource');

						webdavURLInput.focus();
					}
				);

				webdavDialog.on(
					'close',
					function(event) {
						var trigger = A.one('#<portlet:namespace /><%= iconMenuId %>Button');

						if (trigger) {
							trigger.focus();
						}
					}
				);

				webdavDialog.render();
			}
		);
	}
</aui:script>