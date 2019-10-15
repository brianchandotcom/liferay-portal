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
CertificateTool certificateTool = (CertificateTool)request.getAttribute(SamlWebKeys.SAML_CERTIFICATE_TOOL);

LocalEntityManager.CertificateUsage certificateUsage = LocalEntityManager.CertificateUsage.valueOf(ParamUtil.getString(request, "certificateUsage"));

GeneralTabDefaultViewDisplayContext.X509CertificateStatus x509CertificateStatus = generalTabDefaultViewDisplayContext.getX509CertificateStatus(certificateUsage);

boolean certificateAuthNeeded = x509CertificateStatus.getStatus() == GeneralTabDefaultViewDisplayContext.X509CertificateStatus.Status.SAML_X509_CERTIFICATE_AUTH_NEEDED;
X509Certificate x509Certificate = x509CertificateStatus.getX509Certificate();
%>

<liferay-util:buffer
	var="certificateInfo"
>
	<c:if test="<%= x509Certificate != null %>">

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
	</c:if>
</liferay-util:buffer>

<%
String deleteCertificatePrompt = UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this-certificate-from-the-keystore");
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
	<portlet:param name="mvcRenderCommandName" value="/admin/updateCertificate" />
	<portlet:param name="<%= Constants.CMD %>" value="replace" />
	<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
</portlet:renderURL>

<%= certificateInfo %>

<portlet:actionURL name="/admin/updateCertificate" var="deleteCertificateURL">
	<portlet:param name="<%= Constants.CMD %>" value="delete" />
	<portlet:param name="tabs1" value="general" />
	<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
</portlet:actionURL>

<c:choose>
	<c:when test="<%= x509Certificate != null %>">
		<portlet:resourceURL id="/admin/downloadCertificate" var="downloadCertificateURL">
			<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
		</portlet:resourceURL>

		<aui:form action="<%= deleteCertificateURL %>">
			<aui:button-row>
				<aui:button onClick='<%= renderResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="replace-certificate" />
				<aui:button href="<%= downloadCertificateURL %>" value="download-certificate" />

				<c:if test="<%= certificateUsage == LocalEntityManager.CertificateUsage.ENCRYPTION %>">
					<aui:button onClick='<%= "return confirm('" + deleteCertificatePrompt + "')" %>' type="submit" value="delete-certificate" />
				</c:if>
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:when test="<%= (x509Certificate == null) && Validator.isNull(samlProviderConfiguration.entityId()) %>">
		<div class="portlet-msg-info">
			<liferay-ui:message key="entity-id-must-be-set-before-private-key-and-certificate-can-be-generated" />
		</div>
	</c:when>
	<c:when test="<%= certificateAuthNeeded %>">
		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="authCertificateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/admin/updateCertificate" />
			<portlet:param name="<%= Constants.CMD %>" value="auth" />
			<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
		</portlet:renderURL>

		<div class="portlet-msg-info">
			<liferay-ui:message key="<%= introKey %>" />
			<liferay-ui:message key="certificate-needs-auth" />
		</div>

		<aui:form action="<%= deleteCertificateURL %>">
			<aui:button-row>
				<aui:button onClick='<%= renderResponse.getNamespace() + "showCertificateDialog('" + authCertificateURL + "');" %>' value="auth-certificate" />
				<aui:button onClick='<%= renderResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="replace-certificate" />

				<c:if test="<%= certificateUsage == LocalEntityManager.CertificateUsage.ENCRYPTION %>">
					<aui:button onClick='<%= "return confirm('" + deleteCertificatePrompt + "')" %>' type="submit" value="delete-certificate" />
				</c:if>
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="<%= introKey %>" />
		</div>

		<aui:button-row>
			<aui:button onClick='<%= renderResponse.getNamespace() + "showCertificateDialog('" + replaceCertificateURL + "');" %>' value="create-certificate" />
		</aui:button-row>
	</c:otherwise>
</c:choose>