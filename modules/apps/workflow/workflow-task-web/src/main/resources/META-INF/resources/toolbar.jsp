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
String tabs1 = ParamUtil.getString(renderRequest, "tabs1", "pending");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("tabs1", tabs1);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
	<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">
			<portlet:renderURL var="viewPendingURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="tabs1" value="pending" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= viewPendingURL %>"
				label="pending"
				selected='<%= tabs1.equals("pending") %>'
			/>

			<portlet:renderURL var="viewCompletedURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="tabs1" value="completed" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= viewCompletedURL %>"
				label="completed"
				selected='<%= tabs1.equals("completed") %>'
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