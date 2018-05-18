<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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