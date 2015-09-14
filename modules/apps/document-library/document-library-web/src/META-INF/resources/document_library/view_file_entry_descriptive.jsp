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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry fileEntry = null;
FileShortcut fileShortcut = null;

if (row.getObject() instanceof FileEntry) {
	fileEntry = (FileEntry)row.getObject();
}
else if (row.getObject() instanceof FileShortcut) {
	fileShortcut = (FileShortcut)row.getObject();

	fileShortcut = fileShortcut.toEscapedModel();

	fileEntry = DLAppLocalServiceUtil.getFileEntry(fileShortcut.getToFileEntryId());
}

fileEntry = fileEntry.toEscapedModel();

FileVersion fileVersion = fileEntry.getFileVersion();

FileVersion latestFileVersion = fileVersion;

if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
	latestFileVersion = fileEntry.getLatestFileVersion();
}

long assetClassPK = 0;

if (!latestFileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT) && (latestFileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED)) {
	assetClassPK = latestFileVersion.getFileVersionId();
}
else {
	assetClassPK = fileEntry.getFileEntryId();
}

PortletURL rowURL = liferayPortletResponse.createRenderURL();

rowURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry");
rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
%>

<liferay-ui:app-view-entry
	assetCategoryClassName="<%= DLFileEntryConstants.getClassName() %>"
	assetCategoryClassPK="<%= assetClassPK %>"
	assetTagClassName="<%= DLFileEntryConstants.getClassName() %>"
	assetTagClassPK="<%= assetClassPK %>"
	author="<%= latestFileVersion.getUserName() %>"
	createDate="<%= latestFileVersion.getCreateDate() %>"
	description="<%= latestFileVersion.getDescription() %>"
	displayStyle="descriptive"
	latestApprovedVersion="<%= fileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT) ? null : fileVersion.getVersion() %>"
	latestApprovedVersionAuthor="<%= fileVersion.getVersion().equals(DLFileEntryConstants.VERSION_DEFAULT) ? null : fileVersion.getUserName() %>"
	locked="<%= fileEntry.isCheckedOut() %>"
	markupView="lexicon"
	modifiedDate="<%= latestFileVersion.getModifiedDate() %>"
	shortcut="<%= fileShortcut != null %>"
	showCheckbox="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) %>"
	status="<%= latestFileVersion.getStatus() %>"
	title="<%= latestFileVersion.getTitle() %>"
	url="<%= (rowURL != null) ? rowURL.toString() : null %>"
	version="<%= latestFileVersion.getVersion() %>"
/>