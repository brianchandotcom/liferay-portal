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

long commerceShipmentId = commerceShipmentDisplayContext.getCommerceShipmentId();

long shipmentUserId = commerceShipmentDisplayContext.getShipmentUserId();
long commerceAddressId = commerceShipmentDisplayContext.getCommerceAddressId();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(shipmentsURL);
%>

<portlet:actionURL name="editCommerceShipment" var="editCommerceShipmentActionURL" />

<aui:form action="<%= editCommerceShipmentActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShipment == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= shipmentsURL %>" />
	<aui:input name="commerceShipmentId" type="hidden" value="<%= String.valueOf(commerceShipmentId) %>" />

	<aui:model-context bean="<%= commerceShipment %>" model="<%= CommerceShipment.class %>" />

	<aui:select label="user" name="shipmentUserId" showEmptyOption="<%= true %>">

		<%
		List<User> users = commerceShipmentDisplayContext.getUsers();

		for (User curUser : users) {
		%>

			<aui:option label="<%= curUser.getFullName() %>" selected="<%= (commerceShipment != null) && (commerceShipment.getShipmentUserId() == curUser.getUserId()) %>" value="<%= curUser.getUserId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:select label="shipping-address" name="commerceAddressId" showEmptyOption="<%= true %>">

		<%
		List<CommerceAddress> commerceAddresses = commerceShipmentDisplayContext.getCommerceAddresses();

		for (CommerceAddress commerceAddress : commerceAddresses) {
		%>

			<aui:option label="<%= commerceAddress.getName() %>" selected="<%= (commerceShipment != null) && (commerceShipment.getCommerceAddressId() == commerceAddress.getCommerceAddressId()) %>" value="<%= commerceAddress.getCommerceAddressId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:select label="method" name="commerceShippingMethodId" showEmptyOption="<%= true %>">

		<%
		List<CommerceShippingMethod> commerceShippingMethods = commerceShipmentDisplayContext.getCommerceShippingMethods();

		for (CommerceShippingMethod commerceShippingMethod : commerceShippingMethods) {
		%>

			<aui:option label="<%= commerceShippingMethod.getName() %>" selected="<%= (commerceShipment != null) && (commerceShipment.getCommerceShippingMethodId() == commerceShippingMethod.getCommerceShippingMethodId()) %>" value="<%= commerceShippingMethod.getCommerceShippingMethodId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:select label="warehouse" name="commerceWarehouseId">

		<%
		List<CommerceWarehouse> commerceWarehouses = commerceShipmentDisplayContext.getCommerceWarehouses();

		for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
		%>

			<aui:option label="<%= commerceWarehouse.getName() %>" selected="<%= (commerceShipment != null) && (commerceShipment.getCommerceWarehouseId() == commerceWarehouse.getCommerceWarehouseId()) %>" value="<%= commerceWarehouse.getCommerceWarehouseId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:input name="carrier" />

	<aui:input name="trackingNumber" />

	<aui:input name="expectedDuration" />

	<aui:input name="shippingDate" />

	<aui:input name="expectedDate" />

	<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

	<aui:button cssClass="btn-lg" href="<%= shipmentsURL %>" type="cancel" />
</aui:form>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace />shipmentUserId',
				selectData: function(callback) {
					Liferay.Service(
						'/portal.user/get-group-users',
						{
							groupId: <%= scopeGroupId %>
						},
						callback
					);
					},
				selectDesc: 'nameCurrentValue',
				selectId: 'userId',
				selectSort: '<%= true %>',
				selectVal: '<%= shipmentUserId %>'
			},
			{
				select: '<portlet:namespace />commerceAddressId',
				selectData: function(callback, selectKey) {
					Liferay.Service(
						'/commerce.commerceaddress/get-commerce-addresses',
						{
							groupId: <%= scopeGroupId %>,
							addressUserId: Number(selectKey)
						},
						callback
					);
				},
				selectDesc: 'name',
				selectId: 'commerceAddressId',
				selectVal: '<%= commerceAddressId %>'
			}
		]
	);
</aui:script>