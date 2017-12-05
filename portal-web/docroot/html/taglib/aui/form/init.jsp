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
java.lang.String action = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:action"));
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:cssClass"));
boolean inlineLabels = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:form:inlineLabels")));
java.lang.String method = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:method"), "post");
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:name"), "fm");
java.lang.String onSubmit = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:onSubmit"));
java.lang.String portletNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:portletNamespace"));
boolean useNamespace = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:form:useNamespace")), true);
boolean validateOnBlur = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:form:validateOnBlur")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:form:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/form/init-ext.jspf" %>