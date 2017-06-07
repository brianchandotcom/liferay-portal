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
LayoutItemSelectorViewDisplayContext layoutItemSelectorViewDisplayContext = (LayoutItemSelectorViewDisplayContext)request.getAttribute(LayoutsItemSelectorWebKeys.LAYOUT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css", portlet.getTimestamp()) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<%
Map<String, Object> context = new HashMap<>();

context.put("followURLOnTitleClick", layoutItemSelectorViewDisplayContext.isFollowURLOnTitleClick());
context.put("itemSelectorSaveEvent", HtmlUtil.escapeJS(layoutItemSelectorViewDisplayContext.getItemSelectedEventName()));
context.put("multiSelection", false);
context.put("namespace", liferayPortletResponse.getNamespace());
context.put("nodes", layoutItemSelectorViewDisplayContext.getLayoutsJSONArray());
context.put("pathThemeImages", themeDisplay.getPathThemeImages());
context.put("viewType", "tree");
%>

<soy:template-renderer
	context="<%= context %>"
	module="layout-item-selector-web/js/SelectLayout.es"
	templateNamespace="SelectLayout.render"
/>