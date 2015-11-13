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
String tabs1 = ParamUtil.getString(request, "tabs1", "entries");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/announcements/view");
portletURL.setParameter("tabs1", tabs1);
%>

<c:if test="<%= !portletName.equals(AnnouncementsPortletKeys.ALERTS) || (portletName.equals(AnnouncementsPortletKeys.ALERTS) && AnnouncementsEntryPermission.contains(permissionChecker, layout, AnnouncementsPortletKeys.ALERTS, ActionKeys.ADD_ENTRY)) %>">
	<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>" />
</c:if>

<c:choose>
	<c:when test='<%= tabs1.equals("entries") %>'>
		<%@ include file="/view_entries.jspf" %>
	</c:when>
	<c:when test='<%= tabs1.equals("manage-entries") %>'>
		<%@ include file="/view_manage_entries.jspf" %>
	</c:when>
</c:choose>