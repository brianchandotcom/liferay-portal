<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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