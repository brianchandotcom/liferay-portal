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
CommerceAccountDisplayContext commerceAccountDisplayContext = (CommerceAccountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceAccount> commerceAccounts = commerceAccountDisplayContext.getCommerceAccounts();
%>

<c:choose>
	<c:when test="<%= !commerceAccountDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= commerceAccountDisplayContext.getCommerceSiteType() == CommerceAccountConstants.SITE_TYPE_B2C %>">
				<liferay-util:include page="/edit_user.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test="<%= (commerceAccounts.size() > 1) || commerceAccountDisplayContext.hasManageCommerceAccountPermissions() %>">
				<liferay-util:include page="/view_account_list.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test="<%= !commerceAccounts.isEmpty() && commerceAccountDisplayContext.hasCommerceAccountModelPermissions(commerceAccounts.get(0), ActionKeys.VIEW) %>">
				<liferay-util:include page="/view_account.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/view_user.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>