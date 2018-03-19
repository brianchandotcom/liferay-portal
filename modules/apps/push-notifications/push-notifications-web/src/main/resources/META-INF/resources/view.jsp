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
String tabs1 = ParamUtil.getString(request, "tabs1", "devices");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL devicesURL = renderResponse.createRenderURL();

		devicesURL.setParameter("tabs1", "devices");
		%>

		<aui:nav-item href="<%= devicesURL.toString() %>" label="devices" selected='<%= tabs1.equals("devices") %>' />

		<%
		PortletURL testURL = renderResponse.createRenderURL();

		testURL.setParameter("tabs1", "test");
		%>

		<aui:nav-item href="<%= testURL.toString() %>" label="test" selected='<%= tabs1.equals("test") %>' />
	</aui:nav>
</aui:nav-bar>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test='<%= tabs1.equals("test") %>'>
			<%@ include file="/test.jspf" %>
		</c:when>
		<c:otherwise>
			<%@ include file="/devices.jspf" %>
		</c:otherwise>
	</c:choose>
</div>