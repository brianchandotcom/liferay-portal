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
AuthorizeNetCommercePaymentEngineGroupServiceConfiguration authorizeNetCommercePaymentEngineGroupServiceConfiguration = (AuthorizeNetCommercePaymentEngineGroupServiceConfiguration)request.getAttribute(AuthorizeNetCommercePaymentEngineGroupServiceConfiguration.class.getName());
%>

<aui:fieldset-group>
	<aui:fieldset label="authentication">
		<aui:input label="api-login-id" name="settings--apiLoginId--" value="<%= authorizeNetCommercePaymentEngineGroupServiceConfiguration.apiLoginId() %>" />

		<aui:input label="transaction-key" name="settings--transactionKey--" value="<%= authorizeNetCommercePaymentEngineGroupServiceConfiguration.transactionKey() %>" />

		<aui:select name="settings--environment--">

			<%
			for (String environment : AuthorizeNetCommercePaymentEngineConstants.ENVIRONMENTS) {
			%>

				<aui:option label="<%= environment %>" selected="<%= environment.equals(authorizeNetCommercePaymentEngineGroupServiceConfiguration.environment()) %>" value="<%= environment %>" />

			<%
			}
			%>

		</aui:select>
	</aui:fieldset>

	<aui:fieldset label="display">
		<aui:input checked="<%= authorizeNetCommercePaymentEngineGroupServiceConfiguration.showBankAccount() %>" label="show-bank-account" name="settings--showBankAccount--" type="checkbox" />

		<aui:input checked="<%= authorizeNetCommercePaymentEngineGroupServiceConfiguration.showCreditCard() %>" label="show-credit-card" name="settings--showCreditCard--" type="checkbox" />

		<aui:input checked="<%= authorizeNetCommercePaymentEngineGroupServiceConfiguration.showStoreName() %>" label="show-store-name" name="settings--showStoreName--" type="checkbox" />
	</aui:fieldset>

	<aui:fieldset label="security">
		<aui:input checked="<%= authorizeNetCommercePaymentEngineGroupServiceConfiguration.requireCaptcha() %>" label="require-captcha" name="settings--requireCaptcha--" type="checkbox" />

		<aui:input checked="<%= authorizeNetCommercePaymentEngineGroupServiceConfiguration.requireCardCodeVerification() %>" label="require-card-code-verification" name="settings--requireCardCodeVerification--" type="checkbox" />
	</aui:fieldset>
</aui:fieldset-group>