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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry fileEntry = null;
DLFileShortcut fileShortcut = null;

if (row != null) {
	Object result = row.getObject();

	if (result instanceof AssetEntry) {
		AssetEntry assetEntry = (AssetEntry)result;

		if (assetEntry.getClassName().equals(DLFileEntryConstants.getClassName())) {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(assetEntry.getClassPK());
		}
		else {
			fileShortcut = DLAppLocalServiceUtil.getFileShortcut(assetEntry.getClassPK());
		}
	}
	else if (result instanceof FileEntry) {
		fileEntry = (FileEntry)result;
	}
	else if (result instanceof TrashEntry) {
		TrashEntry trashEntry = (TrashEntry)result;

		String className = trashEntry.getClassName();

		if (className.equals(DLFileEntryConstants.getClassName())) {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(trashEntry.getClassPK());
		}
		else {
			fileShortcut = DLAppLocalServiceUtil.getFileShortcut(trashEntry.getClassPK());
		}
	}
	else {
		fileShortcut = (DLFileShortcut)result;
	}
}
else {
	if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY)) {
		if (request.getAttribute("view_file_entry.jsp-fileEntry") != null) {
			fileEntry = (FileEntry)request.getAttribute("view_file_entry.jsp-fileEntry");

			if (request.getAttribute("view_file_entry.jsp-fileShortcut") != null) {
				fileShortcut = (DLFileShortcut)request.getAttribute("view_file_entry.jsp-fileShortcut");
			}
		}
		else {
			fileShortcut = (DLFileShortcut)request.getAttribute("view_file_shortcut.jsp-fileShortcut");
		}
	}
	else {
		if (request.getAttribute("view_entries.jsp-fileEntry") != null) {
			fileEntry = (FileEntry)request.getAttribute("view_entries.jsp-fileEntry");

			if (request.getAttribute("view_entries.jsp-fileShortcut") != null) {
				fileShortcut = (DLFileShortcut)request.getAttribute("view_entries.jsp-fileShortcut");
			}
		}
		else {
			fileShortcut = (DLFileShortcut)request.getAttribute("view_file_shortcut.jsp-fileShortcut");
		}
	}
}

DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = null;

if (fileShortcut == null) {
	dlViewFileVersionDisplayContext = DLDisplayContextProviderUtil.getDLViewFileVersionDisplayContext(request, response, fileEntry.getFileVersion());
}
else {
	dlViewFileVersionDisplayContext = DLDisplayContextProviderUtil.getDLViewFileVersionDisplayContext(request, response, fileShortcut);
}

DLActionsDisplayContext dlActionsDisplayContext = new DLActionsDisplayContext(dlRequestHelper);
%>

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) && !dlActionsDisplayContext.isShowMinimalActionsButton() %>">
		<liferay-ui:menu menu="<%= dlViewFileVersionDisplayContext.getMenu() %>" />
	</c:when>
	<c:otherwise>
		<span class="entry-action overlay">
			<liferay-ui:menu menu="<%= dlViewFileVersionDisplayContext.getMenu() %>" />
		</span>
	</c:otherwise>
</c:choose>