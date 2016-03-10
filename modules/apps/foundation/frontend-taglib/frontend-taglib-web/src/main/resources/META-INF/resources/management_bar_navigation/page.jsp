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

<%@ include file="/management_bar_navigation/init.jsp" %>

<%
boolean disabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:management-bar-navigation:disabled"));
List<ManagementBarFilterItem> managementBarFilterItems = (List<ManagementBarFilterItem>)request.getAttribute("liferay-frontend:management-bar-navigation:managementBarFilterItems");
String label = (String)request.getAttribute("liferay-frontend:management-bar-navigation:label");
%>

<liferay-frontend:management-bar-filter
	disabled="<%= disabled %>"
	managementBarFilterItems="<%= managementBarFilterItems %>"
	value="<%= label %>"
/>