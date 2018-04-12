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
CommerceWarehousesDisplayContext commerceWarehousesDisplayContext = (CommerceWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceWarehouse commerceWarehouse = commerceWarehousesDisplayContext.getCommerceWarehouse();

long commerceCountryId = BeanParamUtil.getLong(commerceWarehouse, request, "commerceCountryId");
long commerceRegionId = BeanParamUtil.getLong(commerceWarehouse, request, "commerceRegionId");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="address"
/>

<liferay-ui:error exception="<%= CommerceWarehouseCommerceRegionIdException.class %>" message="please-enter-a-valid-region" />

<aui:model-context bean="<%= commerceWarehouse %>" model="<%= CommerceWarehouse.class %>" />

<aui:fieldset>
	<div class="col-md-6">
		<aui:input name="street1" />

		<aui:input name="street2" />

		<aui:input name="street3" />

		<aui:select label="country" name="commerceCountryId" />

		<aui:select label="region" name="commerceRegionId" />
	</div>

	<div class="col-md-6">
		<aui:input label="postal-code" name="zip" />

		<aui:input name="city" />
	</div>
</aui:fieldset>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace />commerceCountryId',
				selectData: function(callback) {
					Liferay.Service(
						'/commerce.commercecountry/get-commerce-countries',
						{
							groupId: <%= scopeGroupId %>,
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