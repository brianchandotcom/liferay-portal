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
%>

<aui:container fluid="<%= true %>" id="commerceShippingMethodsContainer">
	<liferay-ui:error exception="<%= CommerceOrderShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />

	<c:choose>
		<c:when test="commerceShippingOptions.isEmpty()">
			<aui:row>
				<aui:col widht="100">
					<aui:alert type="info">
						<liferay-ui:message key="there-are-no-available-shipping-methods" />
					</aui:alert>
				</aui:col>
			</aui:row>
		</c:when>
		<c:otherwise>
			<ul class="list-group">

			<%
			for (CommerceShippingMethod commerceShippingMethod : shippingMethodCheckoutStepDisplayContext.getCommerceShippingMethods()) {
				List<CommerceShippingOption> commerceShippingOptions = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptions(commerceShippingMethod);

				for (CommerceShippingOption commerceShippingOption : commerceShippingOptions) {
					String curCommerceShippingOptionKey = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionKey(commerceShippingMethod.getCommerceShippingMethodId(), commerceShippingOption.getName());
					String label = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionLabel(commerceShippingOption);
			%>

					<li class="commerce-shipping-types list-group-item list-group-item-flex">
						<div class="autofit-col autofit-col-expand">
							<aui:input checked="<%= curCommerceShippingOptionKey.equals(commerceShippingOptionKey) %>" label="<%= label %>" name="commerceShippingOptionKey" type="radio" value="<%= curCommerceShippingOptionKey %>" />
						</div>

						<%
						String thumbnailSrc = commerceShippingMethod.getImageURL(themeDisplay);
						%>

						<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
							<div class="autofit-col">
								<img alt="<%= label %>" src="<%= thumbnailSrc %>" />
							</div>
						</c:if>
					</li>

			<%
				}
			}
			%>

			</ul>
		</c:otherwise>
	</c:choose>
</aui:container>

<c:if test="<%= commerceShippingOptions.isEmpty() %>">
	<aui:script use="aui-base">
		var continue = A.one('#<portlet:namespace />continue');

		if (continue) {
			Liferay.Util.toggleDisabled(continue, true);
		}
	</aui:script>
</c:if>