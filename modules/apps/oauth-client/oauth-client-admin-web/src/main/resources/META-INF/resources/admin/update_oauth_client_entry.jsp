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

<portlet:actionURL name="/oauth_client_admin/update_oauth_client_entry" var="updateOAuthClientEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_oauth_client_entry" />
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
				<liferay-ui:error exception="<%= DuplicateOAuthClientEntryException.class %>" message="oauth-client-duplicate-client" />

				<liferay-ui:error exception="<%= OAuthClientEntryAuthRequestParametersJSONException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientEntryAuthRequestParametersJSONException)errorException).getMessage()) %>" key="oauth-client-invalid-auth-request-parameters-json-x" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= OAuthClientEntryAuthServerWellKnownURIException.class %>" message="oauth-client-invalid-auth-server-well-known-uri" />

				<liferay-ui:error exception="<%= OAuthClientEntryInfoJSONException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientEntryInfoJSONException)errorException).getMessage()) %>" key="oauth-client-invalid-info-json-x" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= OAuthClientEntryOIDCUserInfoMapperJSONException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientEntryOIDCUserInfoMapperJSONException)errorException).getMessage()) %>" key="oauth-client-invalid-oidc-user-info-mapper-json-x" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= OAuthClientEntryTokenRequestParametersJSONException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientEntryTokenRequestParametersJSONException)errorException).getMessage()) %>" key="oauth-client-invalid-token-request-parameters-json-x" />
				</liferay-ui:error>

				<h3 class="sheet-subtitle"><liferay-ui:message key="oauth-client-configurations" /></h3>

				<aui:input helpMessage="oauth-client-as-well-known-uri-help" label="oauth-client-as-well-known-uri" name="authServerWellKnownURI" type="text" />

				<aui:input
					helpMessage="oauth-client-info-json-help"
					label="oauth-client-info-json"
					name="infoJSON"
					style="min-height: 600px;"
					type="textarea"
					value='<%=
						JSONUtil.put(
							"client_id", ""
						).put(
							"client_name", "example_client"
						).put(
							"client_secret", ""
						).put(
							"redirect_uris", JSONUtil.put("")
						).put(
							"scope", "openid email profile"
						).put(
							"subject_type", "public"
						)
					%>'
				/>

				<aui:input name="oAuthClientEntryId" type="hidden" value="<%= (oAuthClientEntry != null) ? oAuthClientEntry.getOAuthClientEntryId() : 0 %>" />

				<aui:input
					helpMessage='<%= LanguageUtil.format(request, "oauth-client-default-auth-request-parameters-json-help", "https://www.iana.org/assignments/oauth-parameters", false) %>'
					label="oauth-client-default-auth-request-parameters-json"
					name="authRequestParametersJSON"
					style="min-height: 200px;"
					type="textarea"
					value='<%=
						JSONUtil.put(
							"custom_request_parameters", JSONUtil.put("example_key", JSONUtil.put(""))
						).put(
							"resource", JSONUtil.put("")
						).put(
							"response_type", "code"
						).put(
							"scope", "openid email profile"
						)
					%>'
				/>

				<aui:input
					helpMessage='<%= LanguageUtil.format(request, "oauth-client-default-token-request-parameters-json-help", "https://www.iana.org/assignments/oauth-parameters", false) %>'
					label="oauth-client-default-token-request-parameters-json"
					name="tokenRequestParametersJSON"
					style="min-height: 200px;"
					type="textarea"
					value='<%=
						JSONUtil.put(
							"custom_request_parameters", JSONUtil.put("example_key", JSONUtil.put(""))
						).put(
							"resource", JSONUtil.put("")
						)
					%>'
				/>

				<h3 class="sheet-subtitle"><liferay-ui:message key="oauth-client-oidc-specific-configurations" /></h3>

				<aui:input helpMessage="oauth-client-oidc-user-info-mapper-json-help" label="oauth-client-oidc-user-info-mapper-json" name="OIDCUserInfoMapperJSON" style="min-height: 400px;" type="textarea" value="<%= OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON %>" />

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

		var authRequestParametersJSON = document.getElementById(
			'<portlet:namespace />authRequestParametersJSON'
		).value;

		try {
			authRequestParametersJSON = JSON.stringify(
				JSON.parse(authRequestParametersJSON),
				null,
				0
			);
		}
		catch (e) {
			alert('Ill-formatted Default Authorization Request Parameters JSON');
			return;
		}

		document.getElementById(
			'<portlet:namespace />authRequestParametersJSON'
		).value = authRequestParametersJSON;

		document.getElementById('<portlet:namespace />infoJSON').value = infoJSON;

		var tokenRequestParametersJSON = document.getElementById(
			'<portlet:namespace />tokenRequestParametersJSON'
		).value;

		try {
			tokenRequestParametersJSON = JSON.stringify(
				JSON.parse(tokenRequestParametersJSON),
				null,
				0
			);
		}
		catch (e) {
			alert('Ill-formatted Default Token Request Parameters JSON');
			return;
		}

		document.getElementById(
			'<portlet:namespace />tokenRequestParametersJSON'
		).value = tokenRequestParametersJSON;

		var oidcUserInfoMapperJSON = document.getElementById(
			'<portlet:namespace />OIDCUserInfoMapperJSON'
		).value;

		try {
			oidcUserInfoMapperJSON = JSON.stringify(
				JSON.parse(oidcUserInfoMapperJSON),
				null,
				0
			);
		}
		catch (e) {
			alert('Ill-formatted OIDC User Info Mapper JSON');
			return;
		}

		document.getElementById(
			'<portlet:namespace />OIDCUserInfoMapperJSON'
		).value = oidcUserInfoMapperJSON;

		submitForm(
			document.getElementById('<portlet:namespace />oauth-client-entry-fm')
		);
	}

	function <portlet:namespace />init() {
		var infoJSON = document.getElementById('<portlet:namespace />infoJSON');

		infoJSON.value = JSON.stringify(JSON.parse(infoJSON.value), null, 4);

		var authRequestParametersJSON = document.getElementById(
			'<portlet:namespace />authRequestParametersJSON'
		);

		authRequestParametersJSON.value = JSON.stringify(
			JSON.parse(authRequestParametersJSON.value),
			null,
			4
		);

		var tokenRequestParametersJSON = document.getElementById(
			'<portlet:namespace />tokenRequestParametersJSON'
		);

		tokenRequestParametersJSON.value = JSON.stringify(
			JSON.parse(tokenRequestParametersJSON.value),
			null,
			4
		);

		var oidcUserInfoMapperJSON = document.getElementById(
			'<portlet:namespace />OIDCUserInfoMapperJSON'
		);

		oidcUserInfoMapperJSON.value = JSON.stringify(
			JSON.parse(oidcUserInfoMapperJSON.value),
			null,
			4
		);
	}
</aui:script>