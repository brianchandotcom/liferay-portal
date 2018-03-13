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

<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderActionURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceOrder" />
</portlet:actionURL>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title='<%= LanguageUtil.format(request, "order-x", commerceOrder.getCommerceOrderId()) %>'
/>

<aui:form action="<%= editCommerceOrderActionURL %>" cssClass="order-details-container" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="commerceOrderId" type="hidden" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />

	<%
	request.setAttribute("order_notes.jsp-showLabel", Boolean.TRUE);
	%>

	<liferay-util:include page="/order_notes.jsp" servletContext="<%= application %>" />

	<div class="autofit-float autofit-row order-details-header">
		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="order-date" /></h3>

				<div class="order-date order-details-subtitle">
					<%= commerceOrganizationOrderDisplayContext.getCommerceOrderDate(commerceOrder) %>
				</div>

				<div class="order-time">
					<%= commerceOrganizationOrderDisplayContext.getCommerceOrderTime(commerceOrder) %>
				</div>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="customer" /></h3>

				<div class="customer-name order-details-subtitle">
					<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderCustomerName(commerceOrder)) %>
				</div>

				<div class="customer-id">
					<%= commerceOrganizationOrderDisplayContext.getCommerceOrderCustomerId(commerceOrder) %>
				</div>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="payment" /></h3>

				<c:if test='<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderPaymentMethodName(commerceOrder)) != "" %>'>
					<div class="order-details-subtitle payment-method-name">
						<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderPaymentMethodName(commerceOrder)) %>
					</div>
				</c:if>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="order-value" /></h3>

				<div class="order-details-subtitle order-value">
					<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderValue(commerceOrder)) %>
				</div>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<h3 class="order-details-title"><liferay-ui:message key="status" /></h3>

				<div class="order-details-subtitle order-status">
					<%= commerceOrganizationOrderDisplayContext.getCommerceOrderStatus(commerceOrder) %>
				</div>
			</div>
		</div>

		<c:if test="<%= !commerceOrder.isOpen() %>">
			<div class="autofit-col autofit-col-expand order-details-reorder">
				<div class="autofit-section">
					<aui:button icon="icon-refresh" iconAlign="right" onClick='<%= renderResponse.getNamespace() + "reorderCommerceOrder();" %>' primary="<%= true %>" value="reorder" />
				</div>
			</div>
		</c:if>
	</div>

	<div class="autofit-float autofit-row order-details-footer">
		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<strong><liferay-ui:message key="carrier" /></strong>

				<c:if test='<%= commerceOrganizationOrderDisplayContext.getCommerceOrderShippingOptionName(commerceOrder) != "" %>'>
					<span class="inline-item-after"><%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderShippingOptionName(commerceOrder)) %></span>
				</c:if>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<strong><liferay-ui:message key="method" /></strong>

				<c:if test='<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderShippingMethodName(commerceOrder)) != "" %>'>
					<span class="inline-item-after"><%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderShippingMethodName(commerceOrder)) %></span>
				</c:if>
			</div>
		</div>

		<div class="autofit-col autofit-col-expand">
			<div class="autofit-section">
				<strong><liferay-ui:message key="expected-duration" /></strong>
				<span class="inline-item-after">3 Business Days</span>
			</div>
		</div>
	</div>
</aui:form>

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

<aui:script>
	function <portlet:namespace />reorderCommerceOrder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'reorder';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>