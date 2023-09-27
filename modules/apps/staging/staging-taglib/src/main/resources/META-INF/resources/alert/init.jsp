<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.staging.taglib.servlet.taglib.AlertType" %>

<%
Object bodyContent = request.getAttribute("liferay-staging:alert:bodyContent");

String bodyContentString = (bodyContent != null) ? bodyContent.toString() : StringPool.BLANK;

boolean dismissible = GetterUtil.getBoolean(request.getAttribute("liferay-staging:alert:dismissible"));
boolean fluid = GetterUtil.getBoolean(request.getAttribute("liferay-staging:alert:fluid"));
String type = GetterUtil.getString(request.getAttribute("liferay-staging:alert:type"));

String spritemap = themeDisplay.getPathThemeSpritemap();
%>