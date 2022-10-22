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
long companyId = ParamUtil.getLong(request, "preferencesCompanyId");

PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(companyId);

PortletPreferences systemPortletPreferences = PrefsPropsUtil.getPreferences(0L);

BiFunction<String, Boolean, String> biFunction = (key, usePropsUtil) -> companyPortletPreferences.getValue(key, systemPortletPreferences.getValue(key, usePropsUtil ? PropsUtil.get(key) : StringPool.BLANK));
%>

<aui:fieldset>
	<aui:input name="preferencesCompanyId" type="hidden" value="<%= companyId %>" />

	<aui:input cssClass="lfr-input-text-container" label="incoming-pop-server" name="pop3Host" type="text" value="<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_POP3_HOST, permissionChecker.isOmniadmin()) %>" />

	<aui:input cssClass="lfr-input-text-container" label="incoming-port" name="pop3Port" type="text" value="<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_POP3_PORT, permissionChecker.isOmniadmin()) %>" />

	<aui:input label="use-a-secure-network-connection" name="pop3Secure" type="checkbox" value='<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_STORE_PROTOCOL, true).equals("pop3s") %>' />

	<aui:input autocomplete="new-password" cssClass="lfr-input-text-container" label="user-name" name="pop3User" type="text" value="<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_POP3_USER, permissionChecker.isOmniadmin()) %>" />

	<%
	String pop3Password = biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_POP3_PASSWORD, true);

	if (Validator.isNotNull(pop3Password)) {
		pop3Password = Portal.TEMP_OBFUSCATION_VALUE;
	}
	%>

	<aui:input autocomplete="new-password" cssClass="lfr-input-text-container" label="password" name="pop3Password" type="password" value="<%= pop3Password %>" />

	<aui:input cssClass="lfr-input-text-container" label="outgoing-smtp-server" name="smtpHost" type="text" value="<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_HOST, permissionChecker.isOmniadmin()) %>" />

	<aui:input cssClass="lfr-input-text-container" label="outgoing-port" name="smtpPort" type="text" value="<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT, permissionChecker.isOmniadmin()) %>" />

	<aui:input label="use-a-secure-network-connection" name="smtpSecure" type="checkbox" value='<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL, true).equals("smtps") %>' />

	<aui:input label="enable-starttls" name="smtpStartTLSEnable" type="checkbox" value="<%= GetterUtil.getBoolean(biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_STARTTLS_ENABLE, true)) %>" />

	<aui:input autocomplete="new-password" cssClass="lfr-input-text-container" label="user-name" name="smtpUser" type="text" value="<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER, permissionChecker.isOmniadmin()) %>" />

	<%
	String smtpPassword = biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_SMTP_PASSWORD, true);

	if (Validator.isNotNull(smtpPassword)) {
		smtpPassword = Portal.TEMP_OBFUSCATION_VALUE;
	}
	%>

	<aui:input autocomplete="new-password" cssClass="lfr-input-text-container" label="password" name="smtpPassword" type="password" value="<%= smtpPassword %>" />

	<aui:input cssClass="lfr-textarea-container" label="manually-specify-additional-javamail-properties-to-override-the-above-configuration" name="advancedProperties" type="textarea" value="<%= biFunction.apply(PropsKeys.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES, true) %>" />
</aui:fieldset>