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

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

JournalFolder folder = null;

if (row != null) {
	folder = (JournalFolder)row.getObject();
}
else {
	folder = (JournalFolder)request.getAttribute("view_entries.jsp-folder");
}

PortletURL rowURL = liferayPortletResponse.createRenderURL();

rowURL.setParameter("redirect", currentURL);
rowURL.setParameter("groupId", String.valueOf(folder.getGroupId()));
rowURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
%>

<liferay-frontend:card
	actionJsp="/folder_action.jsp"
	actionJspServletContext="<%= application %>"
	horizontal="<%= true %>"
	imageCSSClass="icon-monospaced"
	imageUrl="icon-folder-close-alt"
	showCheckbox="<%= JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE) %>"
	title="<%= HtmlUtil.escape(folder.getName()) %>"
	url="<%= rowURL.toString() %>"
/>