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

String commercePaymentMethodKey = BeanParamUtil.getString(commerceOrder, request, "commercePaymentMethodKey");
%>

<div id="commercePaymentMethodsContainer">
	<liferay-ui:error exception="<%= CommerceOrderPaymentMethodException.class %>" message="please-select-a-valid-payment-method" />

	<c:choose>
		<c:when test="<%= commercePaymentMethods.isEmpty() %>">
			<aui:row>
				<aui:col width="<%= 100 %>">
					<aui:alert type="info">
						<liferay-ui:message key="there-are-no-available-payment-methods" />
					</aui:alert>
				</aui:col>
			</aui:row>

			<aui:script use="aui-base">
				var continueButton = A.one('#<portlet:namespace />continue');

				if (continueButton) {
					Liferay.Util.toggleDisabled(continueButton, true);
				}
			</aui:script>
		</c:when>
		<c:otherwise>
			<ul class="list-group">

				<%
				for (CommercePaymentMethod commercePaymentMethod : commercePaymentMethods) {
				%>

					<li class="commerce-payment-types list-group-item list-group-item-flex">
						<div class="autofit-col autofit-col-expand">
							<aui:input checked="<%= commercePaymentMethodKey.equals(commercePaymentMethod.getKey()) %>" label="<%= commercePaymentMethod.getName(locale) %>" name="commercePaymentMethodKey" type="radio" value="<%= commercePaymentMethod.getKey() %>" />
						</div>

						<%
						String thumbnailSrc = paymentMethodCheckoutStepDisplayContext.getImageURL(commerceOrder.getGroupId(), commercePaymentMethod.getKey(), themeDisplay);
						%>

						<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
							<div class="autofit-col">
								<img alt="<%= HtmlUtil.escapeAttribute(commercePaymentMethod.getName(locale)) %>" class="payment-icon" src="<%= HtmlUtil.escapeAttribute(thumbnailSrc) %>" style="height: 45px; width: auto;" />
							</div>
						</c:if>
					</li>

				<%
				}
				%>

			</ul>
		</c:otherwise>
	</c:choose>
</div>

<c:if test="<%= commercePaymentMethods.isEmpty() %>">
	<aui:script use="aui-base">
		var value = A.one('#<portlet:namespace />continue');

		if (value) {
			Liferay.Util.toggleDisabled(value, true);
		}
	</aui:script>
</c:if>