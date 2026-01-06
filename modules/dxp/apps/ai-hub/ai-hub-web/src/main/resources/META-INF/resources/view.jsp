<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
TaskDefinitionDisplayContext taskDefinitionDisplayContext = (TaskDefinitionDisplayContext)request.getAttribute(TaskDefinitionDisplayContext.class.getName());
%>

<div class="ai-hub-tasks__list-container ml-8 mr-8 mt-5">
	<div class="mb-5">
		<h2><liferay-ui:message key="ai-tasks" /></h2>
	</div>

	<frontend-data-set:headless-display
		apiURL="<%= taskDefinitionDisplayContext.getAPIURL() %>"
		creationMenu="<%= taskDefinitionDisplayContext.getCreationMenu() %>"
		fdsActionDropdownItems="<%= taskDefinitionDisplayContext.getFDSActionDropdownItems() %>"
		id="<%= AIHubFDSNames.TASK_DEFINITIONS %>"
		itemsPerPage="<%= 20 %>"
		style="fluid"
	/>
</div>