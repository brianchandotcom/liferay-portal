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
CommerceShipmentItemDisplayContext commerceShipmentItemDisplayContext = (CommerceShipmentItemDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipmentItem commerceShipmentItem = commerceShipmentItemDisplayContext.getCommerceShipmentItem();

long commerceShipmentId = commerceShipmentItemDisplayContext.getCommerceShipmentId();
long commerceShipmentItemId = commerceShipmentItemDisplayContext.getCommerceShipmentItemId();

PortletURL shipmentItemsURL = renderResponse.createRenderURL();

shipmentItemsURL.setParameter("mvcRenderCommandName", "viewCommerceShipmentDetail");
shipmentItemsURL.setParameter("commerceShipmentId", String.valueOf(commerceShipmentId));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(shipmentItemsURL.toString());
%>

<portlet:actionURL name="editCommerceShipmentItem" var="editCommerceShipmentItemActionURL" />

<aui:form action="<%= editCommerceShipmentItemActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= shipmentItemsURL.toString() %>" />
	<aui:input name="commerceShipmentId" type="hidden" value="<%= String.valueOf(commerceShipmentId) %>" />
	<aui:input name="commerceShipmentItemId" type="hidden" value="<%= String.valueOf(commerceShipmentItemId) %>" />

	<liferay-ui:error exception="<%= CommerceShipmentItemQuantityException.class %>" message="please-enter-a-valid-quantity" />

	<aui:model-context bean="<%= commerceShipmentItem %>" model="<%= CommerceShipmentItem.class %>" />

	<aui:input name="quantity" />

	<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

	<aui:button cssClass="btn-lg" href="<%= shipmentItemsURL.toString() %>" type="cancel" />
</aui:form>