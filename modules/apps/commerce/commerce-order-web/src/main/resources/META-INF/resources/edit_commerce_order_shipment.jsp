<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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