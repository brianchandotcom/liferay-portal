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

String paramName = baseAddressCheckoutStepDisplayContext.getParamName();

long commerceAddressId = BeanParamUtil.getLong(baseAddressCheckoutStepDisplayContext.getCommerceOrder(), request, paramName);

if (commerceAddressId == 0) {
	commerceAddressId = baseAddressCheckoutStepDisplayContext.getDefaultCommerceAddressId();
}

String selectLabel = "choose-" + baseAddressCheckoutStepDisplayContext.getTitle();

CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

if (commerceOrder.isGuestOrder()) {
	commerceAddressId = 0;
}

CommerceAddress currentCommerceAddress = baseAddressCheckoutStepDisplayContext.getCommerceAddress(commerceAddressId);

CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

boolean hasManageAddressesPermission = baseAddressCheckoutStepDisplayContext.hasPermission(permissionChecker, commerceAccount, AccountActionKeys.MANAGE_ADDRESSES);
boolean hasViewBillingAddressPermission = baseAddressCheckoutStepDisplayContext.hasViewBillingAddressPermission(permissionChecker, commerceAccount);

boolean showErrorNoBillingAddress = GetterUtil.getBoolean(request.getAttribute(CommerceCheckoutWebKeys.SHOW_ERROR_NO_BILLING_ADDRESS));
%>

<liferay-ui:error exception="<%= CommerceOrderDefaultBillingAddressException.class %>" message="no-default-billing-address" />

<c:if test="<%= !showErrorNoBillingAddress %>">
	<div class="form-group-autofit">
		<c:if test="<%= !commerceOrder.isGuestOrder() %>">
			<c:if test="<%= baseAddressCheckoutStepDisplayContext.hasPermission(permissionChecker, commerceAccount, AccountActionKeys.VIEW_ADDRESSES) %>">
				<aui:select label="<%= selectLabel %>" name="commerceAddress" onChange='<%= liferayPortletResponse.getNamespace() + "selectAddress();" %>' wrapperCssClass="commerce-form-group-item-row form-group-item">
					<c:choose>
						<c:when test="<%= hasManageAddressesPermission %>">
							<aui:option label="add-new-address" value="0" />
						</c:when>
						<c:otherwise>
							<aui:option label="choose-address" value="0" />
						</c:otherwise>
					</c:choose>

					<%
					boolean addressWasFound = false;

					for (CommerceAddress commerceAddress : commerceAddresses) {
						boolean selectedAddress = commerceAddressId == commerceAddress.getCommerceAddressId();

						if (selectedAddress) {
							addressWasFound = true;
						}
					%>

						<aui:option data-city="<%= HtmlUtil.escapeAttribute(commerceAddress.getCity()) %>" data-country="<%= HtmlUtil.escapeAttribute(String.valueOf(commerceAddress.getCountryId())) %>" data-name="<%= HtmlUtil.escapeAttribute(commerceAddress.getName()) %>" data-phone-number="<%= HtmlUtil.escapeAttribute(commerceAddress.getPhoneNumber()) %>" data-region="<%= HtmlUtil.escapeAttribute(String.valueOf(commerceAddress.getRegionId())) %>" data-street-1="<%= HtmlUtil.escapeAttribute(commerceAddress.getStreet1()) %>" data-street-2="<%= Validator.isNotNull(commerceAddress.getStreet2()) ? HtmlUtil.escapeAttribute(commerceAddress.getStreet2()) : StringPool.BLANK %>" data-street-3="<%= Validator.isNotNull(commerceAddress.getStreet3()) ? HtmlUtil.escapeAttribute(commerceAddress.getStreet3()) : StringPool.BLANK %>" data-zip="<%= HtmlUtil.escapeAttribute(commerceAddress.getZip()) %>" label="<%= commerceAddress.getName() %>" selected="<%= selectedAddress %>" value="<%= commerceAddress.getCommerceAddressId() %>" />

					<%
					}
					%>

					<c:if test="<%= (currentCommerceAddress != null) && !addressWasFound %>">
						<aui:option label="<%= HtmlUtil.escapeAttribute(currentCommerceAddress.getName()) %>" selected="<%= true %>" value="<%= currentCommerceAddress.getCommerceAddressId() %>" />
					</c:if>
				</aui:select>
			</c:if>
		</c:if>

		<aui:input disabled="<%= commerceAddresses.isEmpty() ? true : false %>" name="<%= paramName %>" type="hidden" value="<%= commerceAddressId %>" />

		<aui:input name="newAddress" type="hidden" value='<%= ((commerceAddressId > 0) || !hasManageAddressesPermission) ? "0" : "1" %>' />
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
			<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="name" placeholder="name" wrapperCssClass="form-group-item" />

			<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="phoneNumber" placeholder="phone-number" wrapperCssClass="form-group-item" />
		</div>

		<div class="form-group-autofit">
			<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="street1" placeholder="address" wrapperCssClass="form-group-item" />

			<aui:select disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="countryId" placeholder="country" title="country" wrapperCssClass="form-group-item">
				<aui:validator errorMessage='<%= LanguageUtil.get(request, "please-enter-a-valid-country") %>' name="min">1</aui:validator>
			</aui:select>
		</div>

		<c:choose>
			<c:when test="<%= (commerceAddressId > 0) && (!Validator.isBlank(currentCommerceAddress.getStreet2()) || !Validator.isBlank(currentCommerceAddress.getStreet3())) %>">
				<div class="form-group-autofit">
					<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="street2" placeholder="address-2" wrapperCssClass="form-group-item" />
					<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="street3" placeholder="address-3" wrapperCssClass="form-group-item" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="add-street-link form-group-autofit">
					<aui:a hidden="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" href="javascript:;" label="+-add-address-line" onClick='<%= liferayPortletResponse.getNamespace() + "addStreetAddress();" %>' />
				</div>

				<div class="add-street-fields form-group-autofit hide">
					<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="street2" placeholder="address-2" wrapperCssClass="form-group-item" />

					<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="street3" placeholder="address-3" wrapperCssClass="form-group-item" />
				</div>
			</c:otherwise>
		</c:choose>

		<div class="form-group-autofit">
			<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="zip" placeholder="zip" wrapperCssClass="form-group-item" />

			<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="city" placeholder="city" wrapperCssClass="form-group-item" />

			<aui:select disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" label="" name="regionId" placeholder="region" title="region" wrapperCssClass="form-group-item" />

			<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" id="commerceRegionIdInput" label="" name="regionId" placeholder="regionId" title="region" wrapperCssClass="d-none form-group-item" />

			<aui:input disabled="<%= (commerceAddressId > 0) || !hasManageAddressesPermission %>" id="commerceRegionIdName" label="" name="regionId" placeholder="regionName" title="region" wrapperCssClass="d-none form-group-item" />
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

	<c:if test="<%= Objects.equals(CommerceCheckoutWebKeys.SHIPPING_ADDRESS_PARAM_NAME, paramName) && baseAddressCheckoutStepDisplayContext.hasPermission(permissionChecker, commerceAccount, AccountActionKeys.MANAGE_ADDRESSES) && hasViewBillingAddressPermission %>">
		<div class="shipping-as-billing">
			<aui:input checked="<%= baseAddressCheckoutStepDisplayContext.isShippingUsedAsBilling() || (commerceAddressId == 0) %>" disabled="<%= false %>" label="use-shipping-address-as-billing-address" name="use-as-billing" type="checkbox" />
		</div>
	</c:if>

	<aui:script>
		Liferay.provide(
			window,
			'<portlet:namespace />addStreetAddress',
			function <portlet:namespace />addStreetAddress() {
				var A = AUI();

				var addStreetFields = A.one('.add-street-fields');
				var addStreetLink = A.one('.add-street-link');

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
				var A = AUI();

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
				var A = AUI();

				var commerceAddress = A.one('#<portlet:namespace />commerceAddress');
				var commerceAddressParamName = A.one(
					'#<%= liferayPortletResponse.getNamespace() + paramName %>'
				);
				var newAddress = A.one('#<portlet:namespace />newAddress');

				if (newAddress && commerceAddress && commerceAddressParamName) {
					var commerceAddressVal = commerceAddress.val();

					if (commerceAddressVal === '0') {
						<portlet:namespace />clearAddressFields();

						if (<%= hasManageAddressesPermission %>) {
							<portlet:namespace />toggleAddressFields(false);
						}
					}
					else {
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
				var A = AUI();

				Liferay.Util.toggleDisabled(A.all('.address-fields input'), state);
				Liferay.Util.toggleDisabled(A.all('.address-fields select'), state);

				var commerceRegionIdSelect = A.one(
					'#<portlet:namespace />regionId'
				).getDOMNode();
				var commerceRegionIdInput = A.one(
					'#<portlet:namespace />commerceRegionIdInput'
				).getDOMNode();
				var commerceRegionIdName = A.one(
					'#<portlet:namespace />commerceRegionIdName'
				).getDOMNode();

				commerceRegionIdSelect.setAttribute(
					'name',
					'<portlet:namespace />regionId'
				);
				commerceRegionIdSelect.parentElement.classList.remove('d-none');

				commerceRegionIdInput.setAttribute(
					'name',
					'commerceRegionIdInputDisabled'
				);
				commerceRegionIdInput.parentElement.classList.add('d-none');
				commerceRegionIdName.setAttribute(
					'name',
					'commerceRegionIdInputDisabled'
				);
				commerceRegionIdName.parentElement.classList.add('d-none');
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

				var A = AUI();

				var commerceAddress = A.one('#<portlet:namespace />commerceAddress');

				if (commerceAddress) {
					<portlet:namespace />addStreetAddress();
					<portlet:namespace />toggleAddressFields(true);

					var city = A.one('#<portlet:namespace />city');
					var countryId = A.one('#<portlet:namespace />countryId');
					var commerceRegionIdInput = A.one(
						'#<portlet:namespace />commerceRegionIdInput'
					).getDOMNode();
					var commerceRegionIdName = A.one(
						'#<portlet:namespace />commerceRegionIdName'
					).getDOMNode();
					var commerceRegionIdSelect = A.one(
						'#<portlet:namespace />regionId'
					).getDOMNode();
					var name = A.one('#<portlet:namespace />name');
					var phoneNumber = A.one('#<portlet:namespace />phoneNumber');
					var street1 = A.one('#<portlet:namespace />street1');
					var street2 = A.one('#<portlet:namespace />street2');
					var street3 = A.one('#<portlet:namespace />street3');
					var zip = A.one('#<portlet:namespace />zip');

					if (
						city &&
						countryId &&
						commerceRegionIdInput &&
						commerceRegionIdSelect &&
						commerceRegionIdName &&
						name &&
						phoneNumber &&
						street1 &&
						street2 &&
						street3 &&
						zip
					) {
						var selectedOption = commerceAddress
							.get('options')
							.item(selectedVal);

						city.val(selectedOption.getData('city'));
						countryId.val(selectedOption.getData('country'));
						name.val(selectedOption.getData('name'));
						phoneNumber.val(selectedOption.getData('phone-number'));
						street1.val(selectedOption.getData('street-1'));
						street2.val(selectedOption.getData('street-2'));
						street3.val(selectedOption.getData('street-3'));
						zip.val(selectedOption.getData('zip'));

						commerceRegionIdSelect.setAttribute(
							'name',
							'commerceRegionIdSelectIgnore'
						);
						commerceRegionIdSelect.parentElement.classList.add('d-none');

						commerceRegionIdInput.value = selectedOption.getData('region');
						commerceRegionIdInput.setAttribute(
							'name',
							'<portlet:namespace />regionId'
						);
						commerceRegionIdInput.parentElement.classList.add('d-none');

						commerceRegionIdName.setAttribute(
							'name',
							'commerceRegionIdNameIgnore'
						);
						commerceRegionIdName.parentElement.classList.remove('d-none');

						Liferay.Service(
							'/region/get-regions',
							{
								active: true,
								countryId: parseInt(
									selectedOption.getData('country'),
									10
								),
							},
							function setUIOnlyInputRegionName(regions) {
								for (var i = 0; i < regions.length; i++) {
									if (
										regions[i].regionId ===
										selectedOption.getData('region')
									) {
										commerceRegionIdName.value = regions[i].name;

										break;
									}
								}
							}
						);
					}
				}
			},
			['aui-base']
		);
	</aui:script>

	<aui:script>
		Liferay.component(
			'<portlet:namespace />countrySelects',
			new Liferay.DynamicSelect([
				{
					select: '<portlet:namespace />countryId',
					selectData: function (callback) {
						function injectCountryPlaceholder(list) {
							var callbackList = [
								{
									countryId: '0',
									nameCurrentValue:
										'- <liferay-ui:message key="select-country" />',
								},
							];

							list.forEach((listElement) => {
								callbackList.push(listElement);
							});

							callback(callbackList);
						}

						Liferay.Service(
							'/commerce.commercecountrymanagerimpl/<%= baseAddressCheckoutStepDisplayContext.getCommerceCountrySelectionMethodName() %>-by-channel-id',
							{
								channelId: <%= commerceContext.getCommerceChannelId() %>,
								end: -1,
								start: -1,
							},
							injectCountryPlaceholder
						);
					},
					selectDesc: 'nameCurrentValue',
					selectId: 'countryId',
					selectNullable: <%= false %>,
					selectSort: '<%= true %>',
					selectVal:
						'<%= BeanParamUtil.getLong(currentCommerceAddress, request, "countryId", 0) %>',
				},
				{
					select: '<portlet:namespace />regionId',
					selectData: function (callback, selectKey) {
						function injectRegionPlaceholder(list) {
							var callbackList = [
								{
									regionId: '0',
									name:
										'- <liferay-ui:message key="select-region" />',
									nameCurrentValue:
										'- <liferay-ui:message key="select-region" />',
								},
							];

							list.forEach((listElement) => {
								callbackList.push(listElement);
							});

							callback(callbackList);
						}

						Liferay.Service(
							'/region/get-regions',
							{
								active: true,
								countryId: Number(selectKey),
							},
							injectRegionPlaceholder
						);
					},
					selectDesc: 'name',
					selectId: 'regionId',
					selectNullable: <%= false %>,
					selectVal:
						'<%= BeanParamUtil.getLong(currentCommerceAddress, request, "regionId", 0) %>',
				},
			])
		);
	</aui:script>
</c:if>