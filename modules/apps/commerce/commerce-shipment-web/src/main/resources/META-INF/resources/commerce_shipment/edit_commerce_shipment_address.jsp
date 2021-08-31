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
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceShipmentDisplayContext.getCommerceShipment();

CommerceAddress shippingAddress = commerceShipmentDisplayContext.getShippingAddress();
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.format(request, "edit-x", "shipping-address") %>'
>
	<aui:form action="<%= editCommerceShipmentURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="address" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipment.getCommerceShipmentId() %>" />

		<aui:model-context bean="<%= shippingAddress %>" model="<%= CommerceAddress.class %>" />

		<aui:input name="name" />

		<aui:input name="street1" />

		<aui:input name="street2" />

		<aui:input name="street3" />

		<aui:input name="city" />

		<aui:input label="postal-code" name="zip" />

		<aui:select label="country" name="countryId" showEmptyOption="<%= true %>">

			<%
			for (Country country : commerceShipmentDisplayContext.getCountries()) {
			%>

				<aui:option label="<%= country.getTitle(locale) %>" selected="<%= shippingAddress.getCountryId() == country.getCountryId() %>" value="<%= country.getCountryId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="region" name="regionId" showEmptyOption="<%= true %>">

			<%
			for (Region region : commerceShipmentDisplayContext.getRegions(shippingAddress.getCountryId())) {
			%>

				<aui:option label="<%= region.getName() %>" selected="<%= shippingAddress.getRegionId() == region.getRegionId() %>" value="<%= shippingAddress.getRegionId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input name="phoneNumber" />
	</aui:form>
</commerce-ui:modal-content>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />countryId',
			selectData: function (callback) {
				Liferay.Service(
					'/commerce.commercecountrymanagerimpl/get-shipping-countries',
					{
						active: true,
						companyId: <%= company.getCompanyId() %>,
						shippingAllowed: true,
					},
					callback
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'countryId',
			selectSort: '<%= true %>',
			selectVal: '<%= shippingAddress.getCountryId() %>',
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
			selectVal: '<%= shippingAddress.getRegionId() %>',
		},
	]);
</aui:script>