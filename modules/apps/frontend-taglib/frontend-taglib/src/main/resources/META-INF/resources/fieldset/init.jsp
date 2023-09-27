<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.util.SessionClicks" %><%@
page import="com.liferay.taglib.util.InlineUtil" %>

<%
boolean collapsed = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:fieldset:collapsed")));
boolean collapsible = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:fieldset:collapsible")));
boolean column = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:fieldset:column")));
String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:fieldset:cssClass"));
boolean disabled = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:fieldset:disabled")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-frontend:fieldset:dynamicAttributes");
String helpMessage = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:fieldset:helpMessage"));
String id = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:fieldset:id"));
String label = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:fieldset:label"));
boolean localizeLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:fieldset:localizeLabel")));
%>