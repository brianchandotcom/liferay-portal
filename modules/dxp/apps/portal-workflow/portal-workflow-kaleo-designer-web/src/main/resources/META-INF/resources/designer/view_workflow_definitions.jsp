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

<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" portletName="<%= KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW %>" varImpl="portletURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</liferay-portlet:renderURL>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);

String definitionsNavigation = ParamUtil.getString(request, "definitionsNavigation");

portletURL.setParameter("definitionsNavigation", definitionsNavigation);

int displayedStatus = KaleoDefinitionVersionConstants.STATUS_ALL;

if (StringUtil.equals(definitionsNavigation, "published")) {
	displayedStatus = KaleoDefinitionVersionConstants.STATUS_PUBLISHED;
}
else if (StringUtil.equals(definitionsNavigation, "not-published")) {
	displayedStatus = KaleoDefinitionVersionConstants.STATUS_NOT_PUBLISHED;
}

String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL navigationPortletURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (delta > 0) {
	navigationPortletURL.setParameter("delta", String.valueOf(delta));
}

navigationPortletURL.setParameter("orderByCol", orderByCol);
navigationPortletURL.setParameter("orderByType", orderByType);

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (cur > 0) {
	displayStyleURL.setParameter("cur", String.valueOf(cur));
}

KaleoDefinitionVersionSearch kaleoDefinitionVersionSearch = new KaleoDefinitionVersionSearch(renderRequest, portletURL);
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

<liferay-frontend:management-bar
	searchContainerId="kaleoDefinitionVersions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= displayStyleURL %>"
			selectedDisplayStyle="list"
		/>

		<liferay-util:include page="/designer/add_button.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "published", "not-published"} %>'
			navigationParam="definitionsNavigation"
			portletURL="<%= navigationPortletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"title", "last-modified"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280 main-content-body">
	<liferay-ui:search-container
		emptyResultsMessage="no-workflow-definitions-are-defined"
		id="kaleoDefinitionVersions"
		searchContainer="<%= kaleoDefinitionVersionSearch %>"
	>

		<%
		request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
		%>

		<liferay-ui:search-container-results
			results="<%= kaleoDesignerDisplayContext.getSearchContainerResults(searchContainer, displayedStatus, permissionChecker) %>"
		/>

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