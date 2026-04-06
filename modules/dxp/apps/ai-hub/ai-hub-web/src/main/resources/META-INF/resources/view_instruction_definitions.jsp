<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewInstructionDefinitionsDisplayContext viewInstructionDefinitionsDisplayContext = (ViewInstructionDefinitionsDisplayContext)request.getAttribute(ViewInstructionDefinitionsDisplayContext.class.getName());
%>

<frontend-data-set:headless-display
	apiURL="<%= viewInstructionDefinitionsDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewInstructionDefinitionsDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewInstructionDefinitionsDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= AIHubFDSNames.INSTRUCTION_DEFINITIONS %>"
	itemsPerPage="<%= 20 %>"
	propsTransformer="{InstructionDefinitionItemTitlePropsTransformer} from ai-hub-web"
	style="fluid"
/>