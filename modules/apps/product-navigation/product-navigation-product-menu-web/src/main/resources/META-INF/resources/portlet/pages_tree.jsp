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

String namespace = PortalUtil.getPortletNamespace(ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU);

data.put("namespace", namespace);
data.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");

Group group = themeDisplay.getSiteGroup();

boolean privateLayout = GetterUtil.getBoolean(SessionClicks.get(request, namespace + ProductNavigationProductMenuWebKeys.PRIVATE_LAYOUT, "false"), layout.isPrivateLayout());

Map<String, Object> pageTypeSelectorData = new HashMap<>();

pageTypeSelectorData.put("privateLayout", privateLayout);
pageTypeSelectorData.put("namespace", PortalUtil.getPortletNamespace(ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU));
%>

<div id="<%= renderResponse.getNamespace() + "-layout-finder" %>">
	<react:component
		data="<%= data %>"
		module="js/LayoutFinder.es"
		servletContext="<%= application %>"
	/>
</div>

<div id="<%= renderResponse.getNamespace() + "layoutsTree" %>">
	<div id="<%= renderResponse.getNamespace() + "-page-type" %>">
		<react:component
			data="<%= pageTypeSelectorData %>"
			module="js/PageTypeSelector.es"
			servletContext="<%= application %>"
		/>
	</div>

	<liferay-layout:layouts-tree
		groupId="<%= themeDisplay.getSiteGroupId() %>"
		linkTemplate='<a class="{cssClass}" data-regular-url="{regularURL}" data-url="{url}" data-uuid="{uuid}" href="{url}" id="{id}" title="{title}">{label}</a>'
		privateLayout="<%= privateLayout %>"
		rootLinkTemplate='<a class="{cssClass}" href="javascript:void(0);" id="{id}" title="{title}">{label}</a>'
		rootNodeName="<%= group.getLayoutRootNodeName(privateLayout, locale) %>"
		selPlid="<%= plid %>"
		treeId="pagesTree"
	/>

	<div class="pages-administration-link">
		<aui:a cssClass="ml-2" href="<%= administrationPortletURL.toString() %>"><%= LanguageUtil.get(request, "go-to-pages-administration") %></aui:a>
	</div>
</div>

<aui:script>
	function destroyTreeComponent() {
		Liferay.destroyComponent(props.namespace + 'pagesTree');
		Liferay.detach('destroyPortlet', destroyTreeComponent);
	}

	Liferay.on('destroyPortlet', destroyTreeComponent);
</aui:script>