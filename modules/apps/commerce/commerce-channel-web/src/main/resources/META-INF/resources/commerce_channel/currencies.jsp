<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelCurrencyDisplayContext commerceChannelCurrenciesDisplayContext = (CommerceChannelCurrencyDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_channels/edit_commerce_channel_currency" var="editCommerceChannelCurrencyActionURL" />

<aui:form action="<%= editCommerceChannelCurrencyActionURL %>" cssClass="hide" name="addCommerceChannelCurrencyFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelCurrenciesDisplayContext.getCommerceChannelId() %>" />
	<aui:input name="currencyIds" type="hidden" value="" />
</aui:form>

<frontend-data-set:classic-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"commerceChannelId", String.valueOf(commerceChannelCurrenciesDisplayContext.getCommerceChannelId())
		).build()
	%>'
	creationMenu="<%= commerceChannelCurrenciesDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CommerceChannelFDSNames.COMMERCE_CURRENCIES %>"
	id="<%= CommerceChannelFDSNames.COMMERCE_CURRENCIES %>"
	itemsPerPage="<%= 10 %>"
	selectedItemsKey="currencyId"
/>

<liferay-frontend:component
	context="<%= commerceChannelCurrenciesDisplayContext.getJSContext() %>"
	module="{commerceChannelCurrency} from commerce-channel-web"
/>