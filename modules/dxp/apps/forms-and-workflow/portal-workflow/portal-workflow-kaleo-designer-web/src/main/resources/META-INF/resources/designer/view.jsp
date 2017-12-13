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

<%@ include file="/designer/init.jsp" %>

<%
PortletURL iteratorURL = kaleoDesignerDisplayContext.getBasePortletURL();

iteratorURL.setParameter("mvcPath", "/designer/view.jsp");
%>

<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>" message="you-cannot-deactivate-or-delete-this-definition" />

<c:choose>
	<c:when test="<%= WorkflowEngineManagerUtil.isDeployed() %>">
		<liferay-util:include page="/designer/navigation_bar.jsp" servletContext="<%= application %>" />

		<liferay-util:include page="/designer/management_bar.jsp" servletContext="<%= application %>" />

		<div class="container-fluid-1280 main-content-body">
			<liferay-ui:search-container
				emptyResultsMessage="no-workflow-definitions-are-defined"
				iteratorURL="<%= iteratorURL %>"
				orderByComparator="<%= kaleoDesignerDisplayContext.getKaleoDefinitionVersionOrderByComparator() %>"
				searchTerms="<%= new DisplayTerms(renderRequest) %>"
			>
				<liferay-ui:search-container-results>
					<%@ include file="/designer/kaleo_definition_versions_search_results.jspf" %>
				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion"
					escapedModel="<%= false %>"
					keyProperty="kaleoDefinitionVersionId"
					modelVar="kaleoDefinitionVersion"
				>
					<liferay-ui:search-container-column-text
						name="name"
						value="<%= HtmlUtil.escape(kaleoDefinitionVersion.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="title"
						value="<%= HtmlUtil.escape(kaleoDefinitionVersion.getTitle(themeDisplay.getLanguageId())) %>"
					/>

					<liferay-ui:search-container-column-text
						name="version"
						value="<%= kaleoDefinitionVersion.getVersion() %>"
					/>

					<liferay-ui:search-container-column-status
						name="status"
						status="<%= kaleoDefinitionVersion.getStatus() %>"
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/designer/kaleo_definition_version_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" resultRowSplitter="<%= new KaleoDefinitionVersionResultRowSplitter() %>" searchContainer="<%= searchContainer %>" />
			</liferay-ui:search-container>
		</div>

		<c:if test="<%= KaleoDesignerPermission.contains(permissionChecker, themeDisplay.getCompanyGroupId(), KaleoDesignerActionKeys.ADD_DRAFT) %>">
			<portlet:renderURL var="editKaleoDefinitionVersionURL">
				<portlet:param name="mvcPath" value='<%= "/designer/edit_kaleo_definition_version.jsp" %>' />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu>
				<liferay-frontend:add-menu-item title='<%= LanguageUtil.format(request, "add-new-x", "definition") %>' url="<%= editKaleoDefinitionVersionURL.toString() %>" />
			</liferay-frontend:add-menu>
		</c:if>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="no-workflow-engine-is-deployed" />
		</div>
	</c:otherwise>
</c:choose>