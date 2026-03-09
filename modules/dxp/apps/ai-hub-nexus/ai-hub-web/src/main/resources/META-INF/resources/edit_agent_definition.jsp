<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditAgentDefinitionDisplayContext editAgentDefinitionDisplayContext = (EditAgentDefinitionDisplayContext)request.getAttribute(EditAgentDefinitionDisplayContext.class.getName());
%>

<div>
	<react:component
		module="{AgentDefinitionForm} from ai-hub-web"
		props="<%= editAgentDefinitionDisplayContext.getReactData() %>"
	/>
</div>