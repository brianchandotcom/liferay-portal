<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/designer/init.jsp" %>

<clay:management-toolbar
	clearResultsURL="<%= kaleoDesignerDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= kaleoDesignerDisplayContext.getCreationMenu(pageContext) %>"
	filterDropdownItems="<%= kaleoDesignerDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= kaleoDesignerDisplayContext.getTotalItems(displayedStatus) %>"
	searchActionURL="<%= kaleoDesignerDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= kaleoDesignerDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= kaleoDesignerDisplayContext.getOrderByType() %>"
	sortingURL="<%= kaleoDesignerDisplayContext.getSortingURL() %>"
/>