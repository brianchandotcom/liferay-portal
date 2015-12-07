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

<%@ include file="/com.liferay.portal.settings.web/init.jsp" %>

<%
NtlmConfiguration ntlmConfiguration = ConfigurationFactoryUtil.getConfiguration(NtlmConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), "ntlm--", new CompanyServiceSettingsLocator(company.getCompanyId(), NtlmConstants.SERVICE_NAME)));

boolean enabled = ntlmConfiguration.enabled();
String domainController = ntlmConfiguration.domainController();
String domainControllerName = ntlmConfiguration.domainControllerName();
String domain = ntlmConfiguration.domain();
String serviceAccount = ntlmConfiguration.serviceAccount();

String servicePassword = ntlmConfiguration.servicePassword();

if (Validator.isNotNull(servicePassword)) {
	servicePassword = Portal.TEMP_OBFUSCATION_VALUE;
}
%>

<aui:fieldset>

	<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/ntlm" />

	<aui:input label="enabled" name='<%= "ntlm--enabled" %>' type="checkbox" value="<%= enabled %>" />

	<aui:input cssClass="lfr-input-text-container" label="domain-controller" name='<%= "ntlm--domainController" %>' type="text" value="<%= domainController %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="domain-controller-name-help" label="domain-controller-name" name='<%= "ntlm--domainControllerName" %>' type="text" value="<%= domainControllerName %>" />

	<aui:input cssClass="lfr-input-text-container" label="domain" name='<%= "ntlm--domain" %>' type="text" value="<%= domain %>" />

	<aui:input cssClass="lfr-input-text-container" label="service-account" name='<%= "ntlm--serviceAccount" %>' type="text" value="<%= serviceAccount %>" />

	<aui:input cssClass="lfr-input-text-container" label="service-password" name='<%= "ntlm--servicePassword" %>' type="password" value="<%= servicePassword %>" />
</aui:fieldset>