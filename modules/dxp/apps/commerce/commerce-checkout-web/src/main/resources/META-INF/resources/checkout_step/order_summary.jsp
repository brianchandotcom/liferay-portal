<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
OrderSummaryCheckoutStepDisplayContext orderSummaryCheckoutStepDisplayContext = (OrderSummaryCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = orderSummaryCheckoutStepDisplayContext.getCommerceOrder();

String commercePaymentMethodName = StringPool.BLANK;
String commerceShippingMethodName = StringPool.BLANK;

CommercePaymentMethod commercePaymentMethod = commerceOrder.getCommercePaymentMethod();
CommerceShippingMethod commerceShippingMethod = commerceOrder.getCommerceShippingMethod();

if (commercePaymentMethod != null) {
	commercePaymentMethodName = commercePaymentMethod.getName(locale);
}

if (commerceShippingMethod != null) {
	commerceShippingMethodName = commerceShippingMethod.getName(locale);
}

Map<Long, List<CommerceOrderValidatorResult>> commerceOrderValidatorResultMap = orderSummaryCheckoutStepDisplayContext.getCommerceOrderValidatorResults();
%>

<div class="commerce-order-summary">
	<liferay-ui:error exception="<%= CommerceOrderBillingAddressException.class %>" message="please-select-a-valid-billing-address" />
	<liferay-ui:error exception="<%= CommerceOrderPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />
	<liferay-ui:error exception="<%= CommerceOrderShippingAddressException.class %>" message="please-select-a-valid-shipping-address" />
	<liferay-ui:error exception="<%= CommerceOrderShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />

	<aui:row>
		<aui:col cssClass="commerce-checkout-summary" width="<%= 70 %>">
			<ul class="commerce-checkout-summary-header">
				<li class="autofit-row">
					<div class="autofit-col autofit-col-expand">
						<h5 class="commerce-title">
							<liferay-ui:message arguments="<%= orderSummaryCheckoutStepDisplayContext.getCommerceOrderItemsQuantity() %>" key="items-x" translateArguments="<%= false %>" />
						</h5>
					</div>
				</li>
			</ul>

			<div class="commerce-checkout-summary-body" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					cssClass="list-group-flush"
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
						%>

						<liferay-ui:search-container-column-image
							cssClass="thumbnail-section"
							src="<%= thumbnailSrc %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="autofit-col-expand"
						>
							<div class="description-section">
								<div class="list-group-text">Brand</div>

								<div class="list-group-title">
									<%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %>
								</div>

								<%
								List<KeyValuePair> keyValuePairs = orderSummaryCheckoutStepDisplayContext.getKeyValuePairs(commerceOrderItem.getJson(), locale);

								StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

								for (KeyValuePair keyValuePair : keyValuePairs) {
									stringJoiner.add(keyValuePair.getValue());
								}
								%>

								<div class="list-group-subtitle">SKU: <%= HtmlUtil.escape(stringJoiner.toString()) %></div>

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
							</div>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<div class="quantity-section">
								<span class="commerce-quantity"><%= commerceOrderItem.getQuantity() %></span><span class="inline-item-after">x</span>
							</div>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text>
							<div class="value-section">
								<span class="commerce-value"><%= orderSummaryCheckoutStepDisplayContext.getFormattedPrice(commerceOrderItem) %></span>
							</div>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
						paginate="<%= false %>"
					/>
				</liferay-ui:search-container>
			</div>

			<ul class="commerce-checkout-summary-footer">
				<li class="autofit-row commerce-delivery">
					<div class="autofit-col autofit-col-expand">
						<div class="commerce-description"><liferay-ui:message key="delivery" /></div>
					</div>

					<div class="autofit-col">
						<div class="commerce-value"><%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getCommerceOrderShippingPrice()) %></div>
					</div>
				</li>
				<li class="autofit-row commerce-tax">
					<div class="autofit-col autofit-col-expand">
						<div class="commerce-description"><liferay-ui:message key="tax" /></div>
					</div>

					<div class="autofit-col">
						<div class="commerce-value">$0.00</div>
					</div>
				</li>
				<li class="autofit-row commerce-total">
					<div class="autofit-col autofit-col-expand">
						<div class="commerce-description"><liferay-ui:message key="total" /></div>
					</div>

					<div class="autofit-col">
						<div class="commerce-value"><%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getCommerceOrderSubtotal()) %></div>
					</div>
				</li>
			</ul>
		</aui:col>

		<aui:col cssClass="commerce-checkout-info" width="<%= 30 %>">

			<%
			CommerceAddress shippingAddress = commerceOrder.getShippingAddress();
			%>

			<c:if test="<%= shippingAddress != null %>">
				<address class="shipping-address">
					<h5>
						<liferay-ui:message key="shipping-address" />
					</h5>

					<%
					request.setAttribute("address.jsp-commerceAddress", shippingAddress);
					%>

					<%= shippingAddress.getName() %> <br />
					<%= shippingAddress.getStreet1() %> <br />

					<c:if test="<%= Validator.isNotNull(shippingAddress.getStreet2()) %>">
						<%= shippingAddress.getStreet2() %> <br />
					</c:if>

					<c:if test="<%= Validator.isNotNull(shippingAddress.getStreet3()) %>">
						<%= shippingAddress.getStreet3() %> <br />
					</c:if>

					<%= shippingAddress.getCity() %> <br />

					<%
					CommerceCountry commerceCountry = shippingAddress.getCommerceCountry();
					%>

					<c:if test="<%= commerceCountry != null %>">
						<%= HtmlUtil.escape(commerceCountry.getName(locale)) %><br />
					</c:if>
				</address>
			</c:if>

			<div class="shipping-method">
				<h5>
					<liferay-ui:message key="method" />
				</h5>

				<img src="" />

				<div class="shipping-description">
					<%= HtmlUtil.escape(commerceShippingMethodName) %>
				</div>

				<div class="shipping-cost">
					<%= HtmlUtil.escape(orderSummaryCheckoutStepDisplayContext.getCommerceOrderShippingPrice()) %>
				</div>
			</div>

			<div class="payment-method">
				<h5>
					<liferay-ui:message key="payment" />
				</h5>

				<img src="" />

				<div class="shipping-description">
					<%= HtmlUtil.escape(commercePaymentMethodName) %>
				</div>
			</div>
		</aui:col>
	</aui:row>
</div>