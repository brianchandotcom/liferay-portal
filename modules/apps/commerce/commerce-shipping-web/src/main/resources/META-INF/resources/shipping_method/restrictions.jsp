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
CommerceShippingMethodsDisplayContext commerceShippingMethodsDisplayContext = (CommerceShippingMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceChannelId = commerceShippingMethodsDisplayContext.getCommerceChannelId();
%>

<portlet:actionURL name="/commerce_channels/edit_commerce_shipping_method_address_restriction" var="editCommerceShippingMethodAddressRestrictionActionURL" />

<aui:form action="<%= editCommerceShippingMethodAddressRestrictionActionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />

	<clay:data-set-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceChannelId", String.valueOf(commerceChannelId)
			).build()
		%>'
		dataProviderKey="<%= CommerceShippingRestrictionsPageClayTable.NAME %>"
		formId="fm"
		id="<%= CommerceShippingRestrictionsPageClayTable.NAME %>"
		itemsPerPage="<%= commerceShippingMethodsDisplayContext.getCountriesCount() %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= currentURLObj %>"
		selectedItemsKey="countryId"
		showPagination="<%= false %>"
	/>
</aui:form>