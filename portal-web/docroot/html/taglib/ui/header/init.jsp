<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String backLabel = (String)request.getAttribute("liferay-ui:header:backLabel");
String backURL = (String)request.getAttribute("liferay-ui:header:backURL");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:header:cssClass"));
boolean escapeXml = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:header:escapeXml"));
boolean localizeTitle = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:header:localizeTitle"));
boolean showBackURL = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:header:showBackURL"));
String title = (String)request.getAttribute("liferay-ui:header:title");
%>