<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
String helpMessage = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:fieldset:helpMessage"));
String id = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:fieldset:id"));
String label = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:fieldset:label"));
boolean localizeLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:fieldset:localizeLabel")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-frontend:fieldset:dynamicAttributes");
%>