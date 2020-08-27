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
CommerceCartContentTotalDisplayContext commerceCartContentTotalDisplayContext = (CommerceCartContentTotalDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceMoney subtotal = null;
CommerceMoney taxValue = null;
CommerceMoney totalOrder = null;
CommerceMoney totalDiscountAmount = null;
CommerceMoney subtotalDiscountAmount = null;
CommerceDiscountValue totalDiscountValue = null;
CommerceDiscountValue subtotalDiscountValue = null;

String priceDisplayType = commerceCartContentTotalDisplayContext.getCommercePriceDisplayType();

CommerceOrderPrice commerceOrderPrice = commerceCartContentTotalDisplayContext.getCommerceOrderPrice();

if (commerceOrderPrice != null) {
	subtotal = commerceOrderPrice.getSubtotal();
	subtotalDiscountValue = commerceOrderPrice.getSubtotalDiscountValue();

	if (subtotalDiscountValue != null) {
		subtotalDiscountAmount = subtotalDiscountValue.getDiscountAmount();
	}

	taxValue = commerceOrderPrice.getTaxValue();
	totalOrder = commerceOrderPrice.getTotal();
	totalDiscountValue = commerceOrderPrice.getTotalDiscountValue();

	if (totalDiscountValue != null) {
		totalDiscountAmount = totalDiscountValue.getDiscountAmount();
	}

	if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
		subtotal = commerceOrderPrice.getSubtotalWithTaxAmount();
		subtotalDiscountValue = commerceOrderPrice.getSubtotalDiscountValueWithTaxAmount();

		if (subtotalDiscountValue != null) {
			subtotalDiscountAmount = subtotalDiscountValue.getDiscountAmount();
		}

		totalOrder = commerceOrderPrice.getTotalWithTaxAmount();
		totalDiscountValue = commerceOrderPrice.getTotalDiscountValueWithTaxAmount();

		if (totalDiscountValue != null) {
			totalDiscountAmount = totalDiscountValue.getDiscountAmount();
		}
	}
}

Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
	"commerceCartContentTotalDisplayContext", commerceCartContentTotalDisplayContext
).build();

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceCartContentTotalDisplayContext.getSearchContainer();
%>

<liferay-ddm:template-renderer
	className="<%= CommerceCartContentTotalPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceCartContentTotalDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceCartContentTotalDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= commerceOrderItemSearchContainer.getResults() %>"
>
	<div class="order-total text-dark">
		<c:if test="<%= subtotal != null %>">
			<div class="row">
				<c:if test="<%= subtotalDiscountValue != null %>">
					<div class="col-auto">
						<h4><liferay-ui:message key="subtotal-discount" /></h4>
					</div>

					<div class="col-auto">
						<span>(<%= HtmlUtil.escape(subtotalDiscountAmount.format(locale)) %>)</span>
					</div>
				</c:if>

				<div class="col-auto">
					<h3 class="h4"><liferay-ui:message key="subtotal" /></h3>
				</div>

				<div class="col text-right">
					<h3 class="h4"><%= HtmlUtil.escape(subtotal.format(locale)) %></h3>
				</div>
			</div>
		</c:if>

		<c:if test="<%= (taxValue != null) && priceDisplayType.equals(CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE) %>">
			<div class="row">
				<div class="col-auto">
					<h3 class="h4"><liferay-ui:message key="tax" /></h3>
				</div>

				<div class="col text-right">
					<h3 class="h4"><%= HtmlUtil.escape(taxValue.format(locale)) %></h3>
				</div>
			</div>
		</c:if>

		<c:if test="<%= totalOrder != null %>">
			<div class="row">
				<c:if test="<%= totalDiscountValue != null %>">
					<div class="col-auto">
						<h4><liferay-ui:message key="total-discount" /></h4>
					</div>

					<div class="col-auto">
						<span>(<%= HtmlUtil.escape(totalDiscountAmount.format(locale)) %>)</span>
					</div>
				</c:if>

				<div class="col-auto">
					<h3 class="h4"><liferay-ui:message key="total" /></h3>
				</div>

				<div class="col text-right">
					<h3 class="h4"><%= HtmlUtil.escape(totalOrder.format(locale)) %></h3>
				</div>
			</div>
		</c:if>
	</div>

	<aui:button-row>

		<%
		PortletURL checkoutPortletURL = commerceCartContentTotalDisplayContext.getCheckoutPortletURL();
		%>

		<aui:button cssClass="btn-lg" disabled="<%= !commerceCartContentTotalDisplayContext.isValidCommerceOrder() %>" href="<%= checkoutPortletURL.toString() %>" value="checkout" />
	</aui:button-row>

	<aui:script>
		Liferay.after('commerce:productAddedToCart', function (event) {
			Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
		});
	</aui:script>
</liferay-ddm:template-renderer>