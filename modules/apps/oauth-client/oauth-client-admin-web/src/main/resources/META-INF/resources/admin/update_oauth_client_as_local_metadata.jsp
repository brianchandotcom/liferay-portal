<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<portlet:actionURL name="/oauth_client_admin/update_oauth_client_as_local_metadata" var="updateOAuthClientASLocalMetadataURL">
	<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_oauth_client_as_local_metadata" />
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
				<liferay-ui:error exception="<%= DuplicateOAuthClientASLocalMetadataException.class %>" message="oauth-client-as-local-metadata-duplicate-as-local-metadata" />

				<liferay-ui:error exception="<%= OAuthClientASLocalMetadataLocalWellKnownURIException.class %>" message="oauth-client-as-local-metadata-invalid-local-well-known-uri" />

				<liferay-ui:error exception="<%= OAuthClientASLocalMetadataJSONException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientASLocalMetadataJSONException)errorException).getMessage()) %>" key="oauth-client-as-local-metadata-invalid-metadata-json-x" />
				</liferay-ui:error>

				<aui:input helpMessage="oauth-client-as-local-well-known-uri-help" label="oauth-client-as-local-well-known-uri" name="localWellKnownURI" readonly="true" type="text" />

				<aui:input helpMessage='<%= LanguageUtil.format(request, "oauth-client-as-local-well-known-uri-suffix-help", "openid-configuration", false) %>' label="oauth-client-as-local-well-known-uri-suffix" name="oAuthClientASLocalWellKnowURISuffix" readonly="true" type="text" value="openid-configuration" />

				<aui:input
					helpMessage="oauth-client-as-local-metadata-json-help"
					label="oauth-client-as-local-metadata-json"
					name="metadataJSON"
					style="min-height: 600px;"
					type="textarea"
					value='<%=
						JSONUtil.put(
							"authorization_endpoint", ""
						).put(
							"issuer", ""
						).put(
							"jwks_uri", ""
						).put(
							"subject_types_supported", JSONUtil.put("public")
						).put(
							"token_endpoint", ""
						).put(
							"userinfo_endpoint", ""
						)
					%>'
				/>

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