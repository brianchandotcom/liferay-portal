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
SiteNavigationMenuItemItemSelectorViewDisplayContext siteNavigationMenuItemItemSelectorViewDisplayContext = (SiteNavigationMenuItemItemSelectorViewDisplayContext)request.getAttribute(SiteNavigationItemSelectorWebKeys.SITE_NAVIGATION_MENU_ITEM_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= siteNavigationMenuItemItemSelectorViewDisplayContext.isShowSelectSiteNavigationMenuItem() %>">

		<%
		Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
		%>

		<liferay-util:html-top>
			<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css", portlet.getTimestamp()) %>" rel="stylesheet" type="text/css" />
		</liferay-util:html-top>

		<%
		Map<String, Object> context = new HashMap<>();

		context.put("itemSelectorSaveEvent", siteNavigationMenuItemItemSelectorViewDisplayContext.getItemSelectedEventName());
		context.put("namespace", liferayPortletResponse.getNamespace());
		context.put("nodes", siteNavigationMenuItemItemSelectorViewDisplayContext.getSiteNavigationMenuItemsJSONArray());
		context.put("pathThemeImages", themeDisplay.getPathThemeImages());
		%>

		<soy:component-renderer
			context="<%= context %>"
			module="js/SelectSiteNavigationMenuItem.es"
			templateNamespace="com.liferay.site.navigation.item.selector.web.SelectSiteNavigationMenuItem.render"
		/>
	</c:when>
	<c:otherwise>
		<liferay-frontend:empty-result-message
			elementType='<%= LanguageUtil.get(resourceBundle, "navigation-menu-items") %>'
		/>
	</c:otherwise>
</c:choose>