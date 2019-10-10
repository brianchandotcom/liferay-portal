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

<%@ include file="/portlet/init.jsp" %>

<%
Map<String, Object> data = new HashMap<>();

data.put("administrationPortletNamespace", PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES));

PortletURL administrationPortletURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.RENDER_PHASE);

administrationPortletURL.setParameter("redirect", themeDisplay.getURLCurrent());

data.put("administrationPortletURL", administrationPortletURL.toString());

LiferayPortletURL findLayoutsURL = PortletURLFactoryUtil.create(request, ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU, PortletRequest.RESOURCE_PHASE);

findLayoutsURL.setResourceID("/product_menu/find_layouts");

data.put("findLayoutsURL", findLayoutsURL.toString());

data.put("namespace", PortalUtil.getPortletNamespace(ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU));

Group scopeGroup = themeDisplay.getScopeGroup();
%>

<div id="<%= renderResponse.getNamespace() + "-layout-finder" %>">
	<react:component
		data="<%= data %>"
		module="js/LayoutFinder.es"
		servletContext="<%= application %>"
	/>
</div>

<liferay-layout:layouts-tree
	groupId="<%= scopeGroupId %>"
	linkTemplate='<a class="{cssClass}" data-regular-url="{regularURL}" data-url="{url}" data-uuid="{uuid}" href="{url}" id="{id}" title="{title}">{label}</a>'
	privateLayout="<%= layout.isPrivateLayout() %>"
	rootLinkTemplate='<a class="{cssClass}" href="javascript:void(0);" id="{id}" title="{title}">{label}</a>'
	rootNodeName="<%= scopeGroup.getLayoutRootNodeName(layout.isPrivateLayout(), locale) %>"
	selPlid="<%= plid %>"
	treeId="layoutsTree"
/>