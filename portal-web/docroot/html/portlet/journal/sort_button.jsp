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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

String orderByType = ParamUtil.getString(request, "orderByType");

String reverseOrderByType = "asc";

if (orderByType.equals("asc")) {
	reverseOrderByType = "desc";
}
%>

<aui:nav-item dropdown="<%= true %>" id="sortButtonContainer" label="sort-by">
	<portlet:renderURL var="sortDisplayDatetURL">
		<portlet:param name="struts_action" value="/journal/view" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="ddmStructureKey" value="<%= ddmStructureKey %>" />
		<portlet:param name="orderByCol" value="display-date" />
		<portlet:param name="orderByType" value="<%= reverseOrderByType %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= sortDisplayDatetURL %>" iconCssClass="icon-calendar" label="display-date" />

	<portlet:renderURL var="sortModifiedDatetURL">
		<portlet:param name="struts_action" value="/journal/view" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="ddmStructureKey" value="<%= ddmStructureKey %>" />
		<portlet:param name="orderByCol" value="modified-date" />
		<portlet:param name="orderByType" value="<%= reverseOrderByType %>" />
	</portlet:renderURL>

	<aui:nav-item href="<%= sortModifiedDatetURL %>" iconCssClass="icon-calendar" label="modified-date" />
</aui:nav-item>