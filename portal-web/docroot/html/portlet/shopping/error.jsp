<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
String referer = request.getHeader(HttpHeaders.REFERER);
%>

<liferay-ui:header
	backURL='<%= Validator.isNotNull(referer) ? referer : "javascript:history.go(-1);" %>'
	title="error"
/>

<liferay-ui:error exception="<%= NoSuchCategoryException.class %>" message="the-category-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchCouponException.class %>" message="the-coupon-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchItemException.class %>" message="the-item-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchOrderException.class %>" message="the-order-could-not-be-found" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />