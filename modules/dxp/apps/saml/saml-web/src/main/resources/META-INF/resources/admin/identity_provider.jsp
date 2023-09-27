<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:actionURL name="/admin/update_identity_provider" var="updateIdentityProviderURL">
	<portlet:param name="tabs1" value="identity-provider" />
</portlet:actionURL>

<aui:form action="<%= updateIdentityProviderURL %>">
	<aui:fieldset label="general">
		<aui:input helpMessage="saml-sign-metadata-description" label="saml-sign-metadata" name='<%= "settings--" + PortletPropsKeys.SAML_SIGN_METADATA + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.signMetadata() %>" />

		<aui:input helpMessage="saml-ssl-required-description" label="saml-ssl-required" name='<%= "settings--" + PortletPropsKeys.SAML_SSL_REQUIRED + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.sslRequired() %>" />

		<aui:input helpMessage="saml-idp-authn-request-signature-required-description" label="saml-idp-authn-request-signature-required" name='<%= "settings--" + PortletPropsKeys.SAML_IDP_AUTHN_REQUEST_SIGNATURE_REQUIRED + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.authnRequestSignatureRequired() %>" />

		<aui:input helpMessage="saml-idp-authn-request-signing-allows-dynamic-acs-url-description" label="saml-idp-authn-request-signing-allows-dynamic-acs-url" name='<%= "settings--" + PortletPropsKeys.SAML_IDP_AUTHN_REQUEST_SIGNING_ALLOWS_DYNAMIC_ACS_URL + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.authnRequestSigningAllowsDynamicACSURL() %>" />
	</aui:fieldset>

	<aui:fieldset label="session">
		<aui:input helpMessage="saml-idp-session-maximum-age-description" label="saml-idp-session-maximum-age" name='<%= "settings--" + PortletPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE + "--" %>' value="<%= String.valueOf(samlProviderConfiguration.sessionMaximumAge()) %>" />

		<aui:input helpMessage="saml-idp-session-timeout-description" label="saml-idp-session-timeout" name='<%= "settings--" + PortletPropsKeys.SAML_IDP_SESSION_TIMEOUT + "--" %>' value="<%= String.valueOf(samlProviderConfiguration.sessionTimeout()) %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>