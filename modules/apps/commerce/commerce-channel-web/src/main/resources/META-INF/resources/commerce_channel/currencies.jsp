<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelCommerceCurrencyDisplayContext commerceChannelCommerceCurrenciesDisplayContext = (CommerceChannelCommerceCurrencyDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:alert
	displayType="warning"
	message="changing-the-currency-restrictions-will-affect-existing-open-orders"
/>

<portlet:actionURL name="/commerce_channels/edit_commerce_channel_commerce_currency" var="editCommerceChannelCommerceCurrencyActionURL" />

<aui:form action="<%= editCommerceChannelCommerceCurrencyActionURL %>" cssClass="hide" name="addCommerceChannelCommerceCurrencyFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelCommerceCurrenciesDisplayContext.getCommerceChannelId() %>" />
	<aui:input name="currencyIds" type="hidden" value="" />
</aui:form>

<frontend-data-set:classic-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"commerceChannelId", String.valueOf(commerceChannelCommerceCurrenciesDisplayContext.getCommerceChannelId())
		).build()
	%>'
	creationMenu="<%= commerceChannelCommerceCurrenciesDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CommerceChannelFDSNames.COMMERCE_CURRENCIES %>"
	id="<%= CommerceChannelFDSNames.COMMERCE_CURRENCIES %>"
	selectedItemsKey="currencyId"
/>

<liferay-frontend:component
	context="<%= commerceChannelCommerceCurrenciesDisplayContext.getJSContext() %>"
	module="{commerceChannelCommerceCurrency} from commerce-channel-web"
/>