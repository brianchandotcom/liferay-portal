<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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