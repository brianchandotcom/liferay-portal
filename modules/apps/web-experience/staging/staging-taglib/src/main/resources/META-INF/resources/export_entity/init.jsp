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

<%@ page import="com.liferay.exportimport.constants.ExportImportPortletKeys" %><%@
page import="com.liferay.portal.kernel.model.ClassName" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.service.ClassNameLocalServiceUtil" %>

<%
String className = GetterUtil.getString(request.getAttribute("liferay-staging:export-entity:className"));
long classNameId = GetterUtil.getLong(request.getAttribute("liferay-staging:export-entity:classNameId"));
long classPK = GetterUtil.getLong(request.getAttribute("liferay-staging:export-entity:classPK"));

if (Validator.isNotNull(classNameId)) {
	ClassName classNameObject = ClassNameLocalServiceUtil.getClassName(classNameId);
	className = classNameObject.getClassName();
}
%>