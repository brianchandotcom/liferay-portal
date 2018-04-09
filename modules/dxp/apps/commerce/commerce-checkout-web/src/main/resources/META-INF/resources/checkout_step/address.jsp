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
	<aui:select label="choose-shipping-address" name="shippingAddress" onChange='<%= renderResponse.getNamespace() + "selectAddress();" %>' wrapperCssClass="form-group-item">
		<aui:option label="add-new-address" value="0" />

		<%
		for (CommerceAddress commerceAddress : commerceAddresses) {
		%>

			<aui:option selected="<%= commerceAddressId == commerceAddress.getCommerceAddressId() %>" data-city="<%= HtmlUtil.escapeAttribute(commerceAddress.getCity()) %>" data-country="<%= HtmlUtil.escapeAttribute(String.valueOf(commerceAddress.getCommerceCountryId())) %>" data-name="<%= HtmlUtil.escapeAttribute(commerceAddress.getName()) %>" data-phone-number="<%= HtmlUtil.escapeAttribute(commerceAddress.getPhoneNumber()) %>" data-region="" data-street-1="<%= HtmlUtil.escapeAttribute(commerceAddress.getStreet1()) %>" data-street-2="<%= Validator.isNotNull(commerceAddress.getStreet2()) ? HtmlUtil.escapeAttribute(commerceAddress.getStreet2()) : StringPool.BLANK %>" data-street-3="<%= Validator.isNotNull(commerceAddress.getStreet3()) ? HtmlUtil.escapeAttribute(commerceAddress.getStreet3()) : StringPool.BLANK %>" data-zip="<%= HtmlUtil.escapeAttribute(commerceAddress.getZip()) %>" label="<%= commerceAddress.getName() %>" value="<%= commerceAddress.getCommerceAddressId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:input disabled="<%= commerceAddresses.isEmpty() ? true : false %>" name="<%= baseAddressCheckoutStepDisplayContext.getParamName() %>" type="hidden" value="" />

	<aui:input name="newAddress" type="hidden" value='<%= (commerceAddressId > 0) ? "1" : "0" %>' />
</div>

<liferay-ui:error exception="<%= CommerceAddressCityException.class %>" message="please-enter-a-valid-city" />
<liferay-ui:error exception="<%= CommerceAddressCountryException.class %>" message="please-enter-a-valid-country" />
<liferay-ui:error exception="<%= CommerceAddressNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= CommerceAddressStreetException.class %>" message="please-enter-a-valid-street" />
<liferay-ui:error exception="<%= CommerceOrderBillingAddressException.class %>" message="please-enter-a-valid-address" />
<liferay-ui:error exception="<%= CommerceOrderShippingAddressException.class %>" message="please-enter-a-valid-address" />

<aui:model-context model="<%= CommerceAddress.class %>" bean="<%= baseAddressCheckoutStepDisplayContext.getCommerceAddress(commerceAddressId) %>" />

<div class="address-fields">
	<div class="form-group-autofit">
		<aui:input label="" name="name" placeholder="name" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>" />

		<aui:input label="" name="phoneNumber" placeholder="phone-number" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>"  />
	</div>

	<div class="form-group-autofit">
		<aui:input label="" name="street1" placeholder="shipping-address" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>"  />

		<aui:select label="" name="commerceCountryId" placeholder="country" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>"  />
	</div>

	<div class="form-group-autofit add-street-link">
		<aui:a cssClass="form-group-item" href="javascript:;" label="+-add-address-line" disabled="<%= (commerceAddressId > 0) %>"  onClick='<%= renderResponse.getNamespace() + "addStreetAddress();" %>' />
	</div>

	<div class="add-street-fields form-group-autofit hide">
		<aui:input label="" name="street2" placeholder="shipping-address-2" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>"  />

		<aui:input label="" name="street3" placeholder="shipping-address-3" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>"  />
	</div>

	<div class="form-group-autofit">
		<aui:input label="" name="zip" placeholder="zip" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>" />

		<aui:input label="" name="city" placeholder="city" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>" />

		<aui:select label="" name="commerceRegionId" placeholder="region" wrapperCssClass="form-group-item" disabled="<%= (commerceAddressId > 0) %>"  />
	</div>
</div>

<aui:script>
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

	Liferay.provide(
		window,
		'<portlet:namespace />clearAddressFields',
		function() {
			var A = AUI();

			var inputs = A.all('.address-fields input');

			inputs.val('');

			var selects = A.all('.address-fields select');

			selects.set('selectedIndex', 0);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />selectAddress',
		function() {
			var A = AUI();

			var newAddress = A.one('#<portlet:namespace />newAddress');
			var shippingAddress = A.one('#<portlet:namespace />shippingAddress');
			var shippingAddressParamName = A.one('#<%= renderResponse.getNamespace() + baseAddressCheckoutStepDisplayContext.getParamName() %>');

			var isNewAddress = 0;

			if (newAddress && shippingAddress && shippingAddressParamName) {
				var shippingAddressVal = shippingAddress.val();

				var disableShippingAddressParamName = false;

				if (shippingAddressVal == '0') {
					isNewAddress = 1;

					disableShippingAddressParamName = true;

					<portlet:namespace />clearAddressFields();

					<portlet:namespace />toggleAddressFields(false);
				}
				else {
					<portlet:namespace />updateAddressFields(shippingAddress.get('selectedIndex'));
				}

				shippingAddressParamName.val(shippingAddressVal);

				newAddress.val(isNewAddress);

				Liferay.Util.toggleDisabled(shippingAddressParamName, disableShippingAddressParamName);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />toggleAddressFields',
		function(state) {
			var A = AUI();

			var inputs = A.all('.address-fields input');

			Liferay.Util.toggleDisabled(inputs, state);

			var selects = A.all('.address-fields select');

			Liferay.Util.toggleDisabled(selects, state);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateAddressFields',
		function(selectedVal) {
			var A = AUI();

			if (selectedVal && selectedVal != '0') {
				var shippingAddress = A.one('#<portlet:namespace />shippingAddress');

				if (shippingAddress) {
					var options = shippingAddress.get('options');

					var selectedOption = options.item(selectedVal);

					var city = A.one('#<portlet:namespace />city');
					var commerceCountryId = A.one('#<portlet:namespace />commerceCountryId');
					var commerceRegionId = A.one('#<portlet:namespace />commerceRegionId');
					var name = A.one('#<portlet:namespace />name');
					var phoneNumber = A.one('#<portlet:namespace />phoneNumber');
					var street1 = A.one('#<portlet:namespace />street1');
					var street2 = A.one('#<portlet:namespace />street2');
					var street3 = A.one('#<portlet:namespace />street3');
					var zip = A.one('#<portlet:namespace />zip');

					if (city && commerceCountryId && commerceRegionId && name && phoneNumber && street1 && street2 && street3 && zip) {
						var cityVal = selectedOption.getData('city');
						var countryVal = selectedOption.getData('country');
						var nameVal = selectedOption.getData('name');
						var phoneNumberVal = selectedOption.getData('phone-number');
						var regionVal = selectedOption.getData('region');
						var street1Val = selectedOption.getData('street-1');
						var street2Val = selectedOption.getData('street-2');
						var street3Val = selectedOption.getData('street-3');
						var zipVal = selectedOption.getData('zip');

						city.val(cityVal);
						commerceCountryId.val(countryVal);
						commerceRegionId.val(regionVal);
						name.val(nameVal);
						phoneNumber.val(phoneNumberVal);
						street1.val(street1Val);
						street2.val(street2Val);
						street3.val(street3Val);
						zip.val(zipVal);
					}

					<portlet:namespace />addStreetAddress();

					<portlet:namespace />toggleAddressFields(true);
				}
			}
		},
		['aui-base']
	);
</aui:script>

<aui:script use="liferay-dynamic-select">
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