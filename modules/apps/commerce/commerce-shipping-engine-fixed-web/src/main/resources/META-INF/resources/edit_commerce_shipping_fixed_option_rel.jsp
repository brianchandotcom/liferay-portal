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
CommerceShippingFixedOptionRelsDisplayContext commerceShippingFixedOptionRelsDisplayContext = (CommerceShippingFixedOptionRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingFixedOptionRel();

long countryId = commerceShippingFixedOptionRelsDisplayContext.getCountryId();
long regionId = commerceShippingFixedOptionRelsDisplayContext.getRegionId();
long commerceShippingMethodId = commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingMethodId();

long commerceShippingFixedOptionRelId = 0;

if (commerceShippingFixedOptionRel != null) {
	commerceShippingFixedOptionRelId = commerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId();
}
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(resourceBundle, "edit-shipping-option-setting") %>'
>
	<portlet:actionURL name="/commerce_shipping_methods/edit_commerce_shipping_fixed_option_rel" var="editCommerceShippingFixedOptionRelActionURL" />

	<aui:form action="<%= editCommerceShippingFixedOptionRelActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingFixedOptionRel == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceShippingFixedOptionRelId" type="hidden" value="<%= commerceShippingFixedOptionRelId %>" />
		<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />

		<div class="alert alert-info">
			<liferay-ui:message key="commerce-shipping-fixed-option-rel-info" />
		</div>

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<div class="row">
				<div class="col-md-6">
					<aui:select bean="<%= commerceShippingFixedOptionRel %>" label="shipping-option" model="<%= CommerceShippingFixedOptionRel.class %>" name="commerceShippingFixedOptionId" required="<%= true %>">

						<%
						for (CommerceShippingFixedOption commerceShippingFixedOption : commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingFixedOptions()) {
						%>

							<aui:option label="<%= commerceShippingFixedOption.getName(languageId) %>" value="<%= commerceShippingFixedOption.getCommerceShippingFixedOptionId() %>" />

						<%
						}
						%>

					</aui:select>
				</div>

				<div class="col-md-6">
					<aui:select bean="<%= commerceShippingFixedOptionRel %>" label="warehouse" model="<%= CommerceShippingFixedOptionRel.class %>" name="commerceInventoryWarehouseId" showEmptyOption="<%= true %>">

						<%
						for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceShippingFixedOptionRelsDisplayContext.getCommerceInventoryWarehouses()) {
						%>

							<aui:option label="<%= commerceInventoryWarehouse.getName() %>" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

						<%
						}
						%>

					</aui:select>
				</div>
			</div>

			<div class="row">
				<div class="col-md-4">
					<aui:select bean="<%= commerceShippingFixedOptionRel %>" label="country" model="<%= CommerceShippingFixedOptionRel.class %>" name="countryId" showEmptyOption="<%= true %>">

						<%
						for (Country country : commerceShippingFixedOptionRelsDisplayContext.getCountries()) {
						%>

							<aui:option label="<%= country.getTitle(languageId) %>" selected="<%= (commerceShippingFixedOptionRel != null) && (commerceShippingFixedOptionRel.getCountryId() == country.getCountryId()) %>" value="<%= country.getCountryId() %>" />

						<%
						}
						%>

					</aui:select>
				</div>

				<div class="col-md-4">
					<aui:select bean="<%= commerceShippingFixedOptionRel %>" label="region" model="<%= CommerceShippingFixedOptionRel.class %>" name="regionId" showEmptyOption="<%= true %>">

						<%
						for (Region region : commerceShippingFixedOptionRelsDisplayContext.getRegions()) {
						%>

							<aui:option label="<%= region.getName() %>" selected="<%= (commerceShippingFixedOptionRel != null) && (commerceShippingFixedOptionRel.getRegionId() == region.getRegionId()) %>" value="<%= region.getRegionId() %>" />

						<%
						}
						%>

					</aui:select>
				</div>

				<div class="col-md-4">
					<aui:input bean="<%= commerceShippingFixedOptionRel %>" model="<%= CommerceShippingFixedOptionRel.class %>" name="zip" />
				</div>
			</div>
		</commerce-ui:panel>

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "settings") %>'
		>
			<div class="row">
				<div class="col-md-6">
					<aui:input bean="<%= commerceShippingFixedOptionRel %>" model="<%= CommerceShippingFixedOptionRel.class %>" name="weightFrom" suffix="<%= commerceShippingFixedOptionRelsDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_WEIGHT) %>" />
				</div>

				<div class="col-md-6">
					<aui:input bean="<%= commerceShippingFixedOptionRel %>" model="<%= CommerceShippingFixedOptionRel.class %>" name="weightTo" suffix="<%= commerceShippingFixedOptionRelsDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_WEIGHT) %>" />
				</div>
			</div>

			<aui:input name="fixedPrice" suffix="<%= commerceShippingFixedOptionRelsDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= (commerceShippingFixedOptionRel == null) ? BigDecimal.ZERO : commerceShippingFixedOptionRelsDisplayContext.round(commerceShippingFixedOptionRel.getFixedPrice()) %>">
				<aui:validator name="number" />
			</aui:input>

			<aui:input label="price-per-unit-of-weight" name="rateUnitWeightPrice" suffix="<%= commerceShippingFixedOptionRelsDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= (commerceShippingFixedOptionRel == null) ? BigDecimal.ZERO : commerceShippingFixedOptionRelsDisplayContext.round(commerceShippingFixedOptionRel.getRateUnitWeightPrice()) %>">
				<aui:validator name="number" />
			</aui:input>

			<aui:input bean="<%= commerceShippingFixedOptionRel %>" label="subtotal-percentage-price" model="<%= CommerceShippingFixedOptionRel.class %>" name="ratePercentage" suffix="<%= StringPool.PERCENT %>" />
		</commerce-ui:panel>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />countryId',
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
			selectId: 'countryId',
			selectSort: '<%= true %>',
			selectVal: '<%= countryId %>',
		},
		{
			select: '<portlet:namespace />regionId',
			selectData: function (callback, selectKey) {
				Liferay.Service(
					'/region/get-regions',
					{
						active: true,
						countryId: Number(selectKey),
					},
					callback
				);
			},
			selectDesc: 'name',
			selectId: 'regionId',
			selectVal: '<%= regionId %>',
		},
	]);
</aui:script>