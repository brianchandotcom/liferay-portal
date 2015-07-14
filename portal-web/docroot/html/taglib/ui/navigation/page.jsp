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

<%@ include file="/html/taglib/ui/navigation/init.jsp" %>

<c:if test="<%= layout != null %>">

	<div class="nav-menu nav-menu-style-<%= bulletStyle %>">
		<c:choose>
			<c:when test='<%= headerType.equals("root-layout") && (rootNavItem != null) %>'>
				<h2>
					<a href="<%= rootNavItem.getRegularURL() %>" <%= rootNavItem.getTarget() %>><%= rootNavItem.getName() %></a>
				</h2>
			</c:when>
			<c:when test='<%= headerType.equals("portlet-title") %>'>
				<h2><%= HtmlUtil.escape(portletDisplay.getTitle()) %></h2>
			</c:when>
			<c:when test='<%= headerType.equals("breadcrumb") %>'>
				<liferay-ui:breadcrumb />
			</c:when>
			<c:when test="<%= preview && (navigationString.length() == 0) %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-are-no-pages-to-display-for-the-current-page-level" />
				</div>
			</c:when>
		</c:choose>

		<%= navigationString %>
	</div>
</c:if>