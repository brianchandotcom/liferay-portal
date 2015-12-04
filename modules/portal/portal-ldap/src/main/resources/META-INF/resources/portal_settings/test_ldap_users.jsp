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
long ldapServerId = ParamUtil.getLong(request, "ldapServerId", 0);

String baseProviderURL = ParamUtil.getString(request, "baseProviderURL");
String baseDN = ParamUtil.getString(request, "baseDN");
String principal = ParamUtil.getString(request, "principal");

String credentials = request.getParameter("credentials");

if (credentials.equals(Portal.TEMP_OBFUSCATION_VALUE)) {
	LDAPServerConfiguration ldapServerConfiguration = ldapServerConfigurationProvider.getConfiguration(themeDisplay.getCompanyId(), ldapServerId, true);

	credentials = ldapServerConfiguration.securityCredential();

}

LdapContext ldapContext = PortalLDAPUtil.getContext(themeDisplay.getCompanyId(), baseProviderURL, principal, credentials);

if (ldapContext == null) {
%>

	<liferay-ui:message key="liferay-has-failed-to-connect-to-the-ldap-server" />

<%
	return;
}

FullNameDefinition fullNameDefinition = FullNameDefinitionFactory.getInstance(locale);

if (Validator.isNull(ParamUtil.getString(request, "userMappingScreenName")) ||
	Validator.isNull(ParamUtil.getString(request, "userMappingPassword")) ||
	(Validator.isNull(ParamUtil.getString(request, "userMappingEmailAddress")) && PropsValues.USERS_EMAIL_ADDRESS_REQUIRED) ||
	Validator.isNull(ParamUtil.getString(request, "userMappingFirstName")) ||
	(Validator.isNull(ParamUtil.getString(request, "userMappingLastName")) && fullNameDefinition.isFieldRequired("last-name"))) {
%>

	<liferay-ui:message key="please-map-each-of-the-user-properties-screen-name,-password,-email-address,-first-name,-and-last-name-to-an-ldap-attribute" />

<%
	return;
}

String userFilter = ParamUtil.getString(request, "importUserSearchFilter");

if (!LDAPUtil.isValidFilter(userFilter)) {
%>

	<liferay-ui:message key="please-enter-a-valid-ldap-search-filter" />

<%
	return;
}

String userMappingsParams =
	"screenName=" + ParamUtil.getString(request, "userMappingScreenName") +
	"\npassword=" + ParamUtil.getString(request, "userMappingPassword") +
	"\nemailAddress=" + ParamUtil.getString(request, "userMappingEmailAddress") +
	"\nfullName=" + ParamUtil.getString(request, "userMappingFullName") +
	"\nfirstName=" + ParamUtil.getString(request, "userMappingFirstName") +
	"\nlastName=" + ParamUtil.getString(request, "userMappingLastName") +
	"\njobTitle=" + ParamUtil.getString(request, "userMappingJobTitle") +
	"\ngroup=" + ParamUtil.getString(request, "userMappingGroup");

Properties userMappings = PropertiesUtil.load(userMappingsParams);

String[] attributeIds = StringUtil.split(StringUtil.merge(userMappings.values()));

List<SearchResult> searchResults = new ArrayList<SearchResult>();

if (Validator.isNotNull(userFilter) && !userFilter.equals(StringPool.STAR)) {
	PortalLDAPUtil.getUsers(themeDisplay.getCompanyId(), ldapContext, new byte[0], 20, baseDN, userFilter, attributeIds, searchResults);
}
%>

<liferay-ui:message key="test-ldap-users" />

<br /><br />

<liferay-ui:message key="a-subset-of-users-has-been-displayed-for-you-to-review" />

<%
boolean showMissingAttributeMessage = false;

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/portal_settings/test_ldap_users");
portletURL.setParameter("ldapServerId", String.valueOf(ldapServerId));
portletURL.setParameter("baseProviderURL", baseProviderURL);
portletURL.setParameter("baseDN", baseDN);
portletURL.setParameter("principal", principal);
portletURL.setParameter("credentials", credentials);
portletURL.setParameter("importUserSearchFilter", userFilter);
portletURL.setParameter("userMappingScreenName", ParamUtil.getString(request, "userMappingScreenName"));
portletURL.setParameter("userMappingPassword", ParamUtil.getString(request, "userMappingPassword"));
portletURL.setParameter("userMappingEmailAddress", ParamUtil.getString(request, "userMappingEmailAddress"));
portletURL.setParameter("userMappingFullName", ParamUtil.getString(request, "userMappingFullName"));
portletURL.setParameter("userMappingFirstName", ParamUtil.getString(request, "userMappingFirstName"));
portletURL.setParameter("userMappingLastName", ParamUtil.getString(request, "userMappingLastName"));
portletURL.setParameter("userMappingJobTitle", ParamUtil.getString(request, "userMappingJobTitle"));
portletURL.setParameter("userMappingGroup", ParamUtil.getString(request, "userMappingGroup"));
portletURL.setWindowState(LiferayWindowState.POP_UP);
%>

<liferay-ui:search-container
	emptyResultsMessage="no-users-were-found"
	iteratorURL="<%= portletURL %>"
>

	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(searchResults, searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= searchResults.size() %>"
	/>

	<liferay-ui:search-container-row
		className="javax.naming.directory.SearchResult"
		modelVar="searchResult"
	>

		<%
		Attributes attributes = searchResult.getAttributes();

		String emailAddress = LDAPUtil.getAttributeString(attributes, userMappings.getProperty("emailAddress"));
		String firstName = LDAPUtil.getAttributeString(attributes, userMappings.getProperty("firstName"));
		String lastName = LDAPUtil.getAttributeString(attributes, userMappings.getProperty("lastName"));
		String jobTitle = LDAPUtil.getAttributeString(attributes, userMappings.getProperty("jobTitle"));

		String password = StringUtil.toLowerCase(LDAPUtil.getAttributeString(attributes, userMappings.getProperty("password")));
		String screenName = StringUtil.toLowerCase(LDAPUtil.getAttributeString(attributes, userMappings.getProperty("screenName")));

		Attribute attribute = attributes.get(userMappings.getProperty("group"));

		if ((PropsValues.USERS_EMAIL_ADDRESS_REQUIRED && Validator.isNull(emailAddress)) || Validator.isNull(firstName) || (fullNameDefinition.isFieldRequired("last-name") && Validator.isNull(lastName)) || Validator.isNull(password) || Validator.isNull(screenName)) {
			showMissingAttributeMessage = true;
		}
		%>

		<liferay-ui:search-container-column-text
			name="screenName"
			value="<%= HtmlUtil.escape(screenName) %>"
		/>

		<liferay-ui:search-container-column-text
			name="emailAddress"
			value="<%= HtmlUtil.escape(emailAddress) %>"
		/>

		<%@ include file="/portal_settings/test_ldap_users_user_name.jspf" %>

		<liferay-ui:search-container-column-text
			name="password"
			value="<%= Validator.isNotNull(password) ? StringPool.EIGHT_STARS : StringPool.BLANK %>"
		/>

		<liferay-ui:search-container-column-text
			name="job-title"
			value="<%= HtmlUtil.escape(jobTitle) %>"
		/>

		<liferay-ui:search-container-column-text
			name="group"
			value='<%= (attribute == null) ? "0" : String.valueOf(attribute.size()) %>'
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<%
if (showMissingAttributeMessage) {
%>

	<div class="alert alert-info">
		<liferay-ui:message key="the-above-results-include-users-which-are-missing-the-required-attributes-(screen-name,-password,-email-address,-first-name,-and-last-name).-these-users-will-not-be-imported-until-these-attributes-are-filled-in" />
	</div>

<%
}

if (ldapContext != null) {
	ldapContext.close();
}
%>