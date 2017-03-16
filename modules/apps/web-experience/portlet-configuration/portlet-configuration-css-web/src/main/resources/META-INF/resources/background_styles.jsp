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
Map<String, Object> context = new HashMap<>();

context.put("color", portletConfigurationCSSPortletDisplayContext.getBackgroundColor());
context.put("id", renderResponse.getNamespace() + "backgroundColor");
context.put("label", LanguageUtil.get(request, "background-color"));
context.put("name", renderResponse.getNamespace() + "backgroundColor");
%>

<soy:template-renderer
	context="<%= context %>"
	module="portlet-configuration-css-web/js/ColorPickerInput.es"
	templateNamespace="ColorPickerInput.render"
/>