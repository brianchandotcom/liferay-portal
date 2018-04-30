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
CommerceCurrenciesDisplayContext commerceCurrenciesDisplayContext = (CommerceCurrenciesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ExchangeRateProviderGroupServiceConfiguration exchangeRateProviderGroupServiceConfiguration = commerceCurrenciesDisplayContext.getExchangeRateProviderGroupServiceConfiguration();

boolean autoUpdate = exchangeRateProviderGroupServiceConfiguration.autoUpdate();
%>

<div class="container-fluid-1280" id="<portlet:namespace />exchangeRateContainer">
	<portlet:actionURL name="editExchangeRate" var="editExchangeRateActionURL" />

	<aui:form action="<%= editExchangeRateActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="exchangeRateConfiguration--groupId--" type="hidden" value="<%= scopeGroupId %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input id="exchangeRateConfiguration--autoUpdate--" name="exchangeRateConfiguration--autoUpdate--" type="checkbox" value="<%= autoUpdate %>" />

				<aui:select id="exchangeRateConfiguration--defaultExchangeRateProviderKey--" label="exchange-rate-provider" name="exchangeRateConfiguration--defaultExchangeRateProviderKey--" showEmptyOption="<%= true %>">

					<%
					for (String exchangeRateProviderKey : commerceCurrenciesDisplayContext.getExchangeRateProviderKeys()) {
					%>

						<aui:option label="<%= LanguageUtil.get(request, exchangeRateProviderKey) %>" selected="<%= exchangeRateProviderKey.equals(exchangeRateProviderGroupServiceConfiguration.defaultExchangeRateProviderKey()) %>" value="<%= exchangeRateProviderKey %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button name="saveButton" type="submit" value="save" />
	</aui:form>
</div>