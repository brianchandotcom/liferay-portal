<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FragmentDisplayContext fragmentDisplayContext = (FragmentDisplayContext)request.getAttribute(FragmentWebKeys.FRAGMENT_DISPLAY_CONTEXT);
%>

<li class="control-menu-nav-item">
	<react:component
		module="{FragmentAIAssistantTriggerButton} from fragment-web"
		props="<%= fragmentDisplayContext.getAIAssistantProps() %>"
	/>
</li>