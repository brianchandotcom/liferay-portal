<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

// Configuration

PortletURL configurationURL = renderResponse.createRenderURL();

configurationURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
configurationURL.setParameter("redirect", redirect);
configurationURL.setParameter("returnToFullPageURL", returnToFullPageURL);
configurationURL.setParameter("portletResource", portletResource);

// Supported clients

PortletURL supportedClientsURL = renderResponse.createRenderURL();

supportedClientsURL.setParameter("struts_action", "/portlet_configuration/edit_supported_clients");
supportedClientsURL.setParameter("redirect", redirect);
supportedClientsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
supportedClientsURL.setParameter("portletResource", portletResource);

// Permissions

PortletURL permissionsURL = renderResponse.createRenderURL();

permissionsURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");
permissionsURL.setParameter("redirect", redirect);
permissionsURL.setParameter("returnToFullPageURL", returnToFullPageURL);
permissionsURL.setParameter("portletResource", portletResource);
permissionsURL.setParameter("resourcePrimKey", PortletPermissionUtil.getPrimaryKey(layout.getPlid(), portletResource));

// Public render parameters

PortletURL publicRenderParametersURL = renderResponse.createRenderURL();

publicRenderParametersURL.setParameter("struts_action", "/portlet_configuration/edit_public_render_parameters");
publicRenderParametersURL.setParameter("redirect", redirect);
publicRenderParametersURL.setParameter("returnToFullPageURL", returnToFullPageURL);
publicRenderParametersURL.setParameter("portletResource", portletResource);

// Sharing

PortletURL sharingURL = renderResponse.createRenderURL();

sharingURL.setParameter("struts_action", "/portlet_configuration/edit_sharing");
sharingURL.setParameter("redirect", redirect);
sharingURL.setParameter("returnToFullPageURL", returnToFullPageURL);
sharingURL.setParameter("portletResource", portletResource);

// Scope

PortletURL scopeURL = renderResponse.createRenderURL();

scopeURL.setParameter("struts_action", "/portlet_configuration/edit_scope");
scopeURL.setParameter("redirect", redirect);
scopeURL.setParameter("returnToFullPageURL", returnToFullPageURL);
scopeURL.setParameter("portletResource", portletResource);

int pos = 0;

String tabs1Names = StringPool.BLANK;

if (Validator.isNotNull(selPortlet.getConfigurationActionClass())) {
	tabs1Names += ",setup";

	request.setAttribute("liferay-ui:tabs:url" + pos++, configurationURL.toString());
}

if (selPortlet.hasMultipleMimeTypes()) {
	tabs1Names += ",supported-clients";

	request.setAttribute("liferay-ui:tabs:url" + pos++, supportedClientsURL.toString());
}

if (PortletPermissionUtil.contains(permissionChecker, layout, portletResource, ActionKeys.PERMISSIONS)) {
	tabs1Names += ",permissions";

	request.setAttribute("liferay-ui:tabs:url" + pos++, permissionsURL.toString());
}

if (!selPortlet.getPublicRenderParameters().isEmpty()) {
	tabs1Names += ",communication";

	request.setAttribute("liferay-ui:tabs:url" + pos++, publicRenderParametersURL.toString());
}

tabs1Names += ",sharing";

request.setAttribute("liferay-ui:tabs:url" + pos++, sharingURL.toString());

if (selPortlet.isScopeable()) {
	tabs1Names += ",scope";

	request.setAttribute("liferay-ui:tabs:url" + pos++, scopeURL.toString());
}

if (tabs1Names.startsWith(",")) {
	tabs1Names = tabs1Names.substring(1);
}

String tabs1 = ParamUtil.getString(request, "tabs1");

PortalUtil.addPortletBreadcrumbEntry(request, PortalUtil.getPortletTitle(renderResponse), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "configuration"), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs1), currentURL);
%>

<liferay-ui:tabs names="<%= tabs1Names %>" />