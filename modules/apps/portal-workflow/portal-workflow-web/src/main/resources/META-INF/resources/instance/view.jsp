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

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new WorkflowInstanceViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, workflowInstanceViewDisplayContext.getSearchContainer()) %>"
/>

<clay:container-fluid
	cssClass="workflow-instance-container"
>
	<%@ include file="/instance/workflow_instance.jspf" %>
</clay:container-fluid>