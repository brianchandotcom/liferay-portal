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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.util.UUID" %>

<liferay-theme:defineObjects />

<%
String field = (String)request.getAttribute("liferay-commerce:dynamic-field:field");
String fieldValue = (String)request.getAttribute("liferay-commerce:dynamic-field:fieldValue");
String label = (String)request.getAttribute("liferay-commerce:dynamic-field:label");
String labelElementType = (String)request.getAttribute("liferay-commerce:dynamic-field:labelElementType");
String namespace = (String)request.getAttribute("liferay-commerce:dynamic-field:namespace");
String uuid = String.valueOf(UUID.randomUUID());
String valueElementType = (String)request.getAttribute("liferay-commerce:dynamic-field:valueElementType");
%>