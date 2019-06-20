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

<%@ taglib uri="http://liferay.com/tld/soy" prefix="soy" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.search.admin.web.internal.constants.SearchAdminWebKeys" %><%@
page import="com.liferay.portal.search.admin.web.internal.display.context.FieldMappingsDisplayContext" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<liferay-theme:defineObjects />

<%
FieldMappingsDisplayContext fieldMappingsDisplayContext = (FieldMappingsDisplayContext)request.getAttribute(SearchAdminWebKeys.FIELD_MAPPINGS_DISPLAY_CONTEXT);

Map<String, Object> context = new HashMap<>();

context.put("fieldMappingsJson", fieldMappingsDisplayContext.getFieldMappings());
context.put("indexList", fieldMappingsDisplayContext.getFieldMappingIndexDisplayContexts());
context.put("selectedIndexName", fieldMappingsDisplayContext.getSelectedIndexName());
context.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
%>

<soy:component-renderer
	context="<%= context %>"
	module="js/FieldMappings.es"
	templateNamespace="com.liferay.portal.search.admin.web.FieldMappings.render"
/>