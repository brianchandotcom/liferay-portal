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
String tabs1 = ParamUtil.getString(request, "tabs1", "calendar");

PortletURL portletURL = renderResponse.createRenderURL();
%>

<div id="<portlet:namespace />alert"></div>

<c:if test="<%= themeDisplay.isSignedIn() %>">

	<aui:nav-bar cssClass="collapse-basic-search container-fluid" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">

			<%
			portletURL.setParameter("tabs1", "calendar");
			%>

			<aui:nav-item
				href="<%= portletURL.toString() %>"
				label="calendar"
				selected='<%= tabs1.equals("calendar") %>'
			/>

			<%
			portletURL.setParameter("tabs1", "resources");
			%>

			<aui:nav-item
				href="<%= portletURL.toString() %>"
				label="resources"
				selected='<%= tabs1.equals("resources") %>'
			/>

		</aui:nav>

		<c:if test='<%= tabs1.equals("resources") %>'>
			<aui:nav-bar-search>

				<liferay-portlet:renderURL varImpl="searchURL" />

				<aui:form action="<%= searchURL %>" method="get" name="fm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="mvcPath" type="hidden" value="/view.jsp" />
					<aui:input name="tabs1" type="hidden" value="resources" />

					<liferay-ui:search-form
						page="/calendar_resource_search.jsp"
						servletContext="<%= application %>"
					/>
				</aui:form>
			</aui:nav-bar-search>
		</c:if>
	</aui:nav-bar>
</c:if>

<c:choose>
	<c:when test='<%= tabs1.equals("calendar") %>'>
		<liferay-util:include page="/view_calendar.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("resources") %>'>
		<liferay-util:include page="/view_calendar_resources.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>