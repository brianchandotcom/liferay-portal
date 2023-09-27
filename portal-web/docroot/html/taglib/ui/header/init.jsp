<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%
String backLabel = (String)request.getAttribute("liferay-ui:header:backLabel");
String backURL = (String)request.getAttribute("liferay-ui:header:backURL");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:header:cssClass"));
boolean escapeXml = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:header:escapeXml"));
boolean localizeTitle = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:header:localizeTitle"));
boolean showBackURL = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:header:showBackURL"));
String title = (String)request.getAttribute("liferay-ui:header:title");
%>