<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/designer/init.jsp" %>

<%
KaleoDefinitionVersionSearch kaleoDefinitionVersionSearch = kaleoDesignerDisplayContext.getKaleoDefinitionVersionSearch(displayedStatus);
%>

<liferay-ui:success key='<%= KaleoDesignerPortletKeys.KALEO_DESIGNER + "requestProcessed" %>' message='<%= (String)MultiSessionMessages.get(renderRequest, KaleoDesignerPortletKeys.KALEO_DESIGNER + "requestProcessed") %>' translateMessage="<%= false %>" />

<liferay-util:include page="/designer/management_bar.jsp" servletContext="<%= application %>" />

<clay:container-fluid
	cssClass="workflow-definition-container"
>
	<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>">
		<liferay-ui:message arguments="<%= kaleoDesignerDisplayContext.getMessageArguments((RequiredWorkflowDefinitionException)errorException) %>" key="<%= kaleoDesignerDisplayContext.getMessageKey((RequiredWorkflowDefinitionException)errorException) %>" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= IncompleteWorkflowInstancesException.class %>">
		<liferay-ui:message arguments="<%= kaleoDesignerDisplayContext.getMessageArguments((IncompleteWorkflowInstancesException)errorException) %>" key="<%= kaleoDesignerDisplayContext.getMessageKey((IncompleteWorkflowInstancesException)errorException) %>" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:search-container
		emptyResultsMessage="no-workflow-definitions-are-defined"
		id="<%= kaleoDesignerDisplayContext.getSearchContainerId() %>"
		searchContainer="<%= kaleoDefinitionVersionSearch %>"
	>

		<%
		request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
		%>

		<liferay-ui:search-container-row
			className="com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion"
			escapedModel="<%= false %>"
			keyProperty="kaleoDefinitionVersionId"
			modelVar="kaleoDefinitionVersion"
		>
			<liferay-portlet:renderURL portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="rowURL">
				<portlet:param name="mvcPath" value="/designer/edit_workflow_definition.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
				<portlet:param name="draftVersion" value="<%= kaleoDefinitionVersion.getVersion() %>" />
				<portlet:param name="clearSessionMessage" value="true" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="title"
				value="<%= kaleoDesignerDisplayContext.getTitle(kaleoDefinitionVersion) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="description"
				value="<%= HtmlUtil.escape(kaleoDefinitionVersion.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-date
				href="<%= rowURL %>"
				name="last-modified"
				userName="<%= kaleoDesignerDisplayContext.getUserName(kaleoDefinitionVersion) %>"
				value="<%= kaleoDesignerDisplayContext.getModifiedDate(kaleoDefinitionVersion) %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/designer/kaleo_definition_version_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			resultRowSplitter="<%= new KaleoDefinitionVersionResultRowSplitter() %>"
			searchContainer="<%= kaleoDefinitionVersionSearch %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>