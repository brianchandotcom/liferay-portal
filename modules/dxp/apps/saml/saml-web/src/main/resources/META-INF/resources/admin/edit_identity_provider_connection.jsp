<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

SamlSpIdpConnection samlSpIdpConnection = (SamlSpIdpConnection)request.getAttribute(SamlWebKeys.SAML_SP_IDP_CONNECTION);

long clockSkew = GetterUtil.getLong(request.getAttribute(SamlWebKeys.SAML_CLOCK_SKEW), samlProviderConfiguration.clockSkew());
%>

<div class="container-fluid-1280">
	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (samlSpIdpConnection != null) ? samlSpIdpConnection.getName() : "new-identity-provider" %>'
	/>
</div>

<portlet:actionURL name="/admin/updateIdentityProviderConnection" var="updateIdentityProviderConnectionURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_identity_provider_connection" />
	<portlet:param name="samlSpIdpConnectionId" value='<%= (samlSpIdpConnection != null) ? String.valueOf(samlSpIdpConnection.getSamlSpIdpConnectionId()) : "" %>' />
</portlet:actionURL>

<aui:form action="<%= updateIdentityProviderConnectionURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= DuplicateSamlSpIdpConnectionSamlIdpEntityIdException.class %>" message="please-enter-a-unique-identity-provider-entity-id" />
	<liferay-ui:error exception="<%= SamlSpIdpConnectionMetadataUrlException.class %>" message="please-enter-a-valid-metadata-endpoint-url" />
	<liferay-ui:error exception="<%= SamlSpIdpConnectionMetadataXmlException.class %>" message="please-enter-a-valid-metadata-xml" />
	<liferay-ui:error exception="<%= SamlSpIdpConnectionSamlIdpEntityIdException.class %>" message="please-enter-a-valid-identity-provider-entity-id" />

	<aui:model-context bean="<%= samlSpIdpConnection %>" model="<%= SamlSpIdpConnection.class %>" />

	<liferay-util:dynamic-include key="com.liferay.saml.web#/admin/edit_identity_provider_connection.jsp#pre" />

	<aui:fieldset label="general">
		<aui:input name="name" required="<%= true %>" />

		<aui:input helpMessage="identity-provider-connection-entity-id-help" label="saml-entity-id" name="samlIdpEntityId" required="<%= true %>" />

		<aui:input name="enabled" />

		<aui:input helpMessage="saml-sp-clock-skew-description" label="saml-sp-clock-skew" name="clockSkew" value="<%= String.valueOf(clockSkew) %>" />

		<aui:input helpMessage="force-authn-help" name="forceAuthn" />

		<aui:input helpMessage="unknown-users-are-strangers-help" name="unknownUsersAreStrangers" />
	</aui:fieldset>

	<aui:fieldset helpMessage="identity-provider-metadata-help" label="metadata">
		<aui:input name="metadataUrl" />

		<aui:button-row>
			<aui:button onClick='<%= renderResponse.getNamespace() + "uploadMetadataXml();" %>' value="upload-metadata-xml" />
		</aui:button-row>

		<div class="hide" id="<portlet:namespace />uploadMetadataXmlForm">
			<aui:fieldset label="upload-metadata">
				<aui:input name="metadataXml" type="file" />
			</aui:fieldset>
		</div>
	</aui:fieldset>

	<aui:fieldset label="name-identifier">
		<aui:select label="name-identifier-format" name="nameIdFormat">
			<aui:option label="email-address" value="<%= nameIdTypeValues.getEmail() %>" />
			<aui:option label="encrypted" value="<%= nameIdTypeValues.getEncrypted() %>" />
			<aui:option label="entity" value="<%= nameIdTypeValues.getEntity() %>" />
			<aui:option label="kerberos" value="<%= nameIdTypeValues.getKerberos() %>" />
			<aui:option label="persistent" value="<%= nameIdTypeValues.getPersistent() %>" />
			<aui:option label="transient" value="<%= nameIdTypeValues.getTransient() %>" />
			<aui:option label="unspecified" value="<%= nameIdTypeValues.getUnspecified() %>" />
			<aui:option label="windows-domain-qualified-name" value="<%= nameIdTypeValues.getWinDomainQualified() %>" />
			<aui:option label="x509-subject-name" value="<%= nameIdTypeValues.getX509Subject() %>" />
		</aui:select>
	</aui:fieldset>

	<aui:fieldset label="attributes">
		<aui:input helpMessage="attribute-mapping-help" label="attribute-mapping" name="userAttributeMappings" />
	</aui:fieldset>

	<liferay-util:dynamic-include key="com.liferay.saml.web#/admin/edit_identity_provider_connection.jsp#post" />

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />uploadMetadataXml',
		function() {
			var A = AUI();

			var uploadMetadataXmlForm = A.one(
				'#<portlet:namespace />uploadMetadataXmlForm'
			);

			if (uploadMetadataXmlForm) {
				uploadMetadataXmlForm.show();
			}
		},
		['aui-base']
	);
</aui:script>