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
	componentId="bookmarksManagementToolbar"
	creationMenu="<%= bookmarksManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= bookmarksManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= bookmarksManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= bookmarksManagementToolbarDisplayContext.getFilterLabelItems() %>"
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
	var deleteEntries = function() {
		if (
			<%= trashHelper.isTrashEnabled(scopeGroupId) %> ||
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />'
			)
		) {
			var form = document.getElementById('<portlet:namespace />fm');

			if (form) {
				form.setAttribute('method', 'post');

				var cmd = form.querySelector(
					'#<portlet:namespace /><%= Constants.CMD %>'
				);

				if (cmd) {
					cmd.setAttribute(
						'value',
						'<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>'
					);

					submitForm(
						form,
						'<portlet:actionURL name="/bookmarks/edit_entry" />'
					);
				}
			}
		}
	};

	var ACTIONS = {
		deleteEntries: deleteEntries
	};

	Liferay.componentReady('bookmarksManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>