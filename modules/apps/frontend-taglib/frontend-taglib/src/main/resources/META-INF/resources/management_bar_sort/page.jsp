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

<%@ include file="/management_bar_sort/init.jsp" %>

<%
boolean disabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:management-bar-sort:disabled"));
List<ManagementBarFilterItem> managementBarFilterItems = (List<ManagementBarFilterItem>)request.getAttribute("liferay-frontend:management-bar-sort:managementBarFilterItems");
String orderByCol = (String)request.getAttribute("liferay-frontend:management-bar-sort:orderByCol");
String orderByColLabel = (String)request.getAttribute("liferay-frontend:management-bar-sort:orderByColLabel");
String orderByType = (String)request.getAttribute("liferay-frontend:management-bar-sort:orderByType");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:management-bar-sort:portletURL");
%>

<liferay-frontend:management-bar-filter
	disabled="<%= disabled %>"
	label="order-by"
	managementBarFilterItems="<%= managementBarFilterItems %>"
	value="<%= orderByColLabel %>"
/>

<%
PortletURL orderByColAscURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

orderByColAscURL.setParameter("orderByCol", orderByCol);
orderByColAscURL.setParameter("orderByType", "asc");
%>

<li>
	<liferay-frontend:management-bar-button
		active='<%= Validator.isNotNull(orderByType) && orderByType.equals("asc") %>'
		cssClass="hidden-xs"
		disabled="<%= disabled %>"
		href="<%= orderByColAscURL.toString() %>"
		icon="caret-top"
		label="ascending"
	/>
</li>

<%
PortletURL orderByColDescURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

orderByColDescURL.setParameter("orderByCol", orderByCol);
orderByColDescURL.setParameter("orderByType", "desc");
%>

<li>
	<liferay-frontend:management-bar-button
		active='<%= Validator.isNotNull(orderByType) && orderByType.equals("desc") %>'
		cssClass="hidden-xs"
		disabled="<%= disabled %>"
		href="<%= orderByColDescURL.toString() %>"
		icon="caret-bottom"
		label="descending"
	/>
</li>