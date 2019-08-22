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
RoleTypeContributor roleTypeContributor = RoleTypeContributorRetrieverUtil.getCurrentRoleTypeContributor(request);
%>

<c:choose>
	<c:when test="<%= roleTypeContributor.getType() == RoleConstants.TYPE_ORGANIZATION %>">
		<%@ include file="/select_organization_role.jspf" %>
	</c:when>
	<c:when test="<%= roleTypeContributor.getType() == RoleConstants.TYPE_SITE %>">
		<%@ include file="/select_site_role.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/select_regular_role.jspf" %>
	</c:otherwise>
</c:choose>