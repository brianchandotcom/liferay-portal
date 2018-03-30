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

<blockquote><p>Management toolbar is an extension of Toolbar. A combination of different components as filters, orders, search, visualization select and other actions, that allow to manage dataset.</p></blockquote>

<h3>DEFAULT STATE</h3>

<clay:management-toolbar
	creationMenu="<%= managementToolbarsDisplayContext.getCreationMenu() %>"
	filterItems="<%= managementToolbarsDisplayContext.getFilterItemList() %>"
	searchActionURL="mySearchActionURL"
	searchFormName="mySearchName"
	searchInputName="mySearchInputName"
	selectable="<%= true %>"
	sortingOrder="desc"
	viewTypes="<%= managementToolbarsDisplayContext.getViewTypesItemList() %>"
/>

<h3>ACTIVE STATE</h3>

<clay:management-toolbar
	actionItems="<%= managementToolbarsDisplayContext.getActionDropdownItemList() %>"
	selectable="<%= true %>"
	selectedItems="<%= 14 %>"
	totalItems="<%= 42 %>"
/>