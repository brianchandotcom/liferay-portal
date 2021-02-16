<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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