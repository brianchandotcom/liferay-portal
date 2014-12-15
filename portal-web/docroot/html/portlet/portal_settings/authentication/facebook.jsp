<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
boolean facebookConnectAuthEnabled = FacebookConnectUtil.isEnabled(company.getCompanyId());
boolean facebookConnectVerifiedAccountRequired = FacebookConnectUtil.isVerifiedAccountRequired(company.getCompanyId());
String facebookConnectAppId = FacebookConnectUtil.getAppId(company.getCompanyId());

String facebookConnectAppSecret = FacebookConnectUtil.getAppSecret(company.getCompanyId());

if (Validator.isNotNull(facebookConnectAppSecret)) {
	facebookConnectAppSecret = Portal.TEMP_OBFUSCATION_VALUE;
}

String facebookConnectGraphURL = FacebookConnectUtil.getGraphURL(company.getCompanyId());
String facebookConnectOauthAuthURL = FacebookConnectUtil.getAuthURL(company.getCompanyId());
String facebookConnectOauthTokenURL = FacebookConnectUtil.getAccessTokenURL(company.getCompanyId());
String facebookConnectRedirectURL = FacebookConnectUtil.getRedirectURL(company.getCompanyId());
%>

<aui:fieldset>
	<aui:input label="enabled" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= facebookConnectAuthEnabled %>" />

	<aui:input label="verified-account-required" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_VERIFIED_ACCOUNT_REQUIRED + "--" %>' type="checkbox" value="<%= facebookConnectVerifiedAccountRequired %>" />

	<aui:input cssClass="lfr-input-text-container" label="application-id" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_APP_ID + "--" %>' type="text" value="<%= facebookConnectAppId %>" />

	<aui:input cssClass="lfr-input-text-container" label="application-secret" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_APP_SECRET + "--" %>' type="password" value="<%= facebookConnectAppSecret %>" />

	<aui:input cssClass="lfr-input-text-container" label="graph-url" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_GRAPH_URL + "--" %>' type="text" value="<%= facebookConnectGraphURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="oauth-authentication-url" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_OAUTH_AUTH_URL + "--" %>' type="text" value="<%= facebookConnectOauthAuthURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="oauth-token-url" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_OAUTH_TOKEN_URL + "--" %>' type="text" value="<%= facebookConnectOauthTokenURL %>" />

	<aui:input cssClass="lfr-input-text-container" label="redirect-url" name='<%= "settings--" + PropsKeys.FACEBOOK_CONNECT_OAUTH_REDIRECT_URL + "--" %>' type="text" value="<%= facebookConnectRedirectURL %>" />
</aui:fieldset>