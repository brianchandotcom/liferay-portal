<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();
%>

<portlet:actionURL name="/dynamic_data_lists/delete_record_set" var="deleteRecordSetsURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= ddlDisplayContext.getActionItemsDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteRecordSetsURL", deleteRecordSetsURL.toString()
		).build()
	%>'
	clearResultsURL="<%= ddlDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= ddlDisplayContext.getCreationMenu() %>"
	disabled="<%= ddlDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= ddlDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= ddlDisplayContext.getTotalItems() %>"
	propsTransformer="js/ManagementToolbarPropsTransformer"
	searchActionURL="<%= portletURL.toString() %>"
	searchContainerId="<%= ddlDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	sortingOrder="<%= ddlDisplayContext.getOrderByType() %>"
	sortingURL="<%= ddlDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= ddlDisplayContext.getViewTypesItems() %>"
/>