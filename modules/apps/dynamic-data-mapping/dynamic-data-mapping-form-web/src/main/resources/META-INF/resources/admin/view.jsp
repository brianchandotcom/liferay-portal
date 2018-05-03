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

<%@ include file="/admin/init.jsp" %>

<%
String currentTab = ParamUtil.getString(request, "currentTab", "forms");
%>

<liferay-util:include page="/admin/navigation_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/admin/management_bar.jsp" servletContext="<%= application %>" />

<c:choose>
	<c:when test='<%= currentTab.equals("forms") %>'>
		<liferay-util:include page="/admin/view_form_instance.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= currentTab.equals("element-set") %>'>
		<liferay-util:include page="/admin/view_element_set.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>