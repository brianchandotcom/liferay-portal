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
CommerceAccountGroupAdminDisplayContext commerceAccountGroupAdminDisplayContext = (CommerceAccountGroupAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAccountGroup commerceAccountGroup = commerceAccountGroupAdminDisplayContext.getCommerceAccountGroup();
PortletURL portletURL = commerceAccountGroupAdminDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCommerceAccountGroup");

String title = LanguageUtil.get(request, "add-account-group");

if (commerceAccountGroup != null) {
	title = LanguageUtil.format(request, "edit-x", commerceAccountGroup.getName(), false);
}

renderResponse.setTitle(title);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	context="<%= commerceAccountGroup %>"
	fullContainerCssClass="col-md-8 offset-md-2"
	key="<%= CommerceAccountGroupScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ACCOUNT_GROUP_GENERAL %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>