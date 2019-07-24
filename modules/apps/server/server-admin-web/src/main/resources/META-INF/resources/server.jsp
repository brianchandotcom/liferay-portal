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

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.NORMAL) %>">
		<html:link page="/server_admin/view?windowState=maximized&portletMode=view&actionURL=0"><liferay-ui:message key="more" /></html:link> &raquo;
	</c:when>
	<c:otherwise>

		<%
		ServerDisplayContext serverDisplayContext = new ServerDisplayContext(request, renderResponse);
		%>

		<clay:navigation-bar
			inverted="<%= true %>"
			navigationItems="<%= serverDisplayContext.getServerNavigationItems() %>"
		/>

		<div class="<%= tabs1.equals("log-levels") ? StringPool.BLANK : "container-fluid-1280" %>">
			<c:choose>
				<c:when test='<%= tabs1.equals("log-levels") %>'>
					<liferay-util:include page="/log_levels.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("properties") %>'>
					<liferay-util:include page="/properties.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("data-migration") %>'>
					<liferay-util:include page="/data_migration.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("mail") %>'>
					<liferay-util:include page="/mail.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("external-services") %>'>
					<liferay-util:include page="/external_services.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("script") %>'>
					<liferay-util:include page="/script.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs1.equals("shutdown") %>'>
					<liferay-util:include page="/shutdown.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/resources.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>