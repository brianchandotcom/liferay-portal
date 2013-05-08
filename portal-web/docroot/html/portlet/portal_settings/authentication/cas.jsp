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
boolean casAuthEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CAS_AUTH_ENABLED, PropsValues.CAS_AUTH_ENABLED);
boolean casImportFromLdap = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CAS_IMPORT_FROM_LDAP, PropsValues.CAS_IMPORT_FROM_LDAP);
String casLoginURL = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_LOGIN_URL, PropsValues.CAS_LOGIN_URL);
String casLogoutURL = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_LOGOUT_URL, PropsValues.CAS_LOGOUT_URL);
String casServerName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVER_NAME, PropsValues.CAS_SERVER_NAME);
String casServerURL = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVER_URL, PropsValues.CAS_SERVER_URL);
String casServiceURL = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_SERVICE_URL, PropsValues.CAS_SERVICE_URL);
String casNoSuchUserRedirectURL = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CAS_NO_SUCH_USER_REDIRECT_URL, PropsValues.CAS_NO_SUCH_USER_REDIRECT_URL);
%>

<aui:fieldset>
	<liferay-ui:error key="casServerNameInvalid" message="the-cas-server-name-is-invalid" />
	<liferay-ui:error key="casServerURLInvalid" message="the-cas-server-url-is-invalid" />
	<liferay-ui:error key="casServiceURLInvalid" message="the-cas-service-url-is-invalid" />
	<liferay-ui:error key="casLoginURLInvalid" message="the-cas-login-url-is-invalid" />
	<liferay-ui:error key="casLogoutURLInvalid" message="the-cas-logout-url-is-invalid" />
	<liferay-ui:error key="casNoSuchUserURLInvalid" message="the-cas-no-such-user-url-is-invalid" />
	<liferay-ui:error key="casServerURLAndServiceURLConflict" message="the-cas-server-url-and-cas-service-url-cannot-both-be-set" />
	<liferay-ui:error key="casServerURLAndServiceURLNotSet" message="either-the-cas-server-url-or-the-cas-service-url-must-be-set" />

	<aui:input label="enabled" name='<%= "settings--" + PropsKeys.CAS_AUTH_ENABLED + "--" %>' type="checkbox" value="<%= casAuthEnabled %>" />

	<aui:input helpMessage="import-cas-users-from-ldap-help" label="import-cas-users-from-ldap" name='<%= "settings--" + PropsKeys.CAS_IMPORT_FROM_LDAP + "--" %>' type="checkbox" value="<%= casImportFromLdap %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-login-url-help" label="login-url" name='<%= "settings--" + PropsKeys.CAS_LOGIN_URL + "--" %>' type="text" value="<%= casLoginURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-logout-url-help" label="logout-url" name='<%= "settings--" + PropsKeys.CAS_LOGOUT_URL + "--" %>' type="text" value="<%= casLogoutURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-server-name-help" label="server-name" name='<%= "settings--" + PropsKeys.CAS_SERVER_NAME + "--" %>' type="text" value="<%= casServerName %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-server-url-help" label="server-url" name='<%= "settings--" + PropsKeys.CAS_SERVER_URL + "--" %>' type="text" value="<%= casServerURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-service-url-help" label="service-url" name='<%= "settings--" + PropsKeys.CAS_SERVICE_URL + "--" %>' type="text" value="<%= casServiceURL %>" />

	<aui:input cssClass="lfr-input-text-container" helpMessage="cas-no-such-user-redirect-url-help" label="no-such-user-redirect-url" name='<%= "settings--" + PropsKeys.CAS_NO_SUCH_USER_REDIRECT_URL + "--" %>' type="text" value="<%= casNoSuchUserRedirectURL %>" />

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

			data.<portlet:namespace />casLoginURL = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.CAS_LOGIN_URL %>--'].value;
			data.<portlet:namespace />casLogoutURL = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.CAS_LOGOUT_URL %>--'].value;
			data.<portlet:namespace />casServerURL = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.CAS_SERVER_URL %>--'].value;
			data.<portlet:namespace />casServiceURL = document.<portlet:namespace />fm['<portlet:namespace />settings--<%= PropsKeys.CAS_SERVICE_URL %>--'].value;

			var url = "<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portal_settings/test_cas_configuration" /></portlet:renderURL>";

			var dialog = Liferay.Util.Window.getWindow(
				{
					dialog: {
						destroyOnHide: true
					},
					title: '<%= UnicodeLanguageUtil.get(pageContext, "cas") %>'
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