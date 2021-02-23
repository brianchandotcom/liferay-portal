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
CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = commerceInventoryWarehousesDisplayContext.getCommerceInventoryWarehouse();

String countryTwoLettersISOCode = BeanParamUtil.getString(commerceInventoryWarehouse, request, "countryTwoLettersISOCode");
String commerceRegionCode = BeanParamUtil.getString(commerceInventoryWarehouse, request, "commerceRegionCode");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="address"
/>

<aui:model-context bean="<%= commerceInventoryWarehouse %>" model="<%= CommerceInventoryWarehouse.class %>" />

<aui:fieldset>
	<div class="col-md-6">
		<aui:input name="street1" />

		<aui:input name="street2" />

		<aui:input name="street3" />

		<aui:select label="country" name="countryTwoLettersISOCode" />

		<aui:select label="region" name="commerceRegionCode" />
	</div>

	<div class="col-md-6">
		<aui:input label="postal-code" name="zip" />

		<aui:input name="city" />
	</div>
</aui:fieldset>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />countryTwoLettersISOCode',
			selectData: function (callback) {
				Liferay.Service(
					'/country/get-company-countries',
					{
						active: true,
						companyId: <%= company.getCompanyId() %>,
					},
					callback
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'a2',
			selectSort: '<%= true %>',
			selectVal: '<%= HtmlUtil.escape(countryTwoLettersISOCode) %>',
		},
		{
			select: '<portlet:namespace />commerceRegionCode',
			selectData: function (callback, selectKey) {
				Liferay.Service(
					'/region/get-regions',
					{
						a2: selectKey,
						active: true,
						companyId: <%= company.getCompanyId() %>,
					},
					callback
				);
			},
			selectDesc: 'name',
			selectId: 'regionCode',
			selectVal: '<%= HtmlUtil.escape(commerceRegionCode) %>',
		},
	]);
</aui:script>