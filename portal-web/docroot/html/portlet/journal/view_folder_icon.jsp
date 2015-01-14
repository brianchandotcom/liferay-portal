<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalFolder folder = (JournalFolder)request.getAttribute("view_entries.jsp-folder");

String folderImage = (String)request.getAttribute("view_entries.jsp-folderImage");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");
%>

<liferay-ui:app-view-entry
	actionJsp="/html/portlet/journal/folder_action.jsp"
	description="<%= folder.getDescription() %>"
	displayStyle="icon"
	folder="<%= true %>"
	rowCheckerId="<%= String.valueOf(folder.getFolderId()) %>"
	rowCheckerName="<%= JournalFolder.class.getSimpleName() %>"
	showCheckbox="<%= JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE) %>"
	thumbnailSrc='<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>'
	thumbnailStyle="max-height: 128px; max-width: 128px;"
	title="<%= HtmlUtil.escape(folder.getName()) %>"
	url="<%= tempRowURL.toString() %>"
/>