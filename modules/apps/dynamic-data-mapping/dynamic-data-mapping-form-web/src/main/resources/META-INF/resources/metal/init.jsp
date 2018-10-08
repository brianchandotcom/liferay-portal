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

<%@ include file="/admin/init.jsp" %>

<%
String functionsMetadata = ddmFormAdminDisplayContext.getFunctionsMetadata();
String mainRequire = ddmFormAdminDisplayContext.getMainRequire();
String rolesURL = ddmFormAdminDisplayContext.getRolesURL();
String serializedDDMFormRules = ddmFormAdminDisplayContext.getSerializedDDMFormRules();
String serializedFormBuilderContext = ddmFormAdminDisplayContext.getSerializedFormBuilderContext();
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/metal/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>