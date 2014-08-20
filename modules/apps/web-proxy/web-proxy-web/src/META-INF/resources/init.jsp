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

<%@ taglib uri="/META-INF/c.tld" prefix="c" %>
<%@ taglib uri="/META-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/META-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/META-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/META-INF/x.tld" prefix="x" %>

<%@ taglib uri="/META-INF/liferay-portlet_2_0.tld" prefix="portlet" %>

<%@ taglib uri="/META-INF/aui.tld" prefix="aui" %>
<%@ taglib uri="/META-INF/liferay-ddm.tld" prefix="liferay-ddm" %>
<%@ taglib uri="/META-INF/liferay-portlet-ext.tld" prefix="liferay-portlet" %>
<%@ taglib uri="/META-INF/liferay-security.tld" prefix="liferay-security" %>
<%@ taglib uri="/META-INF/liferay-staging.tld" prefix="liferay-staging" %>
<%@ taglib uri="/META-INF/liferay-theme.tld" prefix="liferay-theme" %>
<%@ taglib uri="/META-INF/liferay-ui.tld" prefix="liferay-ui" %>
<%@ taglib uri="/META-INF/liferay-util.tld" prefix="liferay-util" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %>

<%@ page import="javax.portlet.WindowState" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
WindowState windowState = liferayPortletRequest.getWindowState();

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