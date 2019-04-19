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

String backURL = ParamUtil.getString(request, "backURL", redirect);

long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);

String type = BeanParamUtil.getString(organization, request, "type");

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/users_admin/edit_organization");

if (Validator.isNotNull(redirect)) {
	portletURL.setParameter("redirect", redirect);
}

if (Validator.isNotNull(backURL)) {
	portletURL.setParameter("backURL", backURL);
}

if (organization != null) {
	portletURL.setParameter("organizationId", String.valueOf(organizationId));
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

String headerTitle = null;

if (organization != null) {
	headerTitle = LanguageUtil.format(request, "edit-x", organization.getName(), false);
}
else if (Validator.isNotNull(type)) {
	headerTitle = LanguageUtil.format(request, "add-x", type);
}
else {
	headerTitle = LanguageUtil.get(request, "add-organization");
}

renderResponse.setTitle(headerTitle);
%>

<liferay-frontend:screen-navigation
	containerCssClass="col-lg-8"
	containerWrapperCssClass="container-fluid container-fluid-max-xl container-form-lg"
	context="<%= organization %>"
	headerContainerCssClass=""
	inverted="<%= layout.isTypeControlPanel() %>"
	key="<%= UserScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_ORGANIZATIONS %>"
	menubarCssClass="menubar menubar-transparent menubar-vertical-expand-lg"
	navCssClass="col-lg-3"
	portletURL="<%= portletURL %>"
/>