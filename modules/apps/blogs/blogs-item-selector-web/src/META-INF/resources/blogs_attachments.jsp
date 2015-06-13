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
String displayStyle = ParamUtil.getString(request, "displayStyle");

PortletURL portletURL = (PortletURL)request.getAttribute(BlogsItemSelectorView.PORTLET_URL);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "curDocuments", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

BlogsItemSelectorCriterion blogsItemSelectorCriterion = (BlogsItemSelectorCriterion)request.getAttribute(BlogsItemSelectorView.BLOGS_ITEM_SELECTOR_CRITERION);

Folder folder = BlogsEntryLocalServiceUtil.fetchAttachmentsFolder(themeDisplay.getUserId(), scopeGroupId);

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
	displayStyle="<%= displayStyle %>"
	itemSelectedEventName="<%= GetterUtil.getString(request.getAttribute(BlogsItemSelectorView.ITEM_SELECTED_EVENT_NAME)) %>"
	returnType="<%= ReturnType.parseFirst(blogsItemSelectorCriterion.getDesiredItemSelectorReturnTypes()) %>"
	searchContainer="<%= searchContainer %>"
	tabName="blogs-images"
/>