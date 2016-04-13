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
boolean checkRequired = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:checkRequired")), true);
long classNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html:classNameId")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:html:classPK")));
com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues = (com.liferay.dynamic.data.mapping.storage.DDMFormValues)request.getAttribute("liferay-ddm:html:ddmFormValues");
java.lang.String fieldsNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:html:fieldsNamespace"));
boolean ignoreRequestValue = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:ignoreRequestValue")));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:repeatable")), true);
java.util.Locale requestedLocale = (java.util.Locale)request.getAttribute("liferay-ddm:html:requestedLocale");
boolean showEmptyFieldLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:html:showEmptyFieldLabel")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ddm:html:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("liferay-ddm:html:scopedAttributes");
%>

<%@ include file="/html/init-ext.jspf" %>