<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ExportImportToolbarDisplayContext exportImportToolbarDisplayContext = new ExportImportToolbarDisplayContext(request, liferayPortletResponse);
%>

<clay:management-toolbar
	actionDropdownItems="<%= exportImportToolbarDisplayContext.getActionDropdownItems() %>"
	creationMenu="<%= exportImportToolbarDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= exportImportToolbarDisplayContext.getFilterDropdownItems() %>"
	orderDropdownItems="<%= exportImportToolbarDisplayContext.getOrderByDropDownItems() %>"
	propsTransformer="js/ExportImportManagementToolbarPropsTransformer"
	searchContainerId="<%= exportImportToolbarDisplayContext.getSearchContainerId() %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= false %>"
	sortingOrder="<%= exportImportToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= exportImportToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= exportImportToolbarDisplayContext.getViewTypeItems() %>"
/>