<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/frontend-data-set" prefix="frontend-data-set" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.data.set.model.FDSActionDropdownItem" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %>

<liferay-theme:defineObjects />

<%
Map<String, Object> additionalProps = (Map<String, Object>)request.getAttribute("liferay-commerce:order-returns-data-set:additionalProps");
String apiURL = (String)request.getAttribute("liferay-commerce:order-returns-data-set:apiURL");
String displayStyle = (String)request.getAttribute("liferay-commerce:order-returns-data-set:displayStyle");
List<FDSActionDropdownItem> fdsActionDropdownItems = (List<FDSActionDropdownItem>)request.getAttribute("liferay-commerce:order-returns-data-set:fdsActionDropdownItems");
String name = (String)request.getAttribute("liferay-commerce:order-returns-data-set:name");
String propsTransformer = (String)request.getAttribute("liferay-commerce:order-returns-data-set:propsTransformer");
%>