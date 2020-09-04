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

long commerceCountryId = BeanParamUtil.getLong(billingAddress, request, "commerceCountryId");
long commerceRegionId = BeanParamUtil.getLong(billingAddress, request, "commerceRegionId");
%>

<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderBillingAddressActionURL" />

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

		<aui:select label="country" name="commerceCountryId" wrapperCssClass="form-group-item" />

		<aui:input name="zip" wrapperCssClass="form-group-item" />

		<aui:input name="city" wrapperCssClass="form-group-item" />

		<aui:select label="region" name="commerceRegionId" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />commerceCountryId',
			selectData: function (callback) {
				function injectCountryPlaceholder(list) {
					var callbackList = [
						{
							commerceCountryId: '0',
							nameCurrentValue:
								'- <liferay-ui:message key="select-country" />',
						},
					];

					list.forEach(function (listElement) {
						callbackList.push(listElement);
					});

					callback(callbackList);
				}

				Liferay.Service(
					'/commerce.commercecountry/get-billing-commerce-countries',
					{
						active: true,
						billingAllowed: true,
						companyId: <%= company.getCompanyId() %>,
					},
					injectCountryPlaceholder
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'commerceCountryId',
			selectNullable: <%= false %>,
			selectSort: '<%= true %>',
			selectVal: '<%= commerceCountryId %>',
		},
		{
			select: '<portlet:namespace />commerceRegionId',
			selectData: function (callback, selectKey) {
				function injectRegionPlaceholder(list) {
					var callbackList = [
						{
							commerceRegionId: '0',
							name: '- <liferay-ui:message key="select-region" />',
							nameCurrentValue:
								'- <liferay-ui:message key="select-region" />',
						},
					];

					list.forEach(function (listElement) {
						callbackList.push(listElement);
					});

					callback(callbackList);
				}

				Liferay.Service(
					'/commerce.commerceregion/get-commerce-regions',
					{
						active: true,
						commerceCountryId: Number(selectKey),
					},
					injectRegionPlaceholder
				);
			},
			selectDesc: 'name',
			selectId: 'commerceRegionId',
			selectNullable: <%= false %>,
			selectVal: '<%= commerceRegionId %>',
		},
	]);
</aui:script>