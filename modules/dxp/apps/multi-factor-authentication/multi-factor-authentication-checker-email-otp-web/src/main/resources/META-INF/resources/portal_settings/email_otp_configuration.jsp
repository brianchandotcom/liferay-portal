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
EmailOTPConfiguration emailOTPConfiguration = ConfigurationProviderUtil.getCompanyConfiguration(EmailOTPConfiguration.class, themeDisplay.getCompanyId());
%>

<aui:fieldset>
	<aui:input name="enabled" type="toggle-switch" value="<%= emailOTPConfiguration.enabled() %>" />

	<aui:input helpMessage="retry-timeout-description" label="retry-timeout" name="retryTimeout" type="text" value="<%= emailOTPConfiguration.retryTimeout() %>" />

	<aui:input helpMessage="resend-email-timeout-description" label="resend-email-timeout" name="resendEmailTimeout" type="text" value="<%= emailOTPConfiguration.resendEmailTimeout() %>" />

	<aui:input helpMessage="validation-expiration-time-description" label="validation-expiration-time" name="validationExpirationTime" type="text" value="<%= emailOTPConfiguration.validationExpirationTime() %>" />

	<aui:input helpMessage="email-template-from-sender-description" label="email-template-from-sender" name="emailTemplateFromName" type="text" value="<%= emailOTPConfiguration.emailTemplateFromName() %>" />

	<aui:input helpMessage="email-template-from-description" label="email-template-from" name="emailTemplateFrom" type="text" value="<%= emailOTPConfiguration.emailTemplateFrom() %>" />

	<liferay-frontend:email-notification-settings
		emailBodyLocalizedValuesMap='<%= (LocalizedValuesMap)request.getAttribute("bodyLocalizedValuesMap") %>'
		emailParam="emailOTPTemplate"
		emailSubjectLocalizedValuesMap='<%= (LocalizedValuesMap)request.getAttribute("subjectLocalizedValuesMap") %>'
		showEmailEnabled="<%= false %>"
	/>

	<aui:input helpMessage="failed-attempts-allowed-description" label="failed-attempts-allowed" name="failedAttemptsAllowed" type="text" value="<%= emailOTPConfiguration.failedAttemptsAllowed() %>" />

	<aui:input helpMessage="otp-size-description" label="otp-size" name="otpSize" type="text" value="<%= emailOTPConfiguration.otpSize() %>" />
</aui:fieldset>