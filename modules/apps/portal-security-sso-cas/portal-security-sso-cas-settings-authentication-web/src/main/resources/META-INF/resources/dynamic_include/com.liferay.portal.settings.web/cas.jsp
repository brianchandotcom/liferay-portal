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
CASConfiguration casConfiguration = ConfigurationProviderUtil.getConfiguration(CASConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE, new CompanyServiceSettingsLocator(company.getCompanyId(), CASConstants.SERVICE_NAME)));

boolean enabled = casConfiguration.enabled();
boolean importFromLDAP = casConfiguration.importFromLDAP();
String loginURL = casConfiguration.loginURL();
boolean logoutOnSessionExpiration = casConfiguration.logoutOnSessionExpiration();
String logoutURL = casConfiguration.logoutURL();
String serverName = casConfiguration.serverName();
String serverURL = casConfiguration.serverURL();
String serviceURL = casConfiguration.serviceURL();
String noSuchUserRedirectURL = casConfiguration.noSuchUserRedirectURL();
%>

<aui:fieldset>
	<liferay-ui:error key="casServerNameInvalid" message="the-cas-server-name-is-invalid" />
	<liferay-ui:error key="casServerURLInvalid" message="the-cas-server-url-is-invalid" />
	<liferay-ui:error key="casServiceURLInvalid" message="the-cas-service-url-is-invalid" />
	<liferay-ui:error key="casLoginURLInvalid" message="the-cas-login-url-is-invalid" />
	<liferay-ui:error key="casLogoutURLInvalid" message="the-cas-logout-url-is-invalid" />
	<liferay-ui:error key="casNoSuchUserURLInvalid" message="the-cas-no-such-user-url-is-invalid" />

	<aui:input label="enabled" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= enabled %>" />

	<aui:input helpMessage="import-cas-users-from-ldap-help" label="import-cas-users-from-ldap" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "importFromLDAP" %>' type="checkbox" value="<%= importFromLDAP %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-login-url-help" label="login-url" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "loginURL" %>' type="text" value="<%= loginURL %>" />

	<aui:input helpMessage="cas-logout-on-session-expiration-help" label="cas-logout-on-session-expiration" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "logoutOnSessionExpiration" %>' type="checkbox" value="<%= logoutOnSessionExpiration %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-logout-url-help" label="logout-url" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "logoutURL" %>' type="text" value="<%= logoutURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-server-name-help" label="server-name" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "serverName" %>' type="text" value="<%= serverName %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-server-url-help" label="server-url" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "serverURL" %>' type="text" value="<%= serverURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-service-url-help" label="service-url" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "serviceURL" %>' type="text" value="<%= serviceURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-no-such-user-redirect-url-help" label="no-such-user-redirect-url" name='<%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE + "noSuchUserRedirectURL" %>' type="text" value="<%= noSuchUserRedirectURL %>" />
</aui:fieldset>

<aui:script>
	window['<portlet:namespace />testCasSettings'] = function () {
		var data = {};

		data.<portlet:namespace />casLoginURL =
			document.<portlet:namespace />fm[
				'<portlet:namespace /><%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE %>loginURL'
			].value;
		data.<portlet:namespace />casLogoutURL =
			document.<portlet:namespace />fm[
				'<portlet:namespace /><%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE %>logoutURL'
			].value;
		data.<portlet:namespace />casServerURL =
			document.<portlet:namespace />fm[
				'<portlet:namespace /><%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE %>serverURL'
			].value;
		data.<portlet:namespace />casServiceURL =
			document.<portlet:namespace />fm[
				'<portlet:namespace /><%= PortalSettingsCASConstants.FORM_PARAMETER_NAMESPACE %>serviceURL'
			].value;

		var baseUrl =
			'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/portal_settings_authentication_cas/test_cas" /></portlet:renderURL>';

		var url = new URL(baseUrl);

		var searchParams = Liferay.Util.objectToURLSearchParams(data);

		searchParams.forEach((value, key) => {
			url.searchParams.append(key, value);
		});

		Liferay.Util.fetch(url)
			.then((response) => {
				return response.text();
			})
			.then((text) => {
				Liferay.Util.openModal({
					bodyHTML: text,
					size: 'full-screen',
					title: '<%= UnicodeLanguageUtil.get(request, "cas") %>',
				});
			})
			.catch((error) => {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'an-unexpected-system-error-occurred'
					),
					type: 'danger',
				});
			});
	};
</aui:script>