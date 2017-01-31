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
String tabs1 = ParamUtil.getString(request, "tabs1", "consumers");

PortletURL portletURL = renderResponse.createRenderURL();
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		portletURL.setParameter("tabs1", "consumers");
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="consumers" selected='<%= Objects.equals(tabs1, "consumers") %>' />

		<%
		portletURL.setParameter("tabs1", "producers");
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="producers" selected='<%= Objects.equals(tabs1, "producers") %>' />
	</aui:nav>
</aui:nav-bar>

<c:choose>
	<c:when test='<%= tabs1.equals("producers") %>'>
		<%@ include file="/admin/producers.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/admin/consumers.jspf" %>
	</c:otherwise>
</c:choose>