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

<c:choose>
	<c:when test="<%= WorkflowEngineManagerUtil.isDeployed() %>">
		<liferay-util:include page="/designer/navigation_bar.jsp" servletContext="<%= application %>" />

		<liferay-util:include page="/designer/management_bar.jsp" servletContext="<%= application %>" />

		<div class="container-fluid-1280 main-content-body">
			<liferay-ui:search-container
				emptyResultsMessage="no-workflow-definitions-are-defined"
				iteratorURL="<%= iteratorURL %>"
				orderByComparator="<%= kaleoDesignerDisplayContext.getKaleoDraftDefinitionOrderByComparator() %>"
				searchTerms="<%= new DisplayTerms(renderRequest) %>"
			>
				<liferay-ui:search-container-results>
					<%@ include file="/designer/kaleo_draft_definitions_search_results.jspf" %>
				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition"
					escapedModel="<%= false %>"
					keyProperty="kaleoDraftDefinitionId"
					modelVar="kaleoDraftDefinition"
				>
					<liferay-ui:search-container-column-text
						name="name"
						value="<%= HtmlUtil.escape(kaleoDraftDefinition.getName()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="title"
						value="<%= HtmlUtil.escape(kaleoDraftDefinition.getTitle(themeDisplay.getLanguageId())) %>"
					/>

					<liferay-ui:search-container-column-text
						name="version"
						value="<%= String.valueOf(kaleoDraftDefinition.getVersion()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="draft-version"
						value="<%= String.valueOf(kaleoDraftDefinition.getDraftVersion()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="published"
						value='<%= (kaleoDraftDefinition.getVersion() > 0) ? LanguageUtil.get(request, "yes") : LanguageUtil.get(request, "no") %>'
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/designer/kaleo_draft_definition_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" searchContainer="<%= searchContainer %>" />
			</liferay-ui:search-container>
		</div>

		<c:if test="<%= KaleoDesignerPermission.contains(permissionChecker, themeDisplay.getCompanyGroupId(), KaleoDesignerActionKeys.ADD_DRAFT) %>">
			<portlet:renderURL var="editKaleoDraftDefinitionURL">
				<portlet:param name="mvcPath" value='<%= "/designer/edit_kaleo_draft_definition.jsp" %>' />
				<portlet:param name="backURL" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu>
				<liferay-frontend:add-menu-item title='<%= LanguageUtil.format(request, "add-new-x", "definition") %>' url="<%= editKaleoDraftDefinitionURL.toString() %>" />
			</liferay-frontend:add-menu>
		</c:if>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="no-workflow-engine-is-deployed" />
		</div>
	</c:otherwise>
</c:choose>