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

CommerceAddress billingCommerceAddress = commerceOrder.getBillingAddress();
CommerceAddress shippingCommerceAddress = commerceOrder.getShippingAddress();

CommerceOrderPrice commerceOrderPrice = commerceOrderContentDisplayContext.getCommerceOrderPrice();

CommerceMoney shippingValueCommerceMoney = commerceOrderPrice.getShippingValue();
CommerceDiscountValue shippingCommerceDiscountValue = commerceOrderPrice.getShippingDiscountValue();
CommerceMoney subtotalCommerceMoney = commerceOrderPrice.getSubtotal();
CommerceDiscountValue subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValue();
CommerceMoney taxValueCommerceMoney = commerceOrderPrice.getTaxValue();
CommerceDiscountValue totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValue();
CommerceMoney totalOrderCommerceMoney = commerceOrderPrice.getTotal();

String priceDisplayType = commerceOrderContentDisplayContext.getCommercePriceDisplayType();

if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
	shippingValueCommerceMoney = commerceOrderPrice.getShippingValueWithTaxAmount();
	shippingCommerceDiscountValue = commerceOrderPrice.getShippingDiscountValueWithTaxAmount();
	subtotalCommerceMoney = commerceOrderPrice.getSubtotalWithTaxAmount();
	subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValueWithTaxAmount();
	totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValueWithTaxAmount();
	totalOrderCommerceMoney = commerceOrderPrice.getTotalWithTaxAmount();
}

CommerceAccount commerceAccount = commerceOrderContentDisplayContext.getCommerceAccount();

if (commerceOrder != null) {
	commerceAccount = commerceOrder.getCommerceAccount();
}
%>

<liferay-ui:error exception="<%= CommerceOrderValidatorException.class %>">

	<%
	CommerceOrderValidatorException commerceOrderValidatorException = (CommerceOrderValidatorException)errorException;
	%>

	<c:if test="<%= commerceOrderValidatorException != null %>">

		<%
		for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorException.getCommerceOrderValidatorResults()) {
		%>

			<liferay-ui:message key="<%= HtmlUtil.escape(commerceOrderValidatorResult.getLocalizedMessage()) %>" />

		<%
		}
		%>

	</c:if>
</liferay-ui:error>

<div class="commerce-panel">
	<div class="commerce-panel__content">
		<div class="align-items-center row">
			<div class="col-md-3">
				<div class="commerce-order-title">
					<%= HtmlUtil.escape(commerceAccount.getName()) %>
				</div>
			</div>

			<div class="col-md-3">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="total" /></dt>
					<dd><%= HtmlUtil.escape(totalOrderCommerceMoney.format(locale)) %></dd>
				</dl>
			</div>

			<div class="col-md-3">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="notes" /></dt>
					<dd>

						<%
						request.setAttribute("order_notes.jsp-showLabel", Boolean.TRUE);
						request.setAttribute("order_notes.jsp-taglibLinkCssClass", "link-outline link-outline-borderless link-outline-secondary lfr-icon-item-reverse");
						%>

						<liferay-util:include page="/placed_commerce_orders/order_notes.jsp" servletContext="<%= application %>" />
					</dd>
				</dl>
			</div>
		</div>
	</div>

	<div class="commerce-panel__content">
		<div class="align-items-center row">
			<div class="col-md-3">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="account-id" /></dt>
					<dd><%= commerceAccount.getCommerceAccountId() %></dd>
				</dl>
			</div>

			<div class="col-md-3">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="order-id" /></dt>
					<dd><%= commerceOrder.getCommerceOrderId() %></dd>
				</dl>
			</div>

			<div class="col-md-3">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="order-type" /></dt>
					<dd><%= commerceOrderContentDisplayContext.getCommerceOrderTypeName(languageId) %></dd>
				</dl>
			</div>

			<div class="col-md-3">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="order-date" /></dt>
					<dd>
						<%= commerceOrderContentDisplayContext.getCommerceOrderDate(commerceOrder) %>
						<%= commerceOrderContentDisplayContext.getCommerceOrderTime(commerceOrder) %>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</div>

<c:if test="<%= commerceOrderContentDisplayContext.isShowPurchaseOrderNumber() %>">
	<div class="row">
		<div class="col-md-12">
			<div class="commerce-panel">
				<div class="commerce-panel__title"><liferay-ui:message key="purchase-order-number" /></div>
				<div class="commerce-panel__content">
					<div class="row">
						<div class="col-md-6">
							<dl class="commerce-list">
								<%= HtmlUtil.escape(commerceOrder.getPurchaseOrderNumber()) %>
							</dl>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>

<div class="row">
	<div class="col-md-6">
		<div class="commerce-panel">
			<div class="commerce-panel__title"><liferay-ui:message key="billing-address" /></div>
			<div class="commerce-panel__content">
				<c:if test="<%= billingCommerceAddress != null %>">
					<p><%= HtmlUtil.escape(billingCommerceAddress.getStreet1()) %></p>

					<c:if test="<%= !Validator.isBlank(billingCommerceAddress.getStreet2()) %>">
						<p><%= HtmlUtil.escape(billingCommerceAddress.getStreet2()) %></p>
					</c:if>

					<c:if test="<%= !Validator.isBlank(billingCommerceAddress.getStreet3()) %>">
						<p><%= HtmlUtil.escape(billingCommerceAddress.getStreet3()) %></p>
					</c:if>

					<p><%= HtmlUtil.escape(billingCommerceAddress.getCity() + StringPool.SPACE + billingCommerceAddress.getZip()) %></p>
				</c:if>
			</div>
		</div>
	</div>

	<div class="col-md-6">
		<div class="commerce-panel">
			<div class="commerce-panel__title"><liferay-ui:message key="shipping-address" /></div>
			<div class="commerce-panel__content">
				<c:if test="<%= shippingCommerceAddress != null %>">
					<p><%= HtmlUtil.escape(shippingCommerceAddress.getStreet1()) %></p>

					<c:if test="<%= !Validator.isBlank(shippingCommerceAddress.getStreet2()) %>">
						<p><%= HtmlUtil.escape(shippingCommerceAddress.getStreet2()) %></p>
					</c:if>

					<c:if test="<%= !Validator.isBlank(shippingCommerceAddress.getStreet3()) %>">
						<p><%= HtmlUtil.escape(shippingCommerceAddress.getStreet3()) %></p>
					</c:if>

					<p><%= HtmlUtil.escape(shippingCommerceAddress.getCity() + StringPool.SPACE + shippingCommerceAddress.getZip()) %></p>
				</c:if>
			</div>
		</div>
	</div>

	<div class="col-md-6">
		<div class="commerce-panel">
			<div class="commerce-panel__title"><liferay-ui:message key="delivery-terms" /></div>
			<div class="commerce-panel__content">
				<p>
					<c:if test="<%= commerceOrder.getDeliveryCommerceTermEntryId() != 0 %>">
						<a href="#" id="<%= commerceOrder.getDeliveryCommerceTermEntryId() %>"><%= HtmlUtil.escape(commerceOrder.getDeliveryCommerceTermEntryName()) %></a>

						<liferay-frontend:component
							context='<%=
								HashMapBuilder.<String, Object>put(
									"HTMLElementId", commerceOrder.getDeliveryCommerceTermEntryId()
								).put(
									"modalContent", commerceOrder.getDeliveryCommerceTermEntryDescription()
								).put(
									"modalTitle", commerceOrder.getDeliveryCommerceTermEntryName()
								).build()
							%>'
							module="js/attachModalToHTMLElement"
						/>
					</c:if>
				</p>
			</div>
		</div>
	</div>

	<div class="col-md-6">
		<div class="commerce-panel">
			<div class="commerce-panel__title"><liferay-ui:message key="payment-terms" /></div>
			<div class="commerce-panel__content">
				<p>
					<c:if test="<%= commerceOrder.getPaymentCommerceTermEntryId() != 0 %>">
						<a href="#" id="<%= commerceOrder.getPaymentCommerceTermEntryId() %>"><%= HtmlUtil.escape(commerceOrder.getPaymentCommerceTermEntryName()) %></a>

						<liferay-frontend:component
							context='<%=
								HashMapBuilder.<String, Object>put(
									"HTMLElementId", commerceOrder.getPaymentCommerceTermEntryId()
								).put(
									"modalContent", commerceOrder.getPaymentCommerceTermEntryDescription()
								).put(
									"modalTitle", commerceOrder.getPaymentCommerceTermEntryName()
								).build()
							%>'
							module="js/attachModalToHTMLElement"
						/>
					</c:if>
				</p>
			</div>
		</div>
	</div>
</div>

<div class="commerce-cta is-visible">
	<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderActionURL">
		<portlet:param name="mvcRenderCommandName" value="/commerce_order_content/view_commerce_order_details" />
	</portlet:actionURL>

	<aui:form action="<%= editCommerceOrderActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" />
	</aui:form>

	<aui:button cssClass="btn btn-lg btn-secondary" onClick='<%= liferayPortletResponse.getNamespace() + "reorderCommerceOrder();" %>' value="reorder" />

	<liferay-util:dynamic-include key="com.liferay.commerce.order.content.web#/place_order_detail_cta#" />
</div>

<div class="row">
	<div class="col-md-12">

		<%
		java.util.Map<String, String> contextParams = new java.util.HashMap<>();

		contextParams.put("commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId()));
		%>

		<clay:data-set-display
			contextParams="<%= contextParams %>"
			dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDER_ITEMS %>"
			id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDER_ITEMS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			nestedItemsKey="orderItemId"
			nestedItemsReferenceKey="orderItems"
			pageNumber="<%= 1 %>"
			portletURL="<%= commerceOrderContentDisplayContext.getPortletURL() %>"
			style="stacked"
		/>
	</div>
</div>

<div class="row">
	<div class="col-md-9">
	</div>

	<liferay-ui:icon-menu
		direction="right"
		icon="<%= StringPool.BLANK %>"
		markupView="lexicon"
		message="<%= StringPool.BLANK %>"
		showWhenSingleIcon="<%= true %>"
		triggerCssClass="btn btn-lg btn-monospaced btn-primary position-fixed thumb-menu"
	>
		<liferay-ui:icon
			icon="print"
			message="print"
			target="_blank"
			url="<%= commerceOrderContentDisplayContext.getExportCommerceOrderReportURL() %>"
		/>
	</liferay-ui:icon-menu>

	<div class="col-md-3">
		<div class="commerce-panel">
			<div class="commerce-panel__content">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="subtotal" /></dt>
					<dd class="text-right"><%= HtmlUtil.escape(subtotalCommerceMoney.format(locale)) %></dd>

					<c:if test="<%= (subtotalCommerceDiscountValue != null) && (BigDecimal.ZERO.compareTo(subtotalCommerceDiscountValue.getDiscountPercentage()) < 0) %>">

						<%
						CommerceMoney subtotalDiscountAmountCommerceMoney = subtotalCommerceDiscountValue.getDiscountAmount();
						%>

						<dt><liferay-ui:message key="subtotal-discount" /></dt>
						<dd class="text-right"><%= HtmlUtil.escape(subtotalDiscountAmountCommerceMoney.format(locale)) %></dd>
						<dt></dt>
						<dd class="text-right"><%= HtmlUtil.escape(commerceOrderContentDisplayContext.getLocalizedPercentage(subtotalCommerceDiscountValue.getDiscountPercentage(), locale)) %></dd>
					</c:if>

					<dt><liferay-ui:message key="delivery" /></dt>
					<dd class="text-right"><%= HtmlUtil.escape(shippingValueCommerceMoney.format(locale)) %></dd>

					<c:if test="<%= (shippingCommerceDiscountValue != null) && (BigDecimal.ZERO.compareTo(shippingCommerceDiscountValue.getDiscountPercentage()) < 0) %>">

						<%
						CommerceMoney shippingDiscountAmountCommerceMoney = shippingCommerceDiscountValue.getDiscountAmount();
						%>

						<dt><liferay-ui:message key="delivery-discount" /></dt>
						<dd class="text-right"><%= HtmlUtil.escape(shippingDiscountAmountCommerceMoney.format(locale)) %></dd>
						<dt></dt>
						<dd class="text-right"><%= HtmlUtil.escape(commerceOrderContentDisplayContext.getLocalizedPercentage(shippingCommerceDiscountValue.getDiscountPercentage(), locale)) %></dd>
					</c:if>

					<c:if test="<%= priceDisplayType.equals(CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE) %>">
						<dt><liferay-ui:message key="tax" /></dt>
						<dd class="text-right"><%= HtmlUtil.escape(taxValueCommerceMoney.format(locale)) %></dd>
					</c:if>

					<c:if test="<%= (totalCommerceDiscountValue != null) && (BigDecimal.ZERO.compareTo(totalCommerceDiscountValue.getDiscountPercentage()) < 0) %>">

						<%
						CommerceMoney totalDiscountAmountCommerceMoney = totalCommerceDiscountValue.getDiscountAmount();
						%>

						<dt><liferay-ui:message key="total-discount" /></dt>
						<dd class="text-right"><%= HtmlUtil.escape(totalDiscountAmountCommerceMoney.format(locale)) %></dd>
						<dt></dt>
						<dd class="text-right"><%= HtmlUtil.escape(commerceOrderContentDisplayContext.getLocalizedPercentage(totalCommerceDiscountValue.getDiscountPercentage(), locale)) %></dd>
					</c:if>
				</dl>
			</div>

			<div class="commerce-panel__content">
				<dl class="commerce-list">
					<dt><liferay-ui:message key="total" /></dt>
					<dd class="text-right"><%= HtmlUtil.escape(totalOrderCommerceMoney.format(locale)) %></dd>
				</dl>
			</div>
		</div>
	</div>
</div>

<aui:script>
	function <portlet:namespace />viewCommerceOrderShipments(uri) {
		Liferay.Util.openWindow({
			dialog: {
				centered: true,
				destroyOnClose: true,
				modal: true,
			},
			dialogIframe: {
				bodyCssClass: 'dialog',
			},
			id: 'viewCommerceOrderShipmentsDialog',
			title: '',
			uri: uri,
		});
	}

	function <portlet:namespace />reorderCommerceOrder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'reorder';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>