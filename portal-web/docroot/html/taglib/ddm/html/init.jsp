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

<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
boolean checkRequired = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ddm:html:checkRequired")), true);
long classNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("ddm:html:classNameId")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("ddm:html:classPK")));
com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues ddmFormValues = (com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues)request.getAttribute("ddm:html:ddmFormValues");
java.lang.String fieldsNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("ddm:html:fieldsNamespace"));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ddm:html:readOnly")));
boolean repeatable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ddm:html:repeatable")), true);
java.util.Locale requestedLocale = (java.util.Locale)request.getAttribute("ddm:html:requestedLocale");
boolean showEmptyFieldLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("ddm:html:showEmptyFieldLabel")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("ddm:html:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("ddm:html:scopedAttributes");
%>

<%@ include file="/html/taglib/ddm/html/init-ext.jspf" %>