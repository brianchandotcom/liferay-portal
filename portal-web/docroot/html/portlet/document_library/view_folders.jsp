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

long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

Folder parentFolder = null;

boolean showRootFolder = ParamUtil.getBoolean(request, "showRootFolder", true);
boolean showSiblings = ParamUtil.getBoolean(request, "showSiblings");

if (showSiblings) {
	if (folder != null) {
		parentFolderId = folder.getParentFolderId();

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			try {
				parentFolder = DLAppLocalServiceUtil.getFolder(folderId);
			}
			catch (NoSuchFolderException nsfe) {
				parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}

			repositoryId = parentFolder.getRepositoryId();
		}
		else {
			repositoryId = scopeGroupId;
		}
	}
}
else {
	parentFolderId = folderId;
}

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", entriesPerPage);

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

List<Folder> folders = DLAppServiceUtil.getFolders(repositoryId, parentFolderId, false, folderStart, folderEnd);
List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(repositoryId, parentFolderId, folderStart, folderEnd);

int total = 0;

if ((folderId != rootFolderId) || !showRootFolder) {
	total = DLAppServiceUtil.getFoldersCount(repositoryId, parentFolderId, false);
}

request.setAttribute("view_folders.jsp-total", String.valueOf(total));

List<Folder> ancestorFolders = new ArrayList();

if (folder != null) {
	ancestorFolders = folder.getAncestors();
}

boolean refreshFolders = ParamUtil.getBoolean(request, "refreshFolders");
%>

<div class="lfr-header-row">
	<div class="lfr-header-row-content" id="<portlet:namespace />parentFolderTitleContainer">
		<div class="parent-folder-title" id="<portlet:namespace />parentFolderTitle">
			<c:choose>
				<c:when test="<%= ((folderId != rootFolderId) && (parentFolderId == rootFolderId)) || !showRootFolder %>">
					<span>
						<liferay-ui:message key="documents-home" />
					</span>
				</c:when>
				<c:when test="<%= (folderId != rootFolderId) && (parentFolderId != rootFolderId) %>">

					<span>
						<%= DLAppLocalServiceUtil.getFolder(parentFolderId).getName() %>
					</span>
				</c:when>
			</c:choose>
		</div>
	</div>
</div>

<div class="portlet-msg-error aui-helper-hidden" id="<portlet:namespace />errorContainer">
	<liferay-ui:message key="your-request-failed-to-complete" />
</div>

<div class="body-row">
	<div id="<portlet:namespace />listViewContainer">
		<div class="folder-display-style lfr-list-view-content" <%= refreshFolders ? "data-refresh-folders=\"true\"" : StringPool.BLANK %> id="<portlet:namespace />folderContainer">
			<ul class="lfr-component">
				<c:choose>
					<c:when test="<%= ((folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID && showRootFolder)) || ((folderId == rootFolderId) && (showRootFolder)) %>">

						<%
						int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId);
						%>

						<liferay-portlet:renderURL varImpl="viewDocumentsHomeURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						</liferay-portlet:renderURL>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewDocumentsHomeFoldersURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="showRootFolder" value="<%= Boolean.FALSE.toString() %>" />
							<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
						</liferay-portlet:resourceURL>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewDocumentsHomeEntriesURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:resourceURL>

						<%
						String navigation = ParamUtil.getString(request, "navigation", "documents-home");

						request.setAttribute("view_entries.jsp-folderId", String.valueOf(folderId));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(repositoryId));
						%>

						<li class="folder <%= (navigation.equals("documents-home") && (fileEntryTypeId == -1)) ? "selected" : StringPool.BLANK %>">
							<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />

							<c:if test="<%= (foldersCount > 0) %>">
								<a class="expand-folder" data-folder-id="<%= folderId %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewDocumentsHomeFoldersURL.toString() %>" data-show-root-folder="<%= Boolean.FALSE.toString() %>" data-show-siblings="<%= ((folderId == rootFolderId) && (showRootFolder)) ? Boolean.FALSE.toString() : Boolean.TRUE.toString() %>" href="<%= viewDocumentsHomeURL.toString() %>">
									<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" message="" />
								</a>
							</c:if>

							<a class="browse-folder" data-folder-id="<%= folderId %>" data-navigation="documents-home" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewDocumentsHomeEntriesURL.toString() %>" data-show-siblings="<%= ((folderId == rootFolderId) && (showRootFolder)) ? Boolean.FALSE.toString() : Boolean.TRUE.toString() %>" href="<%= viewDocumentsHomeURL.toString() %>">
								<liferay-ui:icon image="../aui/home" message="" />

								<span class="entry-title">
									<%= LanguageUtil.get(pageContext, "documents-home") %>
								</span>
							</a>
						</li>

						<c:if test="<%= rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
							<liferay-portlet:renderURL varImpl="viewRecentDocumentsURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							</liferay-portlet:renderURL>

							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewRecentDocumentsEntriesURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="navigation" value="recent-documents" />
								<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:resourceURL>

							<li class="folder <%= navigation.equals("recent-documents") ? "selected" : StringPool.BLANK %>">
								<a class="browse-folder" data-navigation="recent-documents" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewRecentDocumentsEntriesURL.toString() %>" href="<%= viewRecentDocumentsURL.toString() %>">
									<liferay-ui:icon image="../aui/clock" message="" />

									<span class="entry-title">
										<%= LanguageUtil.get(pageContext, "recent-documents") %>
									</span>
								</a>
							</li>

							<liferay-portlet:renderURL varImpl="viewMyDocumentsURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							</liferay-portlet:renderURL>

							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewMyDocumentsEntriesURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="navigation" value="my-documents" />
								<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:resourceURL>

							<li class="folder <%= navigation.equals("my-documents") ? "selected" : StringPool.BLANK %>">
								<a class="browse-folder" data-navigation="my-documents" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewMyDocumentsEntriesURL.toString() %>" href="<%= viewMyDocumentsURL.toString() %>">
									<liferay-ui:icon image="../aui/person" message="" />

									<span class="entry-title">
										<%= LanguageUtil.get(pageContext, "my-documents") %>
									</span>
								</a>
							</li>

							<%
							List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeServiceUtil.getFileEntryTypes(DLUtil.getGroupIds(themeDisplay), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
							%>

							<c:if test="<%= !fileEntryTypes.isEmpty() %>">
								<liferay-portlet:renderURL varImpl="viewBasicFileEntryTypeURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(0) %>" />
								</liferay-portlet:renderURL>

								<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewBasicFileEntryTypeEntriesURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(0) %>" />
									<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="entryStart" value="0" />
									<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
									<portlet:param name="folderStart" value="0" />
									<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
								</liferay-portlet:resourceURL>

								<li class="folder file-entry-type <%= (fileEntryTypeId == 0) ? "selected" : StringPool.BLANK %>">
									<a class="browse-folder" data-file-entry-type-id="<%= String.valueOf(0) %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewBasicFileEntryTypeEntriesURL.toString() %>" href="<%= viewBasicFileEntryTypeURL.toString() %>">
										<liferay-ui:icon image="copy" message="" />

										<span class="entry-title">
											<%= LanguageUtil.get(pageContext, "basic-document") %>
										</span>
									</a>
								</li>
							</c:if>

							<%
							for (DLFileEntryType fileEntryType : fileEntryTypes) {
							%>

								<liferay-portlet:renderURL varImpl="viewFileEntryTypeURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
								</liferay-portlet:renderURL>

								<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewFileEntryTypeEntriesURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
									<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="entryStart" value="0" />
									<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
									<portlet:param name="folderStart" value="0" />
									<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
								</liferay-portlet:resourceURL>

								<li class="folder file-entry-type <%= (fileEntryTypeId == fileEntryType.getFileEntryTypeId()) ? "selected" : StringPool.BLANK %>">
									<a class="browse-folder" data-file-entry-type-id="<%= fileEntryType.getFileEntryTypeId() %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewFileEntryTypeEntriesURL.toString() %>" href="<%= viewFileEntryTypeURL.toString() %>">
										<liferay-ui:icon message="" image="copy" />

										<span class="entry-title">
											<%= fileEntryType.getName() %>
										</span>
									</a>
								</li>

							<%
							}

							for (Folder mountFolder : mountFolders) {
								int mountFoldersCount = 0;

								request.setAttribute("view_entries.jsp-folder", mountFolder);
								request.setAttribute("view_entries.jsp-folderId", String.valueOf(mountFolder.getFolderId()));
								request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(mountFolder.getRepositoryId()));
							%>

								<liferay-portlet:renderURL varImpl="viewURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="folderId" value="<%= String.valueOf(mountFolder.getFolderId()) %>" />
								</liferay-portlet:renderURL>

								<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewFoldersURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="folderId" value="<%= String.valueOf(mountFolder.getFolderId()) %>" />
									<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
								</liferay-portlet:resourceURL>

								<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewEntriesURL">
									<portlet:param name="struts_action" value="/document_library/view" />
									<portlet:param name="folderId" value="<%= String.valueOf(mountFolder.getFolderId()) %>" />
									<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="entryStart" value="0" />
									<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
									<portlet:param name="folderStart" value="0" />
									<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
								</liferay-portlet:resourceURL>

								<li class="folder <%= (mountFolder.getFolderId() == folderId) ? "selected" : StringPool.BLANK %>">
									<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />

									<c:if test="<%= mountFoldersCount > 0 %>">
										<a class="expand-folder" data-folder-id="<%= String.valueOf(mountFolder.getFolderId()) %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewFoldersURL.toString() %>" href="<%= viewURL.toString() %>">
											<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" />
										</a>
									</c:if>

									<a class="browse-folder" data-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(mountFolder.getFolderId()) %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>" href="<%= viewURL.toString() %>">
										<liferay-ui:icon image="drive" />

										<span class="entry-title">
											<%= mountFolder.getName() %>
										</span>
									</a>
								</li>

							<%
							}
							%>

						</c:if>
					</c:when>
					<c:otherwise>
						<liferay-portlet:renderURL varImpl="viewURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						</liferay-portlet:renderURL>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewFoldersURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
						</liferay-portlet:resourceURL>

						<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewEntriesURL">
							<portlet:param name="struts_action" value="/document_library/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:resourceURL>

						<li class="folder">
							<a class="expand-folder" data-direction-right="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(parentFolderId) %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewFoldersURL.toString() %>" data-show-siblings="<%= Boolean.TRUE.toString() %>" href="<%= viewURL.toString() %>">
								<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-l" />
							</a>

							<a class="browse-folder" data-folder-id="<%= String.valueOf(parentFolderId) %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>" data-show-siblings="<%= Boolean.TRUE.toString() %>" href="<%= viewURL.toString() %>">
								<liferay-ui:icon src='<%= themeDisplay.getPathThemeImages() + "/arrows/01_up.png" %>' />

								<%= LanguageUtil.get(pageContext, "up") %>
							</a>
						</li>

						<%
						for (Folder curFolder : folders) {
							int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, curFolder.getFolderId());
							int fileEntriesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(repositoryId, curFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED);

							request.setAttribute("view_entries.jsp-folder", curFolder);
							request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
							request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));
							request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));
						%>

							<liferay-portlet:renderURL varImpl="viewURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
							</liferay-portlet:renderURL>

							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewFoldersURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
								<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
							</liferay-portlet:resourceURL>

							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewEntriesURL">
								<portlet:param name="struts_action" value="/document_library/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
								<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:resourceURL>

							<li class="folder <%= (curFolder.getFolderId() == folderId) ? "selected" : StringPool.BLANK %>">
								<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />

								<c:if test="<%= foldersCount > 0 %>">
									<a class="expand-folder" data-folder-id="<%= String.valueOf(curFolder.getFolderId()) %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewFoldersURL.toString() %>" data-show-siblings="<%= Boolean.FALSE.toString() %>" href="<%= viewURL.toString() %>">
										<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" />
									</a>
								</c:if>

								<a class="browse-folder" data-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(curFolder.getFolderId()) %>" data-refresh-entries="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>" data-show-siblings="<%= Boolean.TRUE.toString() %>" href="<%= viewURL.toString() %>">
									<c:choose>
										<c:when test="<%= (foldersCount + fileEntriesCount) > 0 %>">
											<liferay-ui:icon image="folder_full_document" />
										</c:when>
										<c:otherwise>
											<liferay-ui:icon image="folder_empty" />
										</c:otherwise>
									</c:choose>

									<span class="entry-title">
										<%= curFolder.getName() %>
									</span>
								</a>
							</li>

						<%
						}
						%>

					</c:otherwise>
				</c:choose>
			</ul>

			<aui:script>
				Liferay.fire(
					'<portlet:namespace />pageLoaded',
					{
						paginator: {
							name: 'folderPaginator',
							state: {
								page: <%= folderEnd / (folderEnd - folderStart) %>,
								rowsPerPage: <%= (folderEnd - folderStart) %>,
								total: <%= total %>
							}
						}
					}
				);
			</aui:script>
		</div>
	</div>
</div>