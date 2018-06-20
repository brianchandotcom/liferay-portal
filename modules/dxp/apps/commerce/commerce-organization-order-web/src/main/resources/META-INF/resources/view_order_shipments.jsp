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
long commerceOrderItemId = ParamUtil.getLong(request, "commerceOrderItemId");

CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceShipmentItem> commerceShipmentItems = commerceOrganizationOrderDisplayContext.getCommerceShipmentItems(commerceOrderItemId);
%>

<liferay-ui:search-container
	cssClass="table-nowrap table-responsive"
	id="commerceShipmentItems"
	total="<%= commerceShipmentItems.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= commerceShipmentItems %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.model.CommerceShipmentItem"
		keyProperty="commerceShipmentItemId"
		modelVar="commerceShipmentItem"
	>

		<%
		CommerceShipment commerceShipment = commerceShipmentItem.getCommerceShipment();
		%>

		<liferay-ui:search-container-column-text
			name="status"
			value="<%= commerceOrganizationOrderDisplayContext.getCommerceShipmentStatusLabel(commerceShipment.getStatus()) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-list-title"
			name="shipment-number"
			value="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="carrier"
			value="<%= commerceShipment.getCarrier() %>"
		/>

		<liferay-ui:search-container-column-text
			name="tracking-number"
			value="<%= commerceShipment.getTrackingNumber() %>"
		/>

		<liferay-ui:search-container-column-text
			name="quantity"
			value="<%= String.valueOf(commerceShipmentItem.getQuantity()) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="list"
		markupView="lexicon"
		paginate="<%= false %>"
	/>
</liferay-ui:search-container>