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
ClaySampleManagementToolbarsDisplayContext managementToolbarsDisplayContext = new ClaySampleManagementToolbarsDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
%>

<blockquote>
	<p>Management toolbar is an extension of Toolbar. A combination of different components as filters, orders, search, visualization select and other actions, that allow to manage dataset.</p>
</blockquote>

<h3>DEFAULT STATE</h3>

<clay:management-toolbar
	creationMenu="<%= managementToolbarsDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= managementToolbarsDisplayContext.getFilterDropdownItems() %>"
	searchActionURL="mySearchActionURL?key1=val1&key2=val2&key3=val3"
	searchFormName="mySearchName"
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
	displayContext="<%= managementToolbarsDisplayContext %>"
	propsTransformer="js/ClaySampleManagementToolbarPropsTransformer"
/>