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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceOrderEditDisplayContext.getCommerceShipment();
CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();

String title = LanguageUtil.format(request, "order-x", commerceOrder.getCommerceOrderId()) + " - " + commerceShipment.getCommerceShipmentId();

renderResponse.setTitle(title);

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	redirect = String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderItemsPortletURL());
}

portletDisplay.setURLBack(redirect);
%>

<div id="<portlet:namespace />editOrderShipmentContainer">
	<liferay-frontend:screen-navigation
		containerWrapperCssClass="side-panel-iframe-wrapper"
		headerContainerCssClass="side-panel-iframe-menu-wrapper"
		key="<%= CommerceOrderScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_SHIPMENT_GENERAL %>"
		modelBean="<%= commerceShipment %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>