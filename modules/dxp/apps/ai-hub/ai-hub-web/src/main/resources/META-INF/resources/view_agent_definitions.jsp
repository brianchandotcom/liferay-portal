<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewAgentDefinitionsDisplayContext viewAgentDefinitionsDisplayContext = (ViewAgentDefinitionsDisplayContext)request.getAttribute(ViewAgentDefinitionsDisplayContext.class.getName());
%>

<div class="ai-hub-agent-definitions__list-container ml-8 mr-8 mt-5">
	<div class="mb-5">
		<h2><liferay-ui:message key="agents" /></h2>
	</div>
</div>

<frontend-data-set:headless-display
	apiURL="<%= viewAgentDefinitionsDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewAgentDefinitionsDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewAgentDefinitionsDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= AIHubFDSNames.AGENT_DEFINITIONS %>"
	itemsPerPage="<%= 20 %>"
	style="fluid"
/>