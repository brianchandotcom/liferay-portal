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

<%@ include file="/bookmarks/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BookmarksFolder folder = (BookmarksFolder)row.getObject();

folder = folder.toEscapedModel();

PortletURL rowURL = liferayPortletResponse.createRenderURL();

rowURL.setParameter("mvcRenderCommandName", "/bookmarks/view_folder");
rowURL.setParameter("redirect", currentURL);
rowURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
%>

<h4>
	<aui:a href="<%= rowURL.toString() %>">
		<%= folder.getName() %>
	</aui:a>
</h4>

<h5 class="text-default">
	<%= folder.getDescription() %>
</h5>

<%
int foldersCount = BookmarksFolderServiceUtil.getFoldersCount(scopeGroupId, folder.getFolderId());
int entriesCount = BookmarksEntryServiceUtil.getEntriesCount(scopeGroupId, folder.getFolderId());
%>

<span class="h6">
	<liferay-ui:message arguments="<%= foldersCount %>" key='<%= (foldersCount == 1) ? "x-folder" : "x-folders" %>' />
</span>
<span class="h6">
	<liferay-ui:message arguments="<%= entriesCount %>" key='<%= (entriesCount == 1) ? "x-bookmark" : "x-bookmarks" %>' />
</span>