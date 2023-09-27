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
page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-theme:defineObjects />

<%
boolean displayAvailability = (boolean)request.getAttribute("liferay-commerce:availability-label:displayAvailability");
String label = (String)request.getAttribute("liferay-commerce:availability-label:label");
String labelType = (String)request.getAttribute("liferay-commerce:availability-label:labelType");
String namespace = (String)request.getAttribute("liferay-commerce:availability-label:namespace");
%>