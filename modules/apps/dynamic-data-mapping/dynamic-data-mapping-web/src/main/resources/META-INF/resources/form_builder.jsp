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

<%
String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

long structureId = ParamUtil.getLong(request, "structureId");
String className = ParamUtil.getString(request, "className");
String script = ParamUtil.getString(request, "script");
String fieldsJSONArrayString = ParamUtil.getString(request, "fieldsJSONArrayString");

scopeAvailableFields = ParamUtil.getString(request, "scopeAvailableFields");
scopeClassNameId = PortalUtil.getClassNameId(className);
scopeStorageType = ParamUtil.getString(request, "storageType");

DDMStructure structure = null;

if (structureId > 0) {
	structure = DDMStructureServiceUtil.getStructure(structureId);
}

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css", portlet.getTimestamp()) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<%@ include file="/form_builder.jspf" %>