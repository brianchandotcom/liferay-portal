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

PortletURL portletURL = workflowTaskDisplayContext.getPortletURL();
%>

<div class="container-fluid-1280">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
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

				<%
				WorkflowTaskDisplayTerms workflowTaskDisplayTerms = workflowTaskDisplayContext.getWorkflowTaskDisplayTerms();
				%>

					<liferay-ui:search-toggle
						autoFocus="<%= workflowTaskDisplayContext.getWindowState().equals(WindowState.MAXIMIZED) %>"
						buttonLabel="search"
						displayTerms="<%= workflowTaskDisplayTerms %>"
						id="toggle_id_workflow_task_search"
						markupView="lexicon"
					>

						<aui:fieldset>
							<aui:input inlineField="<%= true %>" label="task" name="name" size="20" value="<%= workflowTaskDisplayTerms.getName() %>" />

							<aui:select inlineField="<%= true %>" name="type">

								<%
								for (WorkflowHandler<?> workflowHandler : workflowTaskDisplayContext.getSearchableAssetsWorkflowHandlers()) {
									String className = workflowHandler.getClassName();
								%>

									<aui:option label="<%= workflowHandler.getType(locale) %>" selected="<%= className.equals(workflowTaskDisplayTerms.getType()) %>" value="<%= workflowHandler.getClassName() %>" />

								<%
								}
								%>

							</aui:select>
						</aui:fieldset>
					</liferay-ui:search-toggle>
			</aui:nav-bar-search>
		</aui:nav-bar>

		<c:choose>
			<c:when test="<%= workflowTaskDisplayContext.isPendingTabSelected() %>">
				<liferay-ui:panel-container extended="<%= false %>" id="workflowTasksPanelContainer" persistState="<%= true %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="workflowMyTasksPanel" persistState="<%= true %>" title="assigned-to-me">

						<%
						WorkflowTaskSearch workflowTaskSearch = workflowTaskDisplayContext.getPendingTasksAssignedToMe();
						%>

						<%@ include file="/workflow_tasks.jspf" %>
					</liferay-ui:panel>

					<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="workflowMyRolesTasksPanel" persistState="<%= true %>" title="assigned-to-my-roles">

						<%
						WorkflowTaskSearch workflowTaskSearch = workflowTaskDisplayContext.getPendingTasksAssignedToMyRoles();
						%>

						<%@ include file="/workflow_tasks.jspf" %>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</c:when>
			<c:otherwise>
				<div class="separator"></div>

				<%
				WorkflowTaskSearch workflowTaskSearch = workflowTaskDisplayContext.getCompletedTasksAssignedToMe();
				%>

				<%@ include file="/workflow_tasks.jspf" %>
			</c:otherwise>
		</c:choose>
	</aui:form>
</div>