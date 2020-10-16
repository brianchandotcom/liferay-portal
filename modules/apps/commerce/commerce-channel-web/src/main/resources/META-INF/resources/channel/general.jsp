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

Map<String, String> contextParams = HashMapBuilder.<String, String>put(
	"commerceChannelId", String.valueOf(commerceChannel.getCommerceChannelId())
).build();
%>

<portlet:actionURL name="/commerce_channels/edit_commerce_channel" var="editCommerceChannelActionURL" />

<aui:form action="<%= editCommerceChannelActionURL %>" cssClass="m-0 p-0" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceChannel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />

	<aui:model-context bean="<%= commerceChannel %>" model="<%= CommerceChannel.class %>" />

	<div class="row">
		<div class="col-lg-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:input autoFocus="<%= true %>" name="name" />

				<aui:select label="currency" name="commerceCurrencyCode" required="<%= true %>" title="currency">

					<%
					for (CommerceCurrency commerceCurrency : commerceCurrencies) {
					%>

						<aui:option label="<%= HtmlUtil.escape(commerceCurrency.getName(locale)) %>" selected="<%= (commerceChannel == null) ? commerceCurrency.isPrimary() : commerceCurrencyCode.equals(commerceCurrency.getCode()) %>" value="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" />

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

		<div class="col-lg-4">
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

				<aui:input label="maximum-number-of-open-orders-per-account" name="orderSettings--accountCartMaxAllowed--" type="number" value="<%= commerceChannelDisplayContext.getAccountCartMaxAllowed() %>">
					<aui:validator name="number" />
					<aui:validator name="min">0</aui:validator>
				</aui:input>
			</commerce-ui:panel>
		</div>

		<div class="col-lg-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "prices") %>'
			>
				<label class="control-label" for="shippingTaxSettings--taxCategoryId--"><%= LanguageUtil.get(request, "shipping-tax-category") %></label>

				<div class="mb-4" id="autocomplete-root"></div>

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
		<div class="col">
			<commerce-ui:panel
				bodyClasses="p-0"
				title='<%= LanguageUtil.get(request, "health-checks") %>'
			>
				<clay:data-set-display
					contextParams="<%= contextParams %>"
					dataProviderKey="<%= CommerceChannelHealthCheckClayTable.NAME %>"
					id="<%= CommerceChannelHealthCheckClayTable.NAME %>"
					itemsPerPage="<%= 10 %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
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
			<clay:data-set-display
				contextParams="<%= contextParams %>"
				dataProviderKey="<%= CommercePaymentMethodClayTable.NAME %>"
				id="<%= CommercePaymentMethodClayTable.NAME %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceChannelDisplayContext.getPortletURL() %>"
				showManagementBar="<%= false %>"
			/>
		</commerce-ui:panel>
	</div>

	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "shipping-methods") %>'
		>
			<clay:data-set-display
				contextParams="<%= contextParams %>"
				dataProviderKey="<%= CommerceShippingMethodClayTable.NAME %>"
				id="<%= CommerceShippingMethodClayTable.NAME %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceChannelDisplayContext.getPortletURL() %>"
				showManagementBar="<%= false %>"
			/>
		</commerce-ui:panel>
	</div>

	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "tax-calculations") %>'
		>
			<clay:data-set-display
				contextParams="<%= contextParams %>"
				dataProviderKey="<%= CommerceTaxMethodClayTable.NAME %>"
				id="<%= CommerceTaxMethodClayTable.NAME %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceChannelDisplayContext.getPortletURL() %>"
				showManagementBar="<%= false %>"
			/>
		</commerce-ui:panel>
	</div>
</div>

<%
String shippingTaxCategoryId = StringPool.BLANK;
String shippingTaxCategoryLabel = LanguageUtil.get(request, "no-tax-category");

CPTaxCategory shippingTaxCategory = commerceChannelDisplayContext.getActiveShippingTaxCategory();

if (shippingTaxCategory != null) {
	shippingTaxCategoryId = String.valueOf(shippingTaxCategory.getCPTaxCategoryId());
	shippingTaxCategoryLabel = shippingTaxCategory.getName(locale);
}
%>

<aui:script require="commerce-frontend-js/components/autocomplete/entry as autocomplete, commerce-frontend-js/utilities/eventsDefinitions as events">
	autocomplete.default('autocomplete', 'autocomplete-root', {
		apiUrl: '/o/headless-commerce-admin-channel/v1.0/tax-categories',
		initialLabel: '<%= HtmlUtil.escapeJS(shippingTaxCategoryLabel) %>',
		initialValue: '<%= shippingTaxCategoryId %>',
		inputId: 'shippingTaxCategoryId',
		inputName:
			'<%= liferayPortletResponse.getNamespace() %>shippingTaxSettings--taxCategoryId--',
		itemsKey: 'id',
		itemsLabel: ['name', 'LANG'],
		onValueUpdated: function (value, shippingTaxData) {
			if (value) {
				window.document.querySelector('#shippingTaxCategoryId').value =
					shippingTaxData.id;
			}
			else {
				window.document.querySelector('#shippingTaxCategoryId').value = 0;
			}
		},
	});
</aui:script>