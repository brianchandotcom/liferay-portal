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
String redirect = ParamUtil.getString(request, "redirect");

OAuthClientASLocalMetadata oAuthClientASLocalMetadata = (OAuthClientASLocalMetadata)request.getAttribute(OAuthClientASLocalMetadata.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((oAuthClientASLocalMetadata == null) ? LanguageUtil.get(request, "new-oauth-client-as-local-metadata") : LanguageUtil.get(request, "edit-oauth-client-as-local-metadata"));
%>

<portlet:actionURL name="/oauth_client_admin/update_o_auth_client_as_local_metadata" var="updateOAuthClientASLocalMetadataURL">
	<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_o_auth_client_as_local_metadata" />
	<portlet:param name="redirect" value="<%= HtmlUtil.escape(redirect) %>" />
</portlet:actionURL>

<aui:form action="<%= updateOAuthClientASLocalMetadataURL %>" id="oauth-client-as-fm" method="post" name="oauth-client-as-fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:model-context bean="<%= oAuthClientASLocalMetadata %>" model="<%= OAuthClientASLocalMetadata.class %>" />

	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="sheet">
			<aui:fieldset>
				<aui:input helpMessage="oauth-client-as-local-well-known-uri-help" label="oauth-client-as-local-well-known-uri" name="localWellKnownURI" readonly="true" type="text" />

				<aui:input helpMessage='<%= LanguageUtil.format(request, "oauth-client-as-local-well-known-uri-suffix-help", "openid-configuration", false) %>' label="oauth-client-as-local-well-known-uri-suffix" name="oAuthClientASLocalWellKnowURISuffix" readonly="true" type="text" value="openid-configuration" />

				<aui:input helpMessage="oauth-client-as-local-metadata-json-help" label="oauth-client-as-local-metadata-json" name="metadataJSON" style="min-height: 600px;" type="textarea" value='{"issuer":"","authorization_endpoint":"","token_endpoint":"","userinfo_endpoint":""}' />

				<aui:button-row>
					<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "doSubmit();" %>' type="submit" />
					<aui:button href="<%= HtmlUtil.escape(redirect) %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</div>
	</clay:container-fluid>
</aui:form>

<aui:script>
	<portlet:namespace />init();

	function <portlet:namespace />doSubmit() {
		var form = document.getElementById(
			'<portlet:namespace />oauth-client-as-fm'
		);

		var metadataJSON = document.getElementById(
			'<portlet:namespace />metadataJSON'
		).value;

		try {
			metadataJSON = JSON.stringify(JSON.parse(metadataJSON), null, 0);
		}
		catch (e) {
			alert('Ill-formatted Metadata JSON');
			return;
		}

		document.getElementById(
			'<portlet:namespace />metadataJSON'
		).value = metadataJSON;

		submitForm(form);
	}

	function <portlet:namespace />init() {
		var metadataJSON = document.getElementById(
			'<portlet:namespace />metadataJSON'
		);

		metadataJSON.value = JSON.stringify(
			JSON.parse(metadataJSON.value),
			null,
			4
		);
	}
</aui:script>