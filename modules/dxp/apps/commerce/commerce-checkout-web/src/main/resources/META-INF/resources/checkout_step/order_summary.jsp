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
OrderSummaryCheckoutStepDisplayContext orderSummaryCheckoutStepDisplayContext = (OrderSummaryCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

CommerceCart commerceCart = orderSummaryCheckoutStepDisplayContext.getCommerceCart();

Map<Long, List<CommerceCartValidatorResult>> commerceCartValidatorResultMap = orderSummaryCheckoutStepDisplayContext.getCommerceCartValidatorResults();
%>

<liferay-ui:error exception="<%= CommerceCartBillingAddressException.class %>" message="please-select-a-valid-billing-address" />
<liferay-ui:error exception="<%= CommerceCartPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />
<liferay-ui:error exception="<%= CommerceCartShippingAddressException.class %>" message="please-select-a-valid-shipping-address" />
<liferay-ui:error exception="<%= CommerceCartShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />

<div class="address-container row">
	<div class="col-md-4">
		<div class="card card-commerce">
			<div class="card-body">
				<h3><liferay-ui:message key="billing-address" /></h3>

				<%
				request.setAttribute("address.jsp-commerceAddress", commerceCart.getBillingAddress());
				%>

				<liferay-util:include page="/address.jsp" servletContext="<%= application %>" />
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="card card-commerce">
			<div class="card-body">
				<h3><liferay-ui:message key="shipping-address" /></h3>

				<%
				request.setAttribute("address.jsp-commerceAddress", commerceCart.getShippingAddress());
				%>

				<liferay-util:include page="/address.jsp" servletContext="<%= application %>" />
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="card card-commerce">
			<div class="card-body">
				<h3>Order Total</h3>

				<h3>
					<%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getCommerceCartSubtotal()) %>
				</h3>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<div class="commerce-cart-items-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceCartItems"
			>
				<liferay-ui:search-container-results
					results="<%= commerceCart.getCommerceCartItems() %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceCartItem"
					cssClass="entry-display-style"
					keyProperty="CommerceCartItemId"
					modelVar="commerceCartItem"
				>

					<%
					CPDefinition cpDefinition = commerceCartItem.getCPDefinition();

					String thumbnailSrc = orderSummaryCheckoutStepDisplayContext.getCommerceCartItemThumb(commerceCartItem, themeDisplay);

					List<KeyValuePair> keyValuePairs = orderSummaryCheckoutStepDisplayContext.getKeyValuePairs(commerceCartItem.getJson(), locale);

					StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

					for (KeyValuePair keyValuePair : keyValuePairs) {
						stringJoiner.add(keyValuePair.getValue());
					}
					%>

					<liferay-ui:search-container-column-image
						name="product"
						src="<%= thumbnailSrc %>"
					/>

					<liferay-ui:search-container-column-text
						name="description"
					>
						<%= HtmlUtil.escape(cpDefinition.getTitle(themeDisplay.getLanguageId())) %>

						<h6 class="text-default">
							<%= HtmlUtil.escape(stringJoiner.toString()) %>
						</h6>

						<c:if test="<%= !commerceCartValidatorResultMap.isEmpty() %>">

							<%
							List<CommerceCartValidatorResult> commerceCartValidatorResults = commerceCartValidatorResultMap.get(commerceCartItem.getCommerceCartItemId());

							for (CommerceCartValidatorResult commerceCartValidatorResult : commerceCartValidatorResults) {
							%>

								<div class="alert-danger commerce-alert-danger">
									<c:choose>
										<c:when test="<%= commerceCartValidatorResult.hasArgument() %>">
											<liferay-ui:message arguments="<%= commerceCartValidatorResult.getArgument() %>" key="<%= commerceCartValidatorResult.getMessage() %>" />
										</c:when>
										<c:otherwise>
											<liferay-ui:message key="<%= commerceCartValidatorResult.getMessage() %>" />
										</c:otherwise>
									</c:choose>
								</div>

							<%
							}
							%>

						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="quantity"
						value="<%= String.valueOf(commerceCartItem.getQuantity()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="price"
						value="<%= orderSummaryCheckoutStepDisplayContext.getFormattedPrice(commerceCartItem) %>"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" paginate="<%= false %>" />
			</liferay-ui:search-container>
		</div>
	</div>
</div>