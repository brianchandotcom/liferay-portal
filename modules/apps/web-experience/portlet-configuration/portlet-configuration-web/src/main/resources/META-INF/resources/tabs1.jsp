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
String tabs1 = ParamUtil.getString(request, "tabs1");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

PortalUtil.addPortletBreadcrumbEntry(request, PortalUtil.getPortletTitle(renderResponse), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "configuration"), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs1), currentURL);

List<NavigationItem> navigationItems = new ArrayList<>();

if (selPortlet.getConfigurationActionInstance() != null) {
	NavigationItem navigationItem = new NavigationItem();

	navigationItem.setActive(tabs1.equals("setup"));

	PortletURL configurationURL = renderResponse.createRenderURL();

	configurationURL.setParameter("mvcPath", "/edit_configuration.jsp");
	configurationURL.setParameter("redirect", redirect);
	configurationURL.setParameter("returnToFullPageURL", returnToFullPageURL);
	configurationURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
	configurationURL.setParameter("portletResource", portletResource);

	navigationItem.setHref(configurationURL.toString());

	navigationItem.setLabel(LanguageUtil.get(request, "setup"));

	navigationItems.add(navigationItem);
}

if (selPortlet.hasMultipleMimeTypes()) {
	NavigationItem navigationItem = new NavigationItem();

	navigationItem.setActive(tabs1.equals("supported-clients"));

	PortletURL supportedClientsURL = renderResponse.createRenderURL();

	supportedClientsURL.setParameter("mvcPath", "/edit_supported_clients.jsp");
	supportedClientsURL.setParameter("redirect", redirect);
	supportedClientsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
	supportedClientsURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
	supportedClientsURL.setParameter("portletResource", portletResource);

	navigationItem.setHref(supportedClientsURL.toString());

	navigationItem.setLabel(LanguageUtil.get(request, "supported-clients"));

	navigationItems.add(navigationItem);
}

Set<PublicRenderParameter> publicRenderParameters = selPortlet.getPublicRenderParameters();

if (!publicRenderParameters.isEmpty()) {
	NavigationItem navigationItem = new NavigationItem();

	navigationItem.setActive(tabs1.equals("communication"));

	PortletURL publicRenderParametersURL = renderResponse.createRenderURL();

	publicRenderParametersURL.setParameter("mvcPath", "/edit_public_render_parameters.jsp");
	publicRenderParametersURL.setParameter("redirect", redirect);
	publicRenderParametersURL.setParameter("returnToFullPageURL", returnToFullPageURL);
	publicRenderParametersURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
	publicRenderParametersURL.setParameter("portletResource", portletResource);

	navigationItem.setHref(publicRenderParametersURL.toString());

	navigationItem.setLabel(LanguageUtil.get(request, "communication"));

	navigationItems.add(navigationItem);
}

NavigationItem navigationItem = new NavigationItem();

navigationItem.setActive(tabs1.equals("sharing"));

PortletURL sharingURL = renderResponse.createRenderURL();

sharingURL.setParameter("mvcPath", "/edit_sharing.jsp");
sharingURL.setParameter("redirect", redirect);
sharingURL.setParameter("returnToFullPageURL", returnToFullPageURL);
sharingURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
sharingURL.setParameter("portletResource", portletResource);

navigationItem.setHref(sharingURL.toString());

navigationItem.setLabel(LanguageUtil.get(request, "sharing"));

navigationItems.add(navigationItem);

if (selPortlet.isScopeable()) {
	navigationItem = new NavigationItem();

	navigationItem.setActive(tabs1.equals("scope"));

	PortletURL scopeURL = renderResponse.createRenderURL();

	scopeURL.setParameter("mvcPath", "/edit_scope.jsp");
	scopeURL.setParameter("redirect", redirect);
	scopeURL.setParameter("returnToFullPageURL", returnToFullPageURL);
	scopeURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
	scopeURL.setParameter("portletResource", portletResource);

	navigationItem.setHref(scopeURL.toString());

	navigationItem.setLabel(LanguageUtil.get(request, "scope"));

	navigationItems.add(navigationItem);
}
%>

<clay:navigation-bar
	items="<%= navigationItems %>"
/>