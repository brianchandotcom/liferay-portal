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

long commerceOrderId = commerceOrder.getCommerceOrderId();
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderPaymentMethodActionURL" />

<commerce-ui:modal-content
	contentCssClasses="p-0"
>
	<aui:form action="<%= editCommerceOrderPaymentMethodActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="paymentMethod" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

		<liferay-ui:error exception="<%= CommerceOrderPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />

		<clay:data-set-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceOrderId", String.valueOf(commerceOrderId)
				).build()
			%>'
			dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PAYMENT_METHODS %>"
			formId="fm"
			id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PAYMENT_METHODS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
			selectedItems="<%= Collections.singletonList(String.valueOf(commerceOrder.getCommercePaymentMethodKey())) %>"
			selectedItemsKey="paymentMethodKey"
			selectionType="single"
		/>
	</aui:form>
</commerce-ui:modal-content>