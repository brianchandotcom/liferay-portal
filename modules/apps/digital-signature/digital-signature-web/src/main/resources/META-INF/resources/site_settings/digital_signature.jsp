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
DigitalSignatureConfiguration digitalSignatureConfiguration = (DigitalSignatureConfiguration)request.getAttribute(DigitalSignatureConfiguration.class.getName());
%>

<div class="form-group row">
	<div class="col-md-12">
		<label class="control-label">
			<liferay-ui:message key="site-settings-strategy" />

			<liferay-ui:icon-help message='<%= LanguageUtil.format(resourceBundle, "site-settings-strategy-description", "digital-signature") %>' />
		</label>
	</div>

	<c:if test="<%= Validator.isNotNull(digitalSignatureConfiguration.siteSettingsStrategy()) %>">
		<div class="col-md-12">
			<liferay-ui:message key='<%= "site-settings-strategy-" + digitalSignatureConfiguration.siteSettingsStrategy() %>' />
		</div>
	</c:if>
</div>

<div class="row">
	<div class="col-md-12">

		<%
		boolean digitalSignatureEnabled = GetterUtil.getBoolean(request.getAttribute(DigitalSignatureWebKeys.DIGITAL_SIGNATURE_ENABLED));

		boolean disabled = false;

		if (Objects.equals(digitalSignatureConfiguration.siteSettingsStrategy(), "always-inherit") || Validator.isNull(digitalSignatureConfiguration.siteSettingsStrategy())) {
			disabled = true;
		}
		%>

		<aui:input checked="<%= digitalSignatureEnabled %>" disabled="<%= disabled %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enabled") %>' labelCssClass="simple-toggle-switch" name="enabled" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeDigitalSignatureEnabled(event);" %>' type="toggle-switch" value="<%= digitalSignatureEnabled %>" />
	</div>
</div>

<div id="<%= liferayPortletResponse.getNamespace() + "digitalSignatureProviderCredentials" %>">
	<div class="mb-4">
		<liferay-learn:message
			key="docusign-credentials-help"
			resource="digital-signature-web"
		/>
	</div>

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
	function <portlet:namespace />onChangeDigitalSignatureEnabled(event) {
		var digitalSignatureEnabledElement = document.getElementById(
			'<portlet:namespace />enabled'
		);

		if (!digitalSignatureEnabledElement.checked) {
			if (
				!confirm(
					'<%= HtmlUtil.escapeJS(LanguageUtil.get(resourceBundle, "disabling-the-digital-signature-will-prevent-users-from-seeing-any-digital-signature-data-and-requests")) %>'
				)
			) {
				digitalSignatureEnabledElement.checked = true;
			}
		}
	}
</script>