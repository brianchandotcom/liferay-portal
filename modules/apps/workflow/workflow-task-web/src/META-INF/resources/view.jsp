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
String tabs1 = ParamUtil.getString(request, "tabs1", "pending");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
%>

<liferay-ui:tabs
	names="pending,completed"
	portletURL="<%= portletURL %>"
/>

<%
try {
	String type = "completed";
%>

	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:nav-bar-search>
			<liferay-util:include page="/workflow_search_tasks.jsp" servletContext="<%= application %>" />
		</aui:nav-bar-search>
		<c:choose>
			<c:when test='<%= tabs1.equals("pending") %>'>
				<liferay-ui:panel-container extended="<%= Boolean.FALSE %>" id="workflowTasksPanelContainer" persistState="<%= Boolean.TRUE %>">
					<liferay-ui:panel collapsible="<%= Boolean.TRUE %>" extended="<%= Boolean.FALSE %>" id="workflowMyTasksPanel" persistState="<%= Boolean.TRUE %>" title="assigned-to-me">

						<%
						type = "assigned-to-me";
						%>

						<%@ include file="/workflow_tasks.jspf" %>
					</liferay-ui:panel>

					<liferay-ui:panel collapsible="<%= Boolean.TRUE %>" extended="<%= Boolean.FALSE %>" id="workflowMyRolesTasksPanel" persistState="<%= Boolean.TRUE %>" title="assigned-to-my-roles">

						<%
						type = "assigned-to-my-roles";
						%>

						<%@ include file="/workflow_tasks.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</c:when>
			<c:otherwise>
				<div class="separator"></div>
				<%@ include file="/workflow_tasks.jspf" %>
			</c:otherwise>
		</c:choose>
	</aui:form>

<%
}
catch (Exception e) {
	if (_log.isWarnEnabled()) {
		_log.warn("Error retrieving tasks for user " + user.getUserId(), e);
	}
%>

	<div class="alert alert-danger">
		<liferay-ui:message key="an-error-occurred-while-retrieving-the-list-of-tasks" />
	</div>

<%
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs1), currentURL);
%>

<%!
private static Log _log = LogFactoryUtil.getLog("com.liferay.workflow.task.web.portlet.view_jsp");
%>