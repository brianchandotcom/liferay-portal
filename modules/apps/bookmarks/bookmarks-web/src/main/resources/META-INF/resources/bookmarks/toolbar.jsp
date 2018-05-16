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
BookmarksManagementToolbarDisplayContext bookmarksManagementToolbarDisplayContext = new BookmarksManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, bookmarksGroupServiceOverriddenConfiguration, portalPreferences, trashHelper);
%>

<clay:management-toolbar
	actionDropdownItems="<%= bookmarksManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= bookmarksManagementToolbarDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= bookmarksManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= bookmarksManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= bookmarksManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= bookmarksManagementToolbarDisplayContext.getTotalItems() %>"
	searchActionURL="<%= String.valueOf(bookmarksManagementToolbarDisplayContext.getSearchActionURL()) %>"
	searchContainerId="<%= bookmarksManagementToolbarDisplayContext.getSearchContainerId() %>"
	selectable="<%= bookmarksManagementToolbarDisplayContext.isSelectable() %>"
	showInfoButton="<%= true %>"
	showSearch="<%= bookmarksManagementToolbarDisplayContext.isShowSearch() %>"
	viewTypeItems="<%= bookmarksManagementToolbarDisplayContext.getViewTypes() %>"
/>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= trashHelper.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');

			submitForm(form, '<portlet:actionURL name="/bookmarks/edit_entry" />');
		}
	}
</aui:script>