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

CommerceCurrencyConfiguration commerceCurrencyConfiguration = commerceCurrenciesDisplayContext.getCommerceCurrencyConfiguration();

boolean enableAutoUpdate = commerceCurrencyConfiguration.enableAutoUpdate();
%>

<c:if test="<%= commerceCurrenciesDisplayContext.hasManageCommerceCurrencyPermission() %>">
	<div class="mt-4" id="<portlet:namespace />exchangeRateContainer">
		<portlet:actionURL name="/commerce_currency/edit_exchange_rate" var="editExchangeRateActionURL" />

		<aui:form action="<%= editExchangeRateActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="exchangeRateConfiguration--groupId--" type="hidden" value="<%= scopeGroupId %>" />

			<aui:fieldset-group cssClass="mb-4" markupView="lexicon">
				<aui:fieldset>
					<aui:select id="exchangeRateConfiguration--defaultExchangeRateProviderKey--" label="exchange-rate-provider" name="exchangeRateConfiguration--defaultExchangeRateProviderKey--" showEmptyOption="<%= true %>">

						<%
						for (String exchangeRateProviderKey : commerceCurrenciesDisplayContext.getExchangeRateProviderKeys()) {
						%>

							<aui:option label="<%= LanguageUtil.get(request, exchangeRateProviderKey) %>" selected="<%= exchangeRateProviderKey.equals(commerceCurrencyConfiguration.defaultExchangeRateProviderKey()) %>" value="<%= exchangeRateProviderKey %>" />

						<%
						}
						%>

					</aui:select>

					<aui:input id="exchangeRateConfiguration--enableAutoUpdate--" inlineLabel="right" labelCssClass="simple-toggle-switch" name="exchangeRateConfiguration--enableAutoUpdate--" type="toggle-switch" value="<%= enableAutoUpdate %>" />
				</aui:fieldset>

				<aui:button name="saveButton" type="submit" value="save" />
			</aui:fieldset-group>
		</aui:form>
	</div>
</c:if>