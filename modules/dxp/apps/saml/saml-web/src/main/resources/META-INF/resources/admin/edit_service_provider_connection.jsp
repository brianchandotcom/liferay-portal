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

SamlIdpSpConnection samlIdpSpConnection = (SamlIdpSpConnection)request.getAttribute(SamlWebKeys.SAML_IDP_SP_CONNECTION);

long assertionLifetime = GetterUtil.getLong(request.getAttribute(SamlWebKeys.SAML_ASSERTION_LIFETIME), samlProviderConfiguration.defaultAssertionLifetime());
%>

<div class="container-fluid-1280">
	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (samlIdpSpConnection != null) ? samlIdpSpConnection.getName() : "new-service-provider" %>'
	/>
</div>

<portlet:actionURL name="/admin/updateServiceProviderConnection" var="updateServiceProviderConnectionURL">
	<portlet:param name="mvcPath" value="/admin/edit_service_provider_connection.jsp" />
</portlet:actionURL>

<aui:form action="<%= updateServiceProviderConnectionURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= DuplicateSamlIdpSpConnectionSamlSpEntityIdException.class %>" message="please-enter-a-unique-service-provider-entity-id" />
	<liferay-ui:error exception="<%= SamlIdpSpConnectionMetadataUrlException.class %>" message="please-enter-a-valid-metadata-endpoint-url" />
	<liferay-ui:error exception="<%= SamlIdpSpConnectionMetadataXmlException.class %>" message="please-enter-a-valid-metadata-xml" />
	<liferay-ui:error exception="<%= SamlIdpSpConnectionNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= SamlIdpSpConnectionSamlSpEntityIdException.class %>" message="please-enter-a-valid-service-provider-entity-id" />

	<aui:model-context bean="<%= samlIdpSpConnection %>" model="<%= SamlIdpSpConnection.class %>" />

	<aui:input name="samlIdpSpConnectionId" type="hidden" />

	<liferay-util:dynamic-include key="com.liferay.saml.web#/admin/edit_service_provider_connection.jsp#pre" />

	<aui:fieldset label="general">
		<aui:input name="name" required="<%= true %>" />

		<aui:input helpMessage="service-provider-connection-entity-id-help" label="saml-entity-id" name="samlSpEntityId" required="<%= true %>" />

		<aui:input name="enabled" />

		<aui:input helpMessage="assertion-lifetime-help" name="assertionLifetime" required="<%= true %>" value="<%= String.valueOf(assertionLifetime) %>" />
	</aui:fieldset>

	<aui:fieldset helpMessage="service-provider-metadata-help" label="metadata">
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

		<aui:input helpMessage="name-identifier-attribute-name-help" label="name-identifier-attribute-name" name="nameIdAttribute" required="<%= true %>" />
	</aui:fieldset>

	<aui:fieldset label="attributes">
		<aui:input name="attributesEnabled" />

		<aui:input helpMessage="attributes-namespace-enabled-help" name="attributesNamespaceEnabled" />

		<aui:input helpMessage="attributes-help" label="attributes" name="attributeNames" />
	</aui:fieldset>

	<liferay-util:dynamic-include key="com.liferay.saml.web#/admin/edit_service_provider_connection.jsp#post" />

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

			var uploadMetadataXmlForm = A.one('#<portlet:namespace />uploadMetadataXmlForm');

			if (uploadMetadataXmlForm) {
				uploadMetadataXmlForm.show();
			}
		},
		['aui-base']
	);
</aui:script>