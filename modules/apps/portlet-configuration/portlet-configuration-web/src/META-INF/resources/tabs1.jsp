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
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

// Configuration

PortletURL configurationURL = renderResponse.createRenderURL();

configurationURL.setParameter("mvcPath", "/edit_configuration.jsp");
configurationURL.setParameter("redirect", redirect);
configurationURL.setParameter("returnToFullPageURL", returnToFullPageURL);
configurationURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
configurationURL.setParameter("portletResource", portletResource);

// Supported clients

PortletURL supportedClientsURL = renderResponse.createRenderURL();

supportedClientsURL.setParameter("mvcPath", "/edit_supported_clients.jsp");
supportedClientsURL.setParameter("redirect", redirect);
supportedClientsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
supportedClientsURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
supportedClientsURL.setParameter("portletResource", portletResource);

// Permissions

PortletURL permissionsURL = renderResponse.createRenderURL();

permissionsURL.setParameter("mvcPath", "/edit_permissions.jsp");
permissionsURL.setParameter("redirect", redirect);
permissionsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
permissionsURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
permissionsURL.setParameter("portletResource", portletResource);
permissionsURL.setParameter("resourcePrimKey", PortletPermissionUtil.getPrimaryKey(layout.getPlid(), portletResource));

// Public render parameters

PortletURL publicRenderParametersURL = renderResponse.createRenderURL();

publicRenderParametersURL.setParameter("mvcPath", "/edit_public_render_parameters.jsp");
publicRenderParametersURL.setParameter("redirect", redirect);
publicRenderParametersURL.setParameter("returnToFullPageURL", returnToFullPageURL);
publicRenderParametersURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
publicRenderParametersURL.setParameter("portletResource", portletResource);

// Sharing

PortletURL sharingURL = renderResponse.createRenderURL();

sharingURL.setParameter("mvcPath", "/edit_sharing.jsp");
sharingURL.setParameter("redirect", redirect);
sharingURL.setParameter("returnToFullPageURL", returnToFullPageURL);
sharingURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
sharingURL.setParameter("portletResource", portletResource);

// Scope

PortletURL scopeURL = renderResponse.createRenderURL();

scopeURL.setParameter("mvcPath", "/edit_scope.jsp");
scopeURL.setParameter("redirect", redirect);
scopeURL.setParameter("returnToFullPageURL", returnToFullPageURL);
scopeURL.setParameter("portletConfiguration", Boolean.TRUE.toString());
scopeURL.setParameter("portletResource", portletResource);

int pos = 0;

String tabs1Names = StringPool.BLANK;
String[] tabs1URLs = new String[0];

if (selPortlet.getConfigurationActionInstance() != null) {
	tabs1Names += ",setup";

	tabs1URLs = ArrayUtil.append(tabs1URLs, configurationURL.toString());
}

if (selPortlet.hasMultipleMimeTypes()) {
	tabs1Names += ",supported-clients";

	tabs1URLs = ArrayUtil.append(tabs1URLs, supportedClientsURL.toString());
}

if (PortletPermissionUtil.contains(permissionChecker, layout, portletResource, ActionKeys.PERMISSIONS)) {
	tabs1Names += ",permissions";

	tabs1URLs = ArrayUtil.append(tabs1URLs, permissionsURL.toString());
}

if (!selPortlet.getPublicRenderParameters().isEmpty()) {
	tabs1Names += ",communication";

	tabs1URLs = ArrayUtil.append(tabs1URLs, publicRenderParametersURL.toString());
}

tabs1Names += ",sharing";

tabs1URLs = ArrayUtil.append(tabs1URLs, sharingURL.toString());

if (selPortlet.isScopeable()) {
	tabs1Names += ",scope";

	tabs1URLs = ArrayUtil.append(tabs1URLs, scopeURL.toString());
}

if (tabs1Names.startsWith(",")) {
	tabs1Names = tabs1Names.substring(1);
}

String tabs1 = ParamUtil.getString(request, "tabs1");

PortalUtil.addPortletBreadcrumbEntry(request, PortalUtil.getPortletTitle(renderResponse), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "configuration"), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, tabs1), currentURL);
%>

<liferay-ui:tabs names="<%= tabs1Names %>" type="pills" urls="<%= tabs1URLs %>" />