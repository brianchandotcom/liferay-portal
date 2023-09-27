<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long companyId = ParamUtil.getLong(request, "preferencesCompanyId");

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(companyId);

PortletPreferences systemPortletPreferences = PrefsPropsUtil.getPreferences(0L);

Function<Boolean, Function<String, String>> basePreferenceFunctionFunction = showPropsValues -> key -> companyPortletPreferences.getValue(key, systemPortletPreferences.getValue(key, showPropsValues ? PropsUtil.get(key) : StringPool.BLANK));

Function<String, String> restrictedPreferenceFunction = basePreferenceFunctionFunction.apply(permissionChecker.isOmniadmin());
Function<String, String> unrestrictedPreferenceFunction = basePreferenceFunctionFunction.apply(true);
%>

<aui:fieldset>
	<aui:input name="preferencesCompanyId" type="hidden" value="<%= companyId %>" />

	<aui:input label="enable-pop-server-notifications" name="popServerNotificationsEnabled" type="checkbox" value="<%= restrictedPreferenceFunction.apply(PropsKeys.POP_SERVER_NOTIFICATIONS_ENABLED) %>" />

	<aui:input cssClass="lfr-input-text-container" label="incoming-pop-server" name="pop3Host" type="text" value="<%= restrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_POP3_HOST) %>" />

	<aui:input cssClass="lfr-input-text-container" label="incoming-port" name="pop3Port" type="text" value="<%= restrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_POP3_PORT) %>" />

	<aui:input label="use-a-secure-network-connection" name="pop3Secure" type="checkbox" value='<%= unrestrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_STORE_PROTOCOL).equals("pop3s") %>' />

	<aui:input autocomplete="new-password" cssClass="lfr-input-text-container" label="user-name" name="pop3User" type="text" value="<%= restrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_POP3_USER) %>" />

	<%
	String pop3Password = unrestrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_POP3_PASSWORD);

	if (Validator.isNotNull(pop3Password)) {
		pop3Password = Portal.TEMP_OBFUSCATION_VALUE;
	}
	%>

	<aui:input autocomplete="new-password" cssClass="lfr-input-text-container" label="password" name="pop3Password" type="password" value="<%= pop3Password %>" />

	<aui:input cssClass="lfr-input-text-container" label="outgoing-smtp-server" name="smtpHost" type="text" value="<%= restrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_HOST) %>" />

	<aui:input cssClass="lfr-input-text-container" label="outgoing-port" name="smtpPort" type="text" value="<%= restrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT) %>" />

	<aui:input label="use-a-secure-network-connection" name="smtpSecure" type="checkbox" value='<%= unrestrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL).equals("smtps") %>' />

	<aui:input label="enable-starttls" name="smtpStartTLSEnable" type="checkbox" value="<%= GetterUtil.getBoolean(unrestrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_STARTTLS_ENABLE)) %>" />

	<aui:input autocomplete="new-password" cssClass="lfr-input-text-container" label="user-name" name="smtpUser" type="text" value="<%= restrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER) %>" />

	<%
	String smtpPassword = unrestrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_PASSWORD);

	if (Validator.isNotNull(smtpPassword)) {
		smtpPassword = Portal.TEMP_OBFUSCATION_VALUE;
	}
	%>

	<aui:input autocomplete="new-password" cssClass="lfr-input-text-container" label="password" name="smtpPassword" type="password" value="<%= smtpPassword %>" />

	<aui:input cssClass="lfr-textarea-container" label="manually-specify-additional-javamail-properties-to-override-the-above-configuration" name="advancedProperties" type="textarea" value="<%= unrestrictedPreferenceFunction.apply(PropsKeys.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES) %>" />
</aui:fieldset>