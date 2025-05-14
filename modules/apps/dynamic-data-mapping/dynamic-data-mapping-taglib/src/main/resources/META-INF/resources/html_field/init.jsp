<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
long classNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html-field:classNameId")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html-field:classPK")));
Field field = (Field)request.getAttribute("liferay-ddm:html-field:field");
String fieldsNamespace = GetterUtil.getString((String)request.getAttribute("liferay-ddm:html-field:fieldsNamespace"));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html-field:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html-field:repeatable")), true);
Locale requestedLocale = (Locale)request.getAttribute("liferay-ddm:html-field:requestedLocale");
boolean showEmptyFieldLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html-field:showEmptyFieldLabel")), true);
%>

<%@ include file="/html_field/init-ext.jspf" %>