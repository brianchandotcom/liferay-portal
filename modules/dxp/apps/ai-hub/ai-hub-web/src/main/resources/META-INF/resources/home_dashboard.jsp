<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
HomeDashboardDisplayContext homeDashboardDisplayContext = (HomeDashboardDisplayContext)request.getAttribute(HomeDashboardDisplayContext.class.getName());

if (themeDisplay.isSignedIn()) {
%>

	<style>
		.bg-gradient--blue-white {
			display: none;
		}
	</style>

	<div>
		<react:component
			module="{HomeDashboard} from ai-hub-web"
			props="<%= homeDashboardDisplayContext.getReactData() %>"
		/>
	</div>

<%
}
%>
