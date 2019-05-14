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
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.taglib.util.InlineUtil" %>

<%
String action = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:edit-form:action"));
List<String> checkboxNames = (List<String>)request.getAttribute("LIFERAY_SHARED_aui:form:checkboxNames");
String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:edit-form:cssClass"));
boolean fluid = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:edit-form:fluid")));
boolean inlineLabels = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:edit-form:inlineLabels")));
String method = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:edit-form:method"));
String name = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:edit-form:name"));
String onSubmit = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-frontend:edit-form:onSubmit"));
boolean validateOnBlur = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:edit-form:validateOnBlur")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-frontend:edit-form:dynamicAttributes");
Map<String, List<ValidatorTag>> validatorTagsMap = (Map<String, List<ValidatorTag>>)request.getAttribute("LIFERAY_SHARED_aui:form:validatorTagsMap");
%>