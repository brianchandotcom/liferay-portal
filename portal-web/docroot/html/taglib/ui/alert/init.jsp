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

<%@ include file="/html/taglib/init.jsp" %>

<%
Integer animationTime = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:alert:animationTime"));
boolean closeable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ui:alert:closeable")));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:alert:cssClass"));
boolean destroyOnHide = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ui:alert:destroyOnHide")));
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:alert:message"));
String targetContainer = GetterUtil.getString((String)request.getAttribute("liferay-ui:alert:targetContainer"));
Integer timeout = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:alert:timeout"));
String title = GetterUtil.getString((String)request.getAttribute("liferay-ui:alert:title"));
String type = GetterUtil.getString((String)request.getAttribute("liferay-ui:alert:type"));
%>