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
String tabs1 = ParamUtil.getString(request, "tabs1", "processes");

PortletURL portletURL = renderResponse.createRenderURL();
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		portletURL.setParameter("tabs1", "processes");
		%>

		<aui:nav-item
			href="<%= portletURL.toString() %>"
			label="processes"
			selected='<%= tabs1.equals("processes") %>'
		/>

		<%
		portletURL.setParameter("tabs1", "scheduled");
		%>

		<aui:nav-item
			href="<%= portletURL.toString() %>"
			label="scheduled"
			selected='<%= tabs1.equals("scheduled") %>'
		/>
	</aui:nav>
</aui:nav-bar>

<div class="container-fluid-1280" id="<portlet:namespace />processesContainer">
	<c:choose>
		<c:when test='<%= tabs1.equals("processes") %>'>
			<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

			<liferay-util:include page="/processes_list/view.jsp" servletContext="<%= application %>" />

			<liferay-util:include page="/add_button.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs1.equals("scheduled") %>'>
			<liferay-util:include page="/scheduled_list/view.jsp" servletContext="<%= application %>" />
		</c:when>
	</c:choose>
</div>