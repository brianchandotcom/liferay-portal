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
long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"), ParamUtil.getLong(request, "repositoryId"));

List<Folder> folders = (List<Folder>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDERS);
List<FileEntry> fileEntries = (List<FileEntry>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES);

if (ListUtil.isEmpty(folders) && ListUtil.isEmpty(fileEntries)) {
	long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"), ParamUtil.getLong(request, "folderId"));

	folders = new ArrayList<Folder>();

	Folder folder = (Folder)request.getAttribute("view.jsp-folder");

	if (folder != null) {
		folders.add(folder);
	}
	else if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		folders.add(DLAppLocalServiceUtil.getFolder(folderId));
	}
	else {
		folders.add(null);
	}
}
%>

<c:choose>
	<c:when test="<%= (ListUtil.isEmpty(fileEntries) && ListUtil.isNotEmpty(folders) && (folders.size() == 1)) %>">

		<%
		Folder folder = folders.get(0);

		request.setAttribute("info_panel.jsp-folder", folder);
		%>

		<div class="sidebar-header">
			<ul class="list-inline list-unstyled sidebar-header-actions">
				<li>
					<liferay-util:include page="/document_library/subscribe.jsp" servletContext="<%= application %>" />
				</li>

				<li>
					<liferay-util:include page="/document_library/folder_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= (folder != null) ? folder.getName() : LanguageUtil.get(request, "home") %></h4>

			<div>
				<liferay-ui:message key="folder" />
			</div>
		</div>

		<liferay-ui:tabs names="details" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">
					<h5><strong><liferay-ui:message key="num-of-items" /></strong></h5>

					<%
					long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

					if (folder != null) {
						folderId = folder.getFolderId();
					}
					%>

					<p>
						<%= DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, WorkflowConstants.STATUS_APPROVED, true) %>
					</p>

					<c:if test="<%= folder != null %>">
						<h5><strong><liferay-ui:message key="created" /></strong></h5>

						<p>
							<%= HtmlUtil.escape(folder.getUserName()) %>
						</p>
					</c:if>
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:when test="<%= ListUtil.isEmpty(folders) && ListUtil.isNotEmpty(fileEntries) && (fileEntries.size() == 1) %>">

		<%
		FileEntry fileEntry = fileEntries.get(0);

		request.setAttribute("info_panel.jsp-fileEntry", fileEntry);
		%>

		<div class="sidebar-header">
			<ul class="list-inline list-unstyled sidebar-header-actions">
				<li>
					<liferay-util:include page="/document_library/file_entry_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= fileEntry.getTitle() %></h4>

			<div>
				<liferay-ui:message key="document" />
			</div>
		</div>

		<liferay-ui:tabs names="details,versions" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">

					<%
					FileVersion fileVersion = null;

					if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
						fileVersion = fileEntry.getLatestFileVersion();
					}
					else {
						fileVersion = fileEntry.getFileVersion();
					}
					%>

					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="crop-image" />" class="img-rounded" src="<%= DLUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay) %>" />

					<aui:button href="<%= DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>" value="download" />

					<aui:input name="url" type="resource" value="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true) %>" />

					<c:if test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() %>">

							<%
							String webDavHelpMessage = null;

							if (BrowserSnifferUtil.isWindows(request)) {
								webDavHelpMessage = LanguageUtil.format(request, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"}, false);
							}
							else {
								webDavHelpMessage = LanguageUtil.format(request, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV", false);
							}
							%>

						<aui:input helpMessage="<%= webDavHelpMessage %>" name="webDavURL"  type="resource" value="<%= DLUtil.getWebDavURL(themeDisplay, fileEntry.getFolder(), fileEntry) %>" />
					</c:if>

					<h5><strong><liferay-ui:message key="created" /></strong></h5>

					<p>
						<%= HtmlUtil.escape(fileEntry.getUserName()) %>
					</p>

					<c:if test="<%= Validator.isNotNull(fileEntry.getDescription()) %>">
						<h5><strong><liferay-ui:message key="description" /></strong></h5>

						<p>
							<%= fileEntry.getDescription() %>
						</p>
					</c:if>

					<h5><strong><liferay-ui:message key="size" /></strong></h5>

					<p>
						<%= HtmlUtil.escape(TextFormatter.formatStorageSize(fileEntry.getSize(), locale)) %>
					</p>

					<h5><strong><liferay-ui:message key="version" /></strong></h5>

					<p>
						<%= HtmlUtil.escape(fileVersion.getVersion()) %>
					</p>
				</div>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="sidebar-body">

					<%
					int status = WorkflowConstants.STATUS_APPROVED;

					if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
						status = WorkflowConstants.STATUS_ANY;
					}

					List<FileVersion> fileVersions = fileEntry.getFileVersions(status);

					for (FileVersion fileVersion : fileVersions) {
						request.setAttribute("info_panel.jsp-fileVersion", fileVersion);
					%>

						<div>
							<ul class="list-inline list-unstyled sidebar-header-actions">
								<li>
									<liferay-util:include page="/document_library/file_entry_history_action.jsp" servletContext="<%= application %>" />
								</li>
							</ul>

							<h4><liferay-ui:message arguments="<%= fileVersion.getVersion() %>" key="version-x" /></h4>

							<p>
								<c:choose>
									<c:when test="<%= Validator.isNull(fileVersion.getChangeLog()) %>">
										<small class="text-muted">
											<liferay-ui:message key="no-change-log" />
										</small>
									</c:when>
									<c:otherwise>
										<%= fileVersion.getChangeLog() %>
									</c:otherwise>
								</c:choose>
							</p>
						</div>

					<%
					}
					%>

				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= folders.size() + fileEntries.size() %>" key="x-items-selected" /></h4>
		</div>

		<liferay-ui:tabs names="details" refresh="<%= false %>" type="dropdown">
			<liferay-ui:section>
				<div class="sidebar-body">
					<h5><strong><liferay-ui:message arguments="<%= folders.size() + fileEntries.size() %>" key="x-items-selected" /></strong></h5>
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</c:otherwise>
</c:choose>