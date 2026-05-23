<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewActivityDashboardDisplayContext viewActivityDashboardDisplayContext = (ViewActivityDashboardDisplayContext)request.getAttribute(ViewActivityDashboardDisplayContext.class.getName());
%>

<div>
	<react:component
		module="{ActivityDashboard} from ai-hub-web"
		props="<%= viewActivityDashboardDisplayContext.getProperties() %>"
	/>
</div>