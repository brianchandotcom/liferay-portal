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
CommerceAccountAddressAdminDisplayContext commerceAccountAddressAdminDisplayContext = (CommerceAccountAddressAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAddress commerceAddress = commerceAccountAddressAdminDisplayContext.getCommerceAddress();

int selectedType = CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING;

if (commerceAddress != null) {
	selectedType = commerceAddress.getType();
}
%>

<portlet:actionURL name="/commerce_account_admin/edit_commerce_address" var="editCommerceAddressActionURL" />

<div class="container-fluid container-fluid-max-xl mt-4 sheet">
	<aui:form action="<%= editCommerceAddressActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceAddress == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="commerceAddressId" type="hidden" value="<%= commerceAccountAddressAdminDisplayContext.getCommerceAddressId() %>" />
		<aui:input name="commerceAccountId" type="hidden" value="<%= (commerceAddress == null) ? commerceAccountAddressAdminDisplayContext.getCommerceAccountId() : commerceAddress.getClassPK() %>" />

		<aui:model-context bean="<%= commerceAddress %>" model="<%= CommerceAddress.class %>" />

		<liferay-ui:error exception="<%= CommerceAddressCityException.class %>" message="please-enter-a-valid-city" />
		<liferay-ui:error exception="<%= CommerceAddressCountryException.class %>" message="please-select-a-country" />
		<liferay-ui:error exception="<%= CommerceAddressStreetException.class %>" message="please-enter-a-valid-street" />
		<liferay-ui:error exception="<%= CommerceAddressZipException.class %>" message="please-enter-a-valid-zip" />

		<div class="lfr-form-content">
			<aui:fieldset cssClass="addresses">
				<div class="row">
					<div class="col-md-6">
						<aui:input name="name" />

						<aui:input name="description" />

						<aui:input name="street1" />

						<aui:input name="street2" />

						<aui:input name="street3" />
					</div>

					<div class="col-md-6">
						<aui:input name="city" />

						<aui:input label="postal-code" name="zip" />

						<aui:select label="country" name="countryId" showEmptyOption="<%= true %>">

							<%
							for (Country country : commerceAccountAddressAdminDisplayContext.getCountries()) {
							%>

								<aui:option label="<%= country.getTitle(locale) %>" selected="<%= (commerceAddress != null) && (commerceAddress.getCountryId() == country.getCountryId()) %>" value="<%= country.getCountryId() %>" />

							<%
							}
							%>

						</aui:select>

						<aui:select label="region" name="regionId" showEmptyOption="<%= true %>">

							<%
							for (Region region : commerceAccountAddressAdminDisplayContext.getRegions()) {
							%>

								<aui:option label="<%= region.getName() %>" selected="<%= (commerceAddress != null) && (commerceAddress.getRegionId() == region.getRegionId()) %>" value="<%= region.getRegionId() %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input name="phoneNumber" />

						<aui:select label="type" name="type" showEmptyOption="<%= false %>">

							<%
							for (int type : CommerceAddressConstants.ADDRESS_TYPES) {
							%>

							<aui:option label="<%= CommerceAddressConstants.getAddressTypeLabel(type) %>" selected="<%= type == selectedType %>" value="<%= type %>" />

							<%
							}
							%>

						</aui:select>
					</aui:fieldset>
				</div>

				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" />

					<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
				</aui:button-row>
			</div>
		</div>
	</aui:form>
</div>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />countryId',
			selectData: function (callback) {
				Liferay.Service(
					'/commerce.commercecountrymanagerimpl/get-billing-countries',
					{
						active: true,
						billingAllowed: true,
						companyId: <%= company.getCompanyId() %>,
					},
					callback
				);
			},
			selectDesc: 'nameCurrentValue',
			selectId: 'countryId',
			selectSort: '<%= true %>',
			selectVal:
				'<%= commerceAccountAddressAdminDisplayContext.getCountryId() %>',
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
			selectVal:
				'<%= commerceAccountAddressAdminDisplayContext.getRegionId() %>',
		},
	]);
</aui:script>