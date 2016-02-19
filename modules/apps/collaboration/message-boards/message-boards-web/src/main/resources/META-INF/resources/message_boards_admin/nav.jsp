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

<%@ include file="/message_boards/init.jsp" %>

<%
String navItemSelected = ParamUtil.getString(request, "navItemSelected");
%>

<aui:nav cssClass="navbar-nav">

	<%
	PortletURL messageBoardsHomeURL = renderResponse.createRenderURL();

	messageBoardsHomeURL.setParameter("mvcRenderCommandName", "/message_boards/view");
	messageBoardsHomeURL.setParameter("tag", StringPool.BLANK);
	%>

	<aui:nav-item
		href="<%= messageBoardsHomeURL.toString() %>"
		label="threads"
		selected='<%= navItemSelected.equals("threads") %>'
	/>

	<%
	PortletURL viewStatisticsURL = renderResponse.createRenderURL();

	viewStatisticsURL.setParameter("mvcRenderCommandName", "/message_boards/view_statistics");
	%>

	<aui:nav-item
		href="<%= viewStatisticsURL.toString() %>"
		label="statistics"
		selected='<%= navItemSelected.equals("statistics") %>'
	/>

	<%
	PortletURL bannedUsersURL = renderResponse.createRenderURL();

	bannedUsersURL.setParameter("mvcRenderCommandName", "/message_boards/view_banned_users");
	%>

	<aui:nav-item
		href="<%= bannedUsersURL.toString() %>"
		label="banned-users"
		selected='<%= navItemSelected.equals("banned-users") %>'
	/>
</aui:nav>