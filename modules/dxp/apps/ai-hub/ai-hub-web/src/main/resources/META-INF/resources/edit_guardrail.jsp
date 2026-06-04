<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditGuardrailDisplayContext editGuardrailDisplayContext = (EditGuardrailDisplayContext)request.getAttribute(EditGuardrailDisplayContext.class.getName());
%>

<div>
	<react:component
		module="{GuardrailForm} from ai-hub-web"
		props="<%= editGuardrailDisplayContext.getReactData() %>"
	/>
</div>