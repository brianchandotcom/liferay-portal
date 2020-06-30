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
CommerceChannelDisplayContext commerceChannelDisplayContext = (CommerceChannelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceChannel commerceChannel = commerceChannelDisplayContext.getCommerceChannel();
long commerceChannelId = commerceChannelDisplayContext.getCommerceChannelId();
List<CommerceCurrency> commerceCurrencies = commerceChannelDisplayContext.getCommerceCurrencies();

String commerceCurrencyCode = commerceChannel.getCommerceCurrencyCode();

Map<String, String> contextParams = new HashMap<>();

contextParams.put("commerceChannelId", String.valueOf(commerceChannel.getCommerceChannelId()));
%>

<portlet:actionURL name="editCommerceChannel" var="editCommerceChannelActionURL" />

<aui:form action="<%= editCommerceChannelActionURL %>" cssClass="m-0 p-0" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceChannel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />

	<aui:model-context bean="<%= commerceChannel %>" model="<%= CommerceChannel.class %>" />

	<div class="row">
		<div class="col-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:input autoFocus="<%= true %>" name="name" />

				<aui:select label="currency" name="commerceCurrencyCode" required="<%= true %>" title="currency">

					<%
					for (CommerceCurrency commerceCurrency : commerceCurrencies) {
					%>

						<aui:option label="<%= commerceCurrency.getName(locale) %>" selected="<%= (commerceChannel == null) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCurrency.getCode()) %>" value="<%= commerceCurrency.getCode() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="commerce-site-type" name="settings--commerceSiteType--">

					<%
					for (int commerceSiteType : CommerceAccountConstants.SITE_TYPES) {
					%>

						<aui:option label="<%= CommerceAccountConstants.getSiteTypeLabel(commerceSiteType) %>" selected="<%= commerceSiteType == commerceChannelDisplayContext.getCommerceSiteType() %>" value="<%= commerceSiteType %>" />

					<%
					}
					%>

				</aui:select>

			</commerce-ui:panel>
		</div>

		<div class="col-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "orders") %>'
			>

				<%
				List<WorkflowDefinition> workflowDefinitions = commerceChannelDisplayContext.getActiveWorkflowDefinitions();

				long typePK = CommerceOrderConstants.TYPE_PK_APPROVAL;
				String typePrefix = "buyer-order-approval";
				%>

				<%@ include file="/channel/workflow_definition.jspf" %>

				<%
				typePK = CommerceOrderConstants.TYPE_PK_FULFILLMENT;
				typePrefix = "seller-order-acceptance";
				%>

				<%@ include file="/channel/workflow_definition.jspf" %>

				<aui:input checked="<%= commerceChannelDisplayContext.isShowPurchaseOrderNumber() %>" helpMessage="configures-whether-purchase-order-number-is-shown-or-hidden-in-placed-and-pending-order-details" label="purchase-order-number" labelOff="hide" labelOn="show" name="settings--showPurchaseOrderNumber--" type="toggle-switch" />

				<aui:input checked="<%= commerceChannelDisplayContext.isGuestCheckoutEnabled() %>" helpMessage="configures-whether-a-guest-may-checkout-by-providing-an-email-address-or-if-they-must-sign-in" label="guest-checkout" labelOff="disabled" labelOn="enabled" name="settings--guestCheckoutEnabled--" type="toggle-switch" />
			</commerce-ui:panel>
		</div>

		<div class="col-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "prices") %>'
			>
				<aui:select label="shipping-tax-category" name="shippingTaxSettings--taxCategoryId--">
					<aui:option label="no-tax-category" selected="<%= 0 == commerceChannelDisplayContext.getActiveShippingTaxCategory() %>" value="0" />

					<%
					for (CPTaxCategory taxCategory : commerceChannelDisplayContext.getTaxCategories()) {
					%>

						<aui:option label="<%= taxCategory.getName() %>" selected="<%= taxCategory.getCPTaxCategoryId() == commerceChannelDisplayContext.getActiveShippingTaxCategory() %>" value="<%= taxCategory.getCPTaxCategoryId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="price-type" name="priceDisplayType">

					<%
					String priceDisplayType = commerceChannel.getPriceDisplayType();
					%>

					<aui:option label="net-price" selected="<%= priceDisplayType.equals(CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE) %>" value="<%= CommercePricingConstants.TAX_EXCLUDED_FROM_PRICE %>" />
					<aui:option label="gross-price" selected="<%= priceDisplayType.equals(CommercePricingConstants.TAX_INCLUDED_IN_PRICE) %>" value="<%= CommercePricingConstants.TAX_INCLUDED_IN_PRICE %>" />
				</aui:select>

				<aui:select label="discounts-target-price-type" name="discountsTargetNetPrice">
					<aui:option label="net-price" selected="<%= commerceChannel.isDiscountsTargetNetPrice() %>" value="true" />
					<aui:option label="gross-price" selected="<%= commerceChannel.isDiscountsTargetNetPrice() %>" value="false" />
				</aui:select>
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>

<c:if test="<%= (commerceChannel.getSiteGroupId() > 0) && commerceChannelDisplayContext.hasUnsatisfiedCommerceHealthChecks() %>">
	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="p-0"
				title='<%= LanguageUtil.get(request, "health-checks") %>'
			>
				<commerce-ui:dataset-display
					contextParams="<%= contextParams %>"
					dataProviderKey="<%= CommerceChannelHealthCheckClayTable.NAME %>"
					id="<%= CommerceChannelHealthCheckClayTable.NAME %>"
					itemsPerPage="<%= 10 %>"
					namespace="<%= renderResponse.getNamespace() %>"
					pageNumber="<%= 1 %>"
					portletURL="<%= commerceChannelDisplayContext.getPortletURL() %>"
					showManagementBar="<%= false %>"
				/>
			</commerce-ui:panel>
		</div>
	</div>
</c:if>

<div class="row">
	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "payment-methods") %>'
		>
			<commerce-ui:dataset-display
				contextParams="<%= contextParams %>"
				dataProviderKey="<%= CommercePaymentMethodClayTable.NAME %>"
				id="<%= CommercePaymentMethodClayTable.NAME %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= renderResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceChannelDisplayContext.getPortletURL() %>"
				showManagementBar="<%= false %>"
			/>
		</commerce-ui:panel>
	</div>
</div>

<div class="row">
	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "shipping-methods") %>'
		>
			<commerce-ui:dataset-display
				contextParams="<%= contextParams %>"
				dataProviderKey="<%= CommerceShippingMethodClayTable.NAME %>"
				id="<%= CommerceShippingMethodClayTable.NAME %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= renderResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceChannelDisplayContext.getPortletURL() %>"
				showManagementBar="<%= false %>"
			/>
		</commerce-ui:panel>
	</div>
</div>

<div class="row">
	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "tax-calculations") %>'
		>
			<commerce-ui:dataset-display
				contextParams="<%= contextParams %>"
				dataProviderKey="<%= CommerceTaxMethodClayTable.NAME %>"
				id="<%= CommerceTaxMethodClayTable.NAME %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= renderResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceChannelDisplayContext.getPortletURL() %>"
				showManagementBar="<%= false %>"
			/>
		</commerce-ui:panel>
	</div>
</div>