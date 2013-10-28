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
WorkflowTaskDisplayTerms displayTerms = new WorkflowTaskDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_workflow_task_search"
>

	<aui:fieldset>
		<aui:input label="task" name="<%= displayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />

		<aui:select name="<%= displayTerms.TYPE %>">

			<%
			String displayTermsType = displayTerms.getType();

			List<WorkflowHandler> workflowHandlers = WorkflowHandlerRegistryUtil.getWorkflowHandlers();

			for (WorkflowHandler workflowHandler : workflowHandlers) {
				if (!workflowHandler.isAssetTypeSearchable()) {
					continue;
				}

				String defaultWorkflowHandlerType = workflowHandler.getClassName();
			%>

				<aui:option label="<%= workflowHandler.getType(locale) %>" selected="<%= displayTermsType.equals(defaultWorkflowHandlerType) %>" value="<%= defaultWorkflowHandlerType %>" />

			<%
			}
			%>

		</aui:select>
	</aui:fieldset>
</liferay-ui:search-toggle>