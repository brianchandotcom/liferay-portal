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

<portlet:actionURL name="/admin/updateIdentityProvider" var="updateIdentityProviderURL">
	<portlet:param name="tabs1" value="identity-provider" />
</portlet:actionURL>

<aui:form action="<%= updateIdentityProviderURL %>">
	<aui:fieldset label="general">
		<aui:input helpMessage="saml-sign-metadata-description" label="saml-sign-metadata" name='<%= "settings--" + PortletPropsKeys.SAML_SIGN_METADATA + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.signMetadata() %>" />

		<aui:input helpMessage="saml-ssl-required-description" label="saml-ssl-required" name='<%= "settings--" + PortletPropsKeys.SAML_SSL_REQUIRED + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.sslRequired() %>" />

		<aui:input helpMessage="saml-idp-authn-request-signature-required-description" label="saml-idp-authn-request-signature-required" name='<%= "settings--" + PortletPropsKeys.SAML_IDP_AUTHN_REQUEST_SIGNATURE_REQUIRED + "--" %>' type="checkbox" value="<%= samlProviderConfiguration.authnRequestSignatureRequired() %>" />
	</aui:fieldset>

	<aui:fieldset label="session">
		<aui:input helpMessage="saml-idp-session-maximum-age-description" label="saml-idp-session-maximum-age" name='<%= "settings--" + PortletPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE + "--" %>' value="<%= String.valueOf(samlProviderConfiguration.sessionMaximumAge()) %>" />

		<aui:input helpMessage="saml-idp-session-timeout-description" label="saml-idp-session-timeout" name='<%= "settings--" + PortletPropsKeys.SAML_IDP_SESSION_TIMEOUT + "--" %>' value="<%= String.valueOf(samlProviderConfiguration.sessionTimeout()) %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>