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

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "categories");
%>

<c:choose>
	<c:when test='<%= tabs1.equals("cart") %>'>
		<liferay-util:include page="/cart.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("categories") %>'>
		<liferay-util:include page="/categories.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("coupons") %>'>
		<liferay-util:include page="/coupons.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("orders") && !user.isDefaultUser() %>'>
		<liferay-util:include page="/orders.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>