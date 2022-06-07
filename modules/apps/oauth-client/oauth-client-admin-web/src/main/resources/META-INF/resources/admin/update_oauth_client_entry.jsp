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

OAuthClientEntry oAuthClientEntry = (OAuthClientEntry)request.getAttribute(OAuthClientEntry.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((oAuthClientEntry == null) ? LanguageUtil.get(request, "new-oauth-client") : LanguageUtil.get(request, "edit-oauth-client"));
%>

<portlet:actionURL name="/oauth_client_admin/update_o_auth_client_entry" var="updateOAuthClientEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_o_auth_client_entry" />
	<portlet:param name="redirect" value="<%= HtmlUtil.escape(redirect) %>" />
</portlet:actionURL>

<aui:form action="<%= updateOAuthClientEntryURL %>" id="oauth-client-entry-fm" method="post" name="oauth-client-entry-fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:model-context bean="<%= oAuthClientEntry %>" model="<%= OAuthClientEntry.class %>" />

	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="sheet">
			<aui:fieldset>
				<aui:input helpMessage="oauth-client-as-well-known-uri-help" label="oauth-client-as-well-known-uri" name="authServerWellKnownURI" type="text" />

				<aui:input helpMessage="oauth-client-info-json-help" label="oauth-client-info-json" name="infoJSON" style="min-height: 600px;" type="textarea" value='{"client_id":"","client_secret":"","token_endpoint_auth_method":"client_secret_basic","redirect_uris":["",""],"client_name":"example_client","grant_types":["authorization_code"],"scope":"openid email profile","subject_type":"public","id_token_signed_response_alg":"RS256"}' />

				<aui:input name="oAuthClientEntryId" type="hidden" />

				<aui:input helpMessage="oauth-client-parameters-json-help" label="oauth-client-parameters-json" name="parametersJSON" style="min-height: 200px;" type="textarea" value='{"authorization_request_parameters":{"resource":["resource1","resource2"]},"token_request_parameters":{"audience":"audience1","resource":["resource1","resource2"]}}' />

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
		var infoJSON = document.getElementById('<portlet:namespace />infoJSON')
			.value;

		try {
			infoJSON = JSON.stringify(JSON.parse(infoJSON), null, 0);
		}
		catch (e) {
			alert('Ill-formatted Info JSON');
			return;
		}

		document.getElementById('<portlet:namespace />infoJSON').value = infoJSON;

		var parametersJSON = document.getElementById(
			'<portlet:namespace />parametersJSON'
		).value;

		try {
			parametersJSON = JSON.stringify(JSON.parse(parametersJSON), null, 0);
		}
		catch (e) {
			alert('Ill-formatted Parameters JSON');
			return;
		}

		document.getElementById(
			'<portlet:namespace />parametersJSON'
		).value = parametersJSON;

		submitForm(document.getElementById('<portlet:namespace />oauth-client-entry-fm'));
	}

	function <portlet:namespace />init() {
		var infoJSON = document.getElementById('<portlet:namespace />infoJSON');

		infoJSON.value = JSON.stringify(JSON.parse(infoJSON.value), null, 4);

		var parametersJSON = document.getElementById(
			'<portlet:namespace />parametersJSON'
		);

		parametersJSON.value = JSON.stringify(
			JSON.parse(parametersJSON.value),
			null,
			4
		);
	}
</aui:script>