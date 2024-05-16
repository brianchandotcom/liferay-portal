<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/bookmarks/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BookmarksFolder folder = (BookmarksFolder)row.getObject();

folder = folder.toEscapedModel();
%>

<h4>
	<aui:a
		href='<%=
			PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCRenderCommandName(
				"/bookmarks/view_folder"
			).setRedirect(
				currentURL
			).setParameter(
				"folderId", folder.getFolderId()
			).buildString()
		%>'
	>
		<%= folder.getName() %>
	</aui:a>
</h4>

<h5 class="text-default">
	<%= folder.getDescription() %>
</h5>

<%
int entriesCount = BookmarksEntryServiceUtil.getEntriesCount(scopeGroupId, folder.getFolderId());
int foldersCount = BookmarksFolderServiceUtil.getFoldersCount(scopeGroupId, folder.getFolderId());
%>

<span class="h6">
	<liferay-ui:message arguments="<%= foldersCount %>" key='<%= (foldersCount == 1) ? "x-folder" : "x-folders" %>' />
</span>
<span class="h6">
	<liferay-ui:message arguments="<%= entriesCount %>" key='<%= (entriesCount == 1) ? "x-bookmark" : "x-bookmarks" %>' />
</span>