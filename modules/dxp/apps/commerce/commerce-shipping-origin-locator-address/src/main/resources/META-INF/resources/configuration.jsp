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
AddressCommerceShippingOriginLocatorGroupServiceConfiguration addressCommerceShippingOriginLocatorGroupServiceConfiguration = (AddressCommerceShippingOriginLocatorGroupServiceConfiguration)request.getAttribute(AddressCommerceShippingOriginLocatorGroupServiceConfiguration.class.getName());
%>

<div class="row">
	<div class="col-md-6">
		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--name--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--name--" %>' value="<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.name() %>" />

		<aui:select id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--commerceCountryId--" %>' label="country" name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--commerceCountryId--" %>' />

		<aui:select id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--commerceRegionId--" %>' label="region" name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--commerceRegionId--" %>' />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--zip--" %>' label="postal-code" name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--zip--" %>' value="<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.zip() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--city--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--city--" %>' value="<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.city() %>" />
	</div>

	<div class="col-md-6">
		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street1--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street1--" %>' value="<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.street1() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street2--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street2--" %>' value="<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.street2() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street3--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street3--" %>' value="<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.street3() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--latitude--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--latitude--" %>' value="<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.latitude() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--longitude--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--longitude--" %>' value="<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.longitude() %>" />
	</div>
</div>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace /><%= AddressCommerceShippingOriginLocator.KEY %>Origin--commerceCountryId--',
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
				selectVal: '<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.commerceCountryId() %>'
			},
			{
				select: '<portlet:namespace /><%= AddressCommerceShippingOriginLocator.KEY %>Origin--commerceRegionId--',
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
				selectVal: '<%= addressCommerceShippingOriginLocatorGroupServiceConfiguration.commerceRegionId() %>'
			}
		]
	);
</aui:script>