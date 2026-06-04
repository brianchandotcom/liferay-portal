<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewGuardrailsDisplayContext viewGuardrailsDisplayContext = (ViewGuardrailsDisplayContext)request.getAttribute(ViewGuardrailsDisplayContext.class.getName());
%>

<frontend-data-set:headless-display
	apiURL="<%= viewGuardrailsDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewGuardrailsDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewGuardrailsDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= AIHubFDSNames.GUARDRAILS %>"
	itemsPerPage="<%= 20 %>"
	propsTransformer="{ListTitlePropsTransformer} from ai-hub-web"
	style="fluid"
/>