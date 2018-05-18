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
			<aui:row>
				<aui:col widht="100">
					<aui:alert type="info">
						<liferay-ui:message key="there-are-no-available-shipping-methods" />
					</aui:alert>
				</aui:col>
			</aui:row>

			<aui:script use="aui-base">
				var continue = A.one('#<portlet:namespace />continue');

				if (continue) {
					Liferay.Util.toggleDisabled(continue, true);
				}
			</aui:script>
		</c:when>
		<c:otherwise>
			<ul class="list-group">

				<%
				for (CommerceShippingMethod commerceShippingMethod : commerceShippingMethods) {
					List<CommerceShippingOption> commerceShippingOptions = shippingMethodCheckoutStepDisplayContext.getCommerceShippingOptions(commerceShippingMethod);
				%>

					<c:if test="<%= commerceShippingOptions.isEmpty() %>">
						<li class="commerce-shipping-types list-group-item list-group-item-flex">
							<div class="autofit-col autofit-col-expand">
								<div class="alert alert-info">
									<liferay-ui:message arguments="<%= commerceShippingMethod.getName(locale) %>" key="x-is-not-available" translateArguments="<%= false %>" />
								</div>
							</div>
						</li>
					</c:if>

					<%
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
</div>