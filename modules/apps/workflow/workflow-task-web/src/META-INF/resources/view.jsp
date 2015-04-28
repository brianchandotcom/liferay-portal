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

WorkflowTaskViewDisplayContext displayContext = (WorkflowTaskViewDisplayContext)request.getAttribute(WebKeys.WORKFLOW_TASK_DISPLAY_CONTEXT);

PortletURL portletURL = displayContext.getPortletURL();

WorkflowTaskDisplayTerms displayTerms = displayContext.getDisplayTerms();

String displayTermsType = displayTerms.getType();

String selectedTab = displayContext.getSelectedTab();

List<WorkflowHandler<?>> workflowHandlersOfSearchableAssets = displayContext.getWorkflowHandlersOfSearchableAssets();

%>

<liferay-ui:tabs
	names="pending,completed"
	portletURL="<%= portletURL %>"
/>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:nav-bar>
		<aui:nav-bar-search>
			<liferay-ui:search-toggle
				autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
				buttonLabel="search"
				displayTerms="<%= displayTerms %>"
				id="toggle_id_workflow_task_search">
			
				<aui:fieldset>
					<aui:input inlineField="<%= Boolean.TRUE %>" label="task" name="<%= WorkflowTaskDisplayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />
			
					<aui:select inlineField="<%= Boolean.TRUE %>" name="<%= WorkflowTaskDisplayTerms.TYPE %>">
			
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
		<c:when test='<%= displayContext.isPendingTab() %>'>
			<liferay-ui:panel-container extended="<%= Boolean.FALSE %>" id="workflowTasksPanelContainer" persistState="<%= Boolean.TRUE %>">
				<liferay-ui:panel collapsible="<%= Boolean.TRUE %>" extended="<%= Boolean.FALSE %>" id="workflowMyTasksPanel" persistState="<%= Boolean.TRUE %>" title="assigned-to-me">

					<%
					workflowTaskSearch = (WorkflowTaskSearch)request.getAttribute(WebKeys.WORKFLOW_PENDING_TASKS_ASSIGNED_TO_ME);
					%>

					<%@ include file="/workflow_tasks.jspf" %>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= Boolean.TRUE %>" extended="<%= Boolean.FALSE %>" id="workflowMyRolesTasksPanel" persistState="<%= Boolean.TRUE %>" title="assigned-to-my-roles">

					<%
					workflowTaskSearch = (WorkflowTaskSearch)request.getAttribute(WebKeys.WORKFLOW_PENDING_TASKS_ASSIGNED_TO_MY_ROLES);
					%>

					<%@ include file="/workflow_tasks.jspf" %>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</c:when>
		<c:otherwise>
			<div class="separator"></div>
			<%
			workflowTaskSearch = (WorkflowTaskSearch)request.getAttribute(WebKeys.WORKFLOW_MY_COMPLETED_TASKS);
			%>
			<%@ include file="/workflow_tasks.jspf" %>
		</c:otherwise>
	</c:choose>
</aui:form>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, selectedTab), currentURL);
%>
