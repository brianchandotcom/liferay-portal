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
String cmd = ParamUtil.getString(request, Constants.CMD, "auth");

LocalEntityManager.CertificateUsage certificateUsage = LocalEntityManager.CertificateUsage.valueOf(ParamUtil.getString(request, "certificateUsage"));

X509Certificate x509Certificate = (X509Certificate)request.getAttribute(SamlWebKeys.SAML_X509_CERTIFICATE);
%>

<aui:script>
	window['<portlet:namespace />requestCloseDialog'] = function (stateChange) {
		Liferay.Util.getOpener().<portlet:namespace />closeDialog(
			'<portlet:namespace />certificateDialog',
			stateChange
		);
	};
</aui:script>

<c:if test='<%= cmd.equals("replace") || cmd.equals("import") %>'>
	<clay:navigation-bar
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					PortletURL portletURL = renderResponse.createRenderURL();

					portletURL.setParameter("mvcRenderCommandName", "/admin/updateCertificate");
					portletURL.setParameter("certificateUsage", certificateUsage.name());

					portletURL.setParameter(Constants.CMD, "replace");

					add(
						navigationItem -> {
							navigationItem.setActive(cmd.equals("replace"));
							navigationItem.setHref(portletURL.toString());
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "create-certificate"));
						});

					portletURL.setParameter(Constants.CMD, "import");

					add(
						navigationItem -> {
							navigationItem.setActive(cmd.equals("import"));
							navigationItem.setHref(portletURL.toString());
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "import-certificate"));
						});
				}
			}
		%>'
	/>
</c:if>

<liferay-portlet:actionURL name="/admin/updateCertificate" var="updateCertificateURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/updateCertificate" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= cmd %>" />
	<portlet:param name="certificateUsage" value="<%= certificateUsage.name() %>" />
</liferay-portlet:actionURL>

<aui:form action="<%= updateCertificateURL.toString() %>" cssClass="" method="post" name="fm1">
	<c:choose>
		<c:when test='<%= cmd.equals("import") && (x509Certificate == null) %>'>
			<liferay-util:include page="/admin/import_certificate.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test="<%= x509Certificate == null %>">

			<%
			String certificateKeyAlgorithm = ParamUtil.getString(request, "certificateKeyAlgorithm", "RSA");
			String certificateKeyLength = ParamUtil.getString(request, "certificateKeyLength", "2048");
			%>

			<div class="lfr-form-content" id="<portlet:namespace />certificateForm">
				<div class="inline-alert-container lfr-alert-container"></div>

				<liferay-ui:error exception="<%= CertificateException.class %>" message="please-enter-a-valid-key-length-and-algorithm" />
				<liferay-ui:error exception="<%= CertificateKeyPasswordException.class %>" message="please-enter-a-valid-key-password" />
				<liferay-ui:error exception="<%= InvalidParameterException.class %>" message="please-enter-a-valid-key-length-and-algorithm" />
				<liferay-ui:error key="certificateValidityDays" message="please-enter-a-valid-certificate-validity" />

				<c:choose>
					<c:when test='<%= cmd.equals("replace") %>'>
						<aui:input label="common-name" name="certificateCommonName" required="<%= true %>" value='<%= ParamUtil.getString(request, "certificateCommonName") %>' />

						<aui:input label="organization" name="certificateOrganization" value='<%= ParamUtil.getString(request, "certificateOrganization") %>' />

						<aui:input label="organization-unit" name="certificateOrganizationUnit" value='<%= ParamUtil.getString(request, "certificateOrganizationUnit") %>' />

						<aui:input label="locality" name="certificateLocality" value='<%= ParamUtil.getString(request, "certificateLocality") %>' />

						<aui:input label="state" name="certificateState" value='<%= ParamUtil.getString(request, "certificateState") %>' />

						<aui:input label="country" name="certificateCountry" value='<%= ParamUtil.getString(request, "certificateCountry") %>' />

						<aui:input label="validity-days" name="certificateValidityDays" value='<%= ParamUtil.getString(request, "certificateValidityDays", "356") %>' />

						<c:choose>
							<c:when test="<%= certificateUsage == LocalEntityManager.CertificateUsage.SIGNING %>">
								<aui:select label="key-algorithm" name="certificateKeyAlgorithm" required="<%= true %>">
									<aui:option label="rsa" selected='<%= certificateKeyAlgorithm.equals("RSA") %>' value="RSA" />
									<aui:option label="dsa" selected='<%= certificateKeyAlgorithm.equals("DSA") %>' value="DSA" />
								</aui:select>
							</c:when>
							<c:when test="<%= certificateUsage == LocalEntityManager.CertificateUsage.ENCRYPTION %>">
								<aui:input disabled="<%= true %>" label="key-algorithm" name="certificateKeyAlgorithm" value="RSA" />
								<aui:input label="key-algorithm" name="certificateKeyAlgorithm" type="hidden" value="RSA" />
							</c:when>
						</c:choose>

						<aui:select label="key-length-bits" name="certificateKeyLength" required="<%= true %>">
							<aui:option label="4096" selected='<%= certificateKeyLength.equals("4096") %>' value="4096" />
							<aui:option label="2048" selected='<%= certificateKeyLength.equals("2048") %>' value="2048" />
							<aui:option label="1024" selected='<%= certificateKeyLength.equals("1024") %>' value="1024" />
							<aui:option label="512" selected='<%= certificateKeyLength.equals("512") %>' value="512" />
						</aui:select>
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test="<%= certificateUsage == LocalEntityManager.CertificateUsage.SIGNING %>">
						<aui:input label="key-password" name='<%= "settings--" + PortletPropsKeys.SAML_KEYSTORE_CREDENTIAL_PASSWORD + "--" %>' required="<%= true %>" type="password" value="" />
					</c:when>
					<c:when test="<%= certificateUsage == LocalEntityManager.CertificateUsage.ENCRYPTION %>">
						<aui:input label="key-password" name='<%= "settings--" + PortletPropsKeys.SAML_KEYSTORE_ENCRYPTION_CREDENTIAL_PASSWORD + "--" %>' required="<%= true %>" type="password" value="" />
					</c:when>
				</c:choose>
			</div>

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />
				<aui:button cssClass="btn-lg" onClick='<%= liferayPortletResponse.getNamespace() + "requestCloseDialog(false);" %>' type="cancel" value="cancel" />
			</aui:button-row>
		</c:when>
		<c:otherwise>
			<aui:script>
				<portlet:namespace />requestCloseDialog(true);
			</aui:script>
		</c:otherwise>
	</c:choose>
</aui:form>