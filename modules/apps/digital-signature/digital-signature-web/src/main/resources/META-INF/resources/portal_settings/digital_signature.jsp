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

<div class="row">
	<div class="col-md-12">
		<aui:input checked="<%= digitalSignatureConfiguration.enabled() %>" inlineLabel="right" label='<%= LanguageUtil.get(resourceBundle, "enabled") %>' labelCssClass="simple-toggle-switch" name="enabled" type="toggle-switch" value="<%= digitalSignatureConfiguration.enabled() %>" />
	</div>
</div>

<div class="form-group row">
	<div class="col-md-12">
		<aui:select label="site-settings-strategy" name="siteSettingsStrategy" onchange='<%= liferayPortletResponse.getNamespace() + "onChangeDigitalSignatureSiteSettingsStrategy(event);" %>' required="<%= true %>" value="<%= digitalSignatureConfiguration.siteSettingsStrategy() %>">
			<aui:option label="" value="" />

			<%
			for (String digitalSignatureSiteSettingsStrategy : DigitalSignatureConstants.DIGITAL_SIGNATURE_SITE_SETTINGS_STRATEGIES) {
			%>

				<aui:option label='<%= "site-settings-strategy-" + digitalSignatureSiteSettingsStrategy %>' value="<%= digitalSignatureSiteSettingsStrategy %>" />

			<%
			}
			%>

		</aui:select>

		<label class="text-secondary">
			<liferay-ui:message key="site-settings-strategy-description" />
		</label>
	</div>
</div>

<div id="<%= liferayPortletResponse.getNamespace() + "digitalSignatureProviderCredentials" %>">
	<div class="form-group row">
		<div class="col-md-6">
			<aui:input label="api-username" name="apiUsername" type="text" value="<%= digitalSignatureConfiguration.apiUsername() %>" />
		</div>

		<div class="col-md-6">
			<aui:input label="api-account-id" name="apiAccountId" type="text" value="<%= digitalSignatureConfiguration.apiAccountId() %>" />
		</div>
	</div>

	<div class="form-group row">
		<div class="col-md-6">
			<aui:input label="account's-base-uri" name="accountBaseURI" type="text" value="<%= digitalSignatureConfiguration.accountBaseURI() %>" />
		</div>

		<div class="col-md-6">
			<aui:input label="integration-key" name="integrationKey" type="text" value="<%= digitalSignatureConfiguration.integrationKey() %>" />
		</div>
	</div>

	<div class="form-group row">
		<div class="col-md-12">
			<aui:input label="rsa-private-key" name="rsaPrivateKey" type="textarea" value="<%= digitalSignatureConfiguration.rsaPrivateKey() %>" />
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

	<portlet:namespace />onChangeDigitalSignatureSiteSettingsStrategy();
</script>