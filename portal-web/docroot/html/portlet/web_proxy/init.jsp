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

<%@ include file="/html/portlet/init.jsp" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String initUrl = preferences.getValue("initUrl", StringPool.BLANK);
String scope = preferences.getValue("scope", StringPool.BLANK);
String proxyHost = preferences.getValue("proxyHost", StringPool.BLANK);
String proxyPort = preferences.getValue("proxyPort", StringPool.BLANK);
String proxyAuthentication = preferences.getValue("proxyAuthentication", StringPool.BLANK);
String proxyAuthenticationUsername = preferences.getValue("proxyAuthenticationUsername", StringPool.BLANK);
String proxyAuthenticationPassword = preferences.getValue("proxyAuthenticationPassword", StringPool.BLANK);
String proxyAuthenticationHost = preferences.getValue("proxyAuthenticationHost", StringPool.BLANK);
String proxyAuthenticationDomain = preferences.getValue("proxyAuthenticationDomain", StringPool.BLANK);
String stylesheet = preferences.getValue("stylesheet", StringPool.BLANK);
%>

<%@ include file="/html/portlet/web_proxy/init-ext.jsp" %>