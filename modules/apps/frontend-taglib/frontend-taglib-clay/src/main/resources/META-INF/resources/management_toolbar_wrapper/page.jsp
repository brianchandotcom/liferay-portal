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

<%@ include file="/management_toolbar_wrapper/init.jsp" %>

<clay:management-toolbar
	actionDropdownItems="<%= managementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= managementToolbarDisplayContext.getClearResultsURL() %>"
	componentId="<%= managementToolbarDisplayContext.getComponentId() %>"
	contentRenderer="<%= managementToolbarDisplayContext.getContentRenderer() %>"
	creationMenu="<%= managementToolbarDisplayContext.getCreationMenu() %>"
	data="<%= managementToolbarDisplayContext.getData() %>"
	disabled="<%= managementToolbarDisplayContext.isDisabled() %>"
	elementClasses="<%= managementToolbarDisplayContext.getElementClasses() %>"
	filterDropdownItems="<%= managementToolbarDisplayContext.getFilterDropdownItems() %>"
	id="<%= managementToolbarDisplayContext.getId() %>"
	infoPanelId="<%= managementToolbarDisplayContext.getInfoPanelId() %>"
	itemsTotal="<%= managementToolbarDisplayContext.getItemsTotal() %>"
	namespace="<%= managementToolbarDisplayContext.getNamespace() %>"
	searchActionURL="<%= managementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= managementToolbarDisplayContext.getSearchContainerId() %>"
	searchFormMethod="<%= managementToolbarDisplayContext.getSearchFormMethod() %>"
	searchFormName="<%= managementToolbarDisplayContext.getSearchFormName() %>"
	searchInputName="<%= managementToolbarDisplayContext.getSearchInputName() %>"
	searchValue="<%= managementToolbarDisplayContext.getSearchValue() %>"
	selectable="<%= managementToolbarDisplayContext.isSelectable() %>"
	selectedItems="<%= managementToolbarDisplayContext.getSelectedItems() %>"
	showAdvancedSearch="<%= managementToolbarDisplayContext.isShowAdvancedSearch() %>"
	showCreationMenu="<%= managementToolbarDisplayContext.isShowCreationMenu() %>"
	showFiltersDoneButton="<%= managementToolbarDisplayContext.isShowFiltersDoneButton() %>"
	showInfoButton="<%= managementToolbarDisplayContext.isShowFiltersDoneButton() %>"
	showSearch="<%= managementToolbarDisplayContext.isShowSearch() %>"
	sortingOrder="<%= managementToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= managementToolbarDisplayContext.getSortingURL() %>"
	spritemap="<%= managementToolbarDisplayContext.getSpritemap() %>"
	viewTypeItems="<%= managementToolbarDisplayContext.getViewTypeItems() %>"
/>