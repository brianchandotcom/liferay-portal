<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	<frontend-data-set:classic-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceChannelId", String.valueOf(commerceChannelId)
			).build()
		%>'
		dataProviderKey="<%= CommerceShippingFDSNames.SHIPPING_RESTRICTIONS %>"
		formName="fm"
		id="<%= CommerceShippingFDSNames.SHIPPING_RESTRICTIONS %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="countryId"
	/>
</aui:form>