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

long commerceCountryId = commerceShippingFixedOptionRelsDisplayContext.getCommerceCountryId();
long commerceRegionId = commerceShippingFixedOptionRelsDisplayContext.getCommerceRegionId();
long commerceShippingMethodId = commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingMethodId();

long commerceShippingFixedOptionRelId = 0;

if (commerceShippingFixedOptionRel != null) {
	commerceShippingFixedOptionRelId = commerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId();
}
%>

<commerce-ui:side-panel-content
	title='<%= LanguageUtil.get(resourceBundle, "edit-shipping-option-setting") %>'
>
	<portlet:actionURL name="editCommerceShippingFixedOptionRel" var="editCommerceShippingFixedOptionRelActionURL" />

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
						List<CommerceShippingFixedOption> commerceShippingFixedOptions = commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingFixedOptions();

						for (CommerceShippingFixedOption commerceShippingFixedOption : commerceShippingFixedOptions) {
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
						List<CommerceInventoryWarehouse> commerceInventoryWarehouses = commerceShippingFixedOptionRelsDisplayContext.getCommerceInventoryWarehouses();

						for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryWarehouses) {
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
					<aui:select bean="<%= commerceShippingFixedOptionRel %>" label="country" model="<%= CommerceShippingFixedOptionRel.class %>" name="commerceCountryId" showEmptyOption="<%= true %>">

						<%
						List<CommerceCountry> commerceCountries = commerceShippingFixedOptionRelsDisplayContext.getCommerceCountries();

						for (CommerceCountry commerceCountry : commerceCountries) {
						%>

							<aui:option label="<%= commerceCountry.getName(languageId) %>" selected="<%= (commerceShippingFixedOptionRel != null) && (commerceShippingFixedOptionRel.getCommerceCountryId() == commerceCountry.getCommerceCountryId()) %>" value="<%= commerceCountry.getCommerceCountryId() %>" />

						<%
						}
						%>

					</aui:select>
				</div>

				<div class="col-md-4">
					<aui:select bean="<%= commerceShippingFixedOptionRel %>" label="region" model="<%= CommerceShippingFixedOptionRel.class %>" name="commerceRegionId" showEmptyOption="<%= true %>">

						<%
						List<CommerceRegion> commerceRegions = commerceShippingFixedOptionRelsDisplayContext.getCommerceRegions();

						for (CommerceRegion commerceRegion : commerceRegions) {
						%>

							<aui:option label="<%= commerceRegion.getName() %>" selected="<%= (commerceShippingFixedOptionRel != null) && (commerceShippingFixedOptionRel.getCommerceRegionId() == commerceRegion.getCommerceRegionId()) %>" value="<%= commerceRegion.getCommerceRegionId() %>" />

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
</commerce-ui:side-panel-content>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />commerceCountryId',
			selectData: function (callback) {
				Liferay.Service(
					'/commerce.commercecountry/get-commerce-countries',
					{
						companyId: <%= company.getCompanyId() %>,
						active: true,
					},
					callback
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'commerceCountryId',
			selectSort: '<%= true %>',
			selectVal: '<%= commerceCountryId %>',
		},
		{
			select: '<portlet:namespace />commerceRegionId',
			selectData: function (callback, selectKey) {
				Liferay.Service(
					'/commerce.commerceregion/get-commerce-regions',
					{
						commerceCountryId: Number(selectKey),
						active: true,
					},
					callback
				);
			},
			selectDesc: 'name',
			selectId: 'commerceRegionId',
			selectVal: '<%= commerceRegionId %>',
		},
	]);
</aui:script>