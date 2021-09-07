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
CommerceChannel commerceChannel = commerceOrderContentDisplayContext.fetchCommerceChannel();
%>

<c:choose>
	<c:when test="<%= commerceChannel == null %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="the-site-does-not-belong-to-any-channel" />
		</div>
	</c:when>
	<c:when test="<%= commerceOrderContentDisplayContext.isCommerceSiteTypeB2C() %>">
		<liferay-util:include page="/placed_commerce_orders/b2c/view.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/placed_commerce_orders/b2b/view.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>