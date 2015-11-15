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

<%@ include file="/portal_settings/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long ldapServerId = ParamUtil.getLong(request, "ldapServerId", 0);

LDAPServerConfiguration ldapServerConfiguration = ldapServerConfigurationProvider.getConfiguration(themeDisplay.getCompanyId(), ldapServerId, true);

String ldapServerName = ldapServerConfiguration.serverName();
String ldapBaseProviderUrl = ldapServerConfiguration.baseProviderURL();
String ldapBaseDN = ldapServerConfiguration.baseDN();
String ldapSecurityPrincipal = ldapServerConfiguration.securityPrincipal();

String ldapSecurityCredentials = ldapServerConfiguration.securityCredential();

if (Validator.isNotNull(ldapSecurityCredentials)) {
	ldapSecurityCredentials = Portal.TEMP_OBFUSCATION_VALUE;
}

String ldapAuthSearchFilter = ldapServerConfiguration.authSearchFilter();
String ldapUserSearchFilter = ldapServerConfiguration.userSearchFilter();
String ldapGroupSearchFilter = ldapServerConfiguration.groupSearchFilter();
String ldapUsersDN = ldapServerConfiguration.usersDN();
String[] ldapUserDefaultObjectClasses = ldapServerConfiguration.userDefaultObjectClasses();
String ldapGroupsDN = ldapServerConfiguration.groupsDN();
String[] ldapGroupDefaultObjectClasses = ldapServerConfiguration.groupDefaultObjectClasses();

String[] userMappingArray = ldapServerConfiguration.userMappings();

String userMappingEmailAddress = StringPool.BLANK;
String userMappingFirstName = StringPool.BLANK;
String userMappingFullName = StringPool.BLANK;
String userMappingGroup = StringPool.BLANK;
String userMappingJobTitle = StringPool.BLANK;
String userMappingLastName = StringPool.BLANK;
String userMappingMiddleName = StringPool.BLANK;
String userMappingPassword = StringPool.BLANK;
String userMappingPortrait = StringPool.BLANK;
String userMappingScreenName = StringPool.BLANK;
String userMappingStatus = StringPool.BLANK;
String userMappingUuid = StringPool.BLANK;

for (int i = 0; i < userMappingArray.length; i++) {
	if (!userMappingArray[i].contains("=")) {
		continue;
	}

	String[] mapping = userMappingArray[i].split("=");

	if (mapping.length != 2) {
		continue;
	}

	if (mapping[0].equals("emailAddress")) {
		userMappingEmailAddress = mapping[1];
	}
	else if (mapping[0].equals("firstName")) {
		userMappingFirstName = mapping[1];
	}
	else if (mapping[0].equals("fullName")) {
		userMappingFullName = mapping[1];
	}
	else if (mapping[0].equals("group")) {
		userMappingGroup = mapping[1];
	}
	else if (mapping[0].equals("jobTitle")) {
		userMappingJobTitle = mapping[1];
	}
	else if (mapping[0].equals("lastName")) {
		userMappingLastName = mapping[1];
	}
	else if (mapping[0].equals("middleName")) {
		userMappingMiddleName = mapping[1];
	}
	else if (mapping[0].equals("password")) {
		userMappingPassword = mapping[1];
	}
	else if (mapping[0].equals("portrait")) {
		userMappingPortrait = mapping[1];
	}
	else if (mapping[0].equals("screenName")) {
		userMappingScreenName = mapping[1];
	}
	else if (mapping[0].equals("status")) {
		userMappingStatus = mapping[1];
	}
	else if (mapping[0].equals("uuid")) {
		userMappingUuid = mapping[1];
	}

	mapping[1] = "";
}

String[] groupMappingArray = ldapServerConfiguration.groupMappings();

String groupMappingDescription = StringPool.BLANK;
String groupMappingGroupName = StringPool.BLANK;
String groupMappingUser = StringPool.BLANK;

for (int i = 0; i < groupMappingArray.length; i++) {
	if (!groupMappingArray[i].contains("=")) {
		continue;
	}

	String[] mapping = groupMappingArray[i].split("=");

	if (mapping.length != 2) {
		continue;
	}

	if (mapping[0].equals("description")) {
		groupMappingDescription = mapping[1];
	}
	else if (mapping[0].equals("groupName")) {
		groupMappingGroupName = mapping[1];
	}
	else if (mapping[0].equals("user")) {
		groupMappingUser = mapping[1];
	}
}
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	title='<%= (ldapServerId == 0) ? "add-ldap-server" : "edit-ldap-server" %>'
/>

<portlet:actionURL name="/portal_settings/edit_ldap_server" var="editLDAPServerURL" />

<aui:form action="<%= editLDAPServerURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntry(false);" %>'>
	<liferay-ui:error exception="<%= DuplicateLDAPServerNameException.class %>" message="please-enter-a-unique-ldap-server-name" />
	<liferay-ui:error exception="<%= LDAPFilterException.class %>" message="please-enter-a-valid-ldap-search-filter" />
	<liferay-ui:error exception="<%= LDAPServerNameException.class %>" message="please-enter-a-valid-ldap-server-name" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="ldapServerId" type="hidden" value="<%= ldapServerId %>" />

	<liferay-ui:error key="ldapAuthentication" message="failed-to-bind-to-the-ldap-server-with-given-values" />

	<aui:fieldset>
		<aui:input cssClass="lfr-input-text-container" label="server-name" name='<%= "ldap--" + LDAPConstants.SERVER_NAME + "--" %>' type="text" value="<%= ldapServerName %>" />
	</aui:fieldset>

	<h3><liferay-ui:message key="default-values" /></h3>

	<aui:fieldset>
		<aui:field-wrapper>
			<aui:input label="Apache Directory Server" name="defaultLdap" type="radio" value="apache" />
			<aui:input label="Fedora Directory Server" name="defaultLdap" type="radio" value="fedora" />
			<aui:input label="Microsoft Active Directory Server" name="defaultLdap" type="radio" value="microsoft" />
			<aui:input label="Novell eDirectory" name="defaultLdap" type="radio" value="novell" />
			<aui:input label="OpenLDAP" name="defaultLdap" type="radio" value="open" />
			<aui:input label="other-directory-server" name="defaultLdap" type="radio" value="other" />
		</aui:field-wrapper>

		<aui:button-row>
			<aui:button onClick='<%= renderResponse.getNamespace() + "updateDefaultLdap();" %>' value="reset-values" />
		</aui:button-row>
	</aui:fieldset>

	<h3><liferay-ui:message key="connection" /></h3>

	<aui:fieldset>
		<aui:input cssClass="lfr-input-text-container" helpMessage="the-ldap-url-format-is" label="base-provider-url" name='<%= "ldap--" + LDAPConstants.BASE_PROVIDER_URL + "--" %>' type="text" value="<%= ldapBaseProviderUrl %>" />

		<aui:input cssClass="lfr-input-text-container" helpMessage="the-ldap-url-format-is" label="base-dn" name='<%= "ldap--" + LDAPConstants.BASE_DN + "--" %>' type="text" value="<%= ldapBaseDN %>" />

		<aui:input cssClass="lfr-input-text-container" label="principal" name='<%= "ldap--" + LDAPConstants.SECURITY_PRINCIPAL + "--" %>' type="text" value="<%= ldapSecurityPrincipal %>" />

		<aui:input cssClass="lfr-input-text-container" label="credentials" name='<%= "ldap--" + LDAPConstants.SECURITY_CREDENTIAL + "--" %>' type="password" value="<%= ldapSecurityCredentials %>" />

		<aui:button-row>

			<%
			String taglibOnClick = renderResponse.getNamespace() + "testSettings('ldapConnection');";
			%>

			<aui:button onClick="<%= taglibOnClick %>" value="test-ldap-connection" />
		</aui:button-row>
	</aui:fieldset>

	<h3><liferay-ui:message key="users" /></h3>

	<aui:fieldset>
		<aui:input cssClass="lfr-input-text-container" helpMessage="enter-the-search-filter-that-is-used-to-test-the-validity-of-a-user" label="authentication-search-filter" name='<%= "ldap--" + LDAPConstants.AUTH_SEARCH_FILTER + "--" %>' type="text" value="<%= ldapAuthSearchFilter %>" />

		<aui:input cssClass="lfr-input-text-container" label="import-search-filter" name='<%= "ldap--" + LDAPConstants.USER_SEARCH_FILTER + "--" %>' type="text" value="<%= ldapUserSearchFilter %>" />

		<h4><liferay-ui:message key="user-mapping" /></h4>

		<aui:input cssClass="lfr-input-text-container" label="uuid" name="userMappingUuid" type="text" value="<%= userMappingUuid %>" />

		<aui:input cssClass="lfr-input-text-container" label="screen-name" name="userMappingScreenName" type="text" value="<%= userMappingScreenName %>" />

		<aui:input cssClass="lfr-input-text-container" label="email-address" name="userMappingEmailAddress" type="text" value="<%= userMappingEmailAddress %>" />

		<aui:input cssClass="lfr-input-text-container" label="password" name="userMappingPassword" type="text" value="<%= userMappingPassword %>" />

		<%@ include file="/portal_settings/edit_ldap_server_user_name.jspf" %>

		<aui:input cssClass="lfr-input-text-container" label="job-title" name="userMappingJobTitle" type="text" value="<%= userMappingJobTitle %>" />

		<aui:input cssClass="lfr-input-text-container" label="status" name="userMappingStatus" type="text" value="<%= userMappingStatus %>" />

		<aui:input cssClass="lfr-input-text-container" label="group" name="userMappingGroup" type="text" value="<%= userMappingGroup %>" />

		<aui:input cssClass="lfr-input-text-container" label="portrait" name="userMappingPortrait" type="text" value="<%= userMappingPortrait %>" />

		<aui:input cssClass="lfr-textarea" label="custom-user-mapping" name='<%= "ldap--" + LDAPConstants.USER_CUSTOM_MAPPINGS + "--" %>' type="textarea" value="<%= StringUtil.merge(ldapServerConfiguration.userCustomMappings(), StringPool.COMMA) %>" />

		<aui:input cssClass="lfr-textarea" label="custom-contact-mapping" name='<%= "ldap--" + LDAPConstants.CONTACT_CUSTOM_MAPPINGS + "--" %>' type="textarea" value="<%= StringUtil.merge(ldapServerConfiguration.contactCustomMappings(), StringPool.COMMA) %>" />

		<aui:input name='<%= "ldap--" + LDAPConstants.USER_MAPPINGS + "--" %>' type="hidden" />

		<aui:input name='<%= "ldap--" + LDAPConstants.CONTACT_MAPPINGS + "--" %>' type="hidden" value="<%= StringUtil.merge(ldapServerConfiguration.contactMappings(), StringPool.COMMA) %>" />

		<aui:button-row>

			<%
			String taglibOnClick = renderResponse.getNamespace() + "testSettings('ldapUsers');";
			%>

			<aui:button onClick="<%= taglibOnClick %>" value="test-ldap-users" />
		</aui:button-row>
	</aui:fieldset>

	<h3><liferay-ui:message key="groups" /></h3>

	<aui:fieldset>
		<aui:input cssClass="lfr-input-text-container" label="import-search-filter" name='<%= "ldap--" + LDAPConstants.GROUP_SEARCH_FILTER + "--" %>' type="text" value="<%= ldapGroupSearchFilter %>" />

		<h4><liferay-ui:message key="group-mapping" /></h4>

		<aui:input cssClass="lfr-input-text-container" label="group-name" name="groupMappingGroupName" type="text" value="<%= groupMappingGroupName %>" />

		<aui:input cssClass="lfr-input-text-container" label="description" name="groupMappingDescription" type="text" value="<%= groupMappingDescription %>" />

		<aui:input cssClass="lfr-input-text-container" label="user" name="groupMappingUser" type="text" value="<%= groupMappingUser %>" />

		<aui:input name='<%= "ldap--" + LDAPConstants.GROUP_MAPPINGS + "--" %>' type="hidden" />

		<aui:button-row>

			<%
			String taglibOnClick = renderResponse.getNamespace() + "testSettings('ldapGroups');";
			%>

			<aui:button onClick="<%= taglibOnClick %>" value="test-ldap-groups" />
		</aui:button-row>
	</aui:fieldset>

	<h3><liferay-ui:message key="export" /></h3>

	<aui:fieldset>
		<aui:input cssClass="lfr-input-text-container" label="users-dn" name='<%= "ldap--" + LDAPConstants.USERS_DN + "--" %>' type="text" value="<%= ldapUsersDN %>" />

		<aui:input cssClass="lfr-input-text-container" label="user-default-object-classes" name='<%= "ldap--" + LDAPConstants.USER_DEFAULT_OBJECT_CLASSES + "--" %>' type="text" value="<%= StringUtil.merge(ldapUserDefaultObjectClasses, StringPool.COMMA) %>" />

		<aui:input cssClass="lfr-input-text-container" label="groups-dn" name='<%= "ldap--" + LDAPConstants.GROUPS_DN + "--" %>' type="text" value="<%= ldapGroupsDN %>" />

		<aui:input cssClass="lfr-input-text-container" label="group-default-object-classes" name='<%= "ldap--" + LDAPConstants.GROUP_DEFAULT_OBJECT_CLASSES + "--" %>' type="text" value="<%= StringUtil.merge(ldapGroupDefaultObjectClasses, StringPool.COMMA) %>" />
	</aui:fieldset>

	<aui:button-row>

		<%
		String taglibOnClick = renderResponse.getNamespace() + "saveLdap();";
		%>

		<aui:button name="saveButton" onClick="<%= taglibOnClick %>" value="save" />

		<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />mapValues(fields, fieldValues) {
		var form = AUI.$(document.<portlet:namespace />fm);

		return AUI._.reduce(
			fields,
			function(prev, item, index) {
				var mappingValue = form.fm(fieldValues[index]).val();

				if (mappingValue) {
					prev += item + '=' + mappingValue + ',';
				}

				return prev;
			},
			''
		);
	}

	function <portlet:namespace />saveLdap() {
		var userMappingFields = ['emailAddress', 'firstName', 'fullName', 'group', 'jobTitle', 'lastName', 'middleName', 'password', 'portrait', 'screenName', 'status', 'uuid'];
		var userMappingFieldValues = ['userMappingEmailAddress', 'userMappingFirstName', 'userMappingFullName', 'userMappingGroup', 'userMappingJobTitle', 'userMappingLastName', 'userMappingMiddleName', 'userMappingPassword', 'userMappingPortrait', 'userMappingScreenName', 'userMappingStatus', 'userMappingUuid'];

		var form = AUI.$(document.<portlet:namespace />fm);

		var userMapping = <portlet:namespace />mapValues(userMappingFields, userMappingFieldValues);

		form.fm('ldap--<%= LDAPConstants.USER_MAPPINGS %>--').val(userMapping);

		var groupMappingFields = ['description', 'groupName', 'user'];
		var groupMappingFieldValues = ['groupMappingDescription', 'groupMappingGroupName', 'groupMappingUser'];

		var groupMapping = <portlet:namespace />mapValues(groupMappingFields, groupMappingFieldValues);

		form.fm('ldap--<%= LDAPConstants.GROUP_MAPPINGS %>--').val(groupMapping);

		form.fm('<%= Constants.CMD %>').val('<%= ldapServerId <= 0 ? Constants.ADD : Constants.UPDATE %>');

		submitForm(form);
	}

	function <portlet:namespace />updateDefaultLdap() {
		var baseProviderURL = '';
		var baseDN = '';
		var principal = '';
		var credentials = '';
		var searchFilter = '';
		var importUserSearchFilter = '';
		var userMappingEmailAddress = '';
		var userMappingFirstName = '';
		var userMappingFullName = '';
		var userMappingGroup = '';
		var userMappingJobTitle = '';
		var userMappingLastName = '';
		var userMappingMiddleName = '';
		var userMappingPassword = '';
		var userMappingPortrait = '';
		var userMappingScreenName = '';
		var userMappingStatus = '';
		var userMappingUuid = '';
		var importGroupSearchFilter = '';
		var groupMappingDescription = '';
		var groupMappingGroupName = '';
		var groupMappingUser = '';
		var exportMappingUserDefaultObjectClass = '';
		var exportMappingGroupDefaultObjectClass = '';

		var form = AUI.$(document.<portlet:namespace />fm);

		var ldapType = form.fm('defaultLdap').filter(':checked').val();

		if (ldapType == 'apache') {
			baseProviderURL = 'ldap://localhost:10389';
			baseDN = 'dc=example,dc=com';
			principal = 'uid=admin,ou=system';
			credentials = 'secret';
			searchFilter = '(mail=@email_address@)';
			importUserSearchFilter = '(objectClass=person)';
			userMappingEmailAddress = 'mail';
			userMappingFirstName = 'givenName';
			userMappingFullName = '';
			userMappingGroup = '';
			userMappingJobTitle = 'title';
			userMappingLastName = 'sn';
			userMappingMiddleName = '';
			userMappingPassword = 'userPassword';
			userMappingPortrait = '';
			userMappingScreenName = 'cn';
			userMappingStatus = '';
			userMappingUuid = '';
			importGroupSearchFilter = '(objectClass=groupOfUniqueNames)';
			groupMappingDescription = 'description';
			groupMappingGroupName = 'cn';
			groupMappingUser = 'uniqueMember';
			exportMappingUserDefaultObjectClass = 'top,person,inetOrgPerson,organizationalPerson';
			exportMappingGroupDefaultObjectClass = 'top,groupOfUniqueNames';
		}
		else if (ldapType == 'fedora') {
			baseProviderURL = 'ldap://localhost:19389';
			baseDN = 'dc=localdomain';
			principal = 'cn=Directory Manager';
			credentials = '';
			searchFilter = '(mail=@email_address@)';
			importUserSearchFilter = '(objectClass=inetOrgPerson)';
			userMappingEmailAddress = 'mail';
			userMappingFirstName = 'givenName';
			userMappingFullName = 'cn';
			userMappingGroup = '';
			userMappingJobTitle = 'title';
			userMappingLastName = 'sn';
			userMappingMiddleName = '';
			userMappingPassword = 'userPassword';
			userMappingPortrait = '';
			userMappingScreenName = 'uid';
			userMappingStatus = '';
			userMappingUuid = '';
			importGroupSearchFilter = '';
			groupMappingDescription = '';
			groupMappingGroupName = '';
			groupMappingUser = '';
			exportMappingUserDefaultObjectClass = '';
			exportMappingGroupDefaultObjectClass = '';
		}
		else if (ldapType == 'microsoft') {
			baseProviderURL = 'ldap://localhost:389';
			baseDN = 'dc=example,dc=com';
			principal = 'admin';
			credentials = 'secret';
			searchFilter = '(&(objectCategory=person)(sAMAccountName=@user_id@))';
			importUserSearchFilter = '(objectClass=person)';
			userMappingEmailAddress = 'userprincipalname';
			userMappingFirstName = 'givenName';
			userMappingFullName = 'cn';
			userMappingGroup = 'memberOf';
			userMappingJobTitle = '';
			userMappingLastName = 'sn';
			userMappingMiddleName = 'middleName';
			userMappingPassword = 'unicodePwd';
			userMappingPortrait = '';
			userMappingScreenName = 'sAMAccountName';
			userMappingStatus = '';
			userMappingUuid = '';
			importGroupSearchFilter = '(objectClass=group)';
			groupMappingDescription = 'sAMAccountName';
			groupMappingGroupName = 'cn';
			groupMappingUser = 'member';
			exportMappingUserDefaultObjectClass = '';
			exportMappingGroupDefaultObjectClass = '';
		}
		else if (ldapType == 'novell') {
			baseProviderURL = 'ldap://localhost:389';
			baseDN = '';
			principal = 'cn=admin,ou=test';
			credentials = 'secret';
			searchFilter = '(mail=@email_address@)';
			importUserSearchFilter = '';
			userMappingEmailAddress = 'mail';
			userMappingFirstName = 'givenName';
			userMappingFullName = '';
			userMappingGroup = '';
			userMappingJobTitle = 'title';
			userMappingLastName = 'sn';
			userMappingMiddleName = '';
			userMappingPassword = 'userPassword';
			userMappingPortrait = '';
			userMappingScreenName = 'cn';
			userMappingStatus = '';
			userMappingUuid = '';
			importGroupSearchFilter = '';
			groupMappingDescription = '';
			groupMappingGroupName = '';
			groupMappingUser = '';
			exportMappingUserDefaultObjectClass = '';
			exportMappingGroupDefaultObjectClass = '';
		}
		else if (ldapType == 'open') {
			baseProviderURL = 'ldap://localhost:389';
			baseDN = 'dc=example,dc=com';
			principal = 'cn=admin,ou=test';
			credentials = 'secret';
			searchFilter = '(mail=@email_address@)';
			importUserSearchFilter = '(objectClass=inetOrgPerson)';
			userMappingEmailAddress = 'mail';
			userMappingFirstName = 'givenName';
			userMappingFullName = '';
			userMappingGroup = '';
			userMappingJobTitle = 'title';
			userMappingLastName = 'sn';
			userMappingMiddleName = '';
			userMappingPassword = 'userPassword';
			userMappingPortrait = '';
			userMappingScreenName = 'cn';
			userMappingStatus = '';
			userMappingUuid = '';
			importGroupSearchFilter = '(objectClass=groupOfUniqueNames)';
			groupMappingGroupName = 'cn';
			groupMappingDescription = 'description';
			groupMappingUser = 'uniqueMember';
			exportMappingUserDefaultObjectClass = '';
			exportMappingGroupDefaultObjectClass = '';
		}

		form.fm('ldap--<%= LDAPConstants.BASE_PROVIDER_URL %>--').val(baseProviderURL);
		form.fm('ldap--<%= LDAPConstants.BASE_DN %>--').val(baseDN);
		form.fm('ldap--<%= LDAPConstants.SECURITY_PRINCIPAL %>--').val(principal);
		form.fm('ldap--<%= LDAPConstants.SECURITY_CREDENTIAL %>--').val(credentials);
		form.fm('ldap--<%= LDAPConstants.AUTH_SEARCH_FILTER %>--').val(searchFilter);
		form.fm('ldap--<%= LDAPConstants.USER_SEARCH_FILTER %>--').val(importUserSearchFilter);
		form.fm('userMappingEmailAddress').val(userMappingEmailAddress);
		form.fm('userMappingFirstName').val(userMappingFirstName);
		form.fm('userMappingFullName').val(userMappingFullName);
		form.fm('userMappingGroup').val(userMappingGroup);
		form.fm('userMappingJobTitle').val(userMappingJobTitle);
		form.fm('userMappingLastName').val(userMappingLastName);
		form.fm('userMappingMiddleName').val(userMappingMiddleName);
		form.fm('userMappingPassword').val(userMappingPassword);
		form.fm('userMappingPortrait').val(userMappingPortrait);
		form.fm('userMappingScreenName').val(userMappingScreenName);
		form.fm('userMappingStatus').val(userMappingStatus);
		form.fm('userMappingUuid').val(userMappingUuid);
		form.fm('ldap--<%= LDAPConstants.GROUP_SEARCH_FILTER %>--').val(importGroupSearchFilter);
		form.fm('groupMappingDescription').val(groupMappingDescription);
		form.fm('groupMappingGroupName').val(groupMappingGroupName);
		form.fm('groupMappingUser').val(groupMappingUser);
		form.fm('ldap--<%= LDAPConstants.USERS_DN %>--').val(baseDN);
		form.fm('ldap--<%= LDAPConstants.USER_DEFAULT_OBJECT_CLASSES %>--').val(exportMappingUserDefaultObjectClass);
		form.fm('ldap--<%= LDAPConstants.GROUPS_DN %>--').val(baseDN);
		form.fm('ldap--<%= LDAPConstants.GROUP_DEFAULT_OBJECT_CLASSES %>--').val(exportMappingGroupDefaultObjectClass);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />testSettings',
		function(type) {
			var A = AUI();

			var url = null;

			var data = {};

			if (type == 'ldapConnection') {
				url = '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/portal_settings/test_ldap_connection" /></portlet:renderURL>';
			}
			else if (type == 'ldapGroups') {
				url = '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/portal_settings/test_ldap_groups" /></portlet:renderURL>';

				data.<portlet:namespace />importGroupSearchFilter = document.<portlet:namespace />fm['<portlet:namespace />ldap--<%= LDAPConstants.GROUP_SEARCH_FILTER %>--'].value;
				data.<portlet:namespace />groupMappingDescription = document.<portlet:namespace />fm['<portlet:namespace />groupMappingDescription'].value;
				data.<portlet:namespace />groupMappingGroupName = document.<portlet:namespace />fm['<portlet:namespace />groupMappingGroupName'].value;
				data.<portlet:namespace />groupMappingUser = document.<portlet:namespace />fm['<portlet:namespace />groupMappingUser'].value;
			}
			else if (type == 'ldapUsers') {
				url = '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcRenderCommandName" value="/portal_settings/test_ldap_users" /></portlet:renderURL>';

				data.<portlet:namespace />importUserSearchFilter = document.<portlet:namespace />fm['<portlet:namespace />ldap--<%= LDAPConstants.USER_SEARCH_FILTER %>--'].value;
				data.<portlet:namespace />userMappingEmailAddress = document.<portlet:namespace />fm['<portlet:namespace />userMappingEmailAddress'].value;
				data.<portlet:namespace />userMappingFirstName = document.<portlet:namespace />fm['<portlet:namespace />userMappingFirstName'].value;
				data.<portlet:namespace />userMappingFullName = document.<portlet:namespace />fm['<portlet:namespace />userMappingFullName'].value;
				data.<portlet:namespace />userMappingGroup = document.<portlet:namespace />fm['<portlet:namespace />userMappingGroup'].value;
				data.<portlet:namespace />userMappingJobTitle = document.<portlet:namespace />fm['<portlet:namespace />userMappingJobTitle'].value;
				data.<portlet:namespace />userMappingLastName = document.<portlet:namespace />fm['<portlet:namespace />userMappingLastName'].value;
				data.<portlet:namespace />userMappingMiddleName = document.<portlet:namespace />fm['<portlet:namespace />userMappingMiddleName'].value;
				data.<portlet:namespace />userMappingPassword = document.<portlet:namespace />fm['<portlet:namespace />userMappingPassword'].value;
				data.<portlet:namespace />userMappingPortrait = document.<portlet:namespace />fm['<portlet:namespace />userMappingPortrait'].value;
				data.<portlet:namespace />userMappingScreenName = document.<portlet:namespace />fm['<portlet:namespace />userMappingScreenName'].value;
				data.<portlet:namespace />userMappingStatus = document.<portlet:namespace />fm['<portlet:namespace />userMappingStatus'].value;
				data.<portlet:namespace />userMappingUuid = document.<portlet:namespace />fm['<portlet:namespace />userMappingUuid'].value;
			}

			if (url != null) {
				data.<portlet:namespace />ldapServerId = document.<portlet:namespace />fm['<portlet:namespace />ldapServerId'].value;
				data.<portlet:namespace />baseProviderURL = document.<portlet:namespace />fm['<portlet:namespace />ldap--<%= LDAPConstants.BASE_PROVIDER_URL %>--'].value;
				data.<portlet:namespace />baseDN = document.<portlet:namespace />fm['<portlet:namespace />ldap--<%= LDAPConstants.BASE_DN %>--'].value;
				data.<portlet:namespace />principal = document.<portlet:namespace />fm['<portlet:namespace />ldap--<%= LDAPConstants.SECURITY_PRINCIPAL %>--'].value;
				data.<portlet:namespace />credentials = document.<portlet:namespace />fm['<portlet:namespace />ldap--<%= LDAPConstants.SECURITY_CREDENTIAL %>--'].value;

				var dialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							destroyOnHide: true
						},
						title: '<%= UnicodeLanguageUtil.get(request, "ldap") %>'
					}
				);

				dialog.plug(
					A.Plugin.IO,
					{
						data: data,
						uri: url
					}
				);
			}
		},
		['aui-io', 'aui-io-plugin-deprecated', 'liferay-util-window']
	);
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, (ldapServerId == 0) ? LanguageUtil.get(request, "add-ldap-server") : ldapServerName, currentURL);
%>