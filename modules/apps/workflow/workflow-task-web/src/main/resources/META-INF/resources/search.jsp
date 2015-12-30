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