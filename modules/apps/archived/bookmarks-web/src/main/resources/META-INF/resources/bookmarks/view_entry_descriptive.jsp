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

Object rowObject = row.getObject();

BookmarksEntry entry = null;

if (rowObject instanceof AssetEntry) {
	AssetEntry assetEntry = (AssetEntry)rowObject;

	entry = BookmarksEntryServiceUtil.getEntry(assetEntry.getClassPK());
}
else {
	entry = (BookmarksEntry)rowObject;
}

entry = entry.toEscapedModel();
%>

<h4>
	<aui:a href='<%= themeDisplay.getPathMain() + "/bookmarks/open_entry?entryId=" + entry.getEntryId() %>'>
		<%= entry.getName() %>
	</aui:a>
</h4>

<h5 class="text-default">
	<%= entry.getDescription() %>
</h5>

<span class="h6">
	<liferay-ui:message arguments="<%= entry.getVisits() %>" key='<%= (entry.getVisits() == 1) ? "x-visit" : "x-visits" %>' />
</span>