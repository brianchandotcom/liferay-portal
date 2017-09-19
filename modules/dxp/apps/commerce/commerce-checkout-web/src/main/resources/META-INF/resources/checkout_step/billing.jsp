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
CheckoutStepBillingDisplayContext checkoutStepBillingDisplayContext = (CheckoutStepBillingDisplayContext)request.getAttribute("CommerceCheckoutStepDisplayContext");

List<CommerceAddress> commerceAddresses = checkoutStepBillingDisplayContext.getCommerceAddresses();

long defaultBillingAddressId = 0;

for (CommerceAddress commerceAddress : commerceAddresses) {
	if (commerceAddress.isDefaultBilling()) {
		defaultBillingAddressId = commerceAddress.getCommerceAddressId();

		break;
	}
}

if ((defaultBillingAddressId == 0) && !commerceAddresses.isEmpty()) {
	CommerceAddress commerceAddress = commerceAddresses.get(0);

	defaultBillingAddressId = commerceAddress.getCommerceAddressId();
}

long billingAddressId = BeanParamUtil.getLong(commerceCart, request, "billingAddressId", defaultBillingAddressId);

long commerceCountryId = ParamUtil.getLong(request, "commerceCountryId");
long commerceRegionId = ParamUtil.getLong(request, "commerceRegionId");
%>

<h3><liferay-ui:message key="billing-address" /></h3>

<aui:fieldset>
	<div id="<portlet:namespace />billingAddressChoice">

		<%
		for (CommerceAddress commerceAddress : commerceAddresses) {
		%>

			<aui:input checked="<%= billingAddressId == commerceAddress.getCommerceAddressId() %>" label="<%= commerceAddress.getName() %>" name="billingAddressId" type="radio" value="<%= commerceAddress.getCommerceAddressId() %>" />

		<%
		}
		%>

		<c:if test="<%= !commerceAddresses.isEmpty() %>">
			<aui:input checked="<%= billingAddressId <= 0 %>" id="newAddress" label="add-new-address" name="billingAddressId" type="radio" value="0" />
		</c:if>
	</div>

	<aui:fieldset cssClass='<%= (billingAddressId > 0) ? "hide" : StringPool.BLANK %>' id='<%= renderResponse.getNamespace() + "newAddressContainer" %>'>
		<liferay-ui:error exception="<%= CommerceAddressCityException.class %>" message="please-enter-a-valid-city" />
		<liferay-ui:error exception="<%= CommerceAddressCountryException.class %>" message="please-enter-a-valid-country" />
		<liferay-ui:error exception="<%= CommerceAddressNameException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= CommerceAddressStreetException.class %>" message="please-enter-a-valid-street" />
		<liferay-ui:error exception="<%= CommerceCartBillingAddressException.class %>" message="please-enter-a-valid-address" />

		<div class="alert alert-info">
			<liferay-ui:message key="please-enter-your-personal-information-and-address" />
		</div>

		<aui:model-context model="<%= CommerceAddress.class %>" />

		<aui:col width="<%= 50 %>">
			<aui:input label="full-name" name="name" />

			<aui:input name="street1" />

			<aui:input name="street2" />

			<aui:input name="street3" />

			<aui:select label="country" name="commerceCountryId" />

			<aui:select label="region" name="commerceRegionId" />
		</aui:col>

		<aui:col width="<%= 50 %>">
			<aui:input label="postal-code" name="zip" />

			<aui:input name="city" />

			<aui:input name="phoneNumber" />
		</aui:col>
	</aui:fieldset>
</aui:fieldset>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace />commerceCountryId',
				selectData: function(callback) {
					Liferay.Service(
						'/commerce.commercecountry/get-billing-commerce-countries',
						{
							groupId: <%= scopeGroupId %>,
							billingAllowed: true,
							active: true
						},
						callback
					);
				},
				selectDesc: 'nameCurrentValue',
				selectId: 'commerceCountryId',
				selectSort: '<%= true %>',
				selectVal: '<%= commerceCountryId %>'
			},
			{
				select: '<portlet:namespace />commerceRegionId',
				selectData: function(callback, selectKey) {
					Liferay.Service(
						'/commerce.commerceregion/get-commerce-regions',
						{
							commerceCountryId: Number(selectKey),
							active: true
						},
						callback
					);
				},
				selectDesc: 'name',
				selectId: 'commerceRegionId',
				selectVal: '<%= commerceRegionId %>'
			}
		]
	);

	Liferay.on(
	'form:registered',
	function(event) {
		if (event.formName === '<portlet:namespace />fm') {
			A.Do.before(
				function() {
					if (!document.getElementById('<portlet:namespace />newAddress').checked) {
						return new A.Do.Halt('', false);
					}
				},
				event.form.formValidator,
				'hasErrors'
			);
		}
	});

	A.one('#<portlet:namespace />billingAddressChoice').delegate(
		'change',
		function(event) {
			var input = event.currentTarget;

			if (input.get('checked') && (input.val() == 0)) {
				A.one('#<portlet:namespace />newAddressContainer').show();
			}
			else {
				A.one('#<portlet:namespace />newAddressContainer').hide();
			}
		},
		'input'
	);
</aui:script>