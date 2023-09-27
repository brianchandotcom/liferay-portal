<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AnalyticsReportsDisplayContext analyticsReportsDisplayContext = (AnalyticsReportsDisplayContext)request.getAttribute(AnalyticsReportsWebKeys.ANALYTICS_REPORTS_DISPLAY_CONTEXT);
%>

<span aria-hidden="true" className="loading-animation loading-animation-sm"></span>

<react:component
	module="js/AnalyticsReportsApp"
	props="<%= analyticsReportsDisplayContext.getData() %>"
/>