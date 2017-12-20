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

CShippingFixedOptionRelsDisplayContext cShippingFixedOptionRelsDisplayContext = (CShippingFixedOptionRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CShippingFixedOptionRel cShippingFixedOptionRel = cShippingFixedOptionRelsDisplayContext.getCShippingFixedOptionRel();

long commerceCountryId = cShippingFixedOptionRelsDisplayContext.getCommerceCountryId();
long commerceRegionId = cShippingFixedOptionRelsDisplayContext.getCommerceRegionId();
long commerceShippingMethodId = cShippingFixedOptionRelsDisplayContext.getCommerceShippingMethodId();

long cShippingFixedOptionRelId = 0;

if (cShippingFixedOptionRel != null) {
	cShippingFixedOptionRelId = cShippingFixedOptionRel.getCShippingFixedOptionRelId();
}

PortletURL shippingMethodsURL = renderResponse.createRenderURL();

shippingMethodsURL.setParameter("commerceAdminModuleKey", ShippingMethodsCommerceAdminModule.KEY);

String localizedKey = (cShippingFixedOptionRel == null) ? "add-shipping-option-setting" : "edit-shipping-option-setting";

String title = LanguageUtil.get(request, localizedKey);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String screenNavigationEntryKey = cShippingFixedOptionRelsDisplayContext.getScreenNavigationEntryKey();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, ShippingMethodsCommerceAdminModule.KEY), shippingMethodsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, screenNavigationEntryKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCShippingFixedOptionRel" var="editCShippingFixedOptionRelActionURL" />

<aui:form action="<%= editCShippingFixedOptionRelActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cShippingFixedOptionRel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="cShippingFixedOptionRelId" type="hidden" value="<%= cShippingFixedOptionRelId %>" />
	<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= cShippingFixedOptionRel %>" model="<%= CShippingFixedOptionRel.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:select label="shipping-option" name="commerceShippingFixedOptionId" required="<%= true %>">

					<%
					List<CommerceShippingFixedOption> commerceShippingFixedOptions = cShippingFixedOptionRelsDisplayContext.getCommerceShippingFixedOptions();

					for (CommerceShippingFixedOption commerceShippingFixedOption : commerceShippingFixedOptions) {
					%>

						<aui:option
							label="<%= commerceShippingFixedOption.getName(languageId) %>"
							value="<%= commerceShippingFixedOption.getCommerceShippingFixedOptionId() %>"
						/>

					<%
					}
					%>

				</aui:select>

				<aui:select label="warehouse" name="commerceWarehouseId" showEmptyOption="<%= true %>">

					<%
					List<CommerceWarehouse> commerceWarehouses = cShippingFixedOptionRelsDisplayContext.getCommerceWarehouses();

					for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
					%>

						<aui:option
							label="<%= commerceWarehouse.getName() %>"
							value="<%= commerceWarehouse.getCommerceWarehouseId() %>"
						/>

					<%
					}
					%>

				</aui:select>

				<aui:select label="country" name="commerceCountryId" showEmptyOption="<%= true %>">

					<%
					List<CommerceCountry> commerceCountries = cShippingFixedOptionRelsDisplayContext.getCommerceCountries();

					for (CommerceCountry commerceCountry : commerceCountries) {
					%>

						<aui:option
							label="<%= commerceCountry.getName(languageId) %>"
							selected="<%= (cShippingFixedOptionRel != null) && (cShippingFixedOptionRel.getCommerceCountryId() == commerceCountry.getCommerceCountryId()) %>"
							value="<%= commerceCountry.getCommerceCountryId() %>"
						/>

					<%
					}
					%>

				</aui:select>

				<aui:select label="region" name="commerceRegionId" showEmptyOption="<%= true %>">

					<%
					List<CommerceRegion> commerceRegions = cShippingFixedOptionRelsDisplayContext.getCommerceRegions();

					for (CommerceRegion commerceRegion : commerceRegions) {
					%>

						<aui:option
							label="<%= commerceRegion.getName() %>"
							selected="<%= (cShippingFixedOptionRel != null) && (cShippingFixedOptionRel.getCommerceRegionId() == commerceRegion.getCommerceRegionId()) %>"
							value="<%= commerceRegion.getCommerceRegionId() %>"
						/>

					<%
					}
					%>

				</aui:select>

				<aui:input name="zip" />

				<aui:input name="weightFrom" />

				<aui:input name="weightTo" />

				<aui:input name="fixedPrice" />

				<aui:input name="rateUnitWeightPrice" />

				<aui:input name="ratePercentage" />
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