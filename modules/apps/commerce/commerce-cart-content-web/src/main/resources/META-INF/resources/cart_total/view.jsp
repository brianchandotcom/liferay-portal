<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceCartContentTotalDisplayContext commerceCartContentTotalDisplayContext = (CommerceCartContentTotalDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceMoney subtotalCommerceMoney = null;
CommerceMoney taxValueCommerceMoney = null;
CommerceMoney totalOrderCommerceMoney = null;
CommerceMoney totalDiscountAmountCommerceMoney = null;
CommerceMoney subtotalDiscountAmountCommerceMoney = null;
CommerceDiscountValue totalCommerceDiscountValue = null;
CommerceDiscountValue subtotalCommerceDiscountValue = null;

String priceDisplayType = commerceCartContentTotalDisplayContext.getCommercePriceDisplayType();

CommerceOrderPrice commerceOrderPrice = commerceCartContentTotalDisplayContext.getCommerceOrderPrice();

if (commerceOrderPrice != null) {
	subtotalCommerceMoney = commerceOrderPrice.getSubtotal();

	subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValue();

	if (subtotalCommerceDiscountValue != null) {
		subtotalDiscountAmountCommerceMoney = subtotalCommerceDiscountValue.getDiscountAmount();
	}

	taxValueCommerceMoney = commerceOrderPrice.getTaxValue();
	totalOrderCommerceMoney = commerceOrderPrice.getTotal();

	totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValue();

	if (totalCommerceDiscountValue != null) {
		totalDiscountAmountCommerceMoney = totalCommerceDiscountValue.getDiscountAmount();
	}

	if (priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {
		subtotalCommerceMoney = commerceOrderPrice.getSubtotalWithTaxAmount();

		subtotalCommerceDiscountValue = commerceOrderPrice.getSubtotalDiscountValueWithTaxAmount();

		if (subtotalCommerceDiscountValue != null) {
			subtotalDiscountAmountCommerceMoney = subtotalCommerceDiscountValue.getDiscountAmount();
		}

		totalOrderCommerceMoney = commerceOrderPrice.getTotalWithTaxAmount();

		totalCommerceDiscountValue = commerceOrderPrice.getTotalDiscountValueWithTaxAmount();

		if (totalCommerceDiscountValue != null) {
			totalDiscountAmountCommerceMoney = totalCommerceDiscountValue.getDiscountAmount();
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
		<c:if test="<%= subtotalCommerceMoney != null %>">
			<div class="row">
				<c:if test="<%= subtotalCommerceDiscountValue != null %>">
					<div class="col-auto">
						<h4><liferay-ui:message key="subtotal-discount" /></h4>
					</div>

					<div class="col-auto">
						<span>(<%= HtmlUtil.escape(subtotalDiscountAmountCommerceMoney.format(locale)) %>)</span>
					</div>
				</c:if>

				<div class="col-auto">
					<h3 class="h4"><liferay-ui:message key="subtotal" /></h3>
				</div>

				<div class="col text-right">
					<h3 class="h4"><%= HtmlUtil.escape(subtotalCommerceMoney.format(locale)) %></h3>
				</div>
			</div>
		</c:if>

		<c:if test="<%= (taxValueCommerceMoney != null) && priceDisplayType.equals(CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE) %>">
			<div class="row">
				<div class="col-auto">
					<h3 class="h4"><liferay-ui:message key="tax" /></h3>
				</div>

				<div class="col text-right">
					<h3 class="h4"><%= HtmlUtil.escape(taxValueCommerceMoney.format(locale)) %></h3>
				</div>
			</div>
		</c:if>

		<c:if test="<%= totalOrderCommerceMoney != null %>">
			<div class="row">
				<c:if test="<%= totalCommerceDiscountValue != null %>">
					<div class="col-auto">
						<h4><liferay-ui:message key="total-discount" /></h4>
					</div>

					<div class="col-auto">
						<span>(<%= HtmlUtil.escape(totalDiscountAmountCommerceMoney.format(locale)) %>)</span>
					</div>
				</c:if>

				<div class="col-auto">
					<h3 class="h4"><liferay-ui:message key="total" /></h3>
				</div>

				<div class="col text-right">
					<h3 class="h4"><%= HtmlUtil.escape(totalOrderCommerceMoney.format(locale)) %></h3>
				</div>
			</div>
		</c:if>
	</div>

	<aui:button-row>

		<%
		PortletURL checkoutPortletURL = commerceCartContentTotalDisplayContext.getCheckoutPortletURL();
		%>

		<aui:button cssClass="btn-lg" disabled="<%= !commerceCartContentTotalDisplayContext.isValidCommerceOrder() %>" href="<%= checkoutPortletURL.toString() %>" value="checkout" />

		<c:if test="<%= commerceCartContentTotalDisplayContext.isRequestQuoteEnabled() && commerceCartContentTotalDisplayContext.isValidCommerceOrder() %>">
			<aui:button cssClass="btn-lg request-quote" id="requestQuote" value='<%= LanguageUtil.get(request, "request-a-quote") %>' />
		</c:if>
	</aui:button-row>

	<%@ include file="/cart_total/request_quote.jspf" %>

	<liferay-frontend:component
		module="js/cart_total/view"
	/>
</liferay-ddm:template-renderer>