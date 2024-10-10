<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem" %><%@
page import="com.liferay.frontend.taglib.sample.web.constants.SamplePortletKeys" %><%@
page import="com.liferay.frontend.taglib.sample.web.internal.display.context.SampleDisplayContext" %><%@
page import="com.liferay.frontend.taglib.sample.web.internal.display.context.SearchPaginatorDisplayContext" %>

<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />

<%@ include file="/init-ext.jsp" %>

<%
SampleDisplayContext sampleDisplayContext = (SampleDisplayContext)request.getAttribute(SamplePortletKeys.SAMPLE_DISPLAY_CONTEXT);
SearchPaginatorDisplayContext searchPaginatorDisplayContext = (SearchPaginatorDisplayContext)request.getAttribute(SamplePortletKeys.SEARCH_PAGINATOR_DISPLAY_CONTEXT);
%>