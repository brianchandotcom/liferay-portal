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

<portlet:actionURL name="/dynamic_data_mapping/delete_structure" var="deleteStructuresURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems='<%= ddmDisplayContext.getActionItemsDropdownItems("deleteStructures") %>'
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteStructuresURL", deleteStructuresURL.toString()
		).build()
	%>'
	clearResultsURL="<%= ddmDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= ddmDisplayContext.getStructureCreationMenu() %>"
	disabled="<%= ddmDisplayContext.isDisabledManagementBar(DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE) %>"
	filterDropdownItems="<%= ddmDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmDisplayContext.getTotalItems(DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE) %>"
	propsTransformer="js/DDMStructureManagementToolbarPropsTransformer"
	searchActionURL="<%= ddmDisplayContext.getStructureSearchActionURL() %>"
	searchContainerId="<%= ddmDisplayContext.getStructureSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= !user.isDefaultUser() %>"
	sortingOrder="<%= ddmDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDisplayContext.getSortingURL() %>"
/>