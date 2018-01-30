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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/directory/view");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<c:if test="<%= !portletName.equals(PortletKeys.FRIENDS_DIRECTORY) %>">
		<aui:nav cssClass="navbar-nav">

			<%
			portletURL.setParameter("tabs1", "users");
			%>

			<aui:nav-item href="<%= portletURL.toString() %>" label="users" selected='<%= tabs1.equals("users") %>' />

			<%
			portletURL.setParameter("tabs1", "organizations");
			%>

			<aui:nav-item href="<%= portletURL.toString() %>" label="organizations" selected='<%= tabs1.equals("organizations") %>' />

			<%
			portletURL.setParameter("tabs1", "user-groups");
			%>

			<aui:nav-item href="<%= portletURL.toString() %>" label="user-groups" selected='<%= tabs1.equals("user-groups") %>' />
		</aui:nav>
	</c:if>
</aui:nav-bar>