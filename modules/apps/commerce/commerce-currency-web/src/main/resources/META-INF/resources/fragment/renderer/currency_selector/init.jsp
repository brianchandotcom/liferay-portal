<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %>

<liferay-theme:defineObjects />

<%
long commerceChannelId = (long)request.getAttribute("liferay-commerce:currency-selector:commerceChannelId");
String commerceOrderDetailBaseURL = (String)request.getAttribute("liferay-commerce:currency-selector:commerceOrderDetailBaseURL");
long commerceOrderId = (long)request.getAttribute("liferay-commerce:currency-selector:commerceOrderId");
JSONArray commerceOrderTypesJSONArray = (JSONArray)request.getAttribute("liferay-commerce:currency-selector:commerceOrderTypes");
%>