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

<%@ page import="com.liferay.frontend.data.set.model.FDSPaginationEntry" %><%@
page import="com.liferay.frontend.data.set.model.FDSSortItemList" %><%@
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
String actionParameterName = (String)request.getAttribute("frontend-data-set:classic-display:actionParameterName");
String activeViewSettingsJSON = GetterUtil.getString(request.getAttribute("frontend-data-set:classic-display:activeViewSettingsJSON"), "{}");
String apiURL = (String)request.getAttribute("frontend-data-set:classic-display:apiURL");
String appURL = (String)request.getAttribute("frontend-data-set:classic-display:appURL");
List<DropdownItem> bulkActionDropdownItems = (List<DropdownItem>)request.getAttribute("frontend-data-set:classic-display:bulkActionDropdownItems");
Object dataSetDisplayViewsContext = request.getAttribute("frontend-data-set:classic-display:dataSetDisplayViewsContext");
CreationMenu creationMenu = (CreationMenu)request.getAttribute("frontend-data-set:classic-display:creationMenu");
String dataProviderKey = (String)request.getAttribute("frontend-data-set:classic-display:dataProviderKey");
String formId = (String)request.getAttribute("frontend-data-set:classic-display:formId");
String formName = (String)request.getAttribute("frontend-data-set:classic-display:formName");
String id = (String)request.getAttribute("frontend-data-set:classic-display:id");
int itemsPerPage = (int)request.getAttribute("frontend-data-set:classic-display:itemsPerPage");
String namespace = (String)request.getAttribute("frontend-data-set:classic-display:namespace");
String nestedItemsKey = (String)request.getAttribute("frontend-data-set:classic-display:nestedItemsKey");
String nestedItemsReferenceKey = (String)request.getAttribute("frontend-data-set:classic-display:nestedItemsReferenceKey");
int pageNumber = (int)request.getAttribute("frontend-data-set:classic-display:pageNumber");
List<FDSPaginationEntry> fdsPaginationEntries = (List<FDSPaginationEntry>)request.getAttribute("frontend-data-set:classic-display:fdsPaginationEntries");
PortletURL portletURL = (PortletURL)request.getAttribute("frontend-data-set:classic-display:portletURL");
List<String> selectedItems = (List<String>)request.getAttribute("frontend-data-set:classic-display:selectedItems");
String selectedItemsKey = (String)request.getAttribute("frontend-data-set:classic-display:selectedItemsKey");
String selectionType = (String)request.getAttribute("frontend-data-set:classic-display:selectionType");
boolean showManagementBar = (boolean)request.getAttribute("frontend-data-set:classic-display:showManagementBar");
boolean showPagination = (boolean)request.getAttribute("frontend-data-set:classic-display:showPagination");
boolean showSearch = (boolean)request.getAttribute("frontend-data-set:classic-display:showSearch");
FDSSortItemList fdsSortItemList = (FDSSortItemList)request.getAttribute("frontend-data-set:headless-display:sortItemList");
String style = (String)request.getAttribute("frontend-data-set:classic-display:style");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_step_tracker") + StringPool.UNDERLINE;

String containerId = randomNamespace + "table-id";
%>