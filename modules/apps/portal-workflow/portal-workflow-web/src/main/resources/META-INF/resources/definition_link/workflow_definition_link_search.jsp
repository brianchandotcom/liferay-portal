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

<%@ include file="/definition_link/init.jsp" %>

<%
WorkflowDefinitionLinkDisplayTerms displayTerms = new WorkflowDefinitionLinkDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_workflow_definition_link_search"
	markupView="lexicon"
>
	<aui:fieldset>
		<aui:input inlineField="<%= true %>" name="<%= WorkflowDefinitionLinkDisplayTerms.RESOURCE %>" size="20" type="text" value="<%= displayTerms.getResource() %>" />

		<aui:input inlineField="<%= true %>" name="<%= WorkflowDefinitionLinkDisplayTerms.WORKFLOW %>" size="20" type="text" value="<%= displayTerms.getWorkflow() %>" />
	</aui:fieldset>
</liferay-ui:search-toggle>