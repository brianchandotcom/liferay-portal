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

CommerceOrder commerceOrder = orderSummaryCheckoutStepDisplayContext.getCommerceOrder();

Map<Long, List<CommerceOrderValidatorResult>> commerceOrderValidatorResultMap = orderSummaryCheckoutStepDisplayContext.getCommerceOrderValidatorResults();
%>

<liferay-ui:error exception="<%= CommerceOrderBillingAddressException.class %>" message="please-select-a-valid-billing-address" />
<liferay-ui:error exception="<%= CommerceOrderPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />
<liferay-ui:error exception="<%= CommerceOrderShippingAddressException.class %>" message="please-select-a-valid-shipping-address" />
<liferay-ui:error exception="<%= CommerceOrderShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />

<div class="address-container row">

	<%
	CommerceAddress billingAddress = commerceOrder.getBillingAddress();
	%>

	<c:if test="<%= billingAddress != null %>">
		<div class="col-md-4">
			<div class="card card-commerce">
				<div class="card-body">
					<h3><liferay-ui:message key="billing-address" /></h3>

					<%
					request.setAttribute("address.jsp-commerceAddress", billingAddress);
					%>

					<liferay-util:include page="/address.jsp" servletContext="<%= application %>" />
				</div>
			</div>
		</div>
	</c:if>

	<%
	CommerceAddress shippingAddress = commerceOrder.getShippingAddress();
	%>

	<c:if test="<%= shippingAddress != null %>">
		<div class="col-md-4">
			<div class="card card-commerce">
				<div class="card-body">
					<h3><liferay-ui:message key="shipping-address" /></h3>

					<%
					request.setAttribute("address.jsp-commerceAddress", shippingAddress);
					%>

					<liferay-util:include page="/address.jsp" servletContext="<%= application %>" />
				</div>
			</div>
		</div>
	</c:if>

	<div class="col-md-4">
		<div class="card card-commerce">
			<div class="card-body">
				<h3>Order Total</h3>

				<h3>
					<%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getCommerceOrderSubtotal()) %>
				</h3>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<div class="commerce-cart-items-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceOrderItems"
			>
				<liferay-ui:search-container-results
					results="<%= commerceOrder.getCommerceOrderItems() %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceOrderItem"
					cssClass="entry-display-style"
					keyProperty="CommerceOrderItemId"
					modelVar="commerceOrderItem"
				>

					<%
					CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

					String thumbnailSrc = orderSummaryCheckoutStepDisplayContext.getCommerceOrderItemThumbnailSrc(commerceOrderItem, themeDisplay);

					List<KeyValuePair> keyValuePairs = orderSummaryCheckoutStepDisplayContext.getKeyValuePairs(commerceOrderItem.getJson(), locale);

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

						<c:if test="<%= !commerceOrderValidatorResultMap.isEmpty() %>">

							<%
							List<CommerceOrderValidatorResult> commerceOrderValidatorResults = commerceOrderValidatorResultMap.get(commerceOrderItem.getCommerceOrderItemId());

							for (CommerceOrderValidatorResult commerceOrderValidatorResult : commerceOrderValidatorResults) {
							%>

								<div class="alert-danger commerce-alert-danger">
									<c:choose>
										<c:when test="<%= commerceOrderValidatorResult.hasArgument() %>">
											<liferay-ui:message arguments="<%= commerceOrderValidatorResult.getArgument() %>" key="<%= commerceOrderValidatorResult.getMessage() %>" />
										</c:when>
										<c:otherwise>
											<liferay-ui:message key="<%= commerceOrderValidatorResult.getMessage() %>" />
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
						value="<%= String.valueOf(commerceOrderItem.getQuantity()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="price"
						value="<%= orderSummaryCheckoutStepDisplayContext.getFormattedPrice(commerceOrderItem) %>"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" paginate="<%= false %>" />
			</liferay-ui:search-container>
		</div>
	</div>
</div>