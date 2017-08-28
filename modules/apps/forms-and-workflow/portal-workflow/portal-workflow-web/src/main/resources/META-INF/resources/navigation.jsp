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
String searchPage = ParamUtil.getString(request, "searchPage");

String searchURL = ParamUtil.getString(request, "searchURL");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= workflowDefinitionTabVisible %>">
			<portlet:renderURL var="workflowRenderURL">
				<portlet:param name="tab" value="<%= WorkflowWebKeys.WORKFLOW_TAB_DEFINITION %>" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= workflowRenderURL.toString() %>"
				label="workflows"
				selected="<%= tab.equals(WorkflowWebKeys.WORKFLOW_TAB_DEFINITION) %>"
			/>
		</c:if>

		<c:if test="<%= workflowDefinitionLinkTabVisible %>">
			<portlet:renderURL var="schemesRenderURL">
				<portlet:param name="tab" value="<%= WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK %>" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= schemesRenderURL.toString() %>"
				label="schemes"
				selected="<%= tab.equals(WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK) %>"
			/>
		</c:if>

		<c:if test="<%= workflowInstanceTabVisible %>">
			<portlet:renderURL var="monitoringRenderURL">
				<portlet:param name="tab" value="<%= WorkflowWebKeys.WORKFLOW_TAB_INSTANCE %>" />
			</portlet:renderURL>

			<%
			String monitoringLabel = "monitoring";

			if (portletName.equals(WorkflowPortletKeys.USER_WORKFLOW)) {
				monitoringLabel = "my-submissions";
			}
			%>

			<aui:nav-item
				href="<%= monitoringRenderURL.toString() %>"
				label="<%= monitoringLabel %>"
				selected="<%= tab.equals(WorkflowWebKeys.WORKFLOW_TAB_INSTANCE) %>"
			/>
		</c:if>
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURL %>" method="post" name="fm1">
			<liferay-util:include page="<%= searchPage %>" servletContext="<%= application %>" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>