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

CommerceAddress billingAddress = orderSummaryCheckoutStepDisplayContext.getBillingAddress();
%>

<div class="address-container row">
	<div class="col-md-4">
		<div class="card card-horizontal">
			<div class="card-row card-row-valign-top">
				<div class="card-col-content card-col-gutters">
					<h3>Billing Adrress</h3>

					<h4><%= billingAddress.getName() %></h4>
					<p><%= billingAddress.getStreet1() %></p>

					<c:if test="<%= Validator.isNotNull(billingAddress.getStreet2()) %>">
						<p><%= billingAddress.getStreet2() %></p>
					</c:if>

					<c:if test="<%= Validator.isNotNull(billingAddress.getStreet3()) %>">
						<p><%= billingAddress.getStreet3() %></p>
					</c:if>

					<p><%= billingAddress.getCity() %></p>

					<%
					CommerceCountry commerceCountry = billingAddress.getCommerceCountry();
					%>

					<c:if test="<%= commerceCountry != null %>">
						<p><%= commerceCountry.getName(locale) %></p>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="card card-horizontal">
			<div class="card-row card-row-valign-top">
				<div class="card-col-content card-col-gutters">
					<h3>Shipping Address</h3>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="card card-horizontal">
			<div class="card-row card-row-valign-top">
				<div class="card-col-content card-col-gutters">
					<h3>Order Total</h3>
					<h3>
						<%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getCommerceCartTotal()) %>
					</h3>
				</div>
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

					List<KeyValuePair> keyValuePairs = orderSummaryCheckoutStepDisplayContext.parseJSONString(commerceCartItem.getJson(), locale);

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