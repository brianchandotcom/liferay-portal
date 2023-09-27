<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<portlet:actionURL name="/kaleo_forms_admin/delete_kaleo_process" var="deleteKaleoProcessURL">
	<portlet:param name="mvcPath" value="/admin/view.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= kaleoFormsAdminDisplayContext.getActionItemsDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteKaleoProcessURL", deleteKaleoProcessURL.toString()
		).build()
	%>'
	clearResultsURL="<%= kaleoFormsAdminDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= kaleoFormsAdminDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= kaleoFormsAdminDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= kaleoFormsAdminDisplayContext.getTotalItems() %>"
	propsTransformer="admin/js/KaleoFormsAdminManagementToolbarPropsTransformer"
	searchActionURL="<%= kaleoFormsAdminDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= kaleoFormsAdminDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= kaleoFormsAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= kaleoFormsAdminDisplayContext.getSortingURL() %>"
/>