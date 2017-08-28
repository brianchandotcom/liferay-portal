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

<%@ include file="/definition/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);

String navigation = ParamUtil.getString(request, "navigation", "definitions");
String definitionsNavigation = ParamUtil.getString(request, "definitionsNavigation");

String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL navigationPortletURL = renderResponse.createRenderURL();

navigationPortletURL.setParameter("mvcPath", "/definition/view.jsp");

if (delta > 0) {
	navigationPortletURL.setParameter("delta", String.valueOf(delta));
}

navigationPortletURL.setParameter("orderBycol", orderByCol);
navigationPortletURL.setParameter("orderByType", orderByType);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("definitionsNavigation", definitionsNavigation);

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (cur > 0) {
	displayStyleURL.setParameter("cur", String.valueOf(cur));
}

PortletURL searchURL = renderResponse.createRenderURL();

searchURL.setParameter("groupId", String.valueOf(themeDisplay.getScopeGroupId()));
searchURL.setParameter("mvcPath", "/definition/view.jsp");
searchURL.setParameter("tab", "workflows");

WorkflowDefinitionSearch workflowDefinitionSearch = new WorkflowDefinitionSearch(renderRequest, portletURL);
%>

<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>" message="you-cannot-deactivate-or-delete-this-definition" />

<liferay-util:include page="/definition/add_button.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchPage" value="/definition/workflow_definition_search.jsp" />
	<liferay-util:param name="searchURL" value="<%= searchURL.toString() %>" />
</liferay-util:include>

<liferay-frontend:management-bar
	searchContainerId="workflowDefinitions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			navigationParam="definitionsNavigation"
			portletURL="<%= navigationPortletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"active", "name", "title"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280 workflow-definition-container">
	<liferay-ui:search-container
		emptyResultsMessage="no-workflow-definitions-are-defined"
		id="workflowDefinitions"
		searchContainer="<%= workflowDefinitionSearch %>"
	>

		<%
		request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
		%>

		<liferay-ui:search-container-results
			results="<%= workflowDefinitionDisplayContext.getSearchContainerResults(searchContainer) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.workflow.WorkflowDefinition"
			modelVar="workflowDefinition"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcPath", "/definition/edit_workflow_definition.jsp");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("name", workflowDefinition.getName());
			rowURL.setParameter("version", String.valueOf(workflowDefinition.getVersion()));
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				value="<%= workflowDefinitionDisplayContext.getName(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="title"
				value="<%= workflowDefinitionDisplayContext.getTitle(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="version"
				value="<%= workflowDefinitionDisplayContext.getVersion(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="active"
				value="<%= workflowDefinitionDisplayContext.getActive(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/definition/workflow_definition_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" resultRowSplitter="<%= new WorkflowDefinitionResultRowSplitter() %>" searchContainer="<%= workflowDefinitionSearch %>" />
	</liferay-ui:search-container>
</div>