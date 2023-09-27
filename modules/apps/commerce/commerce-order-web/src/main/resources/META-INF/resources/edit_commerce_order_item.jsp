<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderItem commerceOrderItem = commerceOrderEditDisplayContext.getCommerceOrderItem();

CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

String title = LanguageUtil.format(request, "order-x", commerceOrder.getCommerceOrderId()) + " - " + HtmlUtil.escape(commerceOrderItem.getSku());

renderResponse.setTitle(title);

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	redirect = String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderItemsPortletURL());
}

portletDisplay.setURLBack(redirect);
%>

<div id="<portlet:namespace />editOrderItemContainer">
	<liferay-frontend:side-panel-content
		screenNavigatorKey="<%= CommerceOrderScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_ITEM_GENERAL %>"
		screenNavigatorModelBean="<%= commerceOrderItem %>"
		screenNavigatorPortletURL="<%= currentURLObj %>"
		title='<%= StringBundler.concat(commerceOrderItem.getName(locale), " - ", commerceOrderItem.getSku()) %>'
	/>
</div>