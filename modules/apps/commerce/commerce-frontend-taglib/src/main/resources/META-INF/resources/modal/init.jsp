<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
String id = (String)request.getAttribute("liferay-commerce:modal:id");
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_modal") + StringPool.UNDERLINE;
boolean refreshPageOnClose = (boolean)request.getAttribute("liferay-commerce:modal:refreshPageOnClose");
String size = (String)request.getAttribute("liferay-commerce:modal:size");
String spritemap = (String)request.getAttribute("liferay-commerce:modal:spritemap");
String title = (String)request.getAttribute("liferay-commerce:modal:title");
String url = (String)request.getAttribute("liferay-commerce:modal:url");
%>