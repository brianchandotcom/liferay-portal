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
CommerceOrderContentDisplayContext commerceOrderContentDisplayContext = (CommerceOrderContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceOrderItemId = ParamUtil.getLong(request, "commerceOrderItemId");

List<CommerceShipmentItem> commerceShipmentItems = commerceOrderContentDisplayContext.getCommerceShipmentItems(commerceOrderItemId);
%>

<commerce-ui:modal-content
	contentCssClasses="p-0"
	showCancelButton="<%= false %>"
	showSubmitButton="<%= false %>"
	title='<%= LanguageUtil.get(request, "shipments") %>'
>
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
				value="<%= commerceOrderContentDisplayContext.getCommerceShipmentStatusLabel(commerceShipment.getStatus()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-list-title"
				name="shipment-number"
				value="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="carrier"
				value="<%= HtmlUtil.escape(commerceShipment.getCarrier()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="tracking-number"
				value="<%= HtmlUtil.escape(commerceShipment.getTrackingNumber()) %>"
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
</commerce-ui:modal-content>