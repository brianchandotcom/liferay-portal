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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<%
boolean refreshPageOnClose = (boolean)request.getAttribute("liferay-commerce:modal:refreshPageOnClose");
String id = (String)request.getAttribute("liferay-commerce:modal:id");
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_modal") + StringPool.UNDERLINE;
String size = (String)request.getAttribute("liferay-commerce:modal:size");
String spritemap = (String)request.getAttribute("liferay-commerce:modal:spritemap");
String title = (String)request.getAttribute("liferay-commerce:modal:title");
String url = (String)request.getAttribute("liferay-commerce:modal:url");
%>