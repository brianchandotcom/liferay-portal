<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/designer/init.jsp" %>

<%
KaleoDefinitionVersionSearch kaleoDefinitionVersionSearch = kaleoDesignerDisplayContext.getKaleoDefinitionVersionSearch();
%>

<liferay-ui:success key='<%= KaleoDesignerPortletKeys.KALEO_DESIGNER + "requestProcessed" %>' message='<%= (String)MultiSessionMessages.get(renderRequest, KaleoDesignerPortletKeys.KALEO_DESIGNER + "requestProcessed") %>' translateMessage="<%= false %>" />

<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>">

	<%
	RequiredWorkflowDefinitionException requiredWorkflowDefinitionException = (RequiredWorkflowDefinitionException)errorException;

	Object[] messageArguments = kaleoDesignerDisplayContext.getMessageArguments(requiredWorkflowDefinitionException.getWorkflowDefinitionLinks(), request);

	String messageKey = kaleoDesignerDisplayContext.getMessageKey(requiredWorkflowDefinitionException.getWorkflowDefinitionLinks());
	%>

	<liferay-ui:message arguments="<%= messageArguments %>" key="<%= messageKey %>" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-util:include page="/designer/management_bar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280 main-content-body">
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
				<portlet:param name="mvcPath" value='<%= "/designer/edit_kaleo_definition_version.jsp" %>' />
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
</div>