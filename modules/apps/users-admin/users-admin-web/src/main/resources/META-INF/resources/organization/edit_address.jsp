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
	<div class="alert alert-info">
		<liferay-ui:message key="postal-code-could-be-required-in-some-countries" />
	</div>

	<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EDIT %>" />
	<aui:input name="primaryKey" type="hidden" value="<%= addressId %>" />
	<aui:input name="listType" type="hidden" value="<%= ListTypeConstants.ADDRESS %>" />

	<aui:input checked="<%= (address != null)? address.isPrimary() : false %>" id="addressPrimary" label="make-primary" name="addressPrimary" type="checkbox" />

	<aui:select label="type" listType="<%= Organization.class.getName() + ListTypeConstants.ADDRESS %>" name='<%= "addressTypeId" %>' />

	<aui:input fieldParam='<%= "addressStreet1" %>' id='<%= "addressStreet1" %>' name="street1" required="<%= true %>" />

	<aui:input fieldParam='<%= "addressStreet2" %>' id='<%= "addressStreet2" %>' name="street2" />

	<aui:input fieldParam='<%= "addressStreet3" %>' id='<%= "addressStreet3" %>' name="street3" />

	<aui:input fieldParam='<%= "addressCity" %>' id='<%= "addressCity" %>' name="city" required="<%= true %>" />

	<aui:select label="country" name='<%= "addressCountryId" %>' />

	<aui:select label="region" name='<%= "addressRegionId" %>' />

	<aui:input fieldParam='<%= "addressZip" %>' id='<%= "addressZip" %>' label="postal-code" name="zip" required="<%= true %>" />

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