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

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

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

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
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
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= folders.size() + fileEntries.size() %>" key="x-items-selected" /></h4>
		</div>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><strong><liferay-ui:message arguments="<%= folders.size() + fileEntries.size() %>" key="x-items-selected" /></strong></h5>
		</div>
	</c:otherwise>
</c:choose>