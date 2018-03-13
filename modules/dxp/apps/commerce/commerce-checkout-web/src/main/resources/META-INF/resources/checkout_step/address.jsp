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
BaseAddressCheckoutStepDisplayContext baseAddressCheckoutStepDisplayContext = (BaseAddressCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

List<CommerceAddress> commerceAddresses = baseAddressCheckoutStepDisplayContext.getCommerceAddresses();
long defaultCommerceAddressId = baseAddressCheckoutStepDisplayContext.getDefaultCommerceAddressId();

long commerceAddressId = BeanParamUtil.getLong(baseAddressCheckoutStepDisplayContext.getCommerceOrder(), request, baseAddressCheckoutStepDisplayContext.getParamName());

if (commerceAddressId == 0) {
	commerceAddressId = defaultCommerceAddressId;
}

long commerceCountryId = ParamUtil.getLong(request, "commerceCountryId");
long commerceRegionId = ParamUtil.getLong(request, "commerceRegionId");
%>

<div class="form-group-autofit">
	<aui:select label="choose-shipping-address" name="shippingAddress" onChange='<%= renderResponse.getNamespace() + "addNewAddress();" %>' wrapperCssClass="form-group-item">

		<aui:option label="add-new-address" value="-1" />

		<%
		for (CommerceAddress commerceAddress : commerceAddresses) {
		%>

			<aui:option label="<%= commerceAddress.getName() %>" selected="<%= commerceAddressId == commerceAddress.getCommerceAddressId() %>" value="<%= commerceAddress.getCommerceAddressId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:input name="newAddress" type="hidden" value='<%= commerceAddresses.isEmpty() ? "1" : "0" %>' />
</div>

<liferay-ui:error exception="<%= CommerceAddressCityException.class %>" message="please-enter-a-valid-city" />
<liferay-ui:error exception="<%= CommerceAddressCountryException.class %>" message="please-enter-a-valid-country" />
<liferay-ui:error exception="<%= CommerceAddressNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= CommerceAddressStreetException.class %>" message="please-enter-a-valid-street" />
<liferay-ui:error exception="<%= CommerceOrderBillingAddressException.class %>" message="please-enter-a-valid-address" />
<liferay-ui:error exception="<%= CommerceOrderShippingAddressException.class %>" message="please-enter-a-valid-address" />

<aui:model-context model="<%= CommerceAddress.class %>" />

<div class="form-group-autofit">
	<aui:input label="" name="email" placeholder="email" type="text" wrapperCssClass="form-group-item" />

	<aui:input label="" name="phoneNumber" placeholder="phone-number" wrapperCssClass="form-group-item" />
</div>

<div class="form-group-autofit">
	<aui:input label="" name="firstName" placeholder="first-name" type="text" wrapperCssClass="form-group-item" />

	<aui:input label="" name="lastName" placeholder="last-name" type="text" wrapperCssClass="form-group-item" />
</div>

<div class="form-group-autofit">
	<aui:input label="" name="street1" placeholder="shipping-address" wrapperCssClass="form-group-item" />

	<aui:select label="" name="commerceCountryId" placeholder="country" wrapperCssClass="form-group-item" />
</div>

<div class="form-group-autofit add-street-link">
	<aui:a cssClass="form-group-item" href="javascript:;" label="+-add-address-line" onClick='<%= renderResponse.getNamespace() + "addStreetAddress();" %>' />
</div>

<div class="form-group-autofit hide add-street-fields">
	<aui:input label="" name="street2" placeholder="" wrapperCssClass="form-group-item" />

	<aui:input label="" name="street3" placeholder="" wrapperCssClass="form-group-item" />
</div>

<div class="form-group-autofit">
	<aui:input label="" name="zip" placeholder="zip" wrapperCssClass="form-group-item" />

	<aui:input label="" name="city" placeholder="city" wrapperCssClass="form-group-item" />

	<aui:select label="" name="commerceRegionId" placeholder="region" wrapperCssClass="form-group-item" />
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />addNewAddress',
		function(val) {
			var A = AUI();

			if (val == -1) {
				var newAddress = A.one('#<portlet:namespace />newAddress');

				if (newAddress) {
					var val = state ? 1 : 0;

					newAddress.val(val);
				}
			}
		},
		['aui-base']
	);


	Liferay.provide(
		window,
		'<portlet:namespace />addStreetAddress',
		function() {
			var A = AUI();

			var addStreetFields = A.one('.add-street-fields');

			if (addStreetFields) {
				addStreetFields.show();
			}

			var addStreetLink = A.one('.add-street-link');

			if (addStreetLink) {
				addStreetLink.hide();
			}
		},
		['aui-base']
	);
</aui:script>

<aui:script use="aui-base,liferay-dynamic-select">
	Liferay.on(
		'form:registered',
		function(event) {
			if (event.formName === '<portlet:namespace />fm') {
				A.Do.before(
					function() {
						var newAddress = A.one('#<portlet:namespace />newAddress');

						if (!(newAddress.val() == '1')) {
							return new A.Do.Halt('', false);
						}
					},
					event.form.formValidator,
					'hasErrors'
				);
			}
		}
	);

	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace />commerceCountryId',
				selectData: function(callback) {
					Liferay.Service(
						'/commerce.commercecountry/<%= baseAddressCheckoutStepDisplayContext.getCommerceCountrySelectionMethodName() %>',
						{
							groupId: <%= scopeGroupId %>,
							<%= baseAddressCheckoutStepDisplayContext.getCommerceCountrySelectionColumnName() %>: true,
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
</aui:script>