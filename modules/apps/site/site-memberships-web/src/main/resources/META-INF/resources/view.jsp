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
String tabs1 = siteMembershipsDisplayContext.getTabs1();
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL usersURL = siteMembershipsDisplayContext.getPortletURL();

		usersURL.setParameter("tabs1", "users");
		%>

		<aui:nav-item href="<%= usersURL.toString() %>" label="users" selected='<%= tabs1.equals("users") %>' />

		<%
		PortletURL organizationsURL = siteMembershipsDisplayContext.getPortletURL();

		organizationsURL.setParameter("tabs1", "organizations");
		%>

		<aui:nav-item href="<%= organizationsURL.toString() %>" label="organizations" selected='<%= tabs1.equals("organizations") %>' />

		<%
		PortletURL userGroupsURL = siteMembershipsDisplayContext.getPortletURL();

		userGroupsURL.setParameter("tabs1", "user-groups");
		%>

		<aui:nav-item href="<%= userGroupsURL.toString() %>" label="user-groups" selected='<%= tabs1.equals("user-groups") %>' />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= siteMembershipsDisplayContext.getPortletURL() %>" name="searchFm">
			<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<c:choose>
	<c:when test='<%= tabs1.equals("users") %>'>
		<liferay-util:include page="/users.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("organizations") %>'>
		<liferay-util:include page="/organizations.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("user-groups") %>'>
		<liferay-util:include page="/user_groups.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>

<%
Group group = siteMembershipsDisplayContext.getGroup();

PortalUtil.addPortletBreadcrumbEntry(request, group.getDescriptiveName(locale), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), currentURL);
%>