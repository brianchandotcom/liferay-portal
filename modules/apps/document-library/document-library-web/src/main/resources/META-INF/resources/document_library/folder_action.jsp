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
FolderActionDisplayContext folderActionDisplayContext = new FolderActionDisplayContext(request);

Folder folder = folderActionDisplayContext.getFolder();

long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

if (folder != null) {
	folderId = folder.getFolderId();
}

long repositoryId = folderActionDisplayContext.getRepositoryId();
%>

<c:if test="<%= folderActionDisplayContext.isShowActions() %>">
	<liferay-ui:icon-menu
		direction="left-side"
		icon="<%= StringPool.BLANK %>"
		markupView="lexicon"
		message='<%= LanguageUtil.get(request, "actions") %>'
		showWhenSingleIcon="<%= true %>"
	>
		<c:if test="<%= folderActionDisplayContext.isDownloadFolderActionVisible() %>">
			<portlet:resourceURL id="/document_library/download_folder" var="downloadURL">
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
				<c:if test="<%= folderActionDisplayContext.hasUpdatePermission() %>">
					<c:choose>
						<c:when test="<%= !folder.isMountPoint() && !folder.isRoot() %>">

							<%
							PortletURL editURL = PortalUtil.getControlPanelPortletURL(request, themeDisplay.getScopeGroup(), DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0, PortletRequest.RENDER_PHASE);

							editURL.setParameter("mvcRenderCommandName", "/document_library/edit_folder");
							editURL.setParameter("redirect", folderActionDisplayContext.getRedirect());
							editURL.setParameter("folderId", String.valueOf(folderId));
							editURL.setParameter("repositoryId", String.valueOf(repositoryId));
							%>

							<liferay-ui:icon
								message="edit"
								url="<%= editURL.toString() %>"
							/>
						</c:when>
						<c:otherwise>

							<%
							PortletURL editURL = PortalUtil.getControlPanelPortletURL(request, themeDisplay.getScopeGroup(), DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0, PortletRequest.RENDER_PHASE);

							editURL.setParameter("mvcRenderCommandName", "/document_library/edit_repository");
							editURL.setParameter("redirect", folderActionDisplayContext.getRedirect());
							editURL.setParameter("folderId", String.valueOf(folderId));
							editURL.setParameter("repositoryId", String.valueOf(repositoryId));
							%>

							<liferay-ui:icon
								message="edit"
								url="<%= editURL.toString() %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:if>

				<c:if test="<%= folderActionDisplayContext.isMoveFolderActionVisible() %>">
					<liferay-ui:icon
						message="move"
						url='<%= "javascript:" + liferayPortletResponse.getNamespace() + "move(1, 'rowIdsFolder', " + folderId + ");" %>'
					/>
				</c:if>

				<c:if test="<%= folderActionDisplayContext.isDeleteExpiredTemporaryFileEntriesActionVisible() %>">
					<portlet:actionURL name="/document_library/edit_folder" var="deleteExpiredTemporaryFileEntriesURL">
						<portlet:param name="<%= Constants.CMD %>" value="deleteExpiredTemporaryFileEntries" />
						<portlet:param name="redirect" value="<%= folderActionDisplayContext.getRedirect() %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(folder.getRepositoryId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						message="delete-expired-temporary-files"
						url="<%= deleteExpiredTemporaryFileEntriesURL %>"
					/>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="<%= folderActionDisplayContext.isEditFolderActionVisible() %>">
					<portlet:renderURL var="editURL">
						<portlet:param name="mvcRenderCommandName" value="/document_library/edit_folder" />
						<portlet:param name="redirect" value="<%= folderActionDisplayContext.getRedirect() %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						<portlet:param name="rootFolder" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						message="edit"
						url="<%= editURL %>"
					/>
				</c:if>

				<c:if test="<%= folderActionDisplayContext.isAddFolderActionVisible() %>">
					<portlet:renderURL var="addFolderURL">
						<portlet:param name="mvcRenderCommandName" value="/document_library/edit_folder" />
						<portlet:param name="redirect" value="<%= folderActionDisplayContext.getRedirect() %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						message="add-folder"
						url="<%= addFolderURL %>"
					/>
				</c:if>

				<c:if test="<%= folderActionDisplayContext.isAddRepositoryActionVisible() %>">
					<portlet:renderURL var="addRepositoryURL">
						<portlet:param name="mvcRenderCommandName" value="/document_library/edit_repository" />
						<portlet:param name="redirect" value="<%= folderActionDisplayContext.getRedirect() %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						message="add-repository"
						url="<%= addRepositoryURL %>"
					/>
				</c:if>
			</c:otherwise>
		</c:choose>

		<c:if test="<%= portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY) %>">
			<c:if test="<%= folderActionDisplayContext.isAddMediaActionVisible() %>">
				<portlet:renderURL var="editFileEntryURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_entry" />
					<portlet:param name="redirect" value="<%= folderActionDisplayContext.getRedirect() %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					message="add-file-entry"
					url="<%= editFileEntryURL %>"
				/>

				<c:if test="<%= folderActionDisplayContext.isMultipleUploadSupported() %>">
					<portlet:renderURL var="addMultipleFileEntriesURL">
						<portlet:param name="mvcRenderCommandName" value="/document_library/upload_multiple_file_entries" />
						<portlet:param name="redirect" value="<%= folderActionDisplayContext.getRedirect() %>" />
						<portlet:param name="backURL" value="<%= folderActionDisplayContext.getRedirect() %>" />
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

			<c:if test="<%= folderActionDisplayContext.isViewSlideShowActionVisible() %>">
				<liferay-ui:icon
					cssClass='<%= folderActionDisplayContext.getRandomNamespace() + "-slide-show" %>'
					message="view-slide-show"
					url="javascript:;"
				/>
			</c:if>

			<c:if test="<%= folderActionDisplayContext.isAddFileShortcutActionVisible() %>">
				<portlet:renderURL var="editFileShortcutURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_shortcut" />
					<portlet:param name="redirect" value="<%= folderActionDisplayContext.getRedirect() %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					message="add-shortcut"
					url="<%= editFileShortcutURL %>"
				/>
			</c:if>
		</c:if>

		<c:if test="<%= folderActionDisplayContext.isAccessFromDesktopActionVisible() %>">
			<liferay-util:include page="/document_library/access_from_desktop.jsp" servletContext="<%= application %>" />
		</c:if>

		<c:if test="<%= folderActionDisplayContext.isPermissionsActionVisible() %>">
			<liferay-security:permissionsURL
				modelResource="<%= folderActionDisplayContext.getModelResource() %>"
				modelResourceDescription="<%= HtmlUtil.escape(folderActionDisplayContext.getModelResourceDescription()) %>"
				resourcePrimKey="<%= String.valueOf(folderActionDisplayContext.getResourcePrimKey()) %>"
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

		<c:if test="<%= folderActionDisplayContext.isDeleteFolderActionVisible() %>">

			<%
			String mvcRenderCommandName = "/image_gallery_display/view";

			if (!portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY)) {
				mvcRenderCommandName = "/document_library/view";

				if (folder.getParentFolderId() != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
					mvcRenderCommandName = "/document_library/view_folder";
				}
			}
			%>

			<c:choose>
				<c:when test="<%= !folder.isMountPoint() && !folder.isRoot() %>">
					<portlet:renderURL var="redirectURL">
						<portlet:param name="mvcRenderCommandName" value="<%= mvcRenderCommandName %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folder.getParentFolderId()) %>" />
					</portlet:renderURL>

					<portlet:actionURL name="/document_library/edit_folder" var="deleteURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= (folder.isRepositoryCapabilityProvided(TrashCapability.class) && dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId)) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
						<portlet:param name="redirect" value="<%= (folderActionDisplayContext.isView() || folderActionDisplayContext.isFolderSelected()) ? redirectURL : folderActionDisplayContext.getRedirect() %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete
						trash="<%= folder.isRepositoryCapabilityProvided(TrashCapability.class) && dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) %>"
						url="<%= deleteURL %>"
					/>
				</c:when>
				<c:otherwise>

					<%
					long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

					if (!folder.isRoot()) {
						parentFolderId = folder.getParentFolderId();
					}
					%>

					<portlet:renderURL var="redirectURL">
						<portlet:param name="mvcRenderCommandName" value="<%= mvcRenderCommandName %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
					</portlet:renderURL>

					<portlet:actionURL name="/document_library/edit_repository" var="deleteURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
						<portlet:param name="redirect" value="<%= (folderActionDisplayContext.isView() || folderActionDisplayContext.isFolderSelected()) ? redirectURL : folderActionDisplayContext.getRedirect() %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete
						url="<%= deleteURL %>"
					/>
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:if test="<%= folderActionDisplayContext.isPublishFolderActionVisible() %>">
			<portlet:actionURL name="/document_library/publish_folder" var="publishFolderURL">
				<portlet:param name="backURL" value="<%= folderActionDisplayContext.getRedirect() %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				confirmation="are-you-sure-you-want-to-publish-the-selected-folder"
				message="publish-to-live"
				url="<%= publishFolderURL %>"
			/>
		</c:if>
	</liferay-ui:icon-menu>

	<aui:script use="uploader">
		if (!A.UA.ios && A.Uploader.TYPE != 'none') {
			var uploadMultipleDocumentsIcon = A.all(
				'.upload-multiple-documents:hidden'
			);

			uploadMultipleDocumentsIcon.show();
		}

		var slideShow = A.one(
			'.<%= folderActionDisplayContext.getRandomNamespace() %>-slide-show'
		);

		if (slideShow) {
			slideShow.on('click', function(event) {
				<portlet:renderURL var="viewSlideShowURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="/image_gallery_display/view_slide_show" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</portlet:renderURL>

				var slideShowWindow = window.open(
					'<%= viewSlideShowURL %>',
					'slideShow',
					'directories=no,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no'
				);

				slideShowWindow.focus();
			});
		}
	</aui:script>
</c:if>