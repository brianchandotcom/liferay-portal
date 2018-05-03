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
String redirect = ParamUtil.getString(request, "redirect");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceShippingFixedOptionRelsDisplayContext commerceShippingFixedOptionRelsDisplayContext = (CommerceShippingFixedOptionRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingFixedOptionRel commerceShippingFixedOptionRel = commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingFixedOptionRel();

long commerceCountryId = commerceShippingFixedOptionRelsDisplayContext.getCommerceCountryId();
long commerceRegionId = commerceShippingFixedOptionRelsDisplayContext.getCommerceRegionId();
long commerceShippingMethodId = commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingMethodId();

long commerceShippingFixedOptionRelId = 0;

if (commerceShippingFixedOptionRel != null) {
	commerceShippingFixedOptionRelId = commerceShippingFixedOptionRel.getCommerceShippingFixedOptionRelId();
}

PortletURL shippingMethodsURL = renderResponse.createRenderURL();

shippingMethodsURL.setParameter("commerceAdminModuleKey", ShippingMethodsCommerceAdminModule.KEY);

String localizedKey = (commerceShippingFixedOptionRel == null) ? "add-shipping-option-setting" : "edit-shipping-option-setting";

String title = LanguageUtil.get(resourceBundle, localizedKey);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String screenNavigationEntryKey = commerceShippingFixedOptionRelsDisplayContext.getScreenNavigationEntryKey();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), shippingMethodsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, screenNavigationEntryKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceShippingFixedOptionRel" var="editCommerceShippingFixedOptionRelActionURL" />

<aui:form action="<%= editCommerceShippingFixedOptionRelActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingFixedOptionRel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceShippingFixedOptionRelId" type="hidden" value="<%= commerceShippingFixedOptionRelId %>" />
	<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />

	<div class="alert alert-info">
		<liferay-ui:message key="commerce-shipping-fixed-option-rel-info" />
	</div>

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceShippingFixedOptionRel %>" model="<%= CommerceShippingFixedOptionRel.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<div class="row">
					<div class="col-md-6">
						<aui:select label="shipping-option" name="commerceShippingFixedOptionId" required="<%= true %>">

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
						<aui:select label="warehouse" name="commerceWarehouseId" showEmptyOption="<%= true %>">

							<%
							List<CommerceWarehouse> commerceWarehouses = commerceShippingFixedOptionRelsDisplayContext.getCommerceWarehouses();

							for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
							%>

								<aui:option label="<%= commerceWarehouse.getName() %>" value="<%= commerceWarehouse.getCommerceWarehouseId() %>" />

							<%
							}
							%>

						</aui:select>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<aui:select label="country" name="commerceCountryId" showEmptyOption="<%= true %>">

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
						<aui:select label="region" name="commerceRegionId" showEmptyOption="<%= true %>">

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
						<aui:input name="zip" />
					</div>
				</div>
			</aui:fieldset>

			<aui:fieldset collapsed="<%= false %>" collapsible="<%= true %>" label="settings">
				<div class="row">
					<div class="col-md-6">
						<aui:input name="weightFrom" suffix="<%= commerceShippingFixedOptionRelsDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_WEIGHT) %>" />
					</div>

					<div class="col-md-6">
						<aui:input name="weightTo" suffix="<%= commerceShippingFixedOptionRelsDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_WEIGHT) %>" />
					</div>
				</div>

				<aui:input name="fixedPrice" suffix="<%= commerceShippingFixedOptionRelsDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= (commerceShippingFixedOptionRel == null) ? commerceShippingFixedOptionRelsDisplayContext.format(BigDecimal.ZERO) : commerceShippingFixedOptionRelsDisplayContext.format(commerceShippingFixedOptionRel.getFixedPrice()) %>" />

				<aui:input name="rateUnitWeightPrice" suffix="<%= commerceShippingFixedOptionRelsDisplayContext.getCommerceCurrencyCode() %>" type="text" value="<%= (commerceShippingFixedOptionRel == null) ? commerceShippingFixedOptionRelsDisplayContext.format(BigDecimal.ZERO) : commerceShippingFixedOptionRelsDisplayContext.format(commerceShippingFixedOptionRel.getRateUnitWeightPrice()) %>" />

				<aui:input name="ratePercentage" suffix="<%= StringPool.PERCENT %>" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,liferay-dynamic-select">
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