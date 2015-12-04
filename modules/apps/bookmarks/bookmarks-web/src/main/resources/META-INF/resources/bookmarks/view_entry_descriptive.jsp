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

PortletURL rowURL = liferayPortletResponse.createRenderURL();

rowURL.setParameter("mvcRenderCommandName", "/bookmarks/view_entry");
rowURL.setParameter("redirect", currentURL);
rowURL.setParameter("entryId", String.valueOf(entry.getEntryId()));
%>

<liferay-ui:app-view-entry
	assetCategoryClassName="<%= BookmarksEntry.class.getName() %>"
	assetCategoryClassPK="<%= entry.getEntryId() %>"
	assetTagClassName="<%= BookmarksEntry.class.getName() %>"
	assetTagClassPK="<%= entry.getEntryId() %>"
	author="<%= entry.getUserName() %>"
	createDate="<%= entry.getCreateDate() %>"
	description="<%= entry.getDescription() %>"
	displayStyle="descriptive"
	markupView="lexicon"
	modifiedDate="<%= entry.getModifiedDate() %>"
	showCheckbox="<%= true %>"
	title="<%= entry.getName() %>"
	url="<%= rowURL.toString() %>"
/>