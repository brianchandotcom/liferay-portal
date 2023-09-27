<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<liferay-util:include page="/pending_commerce_orders/b2c/view.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/pending_commerce_orders/b2b/view.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>