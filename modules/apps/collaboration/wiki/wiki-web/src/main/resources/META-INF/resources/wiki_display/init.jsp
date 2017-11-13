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

<%@ page import="com.liferay.wiki.configuration.WikiGroupServiceConfiguration" %><%@
page import="com.liferay.wiki.exception.NoSuchNodeException" %><%@
page import="com.liferay.wiki.web.internal.util.WikiWebComponentProvider" %>

<%
WikiWebComponentProvider wikiWebComponentProvider = WikiWebComponentProvider.getWikiWebComponentProvider();

WikiGroupServiceConfiguration wikiGroupServiceConfiguration = wikiWebComponentProvider.getWikiGroupServiceConfiguration();

long nodeId = GetterUtil.getLong(portletPreferences.getValue("nodeId", StringPool.BLANK));
String title = GetterUtil.getString(portletPreferences.getValue("title", wikiGroupServiceConfiguration.frontPageName()));
%>

<%@ include file="/wiki_display/init-ext.jsp" %>