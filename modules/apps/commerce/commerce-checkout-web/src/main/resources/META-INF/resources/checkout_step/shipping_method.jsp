<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ShippingMethodCheckoutStepDisplayContext shippingMethodCheckoutStepDisplayContext = (ShippingMethodCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

List<CommerceShippingMethod> commerceShippingMethods = shippingMethodCheckoutStepDisplayContext.getCommerceShippingMethods();
CommerceOrder commerceOrder = shippingMethodCheckoutStepDisplayContext.getCommerceOrder();

String commerceShippingOptionKey = ParamUtil.getString(request, "commerceShippingOptionKey");

if (Validator.isNull(commerceShippingOptionKey)) {
	commerceShippingOptionKey = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionKey(commerceOrder.getCommerceShippingMethodId(), commerceOrder.getShippingOptionName());
}
%>

<div id="commerceShippingMethodsContainer">
	<liferay-ui:error exception="<%= CommerceOrderShippingMethodException.class %>" message="please-select-a-valid-shipping-method" />

	<c:choose>
		<c:when test="<%= commerceShippingMethods.isEmpty() %>">
			<clay:row>
				<clay:col
					size="12"
				>
					<clay:alert
						message="there-are-no-available-shipping-methods"
					/>
				</clay:col>
			</clay:row>

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
				for (CommerceShippingMethod commerceShippingMethod : commerceShippingMethods) {
					List<CommerceShippingOption> commerceShippingOptions = shippingMethodCheckoutStepDisplayContext.getFilteredCommerceShippingOptions(commerceShippingMethod);
				%>

					<c:if test="<%= commerceShippingOptions.isEmpty() %>">
						<li class="commerce-shipping-types list-group-item list-group-item-flex">
							<div class="autofit-col autofit-col-expand">
								<div class="alert alert-info">
									<liferay-ui:message arguments="<%= HtmlUtil.escape(commerceShippingMethod.getName(locale)) %>" key="x-is-not-available" translateArguments="<%= false %>" />
								</div>
							</div>
						</li>
					</c:if>

					<%
					for (CommerceShippingOption commerceShippingOption : commerceShippingOptions) {
						String commerceShippingOptionName = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionName(commerceShippingOption);
						String curCommerceShippingOptionKey = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptionKey(commerceShippingMethod.getCommerceShippingMethodId(), commerceShippingOption.getKey());
					%>

						<li class="commerce-shipping-types list-group-item list-group-item-flex">
							<div class="autofit-col autofit-col-expand">
								<aui:input checked="<%= curCommerceShippingOptionKey.equals(commerceShippingOptionKey) %>" label="<%= HtmlUtil.escape(commerceShippingOptionName) %>" name="commerceShippingOptionKey" type="radio" value="<%= curCommerceShippingOptionKey %>" />
							</div>

							<%
							String thumbnailSrc = commerceShippingMethod.getImageURL(themeDisplay);
							%>

							<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
								<div class="autofit-col">
									<img alt="<%= HtmlUtil.escapeAttribute(commerceShippingOptionName) %>" src="<%= HtmlUtil.escapeAttribute(thumbnailSrc) %>" />
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
</div>