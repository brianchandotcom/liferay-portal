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

<frontend-data-set:headless-display
	apiURL="<%= taskDefinitionDisplayContext.getAPIURL() %>"
	creationMenu="<%= taskDefinitionDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= taskDefinitionDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= AIHubSiteInitializerFDSNames.TASK_DEFINITION %>"
	itemsPerPage="<%= 20 %>"
	style="fluid"
/>