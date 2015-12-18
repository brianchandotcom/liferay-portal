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

rowURL.setParameter("mvcRenderCommandName", "/bookmarks/view");
rowURL.setParameter("redirect", currentURL);
rowURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
%>

<liferay-ui:app-view-entry
	author="<%= folder.getUserName() %>"
	createDate="<%= folder.getCreateDate() %>"
	description="<%= folder.getDescription() %>"
	displayStyle="descriptive"
	folder="<%= true %>"
	markupView="lexicon"
	modifiedDate="<%= folder.getModifiedDate() %>"
	showCheckbox="<%= true %>"
	title="<%= folder.getName() %>"
	url="<%= (rowURL != null) ? rowURL.toString() : null %>"
/>