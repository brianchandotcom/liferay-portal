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