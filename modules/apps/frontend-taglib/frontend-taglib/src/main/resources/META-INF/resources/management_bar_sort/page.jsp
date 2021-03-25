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
String orderByCol = (String)request.getAttribute("liferay-frontend:management-bar-sort:orderByCol");
String orderByType = (String)request.getAttribute("liferay-frontend:management-bar-sort:orderByType");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-frontend:management-bar-sort:portletURL");
%>

<liferay-frontend:management-bar-filter
	disabled="<%= disabled %>"
	label="order-by"
	managementBarFilterItems='<%= (List<ManagementBarFilterItem>)request.getAttribute("liferay-frontend:management-bar-sort:managementBarFilterItems") %>'
	value='<%= (String)request.getAttribute("liferay-frontend:management-bar-sort:orderByColLabel") %>'
/>

<li>
	<liferay-frontend:management-bar-button
		active='<%= Validator.isNotNull(orderByType) && orderByType.equals("asc") %>'
		cssClass="d-none d-sm-block"
		disabled="<%= disabled %>"
		href='<%=
			PortletURLBuilder.create(
				PortletURLUtil.clone(portletURL, liferayPortletResponse)
			).setParameter(
				"orderByCol", orderByCol
			).setParameter(
				"orderByType", "asc"
			).buildString()
		%>'
		icon="caret-top"
		label="ascending"
	/>
</li>
<li>
	<liferay-frontend:management-bar-button
		active='<%= Validator.isNotNull(orderByType) && orderByType.equals("desc") %>'
		cssClass="d-none d-sm-block"
		disabled="<%= disabled %>"
		href='<%=
			PortletURLBuilder.create(
				PortletURLUtil.clone(portletURL, liferayPortletResponse)
			).setParameter(
				"orderByCol", orderByCol
			).setParameter(
				"orderByType", "desc"
			).buildString()
		%>'
		icon="caret-bottom"
		label="descending"
	/>
</li>