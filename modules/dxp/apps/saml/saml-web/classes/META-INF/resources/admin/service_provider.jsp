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

<portlet:actionURL name="/admin/updateServiceProvider" var="updateServiceProviderURL">
	<portlet:param name="tabs1" value="service-provider" />
</portlet:actionURL>

<aui:form action="<%= updateServiceProviderURL %>">
	<aui:fieldset label="general">
		<aui:input helpMessage="saml-sp-assertion-signature-required-description" label="saml-sp-assertion-signature-required" name='<%= "settings--" + PortletPropsKeys.SAML_SP_ASSERTION_SIGNATURE_REQUIRED + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.assertionSignatureRequired() %>" />

		<aui:input helpMessage="saml-sp-clock-skew-description" label="saml-sp-clock-skew" name='<%= "settings--" + PortletPropsKeys.SAML_SP_CLOCK_SKEW + "--" %>' value="<%= samlProviderConfiguration.clockSkew() %>" />

		<aui:input helpMessage="saml-sp-ldap-import-enabled-description" label="saml-sp-ldap-import-enabled" name='<%= "settings--" + PortletPropsKeys.SAML_SP_LDAP_IMPORT_ENABLED + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.ldapImportEnabled() %>" />

		<aui:input helpMessage="saml-sp-sign-authn-request-description" label="saml-sp-sign-authn-request" name='<%= "settings--" + PortletPropsKeys.SAML_SP_SIGN_AUTHN_REQUEST + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.signAuthnRequest() %>" />

		<aui:input helpMessage="saml-sign-metadata-description" label="saml-sign-metadata" name='<%= "settings--" + PortletPropsKeys.SAML_SIGN_METADATA + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.signMetadata() %>" />

		<aui:input helpMessage="saml-ssl-required-description" label="saml-ssl-required" name='<%= "settings--" + PortletPropsKeys.SAML_SSL_REQUIRED + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.sslRequired() %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>