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
BlogsItemSelectorViewDisplayContext blogsItemSelectorViewDisplayContext = (BlogsItemSelectorViewDisplayContext)request.getAttribute(BlogsItemSelectorView.BLOGS_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

BlogsItemSelectorCriterion blogsItemSelectorCriterion = blogsItemSelectorViewDisplayContext.getBlogsItemSelectorCriterion();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curDocuments", SearchContainer.DEFAULT_DELTA, blogsItemSelectorViewDisplayContext.getPortletURL(), null, null);

Folder folder = blogsItemSelectorViewDisplayContext.fetchAttachmentsFolder(themeDisplay.getUserId(), scopeGroupId);

int total = 0;
List<FileEntry> results = new ArrayList<FileEntry>();

if (folder != null) {
	total = PortletFileRepositoryUtil.getPortletFileEntriesCount(scopeGroupId, folder.getFolderId());
	results = PortletFileRepositoryUtil.getPortletFileEntries(scopeGroupId, folder.getFolderId());
}

searchContainer.setTotal(total);
searchContainer.setResults(results);
%>

<item-selector-ui:browser
	displayStyle="<%= blogsItemSelectorViewDisplayContext.getDisplayStyle(request) %>"
	displayStyleURL="<%= blogsItemSelectorViewDisplayContext.getPortletURL() %>"
	itemSelectedEventName="<%= blogsItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	itemSelectorReturnType="<%= ItemSelectorBrowserReturnTypeUtil.getFirstAvailableItemSelectorReturnType(blogsItemSelectorCriterion.getDesiredItemSelectorReturnTypes()) %>"
	searchContainer="<%= searchContainer %>"
	tabName="<%= blogsItemSelectorViewDisplayContext.getTitle(locale) %>"
/>