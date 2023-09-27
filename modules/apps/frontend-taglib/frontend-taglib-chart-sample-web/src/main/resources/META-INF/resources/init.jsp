<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/chart" prefix="chart" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.taglib.chart.sample.web.constants.ChartSamplePortletKeys" %><%@
page import="com.liferay.frontend.taglib.chart.sample.web.internal.display.context.ChartSampleDisplayContext" %>

<liferay-theme:defineObjects />

<%@ include file="/init-ext.jsp" %>

<%
ChartSampleDisplayContext chartSampleDisplayContext = (ChartSampleDisplayContext)request.getAttribute(ChartSamplePortletKeys.CHART_SAMPLE_DISPLAY_CONTEXT);
%>