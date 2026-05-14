<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
HomeDashboardDisplayContext homeDashboardDisplayContext = (HomeDashboardDisplayContext)request.getAttribute(HomeDashboardDisplayContext.class.getName());
%>

<c:if test="<%= themeDisplay.isSignedIn() %>">
	<aui:style type="text/css">
		.bg-gradient--blue-white {
			display: none;
		}
	</aui:style>

	<div class="home-dashboard-shell">
		<div aria-hidden="true" class="home-dashboard home-dashboard--server">
			<div class="home-dashboard__hero">
				<h2 class="home-dashboard__hero-title">
					<liferay-ui:message key="start-building-ai-agents" />
				</h2>

				<a class="btn btn-primary home-dashboard__hero-btn rounded-pill" href="<%= homeDashboardDisplayContext.getCreateAgentURL() %>">
					<liferay-ui:message key="create-new-agent" />
				</a>
			</div>

			<div class="home-dashboard__spinner">
				<span aria-hidden="true" class="loading-animation"></span>
			</div>
		</div>

		<react:component
			module="{HomeDashboard} from ai-hub-web"
			props="<%= homeDashboardDisplayContext.getReactData() %>"
		/>
	</div>
</c:if>