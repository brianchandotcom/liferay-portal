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
WorkflowInstanceViewDisplayContext workflowInstanceViewDisplayContext = null;

if (portletName.equals(WorkflowInstancePortletKeys.WORKFLOW_INSTANCE)) {
	workflowInstanceViewDisplayContext = new WorkflowInstanceViewDisplayContext(renderRequest, renderResponse);
}
else {
	workflowInstanceViewDisplayContext = new MyWorkflowInstanceViewDisplayContext(renderRequest, renderResponse);
}

PortletURL portletURL = workflowInstanceViewDisplayContext.getViewPortletURL();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, workflowInstanceViewDisplayContext.getTabs2()), currentURL);
%>

<liferay-ui:breadcrumb
	showCurrentGroup="<%= false %>"
	showGuestGroup="<%= false %>"
	showLayout="<%= false %>"
	showParentGroups="<%= false %>"
/>

<liferay-ui:tabs
	names="pending,completed"
	param="tabs2"
	portletURL="<%= portletURL %>"
/>

<div class="container-fluid-1280">

	<liferay-ui:search-container
		searchContainer="<%= workflowInstanceViewDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.workflow.WorkflowInstance"
			cssClass="entry-display-style"
			modelVar="workflowInstance"
			stringKey="<%= true %>"
		>

			<liferay-ui:search-container-row-parameter
				name="workflowInstance"
				value="<%= workflowInstance %>"
			/>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/edit_workflow_instance.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="workflowInstanceId" value="<%= String.valueOf(workflowInstance.getWorkflowInstanceId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="definition"
				value="<%= workflowInstanceViewDisplayContext.getDefinition(workflowInstance) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="asset-title"
				value="<%= workflowInstanceViewDisplayContext.getAssetTitle(workflowInstance) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="asset-type"
				value="<%= workflowInstanceViewDisplayContext.getAssetType(workflowInstance) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="status"
				value="<%= workflowInstanceViewDisplayContext.getStatus(workflowInstance) %>"
			/>

			<liferay-ui:search-container-column-date
				href="<%= rowURL %>"
				name="last-activity-date"
				value="<%= workflowInstanceViewDisplayContext.getLastActivityDate(workflowInstance) %>"
			/>

			<liferay-ui:search-container-column-date
				href="<%= rowURL %>"
				name="end-date"
				value="<%= workflowInstanceViewDisplayContext.getEndDate(workflowInstance) %>"
			/>

			<c:if test="<%= workflowInstanceViewDisplayContext.isShowEntryAction() %>">
				<liferay-ui:search-container-column-jsp
					path="/workflow_instance_action.jsp"
				/>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>

</div>