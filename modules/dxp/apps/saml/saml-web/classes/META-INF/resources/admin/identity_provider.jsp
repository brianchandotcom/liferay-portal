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