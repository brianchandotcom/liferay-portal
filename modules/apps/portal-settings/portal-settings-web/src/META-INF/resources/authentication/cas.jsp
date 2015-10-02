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
CASConfiguration casConfiguration = ConfigurationFactoryUtil.getConfiguration(
	CASConfiguration.class,
	new ParameterMapSettingsLocator(
		liferayPortletRequest.getParameterMap(),
		new CompanyServiceSettingsLocator(company.getCompanyId(), CASConstants.SERVICE_NAME)));

boolean casAuthEnabled = casConfiguration.enabled();
boolean casImportFromLdap = casConfiguration.importFromLDAP();
String casLoginURL = casConfiguration.loginURL();
boolean casLogoutOnSessionExpiration = casConfiguration.logoutOnSessionExpiration();
String casLogoutURL = casConfiguration.logoutURL();
String casServerName = casConfiguration.serverName();
String casServerURL = casConfiguration.serverURL();
String casServiceURL = casConfiguration.serviceURL();
String casNoSuchUserRedirectURL = casConfiguration.noSuchUserRedirectURL();
%>

<aui:fieldset>
	<liferay-ui:error key="casServerNameInvalid" message="the-cas-server-name-is-invalid" />
	<liferay-ui:error key="casServerURLInvalid" message="the-cas-server-url-is-invalid" />
	<liferay-ui:error key="casServiceURLInvalid" message="the-cas-service-url-is-invalid" />
	<liferay-ui:error key="casLoginURLInvalid" message="the-cas-login-url-is-invalid" />
	<liferay-ui:error key="casLogoutURLInvalid" message="the-cas-logout-url-is-invalid" />
	<liferay-ui:error key="casNoSuchUserURLInvalid" message="the-cas-no-such-user-url-is-invalid" />

	<aui:input label="enabled" name='<%= "cas--" + CASConstants.ENABLED + "--" %>' type="checkbox" value="<%= casAuthEnabled %>" />

	<aui:input helpMessage="import-cas-users-from-ldap-help" label="import-cas-users-from-ldap" name='<%= "cas--" + CASConstants.IMPORT_FROM_LDAP + "--" %>' type="checkbox" value="<%= casImportFromLdap %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-login-url-help" label="login-url" name='<%= "cas--" + CASConstants.LOGIN_URL + "--" %>' type="text" value="<%= casLoginURL %>" />

	<aui:input helpMessage="cas-logout-on-session-expiration-help" label="cas-logout-on-session-expiration" name='<%= "cas--" + CASConstants.LOGOUT_ON_SESSION_EXPIRATION + "--" %>' type="checkbox" value="<%= casLogoutOnSessionExpiration %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-logout-url-help" label="logout-url" name='<%= "cas--" + CASConstants.LOGOUT_URL + "--" %>' type="text" value="<%= casLogoutURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-server-name-help" label="server-name" name='<%= "cas--" + CASConstants.SERVER_NAME + "--" %>' type="text" value="<%= casServerName %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-server-url-help" label="server-url" name='<%= "cas--" + CASConstants.SERVER_URL + "--" %>' type="text" value="<%= casServerURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-service-url-help" label="service-url" name='<%= "cas--" + CASConstants.SERVICE_URL + "--" %>' type="text" value="<%= casServiceURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-no-such-user-redirect-url-help" label="no-such-user-redirect-url" name='<%= "cas--" + CASConstants.NO_SUCH_USER_REDIRECT_URL + "--" %>' type="text" value="<%= casNoSuchUserRedirectURL %>" />

	<aui:button-row>

		<%
		String taglibOnClick = renderResponse.getNamespace() + "testCasSettings();";
		%>

		<aui:button onClick="<%= taglibOnClick %>" value="test-cas-configuration" />
	</aui:button-row>
</aui:fieldset>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />testCasSettings',
		function() {
			var A = AUI();

			var data = {};

			data.<portlet:namespace />casLoginURL = document.<portlet:namespace />fm['<portlet:namespace /><%= "cas--" + CASConstants.LOGIN_URL + "--" %>'].value;
			data.<portlet:namespace />casLogoutURL = document.<portlet:namespace />fm['<portlet:namespace /><%= "cas--" + CASConstants.LOGOUT_URL + "--" %>'].value;
			data.<portlet:namespace />casServerURL = document.<portlet:namespace />fm['<portlet:namespace /><%= "cas--" + CASConstants.SERVER_URL + "--" %>'].value;
			data.<portlet:namespace />casServiceURL = document.<portlet:namespace />fm['<portlet:namespace /><%= "cas--" + CASConstants.SERVICE_URL + "--" %>'].value;

			var url = '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/portal_settings/test_cas_configuration" /></portlet:renderURL>';

			var dialog = Liferay.Util.Window.getWindow(
				{
					dialog: {
						destroyOnHide: true
					},
					title: '<%= UnicodeLanguageUtil.get(request, "cas") %>'
				}
			);

			dialog.plug(
				A.Plugin.IO,
				{
					data: data,
					uri: url
				}
			);
		},
		['aui-io-plugin-deprecated', 'aui-io-request', 'liferay-util-window']
	);
</aui:script>