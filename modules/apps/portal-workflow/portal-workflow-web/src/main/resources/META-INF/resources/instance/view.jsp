<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/instance/init.jsp" %>

<%
DateSearchEntry dateSearchEntry = new DateSearchEntry();

String displayStyle = workflowInstanceViewDisplayContext.getDisplayStyle();
%>

<aui:form action="<%= workflowInstanceViewDisplayContext.getViewPortletURL() %>" method="post" name="fm">
	<clay:management-toolbar
		clearResultsURL="<%= workflowInstanceViewDisplayContext.getClearResultsURL() %>"
		filterDropdownItems="<%= workflowInstanceViewDisplayContext.getFilterOptions(request) %>"
		itemsTotal="<%= workflowInstanceViewDisplayContext.getTotalItems() %>"
		searchActionURL="<%= workflowInstanceViewDisplayContext.getSearchURL() %>"
		searchContainerId="workflowInstance"
		searchFormName="fm1"
		selectable="<%= false %>"
		sortingOrder='<%= ParamUtil.getString(request, "orderByType", "asc") %>'
		sortingURL="<%= workflowInstanceViewDisplayContext.getSortingURL(request) %>"
		viewTypeItems="<%= workflowInstanceViewDisplayContext.getViewTypes() %>"
	/>
</aui:form>

<clay:container-fluid
	cssClass="workflow-instance-container"
>
	<%@ include file="/instance/workflow_instance.jspf" %>
</clay:container-fluid>