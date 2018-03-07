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
long[] siteNavigationMenuItemIds = ParamUtil.getLongValues(request, "siteNavigationMenuItemId");
long siteNavigationMenuItemId = siteNavigationMenuItemIds[siteNavigationMenuItemIds.length - 1];
long selectedSiteNavigationMenuItemId = ParamUtil.getLong(request, "selectedSiteNavigationMenuItemId");

SiteNavigationMenuItem siteNavigationMenuItem = SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItem(siteNavigationMenuItemId);

SiteNavigationMenuItemType siteNavigationMenuItemType = siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(siteNavigationMenuItem.getType());

String title = siteNavigationMenuItemType.getTitle(siteNavigationMenuItem, locale);

Map<String, Object> data = new HashMap<String, Object>();

data.put("order", siteNavigationMenuItem.getOrder());
data.put("parent-site-navigation-menu-item-id", siteNavigationMenuItem.getParentSiteNavigationMenuItemId());
data.put("site-navigation-menu-item-id", siteNavigationMenuItemId);
data.put("title", title);

request.setAttribute("edit_site_navigation_menu.jsp-siteNavigationMenuItemId", siteNavigationMenuItem.getSiteNavigationMenuItemId());
%>

<div class="container-item <%= (siteNavigationMenuItem.getParentSiteNavigationMenuItemId() > 0) ? "container-item--nested" : StringPool.BLANK %>" tabindex="0">
	<div class="site-navigation-menu-item <%= (selectedSiteNavigationMenuItemId == siteNavigationMenuItemId) ? "selected" : StringPool.BLANK %>" <%= AUIUtil.buildData(data) %>>
		<liferay-frontend:horizontal-card
			actionJsp="/site_navigation_menu_item_action.jsp"
			actionJspServletContext="<%= application %>"
			text="<%= title %>"
		>
			<liferay-frontend:horizontal-card-col>
				<liferay-frontend:horizontal-card-icon
					icon="<%= siteNavigationMenuItemType.getIcon() %>"
				/>
			</liferay-frontend:horizontal-card-col>
		</liferay-frontend:horizontal-card>
	</div>

	<%
	List<SiteNavigationMenuItem> children = SiteNavigationMenuItemLocalServiceUtil.getChildSiteNavigationMenuItems(siteNavigationMenuItemId);

	for (SiteNavigationMenuItem childSiteNavigationMenuItem : children) {
	%>

		<liferay-util:include page="/view_site_navigation_menu_item.jsp" servletContext="<%= application %>">
			<liferay-util:param name="siteNavigationMenuItemId" value="<%= String.valueOf(childSiteNavigationMenuItem.getSiteNavigationMenuItemId()) %>" />
		</liferay-util:include>

	<%
	}
	%>

</div>