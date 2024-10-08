<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %>

<%@ page import="java.util.List" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
String checkoutURL = (String)request.getAttribute("liferay-commerce:order-actions:checkoutURL");
long commerceOrderId = (long)request.getAttribute("liferay-commerce:order-actions:commerceOrderId");
List<DropdownItem> dropdownItems = (List<DropdownItem>)request.getAttribute("liferay-commerce:order-actions:dropdownItems");
boolean open = (boolean)request.getAttribute("liferay-commerce:order-actions:open");
String reorderURL = (String)request.getAttribute("liferay-commerce:order-actions:reorderURL");
%>