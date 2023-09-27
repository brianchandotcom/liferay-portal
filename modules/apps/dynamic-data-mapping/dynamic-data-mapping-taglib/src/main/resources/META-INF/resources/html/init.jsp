<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ include file="/init.jsp" %>

<%
boolean checkRequired = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:checkRequired")), true);
long classNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html:classNameId")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html:classPK")));
com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues = (com.liferay.dynamic.data.mapping.storage.DDMFormValues)request.getAttribute("liferay-ddm:html:ddmFormValues");
java.util.Locale defaultEditLocale = (java.util.Locale)request.getAttribute("liferay-ddm:html:defaultEditLocale");
java.util.Locale defaultLocale = (java.util.Locale)request.getAttribute("liferay-ddm:html:defaultLocale");
java.lang.String documentLibrarySelectorURL = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:html:documentLibrarySelectorURL"));
java.lang.String fieldsNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:html:fieldsNamespace"));
long groupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html:groupId")));
boolean ignoreRequestValue = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:ignoreRequestValue")));
java.lang.String imageSelectorURL = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:html:imageSelectorURL"));
java.lang.String layoutSelectorURL = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:html:layoutSelectorURL"));
boolean localizable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:localizable")), true);
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:repeatable")), true);
java.util.Locale requestedLocale = (java.util.Locale)request.getAttribute("liferay-ddm:html:requestedLocale");
boolean showEmptyFieldLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:showEmptyFieldLabel")), true);
boolean showLanguageSelector = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:showLanguageSelector")), true);
boolean synchronousFormSubmission = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:synchronousFormSubmission")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ddm:html:dynamicAttributes");
%>

<%@ include file="/html/init-ext.jspf" %>