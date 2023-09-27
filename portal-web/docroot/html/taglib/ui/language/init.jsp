<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry" %>

<%
String ddmTemplateKey = GetterUtil.getString((String)request.getAttribute("liferay-ui:language:ddmTemplateKey"));
long displayStyleGroupId = GetterUtil.getLong(request.getAttribute("liferay-ui:language:displayStyleGroupId"), scopeGroupId);
String formAction = (String)request.getAttribute("liferay-ui:language:formAction");
String formName = (String)request.getAttribute("liferay-ui:language:formName");
List<LanguageEntry> languageEntries = (List<LanguageEntry>)request.getAttribute("liferay-ui:language:languageEntries");
String languageId = GetterUtil.getString((String)request.getAttribute("liferay-ui:language:languageId"), themeDisplay.getLanguageId());
String name = (String)request.getAttribute("liferay-ui:language:name");
%>