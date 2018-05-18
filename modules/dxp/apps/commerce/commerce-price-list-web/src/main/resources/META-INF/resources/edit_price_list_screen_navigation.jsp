<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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