<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/definition_link/init.jsp" %>

<clay:management-toolbar
	clearResultsURL="<%= workflowDefinitionLinkDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= workflowDefinitionLinkDisplayContext.getFilterOptions(request) %>"
	itemsTotal="<%= workflowDefinitionLinkDisplayContext.getTotalItems() %>"
	searchActionURL="<%= workflowDefinitionLinkDisplayContext.getSearchURL() %>"
	searchContainerId="workflowDefinitionLinks"
	searchFormName="fm1"
	selectable="<%= false %>"
	sortingOrder="<%= workflowDefinitionLinkDisplayContext.getOrderByType() %>"
	sortingURL="<%= workflowDefinitionLinkDisplayContext.getSortingURL() %>"
/>