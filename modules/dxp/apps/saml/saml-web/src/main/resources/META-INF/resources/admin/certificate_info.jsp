<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CertificateTool certificateTool = (CertificateTool)request.getAttribute(SamlWebKeys.SAML_CERTIFICATE_TOOL);

LocalEntityManager.CertificateUsage certificateUsage = LocalEntityManager.CertificateUsage.valueOf(ParamUtil.getString(request, "certificateUsage"));

GeneralTabDefaultViewDisplayContext.X509CertificateStatus x509CertificateStatus = generalTabDefaultViewDisplayContext.getX509CertificateStatus(certificateUsage);

X509Certificate x509Certificate = x509CertificateStatus.getX509Certificate();

String introKey = StringPool.BLANK;
%>

<c:choose>
	<c:when test="<%= certificateUsage == LocalEntityManager.CertificateUsage.SIGNING %>">

		<%
		introKey = "please-create-a-signing-credential-before-enabling";
		%>

	</c:when>
	<c:when test="<%= certificateUsage == LocalEntityManager.CertificateUsage.ENCRYPTION %>">

		<%
		introKey = "please-create-an-encryption-credential-if-you-want-assertions-encrypted";
		%>

	</c:when>
</c:choose>

<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="replaceCertificateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/admin/update_certificate" />
	<portlet:param name="<%= Constants.CMD %>" value="replace" />
	<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
</portlet:renderURL>

<c:if test="<%= x509Certificate != null %>">
	<%@ include file="/admin/certificate_info.jspf" %>
</c:if>

<portlet:actionURL name="/admin/update_certificate" var="deleteCertificateURL">
	<portlet:param name="<%= Constants.CMD %>" value="delete" />
	<portlet:param name="tabs1" value="general" />
	<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
</portlet:actionURL>

<c:choose>
	<c:when test="<%= x509Certificate != null %>">
		<portlet:resourceURL id="/admin/download_certificate" var="downloadCertificateURL">
			<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
		</portlet:resourceURL>

		<aui:form action="<%= deleteCertificateURL %>" name="fm">
			<aui:button-row>
				<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="replace-certificate" />
				<aui:button href="<%= downloadCertificateURL %>" value="download-certificate" />

				<c:if test="<%= certificateUsage == LocalEntityManager.CertificateUsage.ENCRYPTION %>">
					<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "handleDeleteCertificatePrompt(...arguments);" %>' type="submit" value="delete-certificate" />
				</c:if>
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:when test="<%= (x509Certificate == null) && Validator.isNull(samlProviderConfiguration.entityId()) %>">
		<div class="portlet-msg-info">
			<liferay-ui:message key="entity-id-must-be-set-before-private-key-and-certificate-can-be-generated" />
		</div>
	</c:when>
	<c:when test="<%= x509CertificateStatus.getStatus() == GeneralTabDefaultViewDisplayContext.X509CertificateStatus.Status.SAML_X509_CERTIFICATE_AUTH_NEEDED %>">
		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="authCertificateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/admin/update_certificate" />
			<portlet:param name="<%= Constants.CMD %>" value="auth" />
			<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
		</portlet:renderURL>

		<div class="portlet-msg-info">
			<liferay-ui:message key="<%= introKey %>" />
			<liferay-ui:message key="certificate-needs-auth" />
		</div>

		<aui:form action="<%= deleteCertificateURL %>" name="fm">
			<aui:button-row>
				<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "showCertificateDialog('" + authCertificateURL + "');" %>' value="auth-certificate" />
				<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="replace-certificate" />

				<c:if test="<%= certificateUsage == LocalEntityManager.CertificateUsage.ENCRYPTION %>">
					<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "handleDeleteCertificatePrompt(...arguments);" %>' type="submit" value="delete-certificate" />
				</c:if>
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="<%= introKey %>" />
		</div>

		<aui:button-row>
			<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="create-certificate" />
		</aui:button-row>
	</c:otherwise>
</c:choose>

<aui:script>
	window['<portlet:namespace />handleDeleteCertificatePrompt'] = function (
		event
	) {
		event.preventDefault();

		const form = event.target.closest('form');

		Liferay.Util.openConfirmModal({
			message:
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this-certificate-from-the-keystore") %>',
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					form.submit();
				}
			},
		});
	};
</aui:script>