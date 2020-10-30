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
		<div class="select-site-navigation-menu-item">
			<react:component
				module="js/SelectSiteNavigationMenuItem"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"itemSelectorSaveEvent", siteNavigationMenuItemItemSelectorViewDisplayContext.getItemSelectedEventName()
					).put(
						"namespace", liferayPortletResponse.getNamespace()
					).put(
						"nodes", siteNavigationMenuItemItemSelectorViewDisplayContext.getSiteNavigationMenuItemsJSONArray()
					).put(
						"pathThemeImages", themeDisplay.getPathThemeImages()
					).build()
				%>'
			/>
		</div>
	</c:when>
	<c:otherwise>
		<liferay-frontend:empty-result-message
			elementType='<%= LanguageUtil.get(resourceBundle, "navigation-menu-items") %>'
		/>
	</c:otherwise>
</c:choose>