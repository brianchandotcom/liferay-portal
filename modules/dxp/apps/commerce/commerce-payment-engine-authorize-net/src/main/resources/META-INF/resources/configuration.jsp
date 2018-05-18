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