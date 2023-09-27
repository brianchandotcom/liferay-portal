<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

SamlIdpSpConnection samlIdpSpConnection = (SamlIdpSpConnection)request.getAttribute(SamlWebKeys.SAML_IDP_SP_CONNECTION);

long assertionLifetime = GetterUtil.getLong(request.getAttribute(SamlWebKeys.SAML_ASSERTION_LIFETIME), samlProviderConfiguration.defaultAssertionLifetime());
boolean metadataXmlUploaded = (samlIdpSpConnection != null) && Validator.isNull(samlIdpSpConnection.getMetadataUrl()) && Validator.isNotNull(samlIdpSpConnection.getMetadataXml());
%>

<clay:container-fluid
	cssClass="container-fluid container-fluid-max-xl sheet"
>
	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (samlIdpSpConnection != null) ? samlIdpSpConnection.getName() : "new-service-provider" %>'
	/>
</clay:container-fluid>

<portlet:actionURL name="/admin/update_service_provider_connection" var="updateServiceProviderConnectionURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_service_provider_connection" />
	<portlet:param name="samlIdpSpConnectionId" value='<%= (samlIdpSpConnection != null) ? String.valueOf(samlIdpSpConnection.getSamlIdpSpConnectionId()) : "" %>' />
</portlet:actionURL>

<aui:form action="<%= updateServiceProviderConnectionURL %>" cssClass="container-fluid container-fluid-max-xl sheet" enctype="multipart/form-data">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= DuplicateSamlIdpSpConnectionSamlSpEntityIdException.class %>" message="please-enter-a-unique-service-provider-entity-id" />
	<liferay-ui:error exception="<%= SamlIdpSpConnectionMetadataUrlException.class %>" message="please-enter-a-valid-metadata-endpoint-url" />
	<liferay-ui:error exception="<%= SamlIdpSpConnectionMetadataXmlException.class %>" message="please-enter-a-valid-metadata-xml" />
	<liferay-ui:error exception="<%= SamlIdpSpConnectionNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= SamlIdpSpConnectionSamlSpEntityIdException.class %>" message="please-enter-a-valid-service-provider-entity-id" />

	<aui:model-context bean="<%= samlIdpSpConnection %>" model="<%= SamlIdpSpConnection.class %>" />

	<liferay-util:dynamic-include key="com.liferay.saml.web#/admin/edit_service_provider_connection.jsp#pre" />

	<aui:fieldset label="general">
		<aui:input name="name" required="<%= true %>" />

		<aui:input helpMessage="service-provider-connection-entity-id-help" label="saml-entity-id" name="samlSpEntityId" required="<%= true %>" />

		<aui:input name="enabled" />

		<aui:input helpMessage="assertion-lifetime-help" name="assertionLifetime" required="<%= true %>" value="<%= String.valueOf(assertionLifetime) %>" />
	</aui:fieldset>

	<aui:fieldset label="encryption">
		<aui:input name="encryptionForced" />
	</aui:fieldset>

	<aui:fieldset helpMessage="service-provider-metadata-help" label="metadata">
		<c:if test="<%= metadataXmlUploaded %>">
			<div class="portlet-msg-alert">
				<liferay-ui:message key="the-connected-provider-is-configured-through-an-uploaded-metadata-file" />
			</div>
		</c:if>

		<aui:input checked="<%= !metadataXmlUploaded %>" label="connect-to-a-metadata-url" name="metadataDelivery" onClick='<%= liferayPortletResponse.getNamespace() + "uploadMetadataXml(false);" %>' type="radio" value="metadataUrl" />
		<aui:input checked="<%= metadataXmlUploaded %>" id="metadataDeliveryXml" label="upload-metadata-xml" name="metadataDelivery" onClick='<%= liferayPortletResponse.getNamespace() + "uploadMetadataXml(true);" %>' type="radio" value="metadataXml" />

		<br />

		<div class="" id="<portlet:namespace />metadataUrlForm">
			<aui:input name="metadataUrl" />
		</div>

		<div class="hide" id="<portlet:namespace />uploadMetadataXmlForm">
			<aui:input name="metadataXml" type="file" />
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
	window['<portlet:namespace />uploadMetadataXml'] = function (selected) {
		var metadataUrlForm = document.getElementById(
			'<portlet:namespace />metadataUrlForm'
		);
		var metadataXmlForm = document.getElementById(
			'<portlet:namespace />uploadMetadataXmlForm'
		);

		if (selected) {
			metadataUrlForm.classList.add('hide');
			metadataXmlForm.classList.remove('hide');
		}
		else {
			metadataUrlForm.classList.remove('hide');
			metadataXmlForm.classList.add('hide');
		}
	};

	<portlet:namespace />uploadMetadataXml(
		document.getElementById('<portlet:namespace />metadataDeliveryXml').checked
	);
</aui:script>