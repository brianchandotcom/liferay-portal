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
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceListDisplayContext.getCommercePriceList();

PortletURL portletURL = commercePriceListDisplayContext.getPortletURL();

String title = LanguageUtil.get(request, "add-price-list");

if (commercePriceList != null) {
	title = commercePriceList.getName();
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String selectedScreenNavigationCategoryKey = commercePriceListDisplayContext.getSelectedScreenNavigationCategoryKey();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "price-lists"), priceListsURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, portletURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, selectedScreenNavigationCategoryKey, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "price-lists"));
%>

<%@ include file="/price_list_navbar.jspf" %>

<%@ include file="/breadcrumb.jspf" %>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	key="<%= CommercePriceListScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_PRICE_LIST_GENERAL %>"
	modelBean="<%= commercePriceList %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>