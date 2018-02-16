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
PaymentMethodCheckoutStepDisplayContext paymentMethodCheckoutStepDisplayContext = (PaymentMethodCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

List<CommercePaymentMethod> commercePaymentMethods = paymentMethodCheckoutStepDisplayContext.getCommercePaymentMethods();

CommerceOrder commerceOrder = paymentMethodCheckoutStepDisplayContext.getCommerceOrder();

long commercePaymentMethodId = BeanParamUtil.getLong(commerceOrder, request, "commercePaymentMethodId");
%>

<h3 class="p-4"><liferay-ui:message key="payment-method" /></h3>

<aui:fieldset>
	<liferay-ui:error exception="<%= CommerceOrderPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />

	<div class="row text-center" id="commercePaymentMethodsContainer">
		<c:choose>
			<c:when test="<%= commercePaymentMethods.isEmpty() %>">
				<div class="alert alert-info mx-auto">
					<liferay-ui:message key="there-are-no-available-payment-methods" />
				</div>
			</c:when>
			<c:otherwise>

				<%
				for (CommercePaymentMethod commercePaymentMethod : commercePaymentMethods) {
				%>

					<div class="col-md-3 mx-auto">
						<div class="radio radio-card radio-middle-left">
							<label>
								<aui:input
									checked="<%= commercePaymentMethod.getCommercePaymentMethodId() == commercePaymentMethodId %>"
									label=""
									name="commercePaymentMethodId"
									type="radio"
									value="<%= commercePaymentMethod.getCommercePaymentMethodId() %>"
								/>

								<h4 class="font-weight-bold text-uppercase"><%= commercePaymentMethod.getName(locale) %></h4>
							</label>
						</div>

						<div class="card card-commerce">
							<div class="card-body">

								<%
								String thumbnailSrc = commercePaymentMethod.getImageURL(themeDisplay);
								%>

								<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
									<img class="w-25" src="<%= thumbnailSrc %>" />
								</c:if>
							</div>
						</div>
					</div>

				<%
				}
				%>

			</c:otherwise>
		</c:choose>
	</div>
</aui:fieldset>

<c:if test="<%= commercePaymentMethods.isEmpty() %>">
	<aui:script>
		var A = AUI();

		var nextCheckoutStepButton = A.one('#<portlet:namespace />nextCheckoutStepButton');

		nextCheckoutStepButton.attr('disabled', true);
	</aui:script>
</c:if>

<aui:script>
	$('.col-md-3').click(
		function() {
			$(this).find('input[type="radio"]').prop("checked", true);
		}
	);
</aui:script>