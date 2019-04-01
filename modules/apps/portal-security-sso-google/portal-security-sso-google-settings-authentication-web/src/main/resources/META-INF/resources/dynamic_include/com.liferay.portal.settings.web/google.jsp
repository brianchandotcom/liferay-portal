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
GoogleAuthorizationConfiguration googleAuthorizationConfiguration = ConfigurationProviderUtil.getConfiguration(GoogleAuthorizationConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), PortalSettingsGoogleConstants.FORM_PARAMETER_NAMESPACE, new CompanyServiceSettingsLocator(company.getCompanyId(), GoogleConstants.SERVICE_NAME)));

boolean googleAuthEnabled = googleAuthorizationConfiguration.enabled();
String googleClientId = googleAuthorizationConfiguration.clientId();
String googleClientSecret = googleAuthorizationConfiguration.clientSecret();
%>

<liferay-ui:error key="googleClientIdInvalid" message="the-google-client-id-is-invalid" />
<liferay-ui:error key="googleClientSecretInvalid" message="the-google-client-secret-is-invalid" />

<aui:fieldset>
	<aui:input label="enabled" name='<%= PortalSettingsGoogleConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= googleAuthEnabled %>" />

	<aui:input label="google-client-id" name='<%= PortalSettingsGoogleConstants.FORM_PARAMETER_NAMESPACE + "clientId" %>' type="text" value="<%= googleClientId %>" wrapperCssClass="lfr-input-text-container" />

	<aui:input label="google-client-secret" name='<%= PortalSettingsGoogleConstants.FORM_PARAMETER_NAMESPACE + "clientSecret" %>' type="text" value="<%= googleClientSecret %>" wrapperCssClass="lfr-input-text-container" />
</aui:fieldset>