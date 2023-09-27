<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
java.util.Locale[] availableLocales = (java.util.Locale[])request.getAttribute("aui:translation-manager:availableLocales");
boolean changeableDefaultLanguage = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:translation-manager:changeableDefaultLanguage")), true);
java.lang.String defaultLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:translation-manager:defaultLanguageId"));
java.lang.String editingLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:translation-manager:editingLanguageId"));
long groupId = GetterUtil.getLong(String.valueOf(request.getAttribute("aui:translation-manager:groupId")));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:translation-manager:id"));
boolean initialize = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:translation-manager:initialize")), true);
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:translation-manager:readOnly")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:translation-manager:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/translation_manager/init-ext.jspf" %>