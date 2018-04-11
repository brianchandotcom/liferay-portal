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

CommerceTaxFixedRateAddressRelsDisplayContext commerceTaxFixedRateAddressRelsDisplayContext = (CommerceTaxFixedRateAddressRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel = commerceTaxFixedRateAddressRelsDisplayContext.getCommerceTaxFixedRateAddressRel();

long commerceCountryId = commerceTaxFixedRateAddressRelsDisplayContext.getCommerceCountryId();
long commerceRegionId = commerceTaxFixedRateAddressRelsDisplayContext.getCommerceRegionId();
long commerceTaxMethodId = commerceTaxFixedRateAddressRelsDisplayContext.getCommerceTaxMethodId();

long commerceTaxFixedRateAddressRelId = 0;

if (commerceTaxFixedRateAddressRel != null) {
	commerceTaxFixedRateAddressRelId = commerceTaxFixedRateAddressRel.getCommerceTaxFixedRateAddressRelId();
}

PortletURL taxMethodsURL = renderResponse.createRenderURL();

taxMethodsURL.setParameter("commerceAdminModuleKey", commerceAdminModuleKey);

String localizedKey = (commerceTaxFixedRateAddressRel == null) ? "add-tax-rate-setting" : "edit-tax-rate-setting";

String title = LanguageUtil.get(resourceBundle, localizedKey);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String screenNavigationEntryKey = commerceTaxFixedRateAddressRelsDisplayContext.getScreenNavigationEntryKey();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), taxMethodsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, screenNavigationEntryKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceTaxFixedRateAddressRel" var="editCommerceTaxFixedRateAddressRelActionURL" />

<aui:form action="<%= editCommerceTaxFixedRateAddressRelActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceTaxFixedRateAddressRel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceTaxFixedRateAddressRelId" type="hidden" value="<%= commerceTaxFixedRateAddressRelId %>" />
	<aui:input name="commerceTaxMethodId" type="hidden" value="<%= commerceTaxMethodId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceTaxFixedRateAddressRel %>" model="<%= CommerceTaxFixedRateAddressRel.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<div class="row">
					<div class="col-md-6">
						<aui:select disabled="<%= commerceTaxFixedRateAddressRel != null %>" label="tax-category" name="cpTaxCategoryId">

							<%
							List<CPTaxCategory> cpTaxCategories = commerceTaxFixedRateAddressRelsDisplayContext.getAvailableCPTaxCategories();

							for (CPTaxCategory cpTaxCategory : cpTaxCategories) {
							%>

								<aui:option
									label="<%= cpTaxCategory.getName(languageId) %>"
									value="<%= cpTaxCategory.getCPTaxCategoryId() %>"
								/>

							<%
							}
							%>

						</aui:select>

						<liferay-portlet:renderURL var="taxCategoriesURL">
							<portlet:param name="commerceAdminModuleKey" value="<%= CommerceConstants.TAXES_COMMERCE_ADMIN_MODULE_KEY %>" />
							<portlet:param name="screenNavigationEntryKey" value="tax-categories" />
						</liferay-portlet:renderURL>

						<a data-senna-off target="_parent" href="<%= taxCategoriesURL %>"><liferay-ui:message key="manage-tax-categories" /></a>
					</div>

					<div class="col-md-6">
						<aui:input name="rate" suffix="<%= commerceTaxFixedRateAddressRelsDisplayContext.getCommerceCurrencyCode() %>" />
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<aui:select label="country" name="commerceCountryId" showEmptyOption="<%= true %>">

							<%
							List<CommerceCountry> commerceCountries = commerceTaxFixedRateAddressRelsDisplayContext.getCommerceCountries();

							for (CommerceCountry commerceCountry : commerceCountries) {
							%>

								<aui:option
									label="<%= commerceCountry.getName(languageId) %>"
									selected="<%= (commerceTaxFixedRateAddressRel != null) && (commerceTaxFixedRateAddressRel.getCommerceCountryId() == commerceCountry.getCommerceCountryId()) %>"
									value="<%= commerceCountry.getCommerceCountryId() %>"
								/>

							<%
							}
							%>

						</aui:select>
					</div>

					<div class="col-md-4">
						<aui:select label="region" name="commerceRegionId" showEmptyOption="<%= true %>">

							<%
							List<CommerceRegion> commerceRegions = commerceTaxFixedRateAddressRelsDisplayContext.getCommerceRegions();

							for (CommerceRegion commerceRegion : commerceRegions) {
							%>

								<aui:option
									label="<%= commerceRegion.getName() %>"
									selected="<%= (commerceTaxFixedRateAddressRel != null) && (commerceTaxFixedRateAddressRel.getCommerceRegionId() == commerceRegion.getCommerceRegionId()) %>"
									value="<%= commerceRegion.getCommerceRegionId() %>"
								/>

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