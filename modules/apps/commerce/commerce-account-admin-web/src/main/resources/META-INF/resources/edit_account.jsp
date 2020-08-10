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
CommerceAccountAdminDisplayContext commerceAccountAdminDisplayContext = (CommerceAccountAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAccount commerceAccount = commerceAccountAdminDisplayContext.getCommerceAccount();
PortletURL portletURL = commerceAccountAdminDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCommerceAccount");

String title = LanguageUtil.get(request, "add-account");

if (commerceAccount != null) {
	title = LanguageUtil.format(request, "edit-x", commerceAccount.getName(), false);
}

renderResponse.setTitle(title);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	context="<%= commerceAccount %>"
	fullContainerCssClass="col-md-8 offset-md-2"
	key="<%= CommerceAccountScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ACCOUNT_GENERAL %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>