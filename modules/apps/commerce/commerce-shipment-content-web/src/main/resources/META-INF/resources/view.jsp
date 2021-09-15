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
CommerceShipmentContentDisplayContext commerceShipmentContentDisplayContext = (CommerceShipmentContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-ui:search-container
	cssClass="table-nowrap table-responsive"
	id="commerceShipments"
	searchContainer="<%= commerceShipmentContentDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.commerce.model.CommerceShipment"
		keyProperty="commerceShipmentId"
		modelVar="commerceShipment"
	>

		<%
		PortletURL rowURL = PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/commerce_shipment_content/view_commerce_shipment_items"
		).setRedirect(
			currentURL
		).setParameter(
			"commerceShipmentId", commerceShipment.getCommerceShipmentId()
		).buildPortletURL();
		%>

		<liferay-ui:search-container-column-text
			cssClass="font-weight-bold table-list-title"
			href="<%= rowURL %>"
			name="shipment-number"
			property="commerceShipmentId"
		/>

		<liferay-ui:search-container-column-text
			name="customer-name"
			value="<%= HtmlUtil.escape(commerceShipmentContentDisplayContext.getCommerceShipmentAccountName(commerceShipment)) %>"
		/>

		<liferay-ui:search-container-column-text
			name="customer-id"
			value="<%= String.valueOf(commerceShipmentContentDisplayContext.getCommerceShipmentAccountId(commerceShipment)) %>"
		/>

		<liferay-ui:search-container-column-date
			name="shipping-date"
			property="shippingDate"
		/>

		<liferay-ui:search-container-column-date
			name="estimated-delivery-date"
			property="expectedDate"
		/>

		<liferay-ui:search-container-column-text
			name="status"
			value="<%= commerceShipmentContentDisplayContext.getCommerceShipmentStatusLabel(commerceShipment.getStatus()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<liferay-ui:icon
				label="<%= true %>"
				message="view-details"
				url="<%= rowURL.toString() %>"
			/>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>