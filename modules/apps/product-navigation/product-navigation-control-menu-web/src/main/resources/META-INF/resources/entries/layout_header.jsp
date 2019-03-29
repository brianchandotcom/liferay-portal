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
LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(layout.getType());

ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());

String headerTitle = HtmlUtil.escape(layout.getName(locale));

String layoutFriendlyURL = layout.getFriendlyURL();

String portletId = ParamUtil.getString(request, "p_p_id");

if (Validator.isNotNull(portletId) && layout.isSystem() && !layout.isTypeControlPanel() && layoutFriendlyURL.equals(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL)) {
	headerTitle = PortalUtil.getPortletTitle(portletId, locale);
}
%>

<li class="align-items-center control-menu-nav-item control-menu-nav-item-content">
	<span class="control-menu-level-1-heading truncate-text" data-qa-id="headerTitle"><%= headerTitle %></span>&nbsp;<span class="text-muted truncate-text">(<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + layout.getType()) %>)</span>

	<%
	Map<String, Object> context = new HashMap<>();

	context.put("administrationPortletNamespace", PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES));

	PortletURL administrationPortletURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.RENDER_PHASE);

	administrationPortletURL.setParameter("redirect", themeDisplay.getURLCurrent());

	context.put("administrationPortletURL", administrationPortletURL.toString());

	LiferayPortletURL findLayoutsURL = PortletURLFactoryUtil.create(request, ProductNavigationControlMenuPortletKeys.PRODUCT_NAVIGATION_CONTROL_MENU, PortletRequest.RESOURCE_PHASE);

	findLayoutsURL.setResourceID("/control_menu/find_layouts");

	context.put("findLayoutsURL", findLayoutsURL.toString());

	context.put("layouts", JSONFactoryUtil.createJSONArray());
	context.put("namespace", PortalUtil.getPortletNamespace(ProductNavigationControlMenuPortletKeys.PRODUCT_NAVIGATION_CONTROL_MENU));
	context.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
	context.put("totalCount", 0);
	%>

	<soy:component-renderer
		componentId="layoutFinder"
		context="<%= context %>"
		module="js/LayoutFinder.es"
		templateNamespace="com.liferay.product.navigation.control.menu.web.LayoutFinder.render"
	/>
</li>