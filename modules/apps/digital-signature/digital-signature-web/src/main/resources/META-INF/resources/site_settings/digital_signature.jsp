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

<%
boolean digitalSignatureEnabled = GetterUtil.getBoolean(request.getAttribute(DigitalSignatureWebKeys.DIGITAL_SIGNATURE_ENABLED));

DigitalSignatureConfiguration digitalSignatureConfiguration = (DigitalSignatureConfiguration)request.getAttribute(DigitalSignatureConfiguration.class.getName());

boolean disabled = false;

if (Objects.equals(digitalSignatureConfiguration.siteSettingsStrategy(), "always-inherit") || Validator.isNull(digitalSignatureConfiguration.siteSettingsStrategy())) {
	disabled = true;
}
%>

<div class="row">
	<div class="col-md-12">
		<aui:input checked="<%= digitalSignatureEnabled %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enabled") %>' labelCssClass="simple-toggle-switch" name="enabled" type="toggle-switch" value="<%= digitalSignatureEnabled %>" />
	</div>
</div>

<div id="<%= liferayPortletResponse.getNamespace() + "digitalSignatureProviderCredentials" %>">
	<div class="form-group row">
		<div class="col-md-6">
			<aui:input disabled="<%= disabled %>" label="api-username" name="apiUsername" type="text" value="<%= GetterUtil.getString(request.getAttribute(DigitalSignatureWebKeys.DIGITAL_SIGNATURE_API_USERNAME)) %>" />
		</div>

		<div class="col-md-6">
			<aui:input disabled="<%= disabled %>" label="api-account-id" name="apiAccountId" type="text" value="<%= GetterUtil.getString(request.getAttribute(DigitalSignatureWebKeys.DIGITAL_SIGNATURE_API_ACCOUNT_ID)) %>" />
		</div>
	</div>

	<div class="form-group row">
		<div class="col-md-6">
			<aui:input disabled="<%= disabled %>" label="account's-base-uri" name="accountBaseURI" type="text" value="<%= GetterUtil.getString(request.getAttribute(DigitalSignatureWebKeys.DIGITAL_SIGNATURE_ACCOUNT_BASE_URI)) %>" />
		</div>

		<div class="col-md-6">
			<aui:input disabled="<%= disabled %>" label="integration-key" name="integrationKey" type="text" value="<%= GetterUtil.getString(request.getAttribute(DigitalSignatureWebKeys.DIGITAL_SIGNATURE_INTEGRATION_KEY)) %>" />
		</div>
	</div>

	<div class="form-group row">
		<div class="col-md-12">
			<aui:input disabled="<%= disabled %>" label="rsa-private-key" name="rsaPrivateKey" type="textarea" value="<%= GetterUtil.getString(request.getAttribute(DigitalSignatureWebKeys.DIGITAL_SIGNATURE_RSA_PRIVATE_KEY)) %>" />
		</div>
	</div>
</div>

<script>
	function <portlet:namespace />onChangeDigitalSignatureSiteSettingsStrategy(
		event
	) {
		var digitalSignatureProviderCredentialsElement = document.getElementById(
			'<portlet:namespace />digitalSignatureProviderCredentials'
		);

		var digitalSignatureSiteSettingsStrategyElement = document.getElementById(
			'<portlet:namespace />siteSettingsStrategy'
		);

		if (
			digitalSignatureSiteSettingsStrategyElement.value === 'always-override'
		) {
			digitalSignatureProviderCredentialsElement.classList.add('hide');
		}
		else {
			digitalSignatureProviderCredentialsElement.classList.remove('hide');
		}
	}
</script>