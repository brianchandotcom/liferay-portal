<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
boolean checkRequired = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:checkRequired")), true);
DDMFormValues ddmFormValues = (DDMFormValues)request.getAttribute("liferay-ddm:html:ddmFormValues");
Locale defaultEditLocale = (Locale)request.getAttribute("liferay-ddm:html:defaultEditLocale");
Locale defaultLocale = (Locale)request.getAttribute("liferay-ddm:html:defaultLocale");
String documentLibrarySelectorURL = GetterUtil.getString((String)request.getAttribute("liferay-ddm:html:documentLibrarySelectorURL"));
String fieldsNamespace = GetterUtil.getString((String)request.getAttribute("liferay-ddm:html:fieldsNamespace"));
long groupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html:groupId")));
String imageSelectorURL = GetterUtil.getString((String)request.getAttribute("liferay-ddm:html:imageSelectorURL"));
String layoutSelectorURL = GetterUtil.getString((String)request.getAttribute("liferay-ddm:html:layoutSelectorURL"));
boolean localizable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:localizable")), true);
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:repeatable")), true);
Locale requestedLocale = (Locale)request.getAttribute("liferay-ddm:html:requestedLocale");
boolean showEmptyFieldLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:showEmptyFieldLabel")), true);
boolean showLanguageSelector = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:showLanguageSelector")), true);
boolean synchronousFormSubmission = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:synchronousFormSubmission")), true);
%>

<%@ include file="/html/init-ext.jspf" %>