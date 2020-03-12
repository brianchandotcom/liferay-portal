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
UnicodeProperties properties = PropertiesParamUtil.getProperties(request, "settings--");

String entityId = properties.getProperty(PortletPropsKeys.SAML_ENTITY_ID, (String)request.getAttribute(SamlWebKeys.SAML_ENTITY_ID));

GeneralTabDefaultViewDisplayContext.X509CertificateStatus x509CertificateStatus = generalTabDefaultViewDisplayContext.getX509CertificateStatus();

boolean keystoreException = x509CertificateStatus.getStatus() == GeneralTabDefaultViewDisplayContext.X509CertificateStatus.Status.SAML_KEYSTORE_EXCEPTION;
boolean keystoreIncorrectPassword = x509CertificateStatus.getStatus() == GeneralTabDefaultViewDisplayContext.X509CertificateStatus.Status.SAML_KEYSTORE_PASSWORD_INCORRECT;
String samlRole = properties.getProperty(PortletPropsKeys.SAML_ROLE, samlProviderConfiguration.role());
%>

<portlet:actionURL name="/admin/updateGeneral" var="updateGeneralURL">
	<portlet:param name="tabs1" value="general" />
</portlet:actionURL>

<aui:form action="<%= updateGeneralURL %>">
	<liferay-ui:error key="certificateInvalid" message="please-create-a-signing-credential-before-enabling" />
	<liferay-ui:error key="entityIdInUse" message="saml-must-be-disabled-before-changing-the-entity-id" />
	<liferay-ui:error key="entityIdTooLong" message="entity-id-too-long" />
	<liferay-ui:error key="idpRoleNotConfigurable" message="the-identity-provider-role-has-been-disabled-please-re-enable-it" />
	<liferay-ui:error key="roleInUse" message="saml-must-be-disabled-before-changing-the-saml-role" />

	<aui:fieldset>
		<aui:input label="enabled" name='<%= "settings--" + PortletPropsKeys.SAML_ENABLED + "--" %>' type="checkbox" value="<%= samlProviderConfigurationHelper.isEnabled() %>" />

		<c:if test="<%= !generalTabDefaultViewDisplayContext.isRoleIdPAvailable() && StringUtil.equalsIgnoreCase(samlProviderConfiguration.role(), SamlProviderConfigurationKeys.SAML_ROLE_IDP) %>">
			<div class="portlet-msg-info">
				<liferay-ui:message key="the-identity-provider-role-has-been-disabled-please-re-enable-it-in-system-settings" />
			</div>
		</c:if>

		<c:if test="<%= StringUtil.equalsIgnoreCase(samlProviderConfiguration.role(), SamlProviderConfigurationKeys.SAML_ROLE_SP) && !localEntityManager.hasDefaultIdpRole() %>">
			<div class="portlet-msg-info">
				<liferay-ui:message key="you-must-configure-at-least-one-identity-provider-connection-for-saml-to-function" />
			</div>
		</c:if>

		<aui:select label="saml-role" name='<%= "settings--" + PortletPropsKeys.SAML_ROLE + "--" %>' required="<%= true %>" showEmptyOption="<%= true %>">
			<aui:option label="identity-provider" selected="<%= samlRole.equals(SamlProviderConfigurationKeys.SAML_ROLE_IDP) %>" value="<%= SamlProviderConfigurationKeys.SAML_ROLE_IDP %>" />
			<aui:option label="service-provider" selected="<%= samlRole.equals(SamlProviderConfigurationKeys.SAML_ROLE_SP) %>" value="<%= SamlProviderConfigurationKeys.SAML_ROLE_SP %>" />
		</aui:select>

		<aui:input helpMessage="entity-id-help" label="saml-entity-id" name='<%= "settings--" + PortletPropsKeys.SAML_ENTITY_ID + "--" %>' required="<%= true %>" value="<%= entityId %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>

<portlet:actionURL name="/admin/updateCertificate" var="updateCertificateURL">
	<portlet:param name="tabs1" value="general" />
</portlet:actionURL>

<c:choose>
	<c:when test="<%= keystoreException %>">
		<div class="portlet-msg-error">
			<liferay-ui:message key="keystore-exception" />
		</div>
	</c:when>
	<c:when test="<%= keystoreIncorrectPassword %>">
		<div class="portlet-msg-error">
			<liferay-ui:message key="keystore-password-incorrect" />
		</div>
	</c:when>
	<c:when test="<%= Validator.isNotNull(entityId) %>">
		<aui:fieldset label="certificate-and-private-key">
			<liferay-util:include page="/admin/certificate_info.jsp" servletContext="<%= application %>">
				<liferay-util:param name="certificateUsage" value="<%= LocalEntityManager.CertificateUsage.SIGNING.name() %>" />
			</liferay-util:include>
		</aui:fieldset>

		<c:if test="<%= StringUtil.equalsIgnoreCase(samlProviderConfiguration.role(), SamlProviderConfigurationKeys.SAML_ROLE_SP) %>">
			<aui:fieldset label="encryption-certificate-and-private-key">
				<liferay-util:include page="/admin/certificate_info.jsp" servletContext="<%= application %>">
					<liferay-util:param name="certificateUsage" value="<%= LocalEntityManager.CertificateUsage.ENCRYPTION.name() %>" />
				</liferay-util:include>
			</aui:fieldset>
		</c:if>
	</c:when>
</c:choose>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />showCertificateDialog',
		function(uri) {
			var dialog = Liferay.Util.Window.getWindow({
				id: '<portlet:namespace />certificateDialog',
				title:
					'<%= UnicodeLanguageUtil.get(request, "certificate-and-private-key") %>',
				uri: uri,
				dialog: {
					cache: false,
					modal: true,
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer',
				},
			});
		},
		['aui-io', 'aui-io-plugin-deprecated', 'liferay-util-window']
	);

	<portlet:renderURL var="refreshViewURL" />

	Liferay.provide(
		window,
		'<portlet:namespace />closeDialog',
		function(dialogId, stateChange) {
			var namespace = window.parent.namespace;
			var dialog = Liferay.Util.getWindow(dialogId);
			dialog.destroy();
			if (stateChange) {
				window.location.replace('<%= HtmlUtil.escapeJS(refreshViewURL) %>');
			}
		},
		['aui-base', 'aui-dialog', 'aui-dialog-iframe']
	);
</aui:script>