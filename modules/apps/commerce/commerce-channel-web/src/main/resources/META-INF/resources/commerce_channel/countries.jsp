<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelCountryDisplayContext commerceChannelCountriesDisplayContext = (CommerceChannelCountryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_channels/edit_commerce_channel_country" var="editCommerceChannelCountryActionURL" />

<aui:form action="<%= editCommerceChannelCountryActionURL %>" cssClass="hide" name="addCommerceChannelCountryFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelCountriesDisplayContext.getCommerceChannelId() %>" />
	<aui:input name="countryIds" type="hidden" value="" />
</aui:form>

<frontend-data-set:classic-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"commerceChannelId", String.valueOf(commerceChannelCountriesDisplayContext.getCommerceChannelId())
		).build()
	%>'
	creationMenu="<%= commerceChannelCountriesDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CommerceChannelFDSNames.CHANNEL_COUNTRIES %>"
	id="<%= CommerceChannelFDSNames.CHANNEL_COUNTRIES %>"
	itemsPerPage="<%= 10 %>"
	selectedItemsKey="countryId"
/>

<liferay-frontend:component
	context="<%= commerceChannelCountriesDisplayContext.getJSContext() %>"
	module="{commerceChannelCountry} from commerce-channel-web"
/>