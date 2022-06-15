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
CommerceOrder commerceOrder = commerceOrderContentDisplayContext.getCommerceOrder();
%>

<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderShippingAddressActionURL" />

<commerce-ui:modal-content
	contentCssClasses="p-0"
	title='<%= LanguageUtil.get(request, "shipping-address") %>'
>
	<aui:form action="<%= editCommerceOrderShippingAddressActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="selectShippingAddress" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId())
				).build()
			%>'
			creationMenu='<%= commerceOrderContentDisplayContext.getCommerceAddressCreationMenu("/commerce_open_order_content/edit_commerce_order_shipping_address") %>'
			dataProviderKey="<%= CommerceOrderFDSNames.SHIPPING_ADDRESSES %>"
			formName="fm"
			id="<%= CommerceOrderFDSNames.SHIPPING_ADDRESSES %>"
			itemsPerPage="<%= 10 %>"
			selectedItems="<%= Collections.singletonList(String.valueOf(commerceOrder.getShippingAddressId())) %>"
			selectedItemsKey="addressId"
			selectionType="single"
		/>
	</aui:form>
</commerce-ui:modal-content>