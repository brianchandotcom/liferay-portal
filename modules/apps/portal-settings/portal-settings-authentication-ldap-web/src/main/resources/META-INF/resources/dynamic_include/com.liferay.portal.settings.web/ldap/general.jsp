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

<%@ include file="/dynamic_include/init.jsp" %>

<%
ConfigurationProvider<LDAPAuthConfiguration> ldapAuthConfigurationProvider = ConfigurationProviderUtil.getLDAPAuthConfigurationProvider();

LDAPAuthConfiguration ldapAuthConfiguration = ldapAuthConfigurationProvider.getConfiguration(themeDisplay.getCompanyId());

boolean ldapAuthEnabled = ldapAuthConfiguration.enabled();
String ldapAuthMethod = ldapAuthConfiguration.method();
boolean ldapAuthRequired = ldapAuthConfiguration.required();
String ldapPasswordEncryptionAlgorithm = ldapAuthConfiguration.passwordEncryptionAlgorithm();
boolean ldapPasswordPolicyEnabled = ldapAuthConfiguration.passwordPolicyEnabled();
%>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= LDAPSettingsConstants.CMD_UPDATE_AUTH %>" />

	<liferay-ui:error key="ldapExportAndImportOnPasswordAutogeneration" message="ldap-export-must-not-be-enabled-when-autogeneration-of-user-passwords-is-enabled-for-ldap-import" />

	<aui:input label="enabled" name='<%= "ldap--" + LDAPConstants.AUTH_ENABLED + "--" %>' type="checkbox" value="<%= ldapAuthEnabled %>" />

	<aui:input label="required" name='<%= "ldap--" + LDAPConstants.AUTH_REQUIRED + "--" %>' type="checkbox" value="<%= ldapAuthRequired %>" />

	<aui:input helpMessage="ldap-password-policy-help" label="use-ldap-password-policy" name='<%= "ldap--" + LDAPConstants.PASSWORD_POLICY_ENABLED + "--" %>' type="checkbox" value="<%= ldapPasswordPolicyEnabled %>" />

	<aui:select label="method" name='<%= "ldap--" + LDAPConstants.AUTH_METHOD + "--" %>' value="<%= ldapAuthMethod %>">
		<aui:option label="bind" value="<%= LDAPConstants.AUTH_METHOD_BIND %>" />
		<aui:option label="password-compare" value="<%= LDAPConstants.AUTH_METHOD_PASSWORD_COMPARE %>" />
	</aui:select>

	<aui:select label="password-encryption-algorithm" name='<%= "ldap--" + LDAPConstants.PASSWORD_ENCRYPTION_ALGORITHM + "--" %>' value="<%= ldapPasswordEncryptionAlgorithm %>">
		<aui:option label="bcrypt" value="<%= LDAPSettingsConstants.BCRYPT %>" />
		<aui:option label="md2" value="<%= LDAPSettingsConstants.MD2 %>" />
		<aui:option label="md5" value="<%= LDAPSettingsConstants.MD5 %>" />
		<aui:option label="none" value="<%= LDAPSettingsConstants.NONE %>" />
		<aui:option label="sha" value="<%= LDAPSettingsConstants.SHA %>" />
		<aui:option label="sha-256" value="<%= LDAPSettingsConstants.SHA_256 %>" />
		<aui:option label="sha-384" value="<%= LDAPSettingsConstants.SHA_384 %>" />
		<aui:option label="ssha" value="<%= LDAPSettingsConstants.SSHA %>" />
		<aui:option label="ufc-crypt" value="<%= LDAPSettingsConstants.UFC_CRYPT %>" />
	</aui:select>
</aui:fieldset>