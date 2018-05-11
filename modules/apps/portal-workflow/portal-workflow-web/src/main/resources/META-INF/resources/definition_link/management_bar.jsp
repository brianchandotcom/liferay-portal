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

<%@ include file="/definition_link/init.jsp" %>

<clay:management-toolbar
	clearResultsURL="<%= workflowDefinitionLinkDisplayContext.getClearResultsURL() %>"
	disabled="<%= workflowDefinitionLinkDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= workflowDefinitionLinkDisplayContext.getFilterOptions(request) %>"
	itemsTotal="<%= workflowDefinitionLinkDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= workflowDefinitionLinkDisplayContext.getSearchURL() %>"
	searchContainerId="workflowDefinitionLinks"
	searchFormName="fm1"
	selectable="false"
	sortingOrder="<%= workflowDefinitionLinkDisplayContext.getOrderByType() %>"
	sortingURL="<%= workflowDefinitionLinkDisplayContext.getSortingURL() %>"
/>