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
CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrganizationOrderDisplayContext.getCommerceOrder();
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title='<%= LanguageUtil.format(request, "order-x", commerceOrder.getCommerceOrderId()) %>'
/>

<div class="order-details-container">

	<%
	request.setAttribute("order_notes.jsp-showLabel", Boolean.TRUE);
	%>

	<liferay-util:include page="/order_notes.jsp" servletContext="<%= application %>" />

	<div class="order-details-header row">
		<div class="col-md-2">
			<h3><liferay-ui:message key="order-date" /></h3>

			<div class="order-date">
				<%= commerceOrganizationOrderDisplayContext.getCommerceOrderDate(commerceOrder) %>
			</div>

			<div class="order-time">
				<%= commerceOrganizationOrderDisplayContext.getCommerceOrderTime(commerceOrder) %>
			</div>
		</div>

		<div class="col-md-2">
			<h3><liferay-ui:message key="customer" /></h3>

			<div class="customer-name">
				<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderCustomerName(commerceOrder)) %>
			</div>

			<div class="customer-id">
				<%= commerceOrganizationOrderDisplayContext.getCommerceOrderCustomerId(commerceOrder) %>
			</div>
		</div>

		<div class="col-md-2">
			<h3><liferay-ui:message key="payment" /></h3>

			<div class="payment-method-name">
				<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderPaymentMethodName(commerceOrder)) %>
			</div>
		</div>

		<div class="col-md-2">
			<h3><liferay-ui:message key="order-value" /></h3>

			<div class="order-value">
				<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderValue(commerceOrder)) %>
			</div>
		</div>

		<div class="col-md-2">
			<h3><liferay-ui:message key="status" /></h3>

			<div class="order-status">
				<%= commerceOrganizationOrderDisplayContext.getCommerceOrderStatus(commerceOrder) %>
			</div>
		</div>

		<c:if test="<%= !commerceOrder.isOpen() %>">
			<div class="col-md-2">
				<aui:button icon="icon-refresh" iconAlign="right" primary="<%= true %>" value="reorder" />
			</div>
		</c:if>
	</div>

	<div class="order-details-footer row">
		<div class="col-md-4">
			<strong><liferay-ui:message key="carrier" /></strong> <%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderShippingOptionName(commerceOrder)) %>
		</div>

		<div class="col-md-4">
			<strong><liferay-ui:message key="method" /></strong> <%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderShippingMethodName(commerceOrder)) %>
		</div>

		<div class="col-md-4">
			<strong><liferay-ui:message key="expected-duration" /></strong>
		</div>
	</div>
</div>

<liferay-ui:search-container
	searchContainer="<%= commerceOrganizationOrderDisplayContext.getCommerceOrderItemsSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.commerce.model.CommerceOrderItem"
		escapedModel="<%= true %>"
		keyProperty="commerceOrderItemId"
		modelVar="commerceOrderItem"
	>
		<liferay-ui:search-container-column-text
			property="sku"
		/>

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(commerceOrderItem.getTitle(locale)) %>"
		/>

		<liferay-ui:search-container-column-text
			property="quantity"
		/>

		<liferay-ui:search-container-column-text
			name="price"
			value="<%= commerceOrganizationOrderDisplayContext.getCommerceOrderItemPrice(commerceOrderItem) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" />
</liferay-ui:search-container>