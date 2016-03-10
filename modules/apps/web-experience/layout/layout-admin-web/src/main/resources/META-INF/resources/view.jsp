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
Group group = layoutsAdminDisplayContext.getGroup();

SitesUtil.addPortletBreadcrumbEntries(group, layoutsAdminDisplayContext.getPagesName(), layoutsAdminDisplayContext.getRedirectURL(), request, renderResponse);

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

String targetNode = null;
%>

<%@ include file="/layout_exception.jspf" %>

<div class="container-fluid-1280">
	<div class="lfr-app-column-view manage-view row">
		<c:choose>
			<c:when test="<%= layoutsAdminDisplayContext.getSelPlid() > 0 %>">
				<liferay-util:include page="/edit_layout.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/edit_layout_set.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</div>
</div>