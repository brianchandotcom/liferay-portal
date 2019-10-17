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
OpenSSOConfiguration openSSOConfiguration = ConfigurationProviderUtil.getConfiguration(OpenSSOConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE, new CompanyServiceSettingsLocator(company.getCompanyId(), OpenSSOConstants.SERVICE_NAME)));

boolean enabled = openSSOConfiguration.enabled();
boolean importFromLDAP = openSSOConfiguration.importFromLDAP();
String loginURL = openSSOConfiguration.loginURL();
String logoutURL = openSSOConfiguration.logoutURL();
String serviceURL = openSSOConfiguration.serviceURL();
String screenNameAttr = openSSOConfiguration.screenNameAttr();
String emailAddressAttr = openSSOConfiguration.emailAddressAttr();
String firstNameAttr = openSSOConfiguration.firstNameAttr();
String lastNameAttr = openSSOConfiguration.lastNameAttr();
String version = openSSOConfiguration.version();
%>

<aui:fieldset>
	<aui:input id='<%= PortalUtil.generateRandomKey(request, "portal_settings_authentication_opensso") %>' name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/opensso" />

	<aui:input label="enabled" name='<%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= enabled %>" />

	<aui:select label="version" name='<%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE + "version" %>' value="<%= version %>">
		<aui:option label="openam-12" value="<%= OpenSSOConfigurationKeys.VERSION_OPENAM_12 %>" />
		<aui:option label="openam-13" value="<%= OpenSSOConfigurationKeys.VERSION_OPENAM_13 %>" />
	</aui:select>

	<aui:input helpMessage="import-opensso-users-from-ldap-help" label="import-opensso-users-from-ldap" name='<%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE + "importFromLDAP" %>' type="checkbox" value="<%= importFromLDAP %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="login-url-for-opensso-help" label="login-url" name='<%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE + "loginURL" %>' type="text" value="<%= loginURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="logout-url-for-opensso-help" label="logout-url" name='<%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE + "logoutURL" %>' type="text" value="<%= logoutURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="service-url-for-opensso-help" label="service-url" name='<%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE + "serviceURL" %>' type="text" value="<%= serviceURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="mappings-for-opensso-help" label="screen-name-attribute" name='<%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE + "screenNameAttr" %>' type="text" value="<%= screenNameAttr %>" />

	<aui:input cssClass="lfr-input-text-container" label="email-address-attribute" name='<%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE + "emailAddressAttr" %>' type="text" value="<%= emailAddressAttr %>" />

	<%@ include file="/dynamic_include/com.liferay.portal.settings.web/opensso_user_name.jspf" %>
</aui:fieldset>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />testOpenSSOSettings',
		function() {
			var A = AUI();

			var data = {};

			data.<portlet:namespace />openSsoLoginURL =
				document.<portlet:namespace />fm[
					'<portlet:namespace /><%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE %>loginURL'
				].value;
			data.<portlet:namespace />openSsoLogoutURL =
				document.<portlet:namespace />fm[
					'<portlet:namespace /><%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE %>logoutURL'
				].value;
			data.<portlet:namespace />openSsoServiceURL =
				document.<portlet:namespace />fm[
					'<portlet:namespace /><%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE %>serviceURL'
				].value;
			data.<portlet:namespace />openSsoScreenNameAttr =
				document.<portlet:namespace />fm[
					'<portlet:namespace /><%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE %>screenNameAttr'
				].value;
			data.<portlet:namespace />openSsoEmailAddressAttr =
				document.<portlet:namespace />fm[
					'<portlet:namespace /><%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE %>emailAddressAttr'
				].value;
			data.<portlet:namespace />openSsoFirstNameAttr =
				document.<portlet:namespace />fm[
					'<portlet:namespace /><%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE %>firstNameAttr'
				].value;
			data.<portlet:namespace />openSsoLastNameAttr =
				document.<portlet:namespace />fm[
					'<portlet:namespace /><%= PortalSettingsOpenSSOConstants.FORM_PARAMETER_NAMESPACE %>lastNameAttr'
				].value;

			var url =
				'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/portal_settings/test_opensso" /></portlet:renderURL>';

			var dialog = Liferay.Util.Window.getWindow({
				dialog: {
					destroyOnHide: true
				},
				title: '<%= UnicodeLanguageUtil.get(request, "opensso") %>'
			});

			dialog.plug(A.Plugin.IO, {
				data: data,
				uri: url
			});
		},
		['aui-io-plugin-deprecated', 'liferay-util-window']
	);
</aui:script>