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
String cmd = ParamUtil.getString(request, Constants.CMD);

CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAddress billingAddress = null;

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();

if ((commerceOrder != null) && Validator.isNull(cmd)) {
	billingAddress = commerceOrder.getBillingAddress();
}

long countryId = BeanParamUtil.getLong(billingAddress, request, "countryId");
long regionId = BeanParamUtil.getLong(billingAddress, request, "regionId");
%>

<portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderBillingAddressActionURL" />

<commerce-ui:modal-content
	title='<%= (billingAddress == null) ? LanguageUtil.get(request, "add-billing-address") : LanguageUtil.get(request, "edit-billing-address") %>'
>
	<aui:form action="<%= editCommerceOrderBillingAddressActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value='<%= (billingAddress == null) ? "addBillingAddress" : "updateBillingAddress" %>' />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrder.getCommerceOrderId() %>" />

		<aui:model-context bean="<%= billingAddress %>" model="<%= CommerceAddress.class %>" />

		<aui:input name="name" wrapperCssClass="form-group-item" />

		<aui:input name="phoneNumber" wrapperCssClass="form-group-item" />

		<aui:input name="street1" wrapperCssClass="form-group-item" />

		<aui:input name="street2" wrapperCssClass="form-group-item" />

		<aui:input name="street3" wrapperCssClass="form-group-item" />

		<aui:select label="country" name="countryId" wrapperCssClass="form-group-item" />

		<aui:input name="zip" wrapperCssClass="form-group-item" />

		<aui:input name="city" wrapperCssClass="form-group-item" />

		<aui:select label="region" name="regionId" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>

<aui:script use="liferay-dynamic-select">
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
					'/commerce.commercecountrymanagerimpl/get-billing-countries',
					{
						active: true,
						billingAllowed: true,
						companyId: <%= company.getCompanyId() %>,
					},
					injectCountryPlaceholder
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'countryId',
			selectNullable: <%= false %>,
			selectSort: '<%= true %>',
			selectVal: '<%= countryId %>',
		},
		{
			select: '<portlet:namespace />regionId',
			selectData: function (callback, selectKey) {
				function injectRegionPlaceholder(list) {
					var callbackList = [
						{
							regionId: '0',
							name: '- <liferay-ui:message key="select-region" />',
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
			selectVal: '<%= regionId %>',
		},
	]);
</aui:script>