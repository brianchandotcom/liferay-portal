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

<%@ include file="/html/portlet/mobile_device_rules/action/init.jsp" %>

<%
String layoutTemplateId = GetterUtil.getString(typeSettingsProperties.getProperty("layoutTemplateId"));

if (Validator.isNull(layoutTemplateId)) {
	layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;
}

String layoutTemplateIdPrefix = StringPool.BLANK;

List<LayoutTemplate> layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates();
%>

<liferay-ui:error-marker key="errorSection" value="layout" />

<h5><%= LanguageUtil.get(request, "layout-template") %></h5>

<%@ include file="/html/portlet/layouts_admin/layout/layout_templates_list.jspf" %>