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
OpenSSOConfiguration openSSOConfiguration = ConfigurationFactoryUtil.getConfiguration(OpenSSOConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), "opensso--", new CompanyServiceSettingsLocator(company.getCompanyId(), OpenSSOConstants.SERVICE_NAME)));

boolean enabled = openSSOConfiguration.enabled();
boolean importFromLDAP = openSSOConfiguration.importFromLDAP();
String loginURL = openSSOConfiguration.loginURL();
String logoutURL = openSSOConfiguration.logoutURL();
String serviceURL = openSSOConfiguration.serviceURL();
String screenNameAttr = openSSOConfiguration.screenNameAttr();
String emailAddressAttr = openSSOConfiguration.emailAddressAttr();
String firstNameAttr = openSSOConfiguration.firstNameAttr();
String lastNameAttr = openSSOConfiguration.lastNameAttr();
%>

<aui:fieldset>
+ <aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/edit_company_opensso_configuration" />

	<aui:input label="enabled" name="opensso--enabled" type="checkbox" value="<%= enabled %>" />

	<aui:input helpMessage="import-opensso-users-from-ldap-help" label="import-opensso-users-from-ldap" name="opensso--importFromLDAP" type="checkbox" value="<%= importFromLDAP %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="login-url-for-opensso-help" label="login-url" name="opensso--loginURL" type="text" value="<%= loginURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="logout-url-for-opensso-help" label="logout-url" name="opensso--logoutURL" type="text" value="<%= logoutURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="service-url-for-opensso-help" label="service-url" name="opensso--serviceURL" type="text" value="<%= serviceURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="mappings-for-opensso-help" label="screen-name-attribute" name="opensso--screenNameAttr" type="text" value="<%= screenNameAttr %>" />

	<aui:input cssClass="lfr-input-text-container" label="email-address-attribute" name="opensso--emailAddressAttr" type="text" value="<%= emailAddressAttr %>" />

	<aui:input cssClass="lfr-input-text-container" label="first-name-attribute" name="opensso--firstNameAttr" type="text" value="<%= firstNameAttr %>" />

	<aui:input cssClass="lfr-input-text-container" label="last-name-attribute" name="opensso--lastNameAttr" type="text" value="<%= lastNameAttr %>" />

	<aui:button-row>

		<%
		String taglibOnClick = renderResponse.getNamespace() + "testOpenSSOSettings();";
		%>

		<aui:button onClick="<%= taglibOnClick %>" value="test-opensso-configuration" />
	</aui:button-row>
</aui:fieldset>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />testOpenSSOSettings',
		function() {
			var A = AUI();

			var data = {};

			data.<portlet:namespace />openSsoLoginURL = document.<portlet:namespace />fm['<portlet:namespace />opensso--loginURL'].value;
			data.<portlet:namespace />openSsoLogoutURL = document.<portlet:namespace />fm['<portlet:namespace />opensso--logoutURL'].value;
			data.<portlet:namespace />openSsoServiceURL = document.<portlet:namespace />fm['<portlet:namespace />opensso--serviceURL'].value;
			data.<portlet:namespace />openSsoScreenNameAttr = document.<portlet:namespace />fm['<portlet:namespace />opensso--screenNameAttr'].value;
			data.<portlet:namespace />openSsoEmailAddressAttr = document.<portlet:namespace />fm['<portlet:namespace />opensso--emailAddressAttr'].value;
			data.<portlet:namespace />openSsoFirstNameAttr = document.<portlet:namespace />fm['<portlet:namespace />opensso--firstNameAttr'].value;
			data.<portlet:namespace />openSsoLastNameAttr = document.<portlet:namespace />fm['<portlet:namespace />opensso--lastNameAttr'].value;

			var url = '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/portal_settings/test_opensso" /></portlet:renderURL>';

			var dialog = Liferay.Util.Window.getWindow(
				{
					dialog: {
						destroyOnHide: true
					},
					title: '<%= UnicodeLanguageUtil.get(request, "opensso") %>'
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