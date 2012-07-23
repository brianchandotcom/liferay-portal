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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

long newFolderId = ParamUtil.getLong(request, "newFolderId");

String fileShortcutIds = ParamUtil.getString(request, "fileShortcutIds");

List<Folder> folders = (List<Folder>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDERS);

List<Folder> invalidMoveFolders = new ArrayList<Folder>();
List<Folder> validMoveFolders = new ArrayList<Folder>();

for (Folder curFolder : folders) {
	boolean movePermission = DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE) && (!curFolder.isLocked() || curFolder.hasLock());

	if (movePermission) {
		validMoveFolders.add(curFolder);
	}
	else {
		invalidMoveFolders.add(curFolder);
	}
}

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

List<FileEntry> fileEntries = null;

if (fileEntry != null) {
	fileEntries = new ArrayList<FileEntry>();

	fileEntries.add(fileEntry);
}
else {
	fileEntries = (List<FileEntry>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRIES);
}

List<FileEntry> validMoveFileEntries = new ArrayList<FileEntry>();
List<FileEntry> invalidMoveFileEntries = new ArrayList<FileEntry>();

for (FileEntry curFileEntry : fileEntries) {
	boolean movePermission = DLFileEntryPermission.contains(permissionChecker, curFileEntry, ActionKeys.UPDATE) && (!curFileEntry.isCheckedOut() || curFileEntry.hasLock());

	if (movePermission) {
		validMoveFileEntries.add(curFileEntry);
	}
	else {
		invalidMoveFileEntries.add(curFileEntry);
	}
}

List<DLFileShortcut> fileShortcuts = (List<DLFileShortcut>)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_SHORTCUTS);

List<DLFileShortcut> invalidShortcutEntries = new ArrayList<DLFileShortcut>();
List<DLFileShortcut> validShortcutEntries = new ArrayList<DLFileShortcut>();

for (DLFileShortcut curFileShortcut : fileShortcuts) {
	boolean movePermission = DLFileShortcutPermission.contains(permissionChecker, curFileShortcut, ActionKeys.UPDATE);

	if (movePermission) {
		validShortcutEntries.add(curFileShortcut);
	}
	else {
		invalidShortcutEntries.add(curFileShortcut);
	}
}
%>

<c:if test="<%= Validator.isNull(referringPortletResource) %>">
	<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />
</c:if>

<portlet:actionURL var="moveFileEntryURL">
	<portlet:param name="struts_action" value="/document_library/move_entry" />
</portlet:actionURL>

<aui:form action="<%= moveFileEntryURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFileEntry(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="newFolderId" type="hidden" value="<%= newFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title="move-files"
	/>

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<c:if test="<%= !validMoveFolders.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-folders-ready-to-be-moved", validMoveFolders.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="lfr-component">

				<%
				for (Folder folder : validMoveFolders) {
				%>

					<li class="move-folder">
						<span class="folder-title">
							<%= folder.getName() %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<c:if test="<%= !invalidMoveFolders.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-folders-cannot-be-moved", invalidMoveFolders.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="lfr-component">

				<%
				for (Folder folder : invalidMoveFolders) {
				%>

					<li class="move-folder move-error">
						<span class="folder-title">
							<%= folder.getName() %>
						</span>

						<span class="error-message">
							<c:choose>
								<c:when test="<%= folder.isLocked() && !folder.hasLock() %>">
									<%= LanguageUtil.get(pageContext, "you-cannot-modify-this-folder-because-it-was-locked") %>
								</c:when>
								<c:otherwise>
									<%= LanguageUtil.get(pageContext, "you-do-not-have-the-required-permissions") %>
								</c:otherwise>
							</c:choose>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<aui:input name="folderIds" type="hidden" value="<%= ListUtil.toString(validMoveFolders, Folder.FOLDER_ID_ACCESSOR) %>" />

	<c:if test="<%= !validMoveFileEntries.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-files-ready-to-be-moved", validMoveFileEntries.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="lfr-component">

				<%
				for (FileEntry validMoveFileEntry : validMoveFileEntries) {
				%>

					<li class="move-file">
						<span class="file-title" title="<%= validMoveFileEntry.getTitle() %>">
							<%= validMoveFileEntry.getTitle() %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<c:if test="<%= !invalidMoveFileEntries.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-files-cannot-be-moved", invalidMoveFileEntries.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="lfr-component">

				<%
				for (FileEntry invalidMoveFileEntry : invalidMoveFileEntries) {
					Lock lock = invalidMoveFileEntry.getLock();
				%>

					<li class="move-file move-error">
						<span class="file-title" title="<%= invalidMoveFileEntry.getTitle() %>">
							<%= invalidMoveFileEntry.getTitle() %>
						</span>

						<span class="error-message">
							<c:choose>
								<c:when test="<%= invalidMoveFileEntry.isCheckedOut() && !invalidMoveFileEntry.hasLock() %>">
									<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-checked-out-by-x-on-x", new Object[] {HtmlUtil.escape(PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId()))), dateFormatDateTime.format(lock.getCreateDate())}, false) %>
								</c:when>
								<c:otherwise>
									<%= LanguageUtil.get(pageContext, "you-do-not-have-the-required-permissions") %>
								</c:otherwise>
							</c:choose>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<aui:input name="fileEntryIds" type="hidden" value="<%= ListUtil.toString(validMoveFileEntries, FileEntry.FILE_ENTRY_ID_ACCESSOR) %>" />

	<c:if test="<%= !validShortcutEntries.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-shortcuts-ready-to-be-moved", validShortcutEntries.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="lfr-component">

				<%
				for (DLFileShortcut fileShortcut : validShortcutEntries) {
				%>

					<li class="move-file">
						<span class="file-title">
							<%= fileShortcut.getToTitle() + " (" + LanguageUtil.get(themeDisplay.getLocale(), "shortcut") + ")" %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<c:if test="<%= !invalidShortcutEntries.isEmpty() %>">
		<div class="move-list-info">
			<h4><%= LanguageUtil.format(pageContext, "x-shortcuts-cannot-be-moved", invalidShortcutEntries.size()) %></h4>
		</div>

		<div class="move-list">
			<ul class="lfr-component">

				<%
				for (DLFileShortcut fileShortcut : invalidShortcutEntries) {
				%>

					<li class="move-file move-error">
						<span class="file-title">
							<%= fileShortcut.getToTitle() + " (" + LanguageUtil.get(themeDisplay.getLocale(), "shortcut") + ")" %>
						</span>

						<span class="error-message">
							<%= LanguageUtil.get(pageContext, "you-do-not-have-the-required-permissions") %>
						</span>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</c:if>

	<aui:input name="fileShortcutIds" type="hidden" value="<%= fileShortcutIds %>" />

	<aui:fieldset>

		<%
		String folderName = StringPool.BLANK;

		if (newFolderId > 0) {
			Folder folder = DLAppLocalServiceUtil.getFolder(newFolderId);

			folder = folder.toEscapedModel();

			folderName = folder.getName();
		}
		else {
			folderName = LanguageUtil.get(pageContext, "home");
		}
		%>

		<portlet:renderURL var="viewFolderURL">
			<portlet:param name="struts_action" value="/document_library/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(newFolderId) %>" />
		</portlet:renderURL>

		<aui:field-wrapper label="new-folder">
			<aui:a href="<%= viewFolderURL %>" id="folderName"><%= folderName %></aui:a>

			<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/document_library/select_folder" />
				<portlet:param name="folderId" value="<%= String.valueOf(newFolderId) %>" />
			</portlet:renderURL>

			<%
			String taglibOpenFolderWindow = "var folderWindow = window.open('" + selectFolderURL + "','folder', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680'); void(''); folderWindow.focus();";
			%>

			<aui:button onClick="<%= taglibOpenFolderWindow %>" value="select" />
		</aui:field-wrapper>

		<aui:button-row>
			<aui:button type="submit" value="move" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />saveFileEntry() {
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectFolder(folderId, folderName) {
		var folderData = {
			idString: 'newFolderId',
			idValue: folderId,
			nameString: 'folderName',
			nameValue: folderName
		};

		Liferay.Util.selectFolder(folderData, '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/view" /></portlet:renderURL>', '<portlet:namespace />');
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />file);
	</c:if>
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move-files"), currentURL);
%>