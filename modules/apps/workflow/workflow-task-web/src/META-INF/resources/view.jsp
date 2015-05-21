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
WorkflowTaskSearch workflowTaskSearch = null;

PortletURL portletURL = workflowTaskDisplayContext.getPortletURL();

WorkflowTaskDisplayTerms displayTerms = workflowTaskDisplayContext.getDisplayTerms();

String displayTermsType = displayTerms.getType();

String selectedTab = workflowTaskDisplayContext.getSelectedTab();

List<WorkflowHandler<?>> workflowHandlersOfSearchableAssets = workflowTaskDisplayContext.getWorkflowHandlersOfSearchableAssets();
%>

<liferay-ui:tabs
	names="pending,completed"
	portletURL="<%= portletURL %>"
/>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:nav-bar>
		<aui:nav-bar-search>
			<liferay-ui:search-toggle
				autoFocus="<%= workflowTaskDisplayContext.getWindowState().equals(WindowState.MAXIMIZED) %>"
				buttonLabel="search"
				displayTerms="<%= displayTerms %>"
				id="toggle_id_workflow_task_search">

				<aui:fieldset>
					<aui:input inlineField="<%= true %>" label="task" name="<%= WorkflowTaskConstants.NAME %>" size="20" value="<%= displayTerms.getName() %>" />

					<aui:select inlineField="<%= true %>" name="<%= WorkflowTaskConstants.TYPE %>">

						<%
						for (WorkflowHandler<?> workflowHandler : workflowHandlersOfSearchableAssets) {

							String defaultWorkflowHandlerType = workflowHandler.getClassName();
						%>

							<aui:option label="<%= workflowHandler.getType(locale) %>" selected="<%= displayTermsType.equals(defaultWorkflowHandlerType) %>" value="<%= defaultWorkflowHandlerType %>" />

						<%
						}
						%>

					</aui:select>
				</aui:fieldset>
			</liferay-ui:search-toggle>
		</aui:nav-bar-search>
	</aui:nav-bar>
	<c:choose>
		<c:when test="<%= workflowTaskDisplayContext.isPendingTab() %>">
			<liferay-ui:panel-container extended="<%= false %>" id="workflowTasksPanelContainer" persistState="<%= true %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="workflowMyTasksPanel" persistState="<%= true %>" title="assigned-to-me">

					<%
					workflowTaskSearch = workflowTaskDisplayContext.getPendingTasksAssignedToMe();
					%>

					<%@ include file="/workflow_tasks.jspf" %>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="workflowMyRolesTasksPanel" persistState="<%= true %>" title="assigned-to-my-roles">

					<%
					workflowTaskSearch = workflowTaskDisplayContext.getPendingTasksAssignedToMyRoles();
					%>

					<%@ include file="/workflow_tasks.jspf" %>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</c:when>
		<c:otherwise>
			<div class="separator"></div>

			<%
			workflowTaskSearch = workflowTaskDisplayContext.getCompletedTasksAssignedToMe();
			%>

			<%@ include file="/workflow_tasks.jspf" %>
		</c:otherwise>
	</c:choose>
</aui:form>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, selectedTab), currentURL);
%>