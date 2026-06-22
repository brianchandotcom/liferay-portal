<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
VIESAccountEntryValidatorConfigurationDisplayContext viesAccountEntryValidatorConfigurationDisplayContext = (VIESAccountEntryValidatorConfigurationDisplayContext)request.getAttribute(VIESAccountEntryValidatorConfigurationDisplayContext.class.getName());
%>

<liferay-ui:error key="viesEndpointURLInvalid" message="please-enter-a-valid-url" />

<aui:input label="enabled" name="enabled" type="checkbox" value="<%= viesAccountEntryValidatorConfigurationDisplayContext.isEnabled() %>" />

<aui:input label="vies-endpoint-url" name="viesEndpointURL" value="<%= viesAccountEntryValidatorConfigurationDisplayContext.getVIESEndpointURL() %>" />

<aui:input label="vies-check-interval" min="1" name="checkInterval" type="number" value="<%= viesAccountEntryValidatorConfigurationDisplayContext.getCheckInterval() %>" />

<aui:fieldset label="vies-country-codes">
	<aui:input name="countries" type="hidden" />

	<liferay-ui:input-move-boxes
		leftBoxName="availableCountries"
		leftList="<%= viesAccountEntryValidatorConfigurationDisplayContext.getAvailableCountries() %>"
		leftTitle="available"
		rightBoxName="currentCountries"
		rightList="<%= viesAccountEntryValidatorConfigurationDisplayContext.getCurrentCountries() %>"
		rightTitle="in-use"
	/>
</aui:fieldset>

<liferay-frontend:component
	module="{VIESAccountEntryValidatorConfiguration} from account-validator-vies-web"
/>