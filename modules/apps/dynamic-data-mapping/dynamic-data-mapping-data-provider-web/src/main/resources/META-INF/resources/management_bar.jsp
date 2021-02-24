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
PortletURL portletURL = ddmDataProviderDisplayContext.getPortletURL();
%>

<portlet:actionURL name="/dynamic_data_mapping_data_provider/delete_data_provider" var="deleteDataProviderURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= ddmDataProviderDisplayContext.getActionItemsDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteDataProviderURL", deleteDataProviderURL.toString()
		).build()
	%>'
	clearResultsURL="<%= ddmDataProviderDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= ddmDataProviderDisplayContext.getCreationMenu() %>"
	disabled="<%= ddmDataProviderDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddmDataProviderDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddmDataProviderDisplayContext.getTotalItems() %>"
	propsTransformer="js/DDMDataProviderManagementToolbarPropsTransformer"
	searchActionURL="<%= portletURL.toString() %>"
	searchContainerId="<%= ddmDataProviderDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= ddmDataProviderDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddmDataProviderDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= ddmDataProviderDisplayContext.getViewTypesItems() %>"
/>