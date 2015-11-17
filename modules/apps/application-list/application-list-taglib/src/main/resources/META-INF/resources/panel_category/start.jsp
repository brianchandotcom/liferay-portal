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

<%@ include file="/panel_category/init.jsp" %>

<c:if test="<%= !panelApps.isEmpty() || showHeader %>">
	<a aria-expanded="false" class="collapse-icon <%= active ? StringPool.BLANK : "collapsed" %> list-group-heading" data-toggle="collapse" href="#<%= id %>">
		<c:if test="<%= !panelCategory.includeHeader(request, new PipingServletResponse(pageContext)) %>">
			<%= panelCategory.getLabel(themeDisplay.getLocale()) %>

			<%
			int notificationsCount = panelCategoryHelper.getNotificationsCount(panelCategory.getKey(), permissionChecker, themeDisplay.getScopeGroup());
			%>

			<c:if test="<%= notificationsCount > 0 %>">
				<span class="badge badge-sm badge-warning"><%= notificationsCount %></span>
			</c:if>
		</c:if>
	</a>

	<div class="collapse <%= active ? "in" : StringPool.BLANK %>" id="<%= id %>">
		<div class="list-group-item">
</c:if>