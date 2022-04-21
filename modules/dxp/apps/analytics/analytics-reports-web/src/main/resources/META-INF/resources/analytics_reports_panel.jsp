<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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