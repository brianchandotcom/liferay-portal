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

long folderId = blogsItemSelectorViewDisplayContext.getFolderId(themeDisplay.getUserId(), scopeGroupId);

searchContainer.setTotal(PortletFileRepositoryUtil.getPortletFileEntriesCount(scopeGroupId, folderId));
searchContainer.setResults(PortletFileRepositoryUtil.getPortletFileEntries(scopeGroupId, folderId));
%>

<item-selector-ui:browser
	displayStyle="<%= blogsItemSelectorViewDisplayContext.getDisplayStyle(request) %>"
	idPrefix="<%= blogsItemSelectorViewDisplayContext.getTitle(locale) %>"
	itemSelectedEventName="<%= blogsItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	returnType="<%= ReturnType.parseFirst(blogsItemSelectorCriterion.getDesiredItemSelectorReturnTypes()) %>"
	searchContainer="<%= searchContainer %>"
	tabName="<%= blogsItemSelectorViewDisplayContext.getTitle(locale) %>"
/>