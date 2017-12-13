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

CommerceCart commerceCart = shippingMethodCheckoutStepDisplayContext.getCommerceCart();

String commerceShippingOptionKey = ParamUtil.getString(request, "commerceShippingOptionKey");

if (Validator.isNull(commerceShippingOptionKey)) {
	commerceShippingOptionKey = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionKey(commerceCart.getCommerceShippingMethodId(), commerceCart.getShippingOptionName());
}
%>

<h3 class="p-4"><liferay-ui:message key="shipping-method" /></h3>

<aui:fieldset>
	<liferay-ui:error exception="<%= CommerceCartShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />

	<div class="row text-center" id="commerceShippingMethodsContainer">

		<%
		for (CommerceShippingMethod commerceShippingMethod : shippingMethodCheckoutStepDisplayContext.getCommerceShippingMethods()) {
			List<CommerceShippingOption> commerceShippingOptions = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptions(commerceShippingMethod);

			for (CommerceShippingOption commerceShippingOption : commerceShippingOptions) {
				String curCommerceShippingOptionKey = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionKey(commerceShippingMethod.getCommerceShippingMethodId(), commerceShippingOption.getName());
		%>

				<div class="col-md-3 mx-auto">
					<div class="radio radio-card radio-middle-left">
						<label>
							<aui:input
								checked="<%= curCommerceShippingOptionKey.equals(commerceShippingOptionKey) %>"
								label=""
								name="commerceShippingOptionKey"
								type="radio"
								value="<%= curCommerceShippingOptionKey %>"
							/>

							<div class="card card-commerce">
								<div class="card-body">

									<%
									String thumbnailSrc = commerceShippingMethod.getShippingMethodImageURL(themeDisplay);
									%>

									<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
										<img class="w-25" src="<%= thumbnailSrc %>" />
									</c:if>
								</div>
							</div>
						</label>
					</div>
				</div>

		<%
			}
		}
		%>

	</div>
</aui:fieldset>

<aui:script>
	$('.col-md-3').click(
		function() {
			$(this).find('input[type="radio"]').prop("checked", true);
		}
	);
</aui:script>