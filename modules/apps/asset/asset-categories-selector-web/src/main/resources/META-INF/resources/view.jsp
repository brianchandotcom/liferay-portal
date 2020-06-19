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
Map<String, Object> context = HashMapBuilder.<String, Object>put(
	"addCategoryURL", assetCategoriesSelectorDisplayContext.getAddCategoryURL()
).put(
	"itemSelectorSaveEvent", HtmlUtil.escapeJS(assetCategoriesSelectorDisplayContext.getEventName())
).put(
	"multiSelection", !assetCategoriesSelectorDisplayContext.isSingleSelect()
).put(
	"namespace", liferayPortletResponse.getNamespace()
).put(
	"nodes", assetCategoriesSelectorDisplayContext.getCategoriesJSONArray()
).build();
%>

<react:component
	data="<%= context %>"
	module="js/SelectCategory.es"
/>