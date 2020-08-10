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
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

BaseAddressCheckoutStepDisplayContext baseAddressCheckoutStepDisplayContext = (BaseAddressCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

List<CommerceAddress> commerceAddresses = baseAddressCheckoutStepDisplayContext.getCommerceAddresses();
long defaultCommerceAddressId = baseAddressCheckoutStepDisplayContext.getDefaultCommerceAddressId();

String paramName = baseAddressCheckoutStepDisplayContext.getParamName();

long commerceAddressId = BeanParamUtil.getLong(baseAddressCheckoutStepDisplayContext.getCommerceOrder(), request, paramName);

if (commerceAddressId == 0) {
	commerceAddressId = defaultCommerceAddressId;
}

String selectLabel = "choose-" + baseAddressCheckoutStepDisplayContext.getTitle();

CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

if (commerceOrder.isGuestOrder()) {
	commerceAddressId = 0;
}

CommerceAddress currentCommerceAddress = baseAddressCheckoutStepDisplayContext.getCommerceAddress(commerceAddressId);

long commerceCountryId = BeanParamUtil.getLong(currentCommerceAddress, request, "commerceCountryId", 0);
long commerceRegionId = BeanParamUtil.getLong(currentCommerceAddress, request, "commerceRegionId", 0);
%>

<div class="form-group-autofit">
	<c:if test="<%= !commerceOrder.isGuestOrder() %>">
		<aui:select label="<%= selectLabel %>" name="commerceAddress" onChange='<%= renderResponse.getNamespace() + "selectAddress();" %>' wrapperCssClass="commerce-form-group-item-row form-group-item">
			<aui:option label="add-new-address" value="0" />

			<%
			boolean addressWasFound = false;

			for (CommerceAddress commerceAddress : commerceAddresses) {
				boolean selectedAddress = commerceAddressId == commerceAddress.getCommerceAddressId();

				if (selectedAddress) {
					addressWasFound = true;
				}
			%>

				<aui:option data-city="<%= HtmlUtil.escapeAttribute(commerceAddress.getCity()) %>" data-country="<%= HtmlUtil.escapeAttribute(String.valueOf(commerceAddress.getCommerceCountryId())) %>" data-name="<%= HtmlUtil.escapeAttribute(commerceAddress.getName()) %>" data-phone-number="<%= HtmlUtil.escapeAttribute(commerceAddress.getPhoneNumber()) %>" data-region="<%= HtmlUtil.escapeAttribute(String.valueOf(commerceAddress.getCommerceRegionId())) %>" data-street-1="<%= HtmlUtil.escapeAttribute(commerceAddress.getStreet1()) %>" data-street-2="<%= Validator.isNotNull(commerceAddress.getStreet2()) ? HtmlUtil.escapeAttribute(commerceAddress.getStreet2()) : StringPool.BLANK %>" data-street-3="<%= Validator.isNotNull(commerceAddress.getStreet3()) ? HtmlUtil.escapeAttribute(commerceAddress.getStreet3()) : StringPool.BLANK %>" data-zip="<%= HtmlUtil.escapeAttribute(commerceAddress.getZip()) %>" label="<%= commerceAddress.getName() %>" selected="<%= selectedAddress %>" value="<%= commerceAddress.getCommerceAddressId() %>" />

			<%
			}
			%>

			<c:if test="<%= (currentCommerceAddress != null) && !addressWasFound %>">
				<aui:option label="<%= currentCommerceAddress.getName() %>" selected="<%= true %>" value="<%= currentCommerceAddress.getCommerceAddressId() %>" />
			</c:if>
		</aui:select>
	</c:if>

	<aui:input disabled="<%= commerceAddresses.isEmpty() ? true : false %>" name="<%= paramName %>" type="hidden" value="<%= commerceAddressId %>" />

	<aui:input name="newAddress" type="hidden" value='<%= (commerceAddressId > 0) ? "0" : "1" %>' />
</div>

<liferay-ui:error exception="<%= CommerceAddressCityException.class %>" message="please-enter-a-valid-city" />
<liferay-ui:error exception="<%= CommerceAddressCountryException.class %>" message="please-enter-a-valid-country" />
<liferay-ui:error exception="<%= CommerceAddressNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= CommerceAddressStreetException.class %>" message="please-enter-a-valid-street" />
<liferay-ui:error exception="<%= CommerceAddressZipException.class %>" message="please-enter-a-valid-zip" />
<liferay-ui:error exception="<%= CommerceOrderBillingAddressException.class %>" message="please-enter-a-valid-address" />
<liferay-ui:error exception="<%= CommerceOrderShippingAddressException.class %>" message="please-enter-a-valid-address" />

<aui:model-context bean="<%= baseAddressCheckoutStepDisplayContext.getCommerceAddress(commerceAddressId) %>" model="<%= CommerceAddress.class %>" />

<div class="address-fields">
	<div class="form-group-autofit">
		<aui:input disabled="<%= commerceAddressId > 0 %>" label="" name="name" placeholder="name" wrapperCssClass="form-group-item" />

		<aui:input disabled="<%= commerceAddressId > 0 %>" label="" name="phoneNumber" placeholder="phone-number" wrapperCssClass="form-group-item" />
	</div>

	<div class="form-group-autofit">
		<aui:input disabled="<%= commerceAddressId > 0 %>" label="" name="street1" placeholder="address" wrapperCssClass="form-group-item" />

		<aui:select disabled="<%= commerceAddressId > 0 %>" label="" name="commerceCountryId" placeholder="country" title="country" wrapperCssClass="form-group-item">
			<aui:validator errorMessage='<%= LanguageUtil.get(request, "please-enter-a-valid-country") %>' name="min">1</aui:validator>
		</aui:select>
	</div>

	<div class="add-street-link form-group-autofit">
		<aui:a disabled="<%= commerceAddressId > 0 %>" href="javascript:;" label="+-add-address-line" onClick='<%= renderResponse.getNamespace() + "addStreetAddress();" %>' />
	</div>

	<div class="add-street-fields form-group-autofit hide">
		<aui:input disabled="<%= commerceAddressId > 0 %>" label="" name="street2" placeholder="address-2" wrapperCssClass="form-group-item" />

		<aui:input disabled="<%= commerceAddressId > 0 %>" label="" name="street3" placeholder="address-3" wrapperCssClass="form-group-item" />
	</div>

	<div class="form-group-autofit">
		<aui:input disabled="<%= commerceAddressId > 0 %>" label="" name="zip" placeholder="zip" wrapperCssClass="form-group-item" />

		<aui:input disabled="<%= commerceAddressId > 0 %>" label="" name="city" placeholder="city" wrapperCssClass="form-group-item" />

		<aui:select disabled="<%= commerceAddressId > 0 %>" label="" name="commerceRegionId" placeholder="region" title="region" wrapperCssClass="form-group-item" />
	</div>

	<div class="form-group-autofit">
		<c:if test="<%= commerceOrder.isGuestOrder() %>">
			<aui:input name="email" type="text" wrapperCssClass="form-group-item">
				<aui:validator name="email" />
				<aui:validator name="required" />
			</aui:input>
		</c:if>
	</div>
</div>

<c:if test="<%= Objects.equals(CommerceCheckoutWebKeys.SHIPPING_ADDRESS_PARAM_NAME, paramName) %>">
	<div class="shipping-as-billing">
		<aui:input checked="<%= baseAddressCheckoutStepDisplayContext.isShippingUsedAsBilling() || (commerceAddressId == 0) %>" disabled="<%= false %>" label="use-shipping-address-as-billing-address" name="use-as-billing" type="checkbox" />
	</div>
</c:if>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />addStreetAddress',
		function <portlet:namespace />addStreetAddress() {
			const A = AUI();

			const addStreetFields = A.one('.add-street-fields');
			const addStreetLink = A.one('.add-street-link');

			if (addStreetFields) {
				addStreetFields.show();
			}
			if (addStreetLink) {
				addStreetLink.hide();
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />clearAddressFields',
		function <portlet:namespace />clearAddressFields() {
			const A = AUI();

			A.all('.address-fields select').set('selectedIndex', 0);
			A.all('.address-fields input').val('');

			var useAsBillingField = A.one('#<portlet:namespace />use-as-billing');

			if (useAsBillingField) {
				useAsBillingField.attr(
					'checked',
					<%= baseAddressCheckoutStepDisplayContext.isShippingUsedAsBilling() %>
				);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />selectAddress',
		function <portlet:namespace />selectAddress() {
			const A = AUI();

			const commerceAddress = A.one('#<portlet:namespace />commerceAddress');
			const commerceAddressParamName = A.one(
				'#<%= renderResponse.getNamespace() + paramName %>'
			);
			const newAddress = A.one('#<portlet:namespace />newAddress');

			if (newAddress && commerceAddress && commerceAddressParamName) {
				const commerceAddressVal = commerceAddress.val();

				if (commerceAddressVal === '0') {
					<portlet:namespace />clearAddressFields();
					<portlet:namespace />toggleAddressFields(false);
				} else {
					<portlet:namespace />updateAddressFields(
						commerceAddress.get('selectedIndex')
					);
					Liferay.Form.get(
						'<portlet:namespace />fm'
					).formValidator.validate();
				}

				commerceAddressParamName.val(commerceAddressVal);
				newAddress.val(Number(commerceAddressVal === '0'));
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />toggleAddressFields',
		function <portlet:namespace />toggleAddressFields(state) {
			const A = AUI();

			Liferay.Util.toggleDisabled(A.all('.address-fields input'), state);
			Liferay.Util.toggleDisabled(A.all('.address-fields select'), state);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateAddressFields',
		function <portlet:namespace />updateAddressFields(selectedVal) {
			if (!selectedVal || selectedVal === '0') {
				return;
			}

			const A = AUI();

			const commerceAddress = A.one('#<portlet:namespace />commerceAddress');

			if (commerceAddress) {
				<portlet:namespace />addStreetAddress();
				<portlet:namespace />toggleAddressFields(true);

				const city = A.one('#<portlet:namespace />city');
				const commerceCountryId = A.one(
					'#<portlet:namespace />commerceCountryId'
				);
				const commerceRegionId = A.one(
					'#<portlet:namespace />commerceRegionId'
				);
				const name = A.one('#<portlet:namespace />name');
				const phoneNumber = A.one('#<portlet:namespace />phoneNumber');
				const street1 = A.one('#<portlet:namespace />street1');
				const street2 = A.one('#<portlet:namespace />street2');
				const street3 = A.one('#<portlet:namespace />street3');
				const zip = A.one('#<portlet:namespace />zip');

				if (
					city &&
					commerceCountryId &&
					commerceRegionId &&
					name &&
					phoneNumber &&
					street1 &&
					street2 &&
					street3 &&
					zip
				) {
					const originalFn = Liferay.component(
						'<portlet:namespace />countrySelects'
					).array[1].selectData;
					const selectedOption = commerceAddress
						.get('options')
						.item(selectedVal);

					city.val(selectedOption.getData('city'));
					commerceCountryId.val(selectedOption.getData('country'));
					commerceRegionId.val(selectedOption.getData('region'));
					name.val(selectedOption.getData('name'));
					phoneNumber.val(selectedOption.getData('phone-number'));
					street1.val(selectedOption.getData('street-1'));
					street2.val(selectedOption.getData('street-2'));
					street3.val(selectedOption.getData('street-3'));
					zip.val(selectedOption.getData('zip'));

					Liferay.component(
						'<portlet:namespace />countrySelects'
					).array[1].selectData = function newSelectData(
						callback,
						selectKey
					) {
						originalFn(function newCallback(list) {
							callback(list);

							commerceRegionId.val(selectedOption.getData('region'));
							Liferay.component(
								'<portlet:namespace />countrySelects'
							).array[1].selectData = originalFn;
						}, selectKey);
					};
					Liferay.component(
						'<portlet:namespace />countrySelects'
					)._callSelectData(0);
				}
			}
		},
		['aui-base']
	);
</aui:script>

<aui:script use="liferay-dynamic-select">
	Liferay.component(
		'<portlet:namespace />countrySelects',
		new Liferay.DynamicSelect([
			{
				select: '<portlet:namespace />commerceCountryId',
				selectData: function(callback) {
					function injectCountryPlaceholder(list) {
						callback([
							{
								commerceCountryId: '0',
								nameCurrentValue:
									'- <liferay-ui:message key="select-country" />'
							},
							...list
						]);
					}

					Liferay.Service(
						'/commerce.commercecountry/<%= baseAddressCheckoutStepDisplayContext.getCommerceCountrySelectionMethodName() %>-by-channel-id',
						{
							commerceChannelId: <%= commerceContext.getCommerceChannelId() %>,
							start: -1,
							end: -1
						},
						injectCountryPlaceholder
					);
				},
				selectDesc: 'nameCurrentValue',
				selectId: 'commerceCountryId',
				selectNullable: <%= false %>,
				selectSort: '<%= true %>',
				selectVal: '<%= commerceCountryId %>'
			},
			{
				select: '<portlet:namespace />commerceRegionId',
				selectData: function(callback, selectKey) {
					function injectRegionPlaceholder(list) {
						callback([
							{
								commerceRegionId: '0',
								name: '- <liferay-ui:message key="select-region" />'
							},
							...list
						]);
					}

					Liferay.Service(
						'/commerce.commerceregion/get-commerce-regions',
						{
							commerceCountryId: Number(selectKey),
							active: true
						},
						injectRegionPlaceholder
					);
				},
				selectDesc: 'name',
				selectId: 'commerceRegionId',
				selectNullable: <%= false %>,
				selectVal: '<%= commerceRegionId %>'
			}
		])
	);
</aui:script>