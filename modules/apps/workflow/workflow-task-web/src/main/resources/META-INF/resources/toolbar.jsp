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
String tabs1 = ParamUtil.getString(renderRequest, "tabs1", "assigned-to-me");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("tabs1", tabs1);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">
			<portlet:renderURL var="viewAssignedToMeURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="tabs1" value="assigned-to-me" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= viewAssignedToMeURL %>"
				label="assigned-to-me"
				selected='<%= tabs1.equals("assigned-to-me") %>'
			/>

			<portlet:renderURL var="viewAssignedToMyRolesURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="tabs1" value="assigned-to-my-roles" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= viewAssignedToMyRolesURL %>"
				label="assigned-to-my-roles"
				selected='<%= tabs1.equals("assigned-to-my-roles") %>'
			/>
		</aui:nav>
		<aui:nav-bar-search>
			<liferay-ui:search-form
				page="/search.jsp"
				servletContext="<%= application %>"
			/>
		</aui:nav-bar-search>
	</aui:nav-bar>
</aui:form>