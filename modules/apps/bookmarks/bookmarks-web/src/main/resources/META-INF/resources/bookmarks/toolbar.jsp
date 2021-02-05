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
BookmarksManagementToolbarDisplayContext bookmarksManagementToolbarDisplayContext = new BookmarksManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, bookmarksGroupServiceOverriddenConfiguration, portalPreferences, trashHelper);
%>

<portlet:actionURL name="/bookmarks/edit_entry" var="deleteEntriesURL" />

<clay:management-toolbar
	actionDropdownItems="<%= bookmarksManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteEntriesURL", deleteEntriesURL.toString()
		).put(
			"inputId", Constants.CMD
		).put(
			"inputValue", trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE
		).put(
			"trashEnabled", trashHelper.isTrashEnabled(scopeGroupId)
		).build()
	%>'
	clearResultsURL="<%= bookmarksManagementToolbarDisplayContext.getClearResultsURL() %>"
	componentId="bookmarksManagementToolbar"
	creationMenu="<%= bookmarksManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= bookmarksManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= bookmarksManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= bookmarksManagementToolbarDisplayContext.getFilterLabelItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= bookmarksManagementToolbarDisplayContext.getTotalItems() %>"
	propsTransformer="bookmarks/js/BookmarksManagementToolbarPropsTransformer"
	searchActionURL="<%= String.valueOf(bookmarksManagementToolbarDisplayContext.getSearchActionURL()) %>"
	searchContainerId="<%= bookmarksManagementToolbarDisplayContext.getSearchContainerId() %>"
	selectable="<%= bookmarksManagementToolbarDisplayContext.isSelectable() %>"
	showInfoButton="<%= true %>"
	showSearch="<%= bookmarksManagementToolbarDisplayContext.isShowSearch() %>"
	viewTypeItems="<%= bookmarksManagementToolbarDisplayContext.getViewTypes() %>"
/>