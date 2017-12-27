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

<%
List<Map<String, Object>> filterItems = new ArrayList<>();

Map<String, Object> navigationFilterItem = new HashMap<>();

filterItems.add(navigationFilterItem);

navigationFilterItem.put("label", "Filter By:");
navigationFilterItem.put("type", "group");

List<Map<String, Object>> navigationFilterItemItems = new ArrayList<>();

navigationFilterItem.put("items", navigationFilterItemItems);

String[] navigationFilterItemItemsTypes = {"All", "Recent", "Mine", "Structures"};

for (int i = 0; i < navigationFilterItemItemsTypes.length; i++) {
    Map<String, Object> navigationFilterItemItem = new HashMap<>();

    navigationFilterItemItem.put("href", "#" + i);
    navigationFilterItemItem.put("label", navigationFilterItemItemsTypes[i]);
    navigationFilterItemItem.put("type", "item");

    navigationFilterItemItems.add(navigationFilterItemItem);
}

Map<String, Object> statusFilterItem = new HashMap<>();

filterItems.add(statusFilterItem);

statusFilterItem.put("label", "Filter By Status:");
statusFilterItem.put("type", "group");

List<Map<String, Object>> statusFilterItemItems = new ArrayList<>();

statusFilterItem.put("items", statusFilterItemItems);

String[] statusFilterItemItemsTypes = {"Any", "Draft", "Scheduled", "Approved", "Expired"};

for (int i = 0; i < statusFilterItemItemsTypes.length; i++) {
    Map<String, Object> statusFilterItemItem = new HashMap<>();

    statusFilterItemItem.put("href", "#" + i);
    statusFilterItemItem.put("label", statusFilterItemItemsTypes[i]);
    statusFilterItemItem.put("type", "item");

    statusFilterItemItems.add(statusFilterItemItem);
}

List<Map<String, Object>> viewTypes = new ArrayList<>();

Map<String, Object> listViewType = new HashMap<>();

viewTypes.add(listViewType);

listViewType.put("active", true);
listViewType.put("icon", "list");
listViewType.put("label", "List");

Map<String, Object> tableViewType = new HashMap<>();

viewTypes.add(tableViewType);

tableViewType.put("icon", "table");
tableViewType.put("label", "Table");

Map<String, Object> cardViewType = new HashMap<>();

viewTypes.add(cardViewType);

cardViewType.put("icon", "card");
cardViewType.put("label", "Card");

List<Map<String, Object>> actionItems = new ArrayList<>();

Map<String, Object> editActionItem = new HashMap<>();

actionItems.add(editActionItem);

editActionItem.put("href", "#1");
editActionItem.put("label", "Edit");

Map<String, Object> previewActionItem = new HashMap<>();

actionItems.add(previewActionItem);

previewActionItem.put("href", "#2");
previewActionItem.put("label", "Preview");
%>

<clay:management-toolbar
    actionItems="<%= actionItems %>"
    filterItems="<%= filterItems %>"
    selectable="<%= true %>"
    sortingOrder="desc"
    viewTypes="<%= viewTypes %>" />