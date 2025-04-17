<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/frontend-data-set" prefix="frontend-data-set" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<liferay-theme:defineObjects />

<%
String apiURL = (String)request.getAttribute("liferay-commerce:placed-order-shipments-data-set:apiURL");
String displayStyle = (String)request.getAttribute("liferay-commerce:placed-order-shipments-data-set:displayStyle");
String name = (String)request.getAttribute("liferay-commerce:placed-order-shipments-data-set:name");
String propsTransformer = (String)request.getAttribute("liferay-commerce:placed-order-shipments-data-set:propsTransformer");
%>