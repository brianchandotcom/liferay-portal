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
CommerceContext commerceContext = (CommerceContext)request.getAttribute(CommerceWebKeys.COMMERCE_CONTEXT);

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

String languageId = LanguageUtil.getLanguageId(locale);

CommerceAddressDisplayContext commerceAddressDisplayContext = (CommerceAddressDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAddress commerceAddress = commerceAddressDisplayContext.getCommerceAddress();

long commerceAddressId = commerceAddressDisplayContext.getCommerceAddressId();
long countryId = commerceAddressDisplayContext.getCountryId();
long regionId = commerceAddressDisplayContext.getRegionId();

CommerceAccount commerceAccount = commerceAddressDisplayContext.getCommerceAccount();
%>

<portlet:actionURL name="/commerce_address_content/edit_commerce_address" var="editCommerceAddressActionURL" />

<aui:form action="<%= editCommerceAddressActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceAddress == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="commerceAddressId" type="hidden" value="<%= commerceAddressId %>" />
	<aui:input name="commerceAccountId" type="hidden" value="<%= (commerceAddress == null) ? commerceAccount.getCommerceAccountId() : commerceAddress.getClassPK() %>" />

	<aui:model-context bean="<%= commerceAddress %>" model="<%= CommerceAddress.class %>" />

	<liferay-ui:error exception="<%= CommerceAddressCityException.class %>" message="please-enter-a-valid-city" />
	<liferay-ui:error exception="<%= CommerceAddressCountryException.class %>" message="please-select-a-country" />
	<liferay-ui:error exception="<%= CommerceAddressStreetException.class %>" message="please-enter-a-valid-street" />
	<liferay-ui:error exception="<%= CommerceAddressZipException.class %>" message="please-enter-a-valid-zip" />

	<div class="lfr-form-content">
		<aui:fieldset cssClass="addresses">
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
					for (Country country : commerceAddressDisplayContext.getCountries()) {
					%>

						<aui:option label="<%= country.getTitle(languageId) %>" selected="<%= (commerceAddress != null) && (commerceAddress.getCountryId() == country.getCountryId()) %>" value="<%= country.getCountryId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:select label="region" name="regionId" showEmptyOption="<%= true %>">

					<%
					for (Region region : commerceAddressDisplayContext.getRegions()) {
					%>

						<aui:option label="<%= region.getName() %>" selected="<%= (commerceAddress != null) && (commerceAddress.getRegionId() == region.getRegionId()) %>" value="<%= region.getRegionId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input name="phoneNumber" />

				<aui:input checked="<%= (commerceAddress != null) && ((commerceAddress.getType() == CommerceAddressConstants.ADDRESS_TYPE_BILLING) || (commerceAddress.getType() == CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING)) %>" name="defaultBilling" type="checkbox" />

				<aui:input checked="<%= (commerceAddress != null) && ((commerceAddress.getType() == CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING) || (commerceAddress.getType() == CommerceAddressConstants.ADDRESS_TYPE_SHIPPING)) %>" name="defaultShipping" type="checkbox" />
			</div>
		</aui:fieldset>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />countryId',
			selectData: function (callback) {
				Liferay.Service(
					'/commerce.commercecountrymanagerimpl/get-billing-countries-by-channel-id',
					{
						channelId: <%= commerceContext.getCommerceChannelId() %>,
						end: -1,
						start: -1,
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