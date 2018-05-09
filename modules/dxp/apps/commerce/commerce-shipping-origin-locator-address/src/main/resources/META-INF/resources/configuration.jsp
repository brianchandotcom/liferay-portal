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
AddressCommerceShippingOriginLocatorDisplayContext addressCommerceShippingOriginLocatorDisplayContext = (AddressCommerceShippingOriginLocatorDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="row">
	<div class="col-md-6">
		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--name--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--name--" %>' value="<%= addressCommerceShippingOriginLocatorDisplayContext.getName() %>" />

		<aui:select id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--commerceCountryId--" %>' label="country" name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--commerceCountryId--" %>' />

		<aui:select id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--commerceRegionId--" %>' label="region" name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--commerceRegionId--" %>' />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--zip--" %>' label="postal-code" name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--zip--" %>' value="<%= addressCommerceShippingOriginLocatorDisplayContext.getZip() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--city--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--city--" %>' value="<%= addressCommerceShippingOriginLocatorDisplayContext.getCity() %>" />
	</div>

	<div class="col-md-6">
		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street1--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street1--" %>' value="<%= addressCommerceShippingOriginLocatorDisplayContext.getStreet1() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street2--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street2--" %>' value="<%= addressCommerceShippingOriginLocatorDisplayContext.getStreet2() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street3--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--street3--" %>' value="<%= addressCommerceShippingOriginLocatorDisplayContext.getStreet3() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--latitude--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--latitude--" %>' value="<%= addressCommerceShippingOriginLocatorDisplayContext.getLatitude() %>" />

		<aui:input id='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--longitude--" %>' name='<%= AddressCommerceShippingOriginLocator.KEY + "Origin--longitude--" %>' value="<%= addressCommerceShippingOriginLocatorDisplayContext.getLongitude() %>" />
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
				selectVal: '<%= addressCommerceShippingOriginLocatorDisplayContext.getCommerceCountryId() %>'
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
				selectVal: '<%= addressCommerceShippingOriginLocatorDisplayContext.getCommerceRegionId() %>'
			}
		]
	);
</aui:script>