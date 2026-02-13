<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String authorizationEndpoint = (String)request.getAttribute("authorizationEndpoint");
String jwksURI = (String)request.getAttribute("jwksURI");
String redirect = ParamUtil.getString(request, "redirect");
String supportedGrantTypes = (String)request.getAttribute("supportedGrantTypes");
String supportedScopes = (String)request.getAttribute("supportedScopes");
String supportedSubjectTypes = (String)request.getAttribute("supportedSubjectTypes");
String tokenEndpoint = (String)request.getAttribute("tokenEndpoint");
String userInfoEndpoint = (String)request.getAttribute("userInfoEndpoint");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

OAuthClientASLocalMetadata oAuthClientASLocalMetadata = (OAuthClientASLocalMetadata)request.getAttribute(OAuthClientASLocalMetadata.class.getName());

renderResponse.setTitle((oAuthClientASLocalMetadata == null) ? LanguageUtil.get(request, "new-oauth-client-as-local-metadata") : LanguageUtil.get(request, "edit-oauth-client-as-local-metadata"));

JSONObject metadataJSONObject = JSONUtil.put(
	"authorizationEndpoint", ""
).put(
	"issuer", ""
).put(
	"jwks_uri", ""
).put(
	"tokenEndpoint", ""
);
%>

<portlet:actionURL name="/oauth_client_admin/update_oauth_client_as_local_metadata" var="updateOAuthClientASLocalMetadataURL">
	<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_oauth_client_as_local_metadata" />
</portlet:actionURL>

<aui:form action="<%= updateOAuthClientASLocalMetadataURL %>" id="oauth-client-as-fm" method="post" name="oauth-client-as-fm" onSubmit="event.preventDefault();">
	<aui:input name="oAuthClientASLocalMetadataId" type="hidden" value="<%= (oAuthClientASLocalMetadata != null) ? oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId() : StringPool.BLANK %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:model-context bean="<%= oAuthClientASLocalMetadata %>" model="<%= OAuthClientASLocalMetadata.class %>" />

	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="sheet">
			<aui:fieldset>
				<liferay-ui:error exception="<%= DuplicateOAuthClientASLocalMetadataException.class %>" message="oauth-client-as-local-metadata-duplicate-as-local-metadata" />

				<liferay-ui:error exception="<%= DuplicateOAuthClientEntryException.class %>" message="oauth-client-as-local-metadata-duplicate-as-issuer" />

				<liferay-ui:error exception="<%= OAuthClientASLocalMetadataIssuerException.class %>" message="oauth-client-as-local-metadata-issuer-cannot-be-empty" />

				<liferay-ui:error exception="<%= OAuthClientASLocalMetadataLocalWellKnownURIException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientASLocalMetadataLocalWellKnownURIException)errorException).getMessage()) %>" key="oauth-client-as-local-metadata-invalid-https-uri-x" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= OAuthClientASLocalMetadataMetadataJSONException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientASLocalMetadataMetadataJSONException)errorException).getMessage()) %>" key="oauth-client-as-local-metadata-invalid-metadata-json-x" />
				</liferay-ui:error>

				<aui:fieldset label="general">
					<aui:input helpMessage="oauth-client-as-local-metadata-issuer-help" label="oauth-client-as-local-metadata-issuer" name="issuer" required="<%= true %>" type="text" />
					<aui:input helpMessage="oauth-client-as-local-metadata-supported-scopes-help" label="oauth-client-as-local-metadata-supported-scopes" name="supportedScopes" type="text" value="<%= supportedScopes %>" />

					<aui:input helpMessage="oauth-client-as-local-metadata-supported-grant-types-help" label="oauth-client-as-local-metadata-supported-grant-types" name="supportedGrantTypes" type="text" value="<%= supportedGrantTypes %>" />

					<aui:input label="oauth-client-as-local-metadata-authorization-endpoint" name="authorizationEndpoint" type="text" value="<%= authorizationEndpoint %>" />

					<aui:input label="oauth-client-as-local-metadata-jwks-uri" name="jwksURI" type="text" value="<%= jwksURI %>" />

					<aui:input label="oauth-client-as-local-metadata-token-endpoint" name="tokenEndpoint" type="text" value="<%= tokenEndpoint %>" />
				</aui:fieldset>

				<aui:fieldset label="oauth-client-as-local-oauth-authorization-server">
					<aui:input checked="<%= (oAuthClientASLocalMetadata != null) ? oAuthClientASLocalMetadata.getLocalWellKnownEnabled() : false %>" label="enable" name="enabledLocalWellKnown" type="checkbox" />

					<aui:input helpMessage="oauth-client-as-local-well-known-uri-oauth-authorization-server-help" label="oauth-client-as-local-well-known-uri-oauth-authorization-server" name="oAuthASLocalWellKnownURI" readonly="true" type="text" value="<%= (oAuthClientASLocalMetadata != null) ? oAuthClientASLocalMetadata.getOAuthASLocalWellKnownURI() : \"\" %>" />

					<aui:input helpMessage="oauth-client-as-local-metadata-json-oauth-authorization-server-help" label="oauth-client-as-local-metadata-json-oauth-authorization-server" name="oAuthASMetadataJSON" readonly="true" style="min-height: 600px;" type="textarea" value="<%= (oAuthClientASLocalMetadata != null) ? oAuthClientASLocalMetadata.getOAuthASMetadataJSON() : metadataJSONObject.toString() %>" />
				</aui:fieldset>

				<aui:fieldset label="oauth-client-as-local-openid-configuration">
					<aui:input label="oauth-client-as-local-metadata-subject-types-supported" name="supportedSubjectTypes" type="text" value='<%= (oAuthClientASLocalMetadata != null) ? supportedSubjectTypes : "public" %>' />

					<aui:input label="oauth-client-as-local-metadata-userinfo-endpoint" name="userInfoEndpoint" type="text" value="<%= userInfoEndpoint %>" />

					<aui:input helpMessage="oauth-client-as-local-well-known-uri-openid-configuration-help" label="oauth-client-as-local-well-known-uri-openid-configuration" name="localWellKnownURI" readonly="true" type="text" />

					<aui:input
						helpMessage="oauth-client-as-local-metadata-json-openid-configuration-help"
						label="oauth-client-as-local-metadata-json-openid-configuration"
						name="metadataJSON"
						readonly="true"
						style="min-height: 600px;"
						type="textarea"
						value='<%=
							JSONUtil.put(
								"authorizationEndpoint", ""
							).put(
								"issuer", ""
							).put(
								"jwks_uri", ""
							).put(
								"subject_types_supported", JSONUtil.put("public")
							).put(
								"tokenEndpoint", ""
							).put(
								"userInfoEndpoint", ""
							)
						%>'
					/>
				</aui:fieldset>

				<aui:button-row>
					<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "doSubmit();" %>' type="submit" />
					<aui:button href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</div>
	</clay:container-fluid>
</aui:form>

<aui:script>
	<portlet:namespace />init();

	function <portlet:namespace />doSubmit() {
		var oAuthASMetadataJSON = document.getElementById(
			'<portlet:namespace />oAuthASMetadataJSON'
		).value;

		try {
			oAuthASMetadataJSON = JSON.stringify(
				JSON.parse(oAuthASMetadataJSON),
				null,
				0
			);
		}
		catch (e) {
			console.error('Ill-formatted Metadata JSON');
			return;
		}

		document.getElementById('<portlet:namespace />oAuthASMetadataJSON').value =
			oAuthASMetadataJSON;

		var metadataJSON = document.getElementById(
			'<portlet:namespace />metadataJSON'
		).value;

		try {
			metadataJSON = JSON.stringify(JSON.parse(metadataJSON), null, 0);
		}
		catch (e) {
			console.error('Ill-formatted Metadata JSON');
			return;
		}

		document.getElementById('<portlet:namespace />metadataJSON').value =
			metadataJSON;

		var form = document.getElementById(
			'<portlet:namespace />oauth-client-as-fm'
		);

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

		var oAuthASMetadataJSON = document.getElementById(
			'<portlet:namespace />oAuthASMetadataJSON'
		);

		oAuthASMetadataJSON.value = JSON.stringify(
			JSON.parse(oAuthASMetadataJSON.value),
			null,
			4
		);
	}
</aui:script>