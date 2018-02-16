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
ShippingMethodCheckoutStepDisplayContext shippingMethodCheckoutStepDisplayContext = (ShippingMethodCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = shippingMethodCheckoutStepDisplayContext.getCommerceOrder();

String commerceShippingOptionKey = ParamUtil.getString(request, "commerceShippingOptionKey");

if (Validator.isNull(commerceShippingOptionKey)) {
	commerceShippingOptionKey = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionKey(commerceOrder.getCommerceShippingMethodId(), commerceOrder.getShippingOptionName());
}

boolean hasShippingOption = false;
%>

<h3 class="p-4"><liferay-ui:message key="shipping-method" /></h3>

<aui:fieldset>
	<liferay-ui:error exception="<%= CommerceOrderShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />

	<div class="row text-center" id="commerceShippingMethodsContainer">

		<%
		for (CommerceShippingMethod commerceShippingMethod : shippingMethodCheckoutStepDisplayContext.getCommerceShippingMethods()) {
			List<CommerceShippingOption> commerceShippingOptions = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptions(commerceShippingMethod);
		%>

			<c:if test="<%= !commerceShippingOptions.isEmpty() %>">

				<%
				hasShippingOption = true;
				%>

				<div class="col-md-3 mx-auto">
					<div class="radio radio-card radio-middle-left">
						<label>

							<%
							for (CommerceShippingOption commerceShippingOption : commerceShippingOptions) {
								String curCommerceShippingOptionKey = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionKey(commerceShippingMethod.getCommerceShippingMethodId(), commerceShippingOption.getName());
								String label = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionLabel(commerceShippingOption);
							%>

								<aui:input
									checked="<%= curCommerceShippingOptionKey.equals(commerceShippingOptionKey) %>"
									label="<%= label %>"
									name="commerceShippingOptionKey"
									type="radio"
									value="<%= curCommerceShippingOptionKey %>"
								/>

							<%
							}
							%>

							<div class="card card-commerce">
								<div class="card-body">

									<%
									String thumbnailSrc = commerceShippingMethod.getImageURL(themeDisplay);
									%>

									<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
										<img class="w-25" src="<%= thumbnailSrc %>" />
									</c:if>
								</div>
							</div>
						</label>
					</div>
				</div>
			</c:if>

		<%
		}
		%>

		<c:if test="<%= !hasShippingOption %>">
			<div class="alert alert-info mx-auto">
				<liferay-ui:message key="there-are-no-available-shipping-methods" />
			</div>
		</c:if>
	</div>
</aui:fieldset>

<c:if test="<%= !hasShippingOption %>">
	<aui:script>
		var A = AUI();

		var nextCheckoutStepButton = A.one('#<portlet:namespace />nextCheckoutStepButton');

		nextCheckoutStepButton.attr('disabled', true);
	</aui:script>
</c:if>