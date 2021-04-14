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

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();

String headerTitle = null;

if (commerceOrder != null) {
	headerTitle = String.valueOf(commerceOrder.getCommerceOrderId());
}
else {
	headerTitle = LanguageUtil.get(request, "add-order");
}

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommerceOrderExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_order/edit_commerce_order_external_reference_code" />
	<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceOrderEditDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceOrder %>"
	beanIdLabel="id"
	externalReferenceCode="<%= commerceOrder.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCommerceOrderExternalReferenceCodeURL %>"
	model="<%= CommerceOrder.class %>"
	thumbnailUrl="<%= commerceOrderEditDisplayContext.getCommerceAccountThumbnailURL() %>"
	title="<%= headerTitle %>"
	transitionPortletURL="<%= commerceOrderEditDisplayContext.getTransitionOrderPortletURL() %>"
/>

<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderBillingAddressException.class %>" message="the-order-selected-needs-a-billing-address" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderShippingAddressException.class %>" message="the-order-selected-needs-a-shipping-address" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderStatusException.class %>" message="this-order-cannot-be-transitioned" />

<div id="<portlet:namespace />editOrderContainer">
	<liferay-frontend:screen-navigation
		containerWrapperCssClass="container mt-4"
		key="<%= CommerceOrderScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_ORDER_GENERAL %>"
		modelBean="<%= commerceOrder %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>