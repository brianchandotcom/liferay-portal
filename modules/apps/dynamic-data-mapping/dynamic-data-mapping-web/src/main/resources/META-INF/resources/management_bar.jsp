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

<clay:management-toolbar
	actionDropdownItems='<%= ddmDisplayContext.getActionItemsDropdownItems("deleteStructures") %>'
	clearResultsURL="<%= ddmDisplayContext.getClearResultsURL() %>"
	componentId="ddmStructureManagementToolbar"
	creationMenu="<%= ddmDisplayContext.getStructureCreationMenu() %>"
	disabled="<%= ddmDisplayContext.isDisabledManagementBar(DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE) %>"
	filterDropdownItems="<%= ddmDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmDisplayContext.getTotalItems(DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE) %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= ddmDisplayContext.getStructureSearchActionURL() %>"
	searchContainerId="<%= ddmDisplayContext.getStructureSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= !user.isDefaultUser() %>"
	sortingOrder="<%= ddmDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDisplayContext.getSortingURL() %>"
/>

<aui:script sandbox="<%= true %>">
	var deleteStructures = function() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			var searchContainer = document.getElementById(
				'<portlet:namespace />entriesContainer'
			);

			if (searchContainer) {
				<portlet:actionURL name="deleteStructure" var="deleteStructuresURL">
					<portlet:param name="mvcPath" value="/view.jsp" />
				</portlet:actionURL>

				Liferay.Util.postForm(document.<portlet:namespace />fm, {
					data: {
						deleteStructureIds: Liferay.Util.listCheckedExcept(
							searchContainer,
							'<portlet:namespace />allRowIds'
						)
					},
					url: '<%= deleteStructuresURL %>'
				});
			}
		}
	};

	var ACTIONS = {
		deleteStructures: deleteStructures
	};

	Liferay.componentReady('ddmStructureManagementToolbar').then(function(
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