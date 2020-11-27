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
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceShipmentDisplayContext.getCommerceShipment();

CommerceAccount commerceAccount = commerceShipment.getCommerceAccount();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-ui:error embed="<%= false %>" exception="<%= CommerceShipmentStatusException.class %>" message="please-select-a-valid-warehouse-and-quantity-for-all-shipment-items" />
<liferay-ui:error embed="<%= false %>" exception="<%= CommerceShipmentItemQuantityException.class %>" message="please-add-at-least-one-item-to-the-shipment" />

<commerce-ui:header
	actions="<%= commerceShipmentDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceShipment %>"
	beanIdLabel="id"
	model="<%= CommerceShipment.class %>"
	thumbnailUrl="<%= commerceShipmentDisplayContext.getCommerceAccountThumbnailURL(commerceAccount, themeDisplay.getPathImage()) %>"
	title="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<div id="<portlet:namespace />editShipmentContainer">
	<liferay-frontend:screen-navigation
		fullContainerCssClass="col-12 pt-4"
		key="<%= CommerceShipmentScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_SHIPMENT_GENERAL %>"
		modelBean="<%= commerceShipment %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>