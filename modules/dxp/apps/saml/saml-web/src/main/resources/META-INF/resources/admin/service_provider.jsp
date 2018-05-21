<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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