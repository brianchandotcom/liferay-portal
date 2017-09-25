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
String searchContainerId = "siteNavigationMenus";
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="menus" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>

		<%
		PortletURL portletURL = liferayPortletResponse.createRenderURL();
		%>

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar searchContainerId="<%= searchContainerId %>">
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= siteNavigationAdminDisplayContext.getDisplayViews() %>"
			portletURL="<%= siteNavigationAdminDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= siteNavigationAdminDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation label="all">
			<liferay-frontend:management-bar-filter-item active="<%= true %>" label="all" url="<%= siteNavigationAdminDisplayContext.getPortletURL().toString() %>" />
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= siteNavigationAdminDisplayContext.getOrderByCol() %>"
			orderByType="<%= siteNavigationAdminDisplayContext.getOrderByType() %>"
			orderColumns="<%= siteNavigationAdminDisplayContext.getOrderColumns() %>"
			portletURL="<%= siteNavigationAdminDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>