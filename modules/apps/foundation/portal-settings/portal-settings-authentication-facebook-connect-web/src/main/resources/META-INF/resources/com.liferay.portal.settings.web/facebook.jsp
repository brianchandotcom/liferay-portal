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
FacebookConnectConfiguration facebookConnectConfiguration = ConfigurationProviderUtil.getConfiguration(FacebookConnectConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE, new CompanyServiceSettingsLocator(company.getCompanyId(), FacebookConnectConstants.SERVICE_NAME)));

boolean authEnabled = facebookConnectConfiguration.enabled();
boolean verifiedAccountRequired = facebookConnectConfiguration.verifiedAccountRequired();
String appId = facebookConnectConfiguration.appId();

String appSecret = facebookConnectConfiguration.appSecret();

if (Validator.isNotNull(appSecret)) {
	appSecret = Portal.TEMP_OBFUSCATION_VALUE;
}

String graphURL = facebookConnectConfiguration.graphURL();
String oauthAuthURL = facebookConnectConfiguration.oauthAuthURL();
String oauthTokenURL = facebookConnectConfiguration.oauthTokenURL();
String oauthRedirectURL = facebookConnectConfiguration.oauthRedirectURL();
%>

<liferay-ui:error key="facebookConnectGraphURLInvalid" message="the-facebook-connect-graph-url-is-invalid" />
<liferay-ui:error key="facebookConnectOauthAuthURLInvalid" message="the-facebook-connect-oauth-auth-url-is-invalid" />
<liferay-ui:error key="facebookConnectOauthRedirectURLInvalid" message="the-facebook-connect-oauth-redirect-url-is-invalid" />
<liferay-ui:error key="facebookConnectOauthTokenURLInvalid" message="the-facebook-connect-oauth-token-url-is-invalid" />

<aui:fieldset>
	<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/facebook_connect" />

	<aui:input label="enabled" name='<%= PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= authEnabled %>" />

	<aui:input label="require-verified-account" name='<%= PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE + "verifiedAccountRequired" %>' type="checkbox" value="<%= verifiedAccountRequired %>" />

	<aui:input cssClass="lfr-input-text-container" label="application-id" name='<%= PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE + "appId" %>' type="text" value="<%= appId %>" />

	<aui:input cssClass="lfr-input-text-container" label="application-secret" name='<%= PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE + "appSecret" %>' type="password" value="<%= appSecret %>" />

	<aui:input cssClass="lfr-input-text-container" label="graph-url" name='<%= PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE + "graphURL" %>' type="text" value="<%= graphURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="oauth-authentication-url" name='<%= PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE + "oauthAuthURL" %>' type="text" value="<%= oauthAuthURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="oauth-token-url" name='<%= PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE + "oauthTokenURL" %>' type="text" value="<%= oauthTokenURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="oauth-redirect-url" name='<%= PortalSettingsFacebookConnectConstants.PARAMETER_NAMESPACE + "oauthRedirectURL" %>' type="text" value="<%= oauthRedirectURL %>" />
</aui:fieldset>