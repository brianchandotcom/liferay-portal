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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.taglib.clay.data.set.model.ClayPaginationEntry" %><%@
page import="com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONSerializer" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<%
String actionParameterName = (String)request.getAttribute("clay:headless-data-set-display:actionParameterName");
String activeViewSettingsJSON = GetterUtil.getString(request.getAttribute("clay:headless-data-set-display:activeViewSettingsJSON"), "{}");
String apiURL = (String)request.getAttribute("clay:headless-data-set-display:apiURL");
String appURL = (String)request.getAttribute("clay:headless-data-set-display:appURL");
List<DropdownItem> bulkActionDropdownItems = (List<DropdownItem>)request.getAttribute("clay:headless-data-set-display:bulkActionDropdownItems");
Object clayDataSetDisplayViewsContext = request.getAttribute("clay:headless-data-set-display:clayDataSetDisplayViewsContext");
Object clayDataSetFiltersContext = request.getAttribute("clay:headless-data-set-display:clayDataSetFiltersContext");
List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems = (List<ClayDataSetActionDropdownItem>)request.getAttribute("clay:headless-data-set-display:clayDataSetActionDropdownItems");
List<ClayPaginationEntry> clayPaginationEntries = (List<ClayPaginationEntry>)request.getAttribute("clay:headless-data-set-display:clayPaginationEntries");
CreationMenu creationMenu = (CreationMenu)request.getAttribute("clay:headless-data-set-display:creationMenu");
String formId = (String)request.getAttribute("clay:headless-data-set-display:formId");
String id = (String)request.getAttribute("clay:headless-data-set-display:id");
int itemsPerPage = (int)request.getAttribute("clay:headless-data-set-display:itemsPerPage");
String module = (String)request.getAttribute("clay:headless-data-set-display:module");
String namespace = (String)request.getAttribute("clay:headless-data-set-display:namespace");
String nestedItemsKey = (String)request.getAttribute("clay:headless-data-set-display:nestedItemsKey");
String nestedItemsReferenceKey = (String)request.getAttribute("clay:headless-data-set-display:nestedItemsReferenceKey");
int pageNumber = (int)request.getAttribute("clay:headless-data-set-display:pageNumber");
PortletURL portletURL = (PortletURL)request.getAttribute("clay:headless-data-set-display:portletURL");
List<String> selectedItems = (List<String>)request.getAttribute("clay:headless-data-set-display:selectedItems");
String selectedItemsKey = (String)request.getAttribute("clay:headless-data-set-display:selectedItemsKey");
String selectionType = (String)request.getAttribute("clay:headless-data-set-display:selectionType");
boolean showManagementBar = (boolean)request.getAttribute("clay:headless-data-set-display:showManagementBar");
boolean showPagination = (boolean)request.getAttribute("clay:headless-data-set-display:showPagination");
boolean showSearch = (boolean)request.getAttribute("clay:headless-data-set-display:showSearch");
String style = (String)request.getAttribute("clay:headless-data-set-display:style");
%>