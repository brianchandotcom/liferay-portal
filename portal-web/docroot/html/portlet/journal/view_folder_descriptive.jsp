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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalFolder folder = (JournalFolder)request.getAttribute("view_entries.jsp-folder");

String folderImage = (String)request.getAttribute("view_entries.jsp-folderImage");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");

boolean showCheckBox = JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE);
%>

<div class="article-display-style display-descriptive <%= showCheckBox ? "selectable" : StringPool.BLANK %>" data-draggable="<%= showCheckBox ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" data-title="<%= StringUtil.shorten(folder.getName(), 60) %>">
	<a class="article-link" data-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= folder.getFolderId() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escape(folder.getName()) + " - " + HtmlUtil.escape(folder.getDescription()) %>">
		<span class="article-thumbnail">
			<img alt="" border="no" src="<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>" style="width: 128px" />
		</span>

		<span class="article-title"><%= HtmlUtil.escape(folder.getName()) %></span>

		<span class="article-description"><%= HtmlUtil.escape(folder.getDescription()) %></span>
	</a>

	<liferay-util:include page="/html/portlet/journal/folder_action.jsp" />

	<c:if test="<%= showCheckBox %>">
		<aui:input cssClass="overlay article-selector" label="" name="<%= RowChecker.ROW_IDS + JournalFolder.class.getSimpleName() %>" type="checkbox" value="<%= folder.getFolderId() %>" />
	</c:if>
</div>