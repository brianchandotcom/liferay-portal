<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%
String formName = (String)request.getAttribute("liferay-ui:tabs:formName");
String[] names = (String[])request.getAttribute("liferay-ui:tabs:names");
String namesJS = (String)request.getAttribute("liferay-ui:tabs:namesJS");
String param = (String)request.getAttribute("liferay-ui:tabs:param");
String[] urls = (String[])request.getAttribute("liferay-ui:tabs:urls");
String value = (String)request.getAttribute("liferay-ui:tabs:value");
String[] values = (String[])request.getAttribute("liferay-ui:tabs:values");
%>