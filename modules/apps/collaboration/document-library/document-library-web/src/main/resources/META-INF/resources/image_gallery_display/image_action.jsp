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

<%@ include file="/image_gallery_display/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry fileEntry = null;
FileShortcut fileShortcut = null;

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
	else if (result instanceof FileShortcut) {
		fileShortcut = (FileShortcut)result;
	}
}

IGViewFileVersionDisplayContext igViewFileVersionDisplayContext = null;

if (fileShortcut == null) {
	igViewFileVersionDisplayContext = igDisplayContextProvider.getIGViewFileVersionActionsDisplayContext(request, response, fileEntry.getFileVersion());
}
else {
	igViewFileVersionDisplayContext = igDisplayContextProvider.getIGViewFileVersionActionsDisplayContext(request, response, fileShortcut);
}
%>

<liferay-ui:menu
	menu="<%= igViewFileVersionDisplayContext.getMenu() %>"
/>