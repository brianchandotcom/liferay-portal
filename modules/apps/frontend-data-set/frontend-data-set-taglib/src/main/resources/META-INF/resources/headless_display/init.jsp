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

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.data.set.model.FrontendDataSetActionDropdownItem" %><%@
page import="com.liferay.frontend.data.set.model.FrontendDataSetPaginationEntry" %><%@
page import="com.liferay.frontend.data.set.model.FrontendDataSetSortItemList" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<%
String actionParameterName = (String)request.getAttribute("frontend-data-set:headless-display:actionParameterName");
String activeViewSettingsJSON = GetterUtil.getString(request.getAttribute("frontend-data-set:headless-display:activeViewSettingsJSON"), "{}");
String apiURL = (String)request.getAttribute("frontend-data-set:headless-display:apiURL");
String appURL = (String)request.getAttribute("frontend-data-set:headless-display:appURL");
List<DropdownItem> bulkActionDropdownItems = (List<DropdownItem>)request.getAttribute("frontend-data-set:headless-display:bulkActionDropdownItems");
CreationMenu creationMenu = (CreationMenu)request.getAttribute("frontend-data-set:headless-display:creationMenu");
boolean customViewsEnabled = (boolean)request.getAttribute("frontend-data-set:headless-display:customViewsEnabled");
String formId = (String)request.getAttribute("frontend-data-set:headless-display:formId");
String formName = (String)request.getAttribute("frontend-data-set:headless-display:formName");
List<FrontendDataSetActionDropdownItem> frontendDataSetActionDropdownItems = (List<FrontendDataSetActionDropdownItem>)request.getAttribute("frontend-data-set:headless-display:frontendDataSetActionDropdownItems");
Object frontendDataSetDisplayViewsContext = request.getAttribute("frontend-data-set:headless-display:frontendDataSetDisplayViewsContext");
Object frontendDataSetFiltersContext = request.getAttribute("frontend-data-set:headless-display:frontendDataSetFiltersContext");
List<FrontendDataSetPaginationEntry> frontendDataSetPaginationEntries = (List<FrontendDataSetPaginationEntry>)request.getAttribute("frontend-data-set:headless-display:frontendDataSetPaginationEntries");
FrontendDataSetSortItemList frontendDataSetSortItemList = (FrontendDataSetSortItemList)request.getAttribute("frontend-data-set:headless-display:frontendDataSetSortItemList");
String id = (String)request.getAttribute("frontend-data-set:headless-display:id");
int itemsPerPage = (int)request.getAttribute("frontend-data-set:headless-display:itemsPerPage");
String namespace = (String)request.getAttribute("frontend-data-set:headless-display:namespace");
String nestedItemsKey = (String)request.getAttribute("frontend-data-set:headless-display:nestedItemsKey");
String nestedItemsReferenceKey = (String)request.getAttribute("frontend-data-set:headless-display:nestedItemsReferenceKey");
int pageNumber = (int)request.getAttribute("frontend-data-set:headless-display:pageNumber");
PortletURL portletURL = (PortletURL)request.getAttribute("frontend-data-set:headless-display:portletURL");
List<String> selectedItems = (List<String>)request.getAttribute("frontend-data-set:headless-display:selectedItems");
String selectedItemsKey = (String)request.getAttribute("frontend-data-set:headless-display:selectedItemsKey");
String selectionType = (String)request.getAttribute("frontend-data-set:headless-display:selectionType");
boolean showManagementBar = (boolean)request.getAttribute("frontend-data-set:headless-display:showManagementBar");
boolean showPagination = (boolean)request.getAttribute("frontend-data-set:headless-display:showPagination");
boolean showSearch = (boolean)request.getAttribute("frontend-data-set:headless-display:showSearch");
String style = (String)request.getAttribute("frontend-data-set:headless-display:style");
%>