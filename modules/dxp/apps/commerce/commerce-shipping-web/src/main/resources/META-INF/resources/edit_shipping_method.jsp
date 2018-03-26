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

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceShippingMethodsDisplayContext commerceShippingMethodsDisplayContext = (CommerceShippingMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingMethod commerceShippingMethod = commerceShippingMethodsDisplayContext.getCommerceShippingMethod();

String title = LanguageUtil.format(request, "edit-x", commerceShippingMethod.getName(locale), false);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String selectedScreenNavigationEntryKey = commerceShippingMethodsDisplayContext.getSelectedScreenNavigationEntryKey();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, currentURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, selectedScreenNavigationEntryKey), StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	key="<%= CommerceShippingScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_SHIPPING_METHOD %>"
	modelBean="<%= commerceShippingMethod %>"
	portletURL="<%= currentURLObj %>"
	navCssClass="col-md-2"
/>