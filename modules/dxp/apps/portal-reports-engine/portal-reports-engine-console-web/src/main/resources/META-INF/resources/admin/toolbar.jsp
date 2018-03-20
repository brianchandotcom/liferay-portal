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
PortletURL portletURL = reportsEngineDisplayContext.getPortletURL();

portletURL.setParameter("mvcPath", "/admin/view.jsp");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<c:if test="<%= reportsEngineDisplayContext.isAdminPortlet() %>">
		<aui:nav cssClass="navbar-nav">
			<portlet:renderURL var="viewReportsURL">
				<portlet:param name="mvcPath" value="/admin/view.jsp" />
				<portlet:param name="tabs1" value="reports" />
			</portlet:renderURL>

			<aui:nav-item href="<%= viewReportsURL %>" label="reports" selected="<%= reportsEngineDisplayContext.isReportsTabSelected() %>" />

			<c:if test="<%= hasAddDefinitionPermission %>">
				<portlet:renderURL var="viewDefinitionsURL">
					<portlet:param name="mvcPath" value="/admin/view.jsp" />
					<portlet:param name="tabs1" value="definitions" />
				</portlet:renderURL>

				<aui:nav-item href="<%= viewDefinitionsURL %>" label="definitions" selected="<%= reportsEngineDisplayContext.isDefinitionsTabSelected() %>" />
			</c:if>

			<c:if test="<%= hasAddSourcePermission %>">
				<portlet:renderURL var="viewSourcesURL">
					<portlet:param name="mvcPath" value="/admin/view.jsp" />
					<portlet:param name="tabs1" value="sources" />
				</portlet:renderURL>

				<aui:nav-item href="<%= viewSourcesURL %>" label="sources" selected="<%= reportsEngineDisplayContext.isSourcesTabSelected() %>" />
			</c:if>
		</aui:nav>
	</c:if>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
			<liferay-ui:search-form
				page="/admin/search.jsp"
				servletContext="<%= application %>"
			/>
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= !reportsEngineDisplayContext.isSearch() %>">
			<liferay-frontend:management-bar-display-buttons
				displayViews="<%= reportsEngineDisplayContext.getDisplayViews() %>"
				portletURL="<%= reportsEngineDisplayContext.getPortletURL() %>"
				selectedDisplayStyle="<%= reportsEngineDisplayContext.getDisplayStyle() %>"
			/>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= reportsEngineDisplayContext.getOrderByCol() %>"
			orderByType="<%= reportsEngineDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= reportsEngineDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>