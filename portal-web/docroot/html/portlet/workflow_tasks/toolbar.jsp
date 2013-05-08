<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/workflow_tasks/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "assigned-to-me");
%>

<aui:nav-bar>
	<aui:nav>
		<portlet:renderURL var="assignedToMeURL">
			<portlet:param name="struts_action" value="/workflow_tasks/view" />
			<portlet:param name="toolbarItem" value="assigned-to-me" />
		</portlet:renderURL>

		<aui:nav-item href="<%= assignedToMeURL %>" label="view-all" selected='<%= toolbarItem.equals("view-all") %>' />

		<portlet:renderURL var="completedURL">
			<portlet:param name="struts_action" value="/workflow_tasks/view" />
			<portlet:param name="toolbarItem" value="my-completed-tasks" />
		</portlet:renderURL>

		<aui:nav-item href="<%= completedURL %>" iconClass="icon-plus" label="my-completed-tasks" selected='<%= toolbarItem.equals("my-completed-tasks") %>' />
	</aui:nav>
</aui:nav-bar>