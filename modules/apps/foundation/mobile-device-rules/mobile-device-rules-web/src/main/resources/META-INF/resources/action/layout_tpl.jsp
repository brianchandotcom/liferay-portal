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

<%@ include file="/action/init.jsp" %>

<%
String layoutTemplateId = GetterUtil.getString(typeSettingsProperties.getProperty("layoutTemplateId"), PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="layout"
/>

<h5><liferay-ui:message key="layout-template" /></h5>

<liferay-ui:layout-templates-list
	layoutTemplateId="<%= layoutTemplateId %>"
	layoutTemplates="<%= LayoutTemplateLocalServiceUtil.getLayoutTemplates() %>"
/>