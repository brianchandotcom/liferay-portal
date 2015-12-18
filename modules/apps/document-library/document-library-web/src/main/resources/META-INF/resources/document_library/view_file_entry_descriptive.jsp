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

FileVersion latestFileVersion = fileEntry.getFileVersion();

if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
	latestFileVersion = fileEntry.getLatestFileVersion();
}

Date modifiedDate = latestFileVersion.getModifiedDate();

String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);

PortletURL rowURL = liferayPortletResponse.createRenderURL();

rowURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry");
rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
%>

<h5 class="text-default">
	<liferay-ui:message arguments="<%= new String[] {latestFileVersion.getUserName(), modifiedDateDescription} %>" key="x-modified-x-ago" />
</h5>

<h4>
	<aui:a href="<%= rowURL.toString() %>">
		<%= latestFileVersion.getTitle() %>
	</aui:a>
</h4>

<h5 class="text-default">
	<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= latestFileVersion.getStatus() %>" />
</h5>