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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String initUrl = portletPreferences.getValue("initUrl", StringPool.BLANK);
String scope = portletPreferences.getValue("scope", StringPool.BLANK);
String proxyHost = portletPreferences.getValue("proxyHost", StringPool.BLANK);
String proxyPort = portletPreferences.getValue("proxyPort", StringPool.BLANK);
String proxyAuthentication = portletPreferences.getValue("proxyAuthentication", StringPool.BLANK);
String proxyAuthenticationUsername = portletPreferences.getValue("proxyAuthenticationUsername", StringPool.BLANK);
String proxyAuthenticationPassword = portletPreferences.getValue("proxyAuthenticationPassword", StringPool.BLANK);
String proxyAuthenticationHost = portletPreferences.getValue("proxyAuthenticationHost", StringPool.BLANK);
String proxyAuthenticationDomain = portletPreferences.getValue("proxyAuthenticationDomain", StringPool.BLANK);
String stylesheet = portletPreferences.getValue("stylesheet", StringPool.BLANK);
%>

<%@ include file="/init-ext.jsp" %>