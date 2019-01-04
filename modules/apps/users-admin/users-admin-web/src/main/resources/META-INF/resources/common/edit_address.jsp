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
String className = ParamUtil.getString(request, "className");

long addressId = ParamUtil.getLong(request, "primaryKey", 0L);

Address address = null;

long countryId = 0L;
long regionId = 0L;

if (addressId > 0L) {
	address = AddressServiceUtil.getAddress(addressId);

	countryId = address.getCountryId();
	regionId = address.getRegionId();
}
%>

<aui:form cssClass="modal-body" name="fm">
	<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= addressId %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.ADDRESS %>" />

	<aui:input checked="<%= (address != null)? address.isPrimary() : false %>" id="addressPrimary" label="make-primary" name="addressPrimary" type="checkbox" />

	<aui:select label="type" listType="<%= className + ListTypeConstants.ADDRESS %>" name='<%= "addressTypeId" %>' />

	<aui:input fieldParam='<%= "addressStreet1" %>' id='<%= "addressStreet1" %>' name="street1" required="<%= true %>" />

	<aui:input fieldParam='<%= "addressStreet2" %>' id='<%= "addressStreet2" %>' name="street2" />

	<aui:input fieldParam='<%= "addressStreet3" %>' id='<%= "addressStreet3" %>' name="street3" />

	<aui:input fieldParam='<%= "addressCity" %>' id='<%= "addressCity" %>' name="city" required="<%= true %>" />

	<aui:select label="country" name='<%= "addressCountryId" %>' />

	<aui:select label="region" name='<%= "addressRegionId" %>' />

	<div class="form-group">
		<label class="control-label" for="<portlet:namespace />addressZip">
			<liferay-ui:message key="postal-code" />

			<span hidden id="<portlet:namespace />addressZipRequiredWrapper">
				<aui:icon cssClass="reference-mark text-warning" image="asterisk" markupView="lexicon" />

				<span class="hide-accessible"><liferay-ui:message key="required" /></span>
			</span>
		</label>

		<aui:input fieldParam='<%= "addressZip" %>' id='<%= "addressZip" %>' label="" name="zip" />
	</div>

	<aui:input cssClass="mailing-ctrl" fieldParam='<%= "addressMailing" %>' id='<%= "addressMailing" %>' name="mailing" />

	<aui:script use="liferay-address,liferay-dynamic-select">
		new Liferay.DynamicSelect(
			[
				{
					select: '<portlet:namespace />addressCountryId',
					selectData: Liferay.Address.getCountries,
					selectDesc: 'nameCurrentValue',
					selectId: 'countryId',
					selectSort: '<%= true %>',
					selectVal: '<%= countryId %>'
				},
				{
					select: '<portlet:namespace />addressRegionId',
					selectData: Liferay.Address.getRegions,
					selectDesc: 'name',
					selectId: 'regionId',
					selectVal: '<%= regionId %>'
				}
			]
		);
	</aui:script>
</aui:form>

<aui:script use="liferay-form">
	const addressCountry = document.getElementById('<portlet:namespace />addressCountryId');
	const addressZipRequiredWrapper = document.getElementById('<portlet:namespace />addressZipRequiredWrapper');
	const formValidator = Liferay.Form.get('<portlet:namespace />fm').formValidator;

	const rules = formValidator._getAttr('rules');

	function updateAdressZipRequired(required) {
		if (required) {
			addressZipRequiredWrapper.removeAttribute('hidden');
		}
		else {
			addressZipRequiredWrapper.setAttribute('hidden', true);
		}

		rules.<portlet:namespace />addressZip = {required: required};
	}

	function checkCountry(countryId) {
		Liferay.Service(
			'/country/get-country',
			{
				countryId: countryId
			},
			function(response, err) {
				if (err) {
					console.error(err);
				}
				else {
					updateAdressZipRequired(response.zipRequired);
				}
			}
		);
	}

	function handleSelectChange(event) {
		const value = Number(event.currentTarget.value);

		if (value > 0) {
			checkCountry(value);
		}
		else {
			updateAdressZipRequired(false);
		}
	}

	if (addressCountry) {
		addressCountry.addEventListener('change', handleSelectChange);

		<c:if test="<%= countryId > 0 %>">
			checkCountry(<%= countryId %>);
		</c:if>
	}
</aui:script>