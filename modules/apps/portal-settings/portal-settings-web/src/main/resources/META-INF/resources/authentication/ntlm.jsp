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
boolean ntlmAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.NTLM_AUTH_ENABLED, PropsValues.NTLM_AUTH_ENABLED);
String ntlmDomainController = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN_CONTROLLER, PropsValues.NTLM_DOMAIN_CONTROLLER);
String ntlmDomainControllerName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN_CONTROLLER_NAME, PropsValues.NTLM_DOMAIN_CONTROLLER_NAME);
String ntlmDomain = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_DOMAIN, PropsValues.NTLM_DOMAIN);
String ntlmServiceAccount = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_SERVICE_ACCOUNT, PropsValues.NTLM_SERVICE_ACCOUNT);

String ntlmServicePassword = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.NTLM_SERVICE_PASSWORD, PropsValues.NTLM_SERVICE_PASSWORD);

if (Validator.isNotNull(ntlmServicePassword)) {
	ntlmServicePassword = Portal.TEMP_OBFUSCATION_VALUE;
}
%>

<aui:fieldset>
	<aui:input label="enabled" name='<%= "settings--" + PropsKeys.NTLM_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= ntlmAuthEnabled %>" />

	<aui:input cssClass="lfr-input-text-container" label="domain-controller" name='<%= "settings--" + PropsKeys.NTLM_DOMAIN_CONTROLLER + "--" %>' type="text" value="<%= ntlmDomainController %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="domain-controller-name-help" label="domain-controller-name" name='<%= "settings--" + PropsKeys.NTLM_DOMAIN_CONTROLLER_NAME + "--" %>' type="text" value="<%= ntlmDomainControllerName %>" />

	<aui:input cssClass="lfr-input-text-container" label="domain" name='<%= "settings--" + PropsKeys.NTLM_DOMAIN + "--" %>' type="text" value="<%= ntlmDomain %>" />

	<aui:input cssClass="lfr-input-text-container" label="service-account" name='<%= "settings--" + PropsKeys.NTLM_SERVICE_ACCOUNT + "--" %>' type="text" value="<%= ntlmServiceAccount %>" />

	<aui:input cssClass="lfr-input-text-container" label="service-password" name='<%= "settings--" + PropsKeys.NTLM_SERVICE_PASSWORD + "--" %>' type="password" value="<%= ntlmServicePassword %>" />
</aui:fieldset>