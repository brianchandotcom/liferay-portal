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

CertificateTool certificateTool = (CertificateTool)request.getAttribute(SamlWebKeys.SAML_CERTIFICATE_TOOL);

X509Certificate x509Certificate = (X509Certificate)request.getAttribute(SamlWebKeys.SAML_X509_CERTIFICATE);

boolean certificateAuthNeeded = GetterUtil.getBoolean(request.getAttribute(SamlWebKeys.SAML_X509_CERTIFICATE_AUTH_NEEDED));
%>

<portlet:actionURL name="/admin/updateGeneral" var="updateGeneralURL">
	<portlet:param name="tabs1" value="general" />
</portlet:actionURL>

<aui:form action="<%= updateGeneralURL %>">
	<liferay-ui:error key="certificateInvalid" message="please-create-a-signing-credential-before-enabling" />
	<liferay-ui:error key="entityIdInUse" message="saml-must-be-disabled-before-changing-the-entity-id" />
	<liferay-ui:error key="entityIdTooLong" message="entity-id-too-long" />
	<liferay-ui:error key="identityProviderInvalid" message="please-configure-identity-provider-before-enabling" />

	<aui:fieldset>
		<aui:input label="enabled" name='<%= "settings--" + PortletPropsKeys.SAML_ENABLED + "--" %>' type="checkbox" value="<%= samlProviderConfigurationHelper.isEnabled() %>" />

		<aui:select label="saml-role" name='<%= "settings--" + PortletPropsKeys.SAML_ROLE + "--" %>' required="<%= true %>" showEmptyOption="<%= true %>">

			<%
			String samlRole = properties.getProperty(PortletPropsKeys.SAML_ROLE, samlProviderConfiguration.role());
			%>

			<aui:option label="identity-provider" selected='<%= samlRole.equals("idp") %>' value="idp" />
			<aui:option label="service-provider" selected='<%= samlRole.equals("sp") %>' value="sp" />
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

<c:if test="<%= Validator.isNotNull(entityId) %>">
	<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="replaceCertificateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="/admin/updateCertificate" />
		<portlet:param name="cmd" value="replace" />
	</portlet:renderURL>

	<aui:fieldset label="certificate-and-private-key">
		<c:choose>
			<c:when test="<%= x509Certificate != null %>">

				<%
				Date now = new Date();
				%>

				<c:if test="<%= now.after(x509Certificate.getNotAfter()) %>">
					<div class="portlet-msg-alert"><liferay-ui:message arguments="<%= new Object[] {x509Certificate.getNotAfter()} %>" key="certificate-expired-on-x" /></div>
				</c:if>

				<dl class="property-list">
					<dt>
						<liferay-ui:message key="subject-dn" />
					</dt>
					<dd>
						<%= HtmlUtil.escape(String.valueOf(certificateTool.getSubjectName(x509Certificate))) %>
					</dd>
					<dt>
						<liferay-ui:message key="serial-number" />
					</dt>
					<dd>
						<%= HtmlUtil.escape(certificateTool.getSerialNumber(x509Certificate)) %>

						<div class="portlet-msg-info-label">
							<liferay-ui:message arguments="<%= new Object[] {x509Certificate.getNotBefore(), x509Certificate.getNotAfter()} %>" key="valid-from-x-until-x" />
						</div>
					</dd>
					<dt>
						<liferay-ui:message key="certificate-fingerprints" />
					</dt>
					<dd class="property-list">
						<dl>
							<dt>
								MD5
							</dt>
							<dd>
								<%= HtmlUtil.escape(certificateTool.getFingerprint("MD5", x509Certificate)) %>
							</dd>
							<dt>
								SHA1
							</dt>
							<dd>
								<%= HtmlUtil.escape(certificateTool.getFingerprint("SHA1", x509Certificate)) %>
							</dd>
						</dl>
					</dd>
					<dt>
						<liferay-ui:message key="signature-algorithm" />
					</dt>
					<dd>
						<%= HtmlUtil.escape(x509Certificate.getSigAlgName()) %>
					</dd>
				</dl>

				<portlet:resourceURL id="/admin/downloadCertificate" var="downloadCertificateURL" />

				<aui:button-row>
					<aui:button onClick='<%= renderResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="replace-certificate" />
					<aui:button href="<%= downloadCertificateURL %>" value="download-certificate" />
				</aui:button-row>
			</c:when>
			<c:when test="<%= (x509Certificate == null) && Validator.isNull(samlProviderConfiguration.entityId()) %>">
				<div class="portlet-msg-info">
					<liferay-ui:message key="entity-id-must-be-set-before-private-key-and-certificate-can-be-generated" />
				</div>
			</c:when>
			<c:when test="<%= certificateAuthNeeded %>">
				<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="authCertificateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="/admin/updateCertificate" />
					<portlet:param name="cmd" value="auth" />
				</portlet:renderURL>

				<div class="portlet-msg-info">
					<liferay-ui:message key="please-create-a-signing-credential-before-enabling" />
					<liferay-ui:message key="certificate-needs-auth" />
				</div>

				<aui:button-row>
					<aui:button onClick='<%= renderResponse.getNamespace() + "showCertificateDialog('" + authCertificateURL + "');" %>' value="auth-certificate" />
					<aui:button onClick='<%= renderResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="replace-certificate" />
				</aui:button-row>
			</c:when>
			<c:otherwise>
				<div class="portlet-msg-info">
					<liferay-ui:message key="please-create-a-signing-credential-before-enabling" />
				</div>

				<aui:button-row>
					<aui:button onClick='<%= renderResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="create-certificate" />
				</aui:button-row>
			</c:otherwise>
		</c:choose>
	</aui:fieldset>
</c:if>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />showCertificateDialog',
		function(uri) {
			var dialog = Liferay.Util.Window.getWindow({
				id: '<portlet:namespace />certificateDialog',
				title : '<%= UnicodeLanguageUtil.get(request, "certificate-and-private-key") %>',
				uri: uri,
				dialog: {
					cache: false,
					modal: true
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				}
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
		['aui-base','aui-dialog','aui-dialog-iframe']
	);
</aui:script>