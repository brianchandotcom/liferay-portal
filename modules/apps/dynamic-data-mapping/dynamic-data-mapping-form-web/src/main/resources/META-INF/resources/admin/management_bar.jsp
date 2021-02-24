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

<%@ include file="/admin/init.jsp" %>

<portlet:actionURL name="/dynamic_data_mapping_form/delete_form_instance" var="deleteFormInstanceURL">
	<portlet:param name="mvcPath" value="/admin/view.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="/dynamic_data_mapping_form/delete_structure" var="deleteStructureURL">
	<portlet:param name="mvcPath" value="/admin/view.jsp" />
	<portlet:param name="currentTab" value="element-set" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= ddmFormAdminDisplayContext.getActionItemsDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteFormInstanceURL", deleteFormInstanceURL.toString()
		).put(
			"deleteStructureURL", deleteStructureURL.toString()
		).build()
	%>'
	clearResultsURL="<%= ddmFormAdminDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= ddmFormAdminDisplayContext.getCreationMenu() %>"
	disabled="<%= ddmFormAdminDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddmFormAdminDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmFormAdminDisplayContext.getTotalItems() %>"
	propsTransformer="admin/js/DDMFormAdminManagementToolbarPropsTransformer"
	searchActionURL="<%= ddmFormAdminDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= ddmFormAdminDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= ddmFormAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmFormAdminDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= ddmFormAdminDisplayContext.getViewTypesItems() %>"
/>