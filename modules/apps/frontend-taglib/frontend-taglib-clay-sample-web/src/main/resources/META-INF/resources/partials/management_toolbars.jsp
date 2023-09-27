<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ClaySampleManagementToolbarsDisplayContext managementToolbarsDisplayContext = new ClaySampleManagementToolbarsDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<blockquote>
	<p>Management toolbar is an extension of Toolbar. A combination of different components as filters, orders, search, visualization select and other actions, that allow to manage dataset.</p>
</blockquote>

<h3>DEFAULT STATE</h3>

<clay:management-toolbar
	creationMenu="<%= managementToolbarsDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= managementToolbarsDisplayContext.getFilterDropdownItems() %>"
	orderDropdownItems="<%= managementToolbarsDisplayContext.getOrderDropdownItems() %>"
	searchActionURL="mySearchActionURL?key1=val1&key2=val2&key3=val3"
	searchFormName="mySearchName"
	searchInputAutoFocus="<%= true %>"
	searchInputName="mySearchInputName"
	selectable="<%= true %>"
	sortingOrder="desc"
	viewTypeItems="<%= managementToolbarsDisplayContext.getViewTypeItems() %>"
/>

<h3>ACTIVE STATE</h3>

<clay:management-toolbar
	actionDropdownItems="<%= managementToolbarsDisplayContext.getActionDropdownItems() %>"
	checkboxStatus="checked"
	itemsTotal="<%= 42 %>"
	selectable="<%= true %>"
	selectedItems="<%= 14 %>"
	showSelectAllButton="<%= true %>"
/>

<h3>WITH RESULTS BAR</h3>

<clay:management-toolbar
	creationMenu="<%= managementToolbarsDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= managementToolbarsDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= managementToolbarsDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= 42 %>"
	orderDropdownItems="<%= managementToolbarsDisplayContext.getOrderDropdownItems() %>"
	searchActionURL="mySearchActionURL?key1=val1&key2=val2&key3=val3"
	searchFormName="mySearchName"
	searchInputName="mySearchInputName"
	searchValue="my search"
	selectable="<%= true %>"
	showResultsBar="<%= true %>"
	sortingOrder="desc"
	viewTypeItems="<%= managementToolbarsDisplayContext.getViewTypeItems() %>"
/>

<h3>USING DISPLAY CONTEXT</h3>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= managementToolbarsDisplayContext %>"
	propsTransformer="js/ClaySampleManagementToolbarPropsTransformer"
/>