<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

@generated
--%>

<%@ include file="/init.jsp" %>

<%
long classNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html-field:classNameId")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html-field:classPK")));
com.liferay.dynamic.data.mapping.storage.Field field = (com.liferay.dynamic.data.mapping.storage.Field)request.getAttribute("liferay-ddm:html-field:field");
java.lang.String fieldsNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:html-field:fieldsNamespace"));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html-field:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html-field:repeatable")), true);
java.util.Locale requestedLocale = (java.util.Locale)request.getAttribute("liferay-ddm:html-field:requestedLocale");
boolean showEmptyFieldLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html-field:showEmptyFieldLabel")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ddm:html-field:dynamicAttributes");
%>

<%@ include file="/html_field/init-ext.jspf" %>