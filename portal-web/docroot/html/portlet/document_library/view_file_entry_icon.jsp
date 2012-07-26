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
FileEntry fileEntry = (FileEntry)request.getAttribute("view_entries.jsp-fileEntry");

FileVersion latestFileVersion = fileEntry.getFileVersion();

if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
	latestFileVersion = fileEntry.getLatestFileVersion();
}

DLFileShortcut fileShortcut = (DLFileShortcut)request.getAttribute("view_entries.jsp-fileShortcut");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");

boolean showCheckBox = DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE);

String rowCheckerName = FileEntry.class.getSimpleName();
long rowCheckerId = fileEntry.getFileEntryId();

if (fileShortcut != null) {
	rowCheckerName = DLFileShortcut.class.getSimpleName();
	rowCheckerId = fileShortcut.getFileShortcutId();
}

String thumbnailDivStyle = DLUtil.getThumbnailStyle(false, 4);
String thumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, fileShortcut, themeDisplay);
String thumbnailStyle = DLUtil.getThumbnailStyle();
%>

<liferay-ui:app-view-entry
	actionJsp="/html/portlet/document_library/file_entry_action.jsp"
	description="<%= fileEntry.getDescription() %>"
	displayStyle="icon"
	isLocked="<%= fileEntry.isCheckedOut() %>"
	isShortcut="<%= fileShortcut != null %>"
	rowCheckerId="<%= String.valueOf(rowCheckerId) %>"
	rowCheckerName="<%= rowCheckerName %>"
	showCheckbox="<%= showCheckBox %>"
	status="<%= latestFileVersion.getStatus() %>"
	thumbnailDivStyle="<%= thumbnailDivStyle %>"
	thumbnailSrc="<%= thumbnailSrc %>"
	thumbnailStyle="<%= thumbnailStyle %>"
	title="<%= fileEntry.getTitle() %>"
	url="<%= tempRowURL.toString() %>"
/>