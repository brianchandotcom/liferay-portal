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
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-theme:defineObjects />

<%
Boolean disabled = (Boolean)request.getAttribute("liferay-commerce:compare-checkbox:disabled");
Boolean inCompare = (Boolean)request.getAttribute("liferay-commerce:compare-checkbox:inCompare");
long itemId = (long)request.getAttribute("liferay-commerce:compare-checkbox:itemId");
String label = (String)request.getAttribute("liferay-commerce:compare-checkbox:label");
String pictureUrl = (String)request.getAttribute("liferay-commerce:compare-checkbox:pictureUrl");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_compare-checkbox") + StringPool.UNDERLINE;

String rootId = randomNamespace + "compare-checkbox";
%>