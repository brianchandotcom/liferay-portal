<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/definition/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowDefinition workflowDefinition = (WorkflowDefinition)row.getObject();

String userName = workflowDefinitionDisplayContext.getUserNameOrBlank(workflowDefinition);
%>

<clay:content-col
	expand="<%= true %>"
>
	<div class="list-group-title"><%= dateFormatTime.format(workflowDefinition.getModifiedDate()) %></div>
	<div class="list-group-subtitle"><%= HtmlUtil.escape(userName) %></div>
</clay:content-col>