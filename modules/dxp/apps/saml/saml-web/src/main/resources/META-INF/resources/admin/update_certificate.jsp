<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, "cmd", "auth");

String certificateCommonName = ParamUtil.getString(request, "certificateCommonName");
String certificateCountry = ParamUtil.getString(request, "certificateCountry");
String certificateLocality = ParamUtil.getString(request, "certificateLocality");
String certificateKeyAlgorithm = ParamUtil.getString(request, "certificateKeyAlgorithm", "RSA");
String certificateKeyLength = ParamUtil.getString(request, "certificateKeyLength", "2048");
String certificateOrganization = ParamUtil.getString(request, "certificateOrganization");
String certificateOrganizationUnit = ParamUtil.getString(request, "certificateOrganizationUnit");
String certificateState = ParamUtil.getString(request, "certificateState");
String certificateValidityDays = ParamUtil.getString(request, "certificateValidityDays", "356");

X509Certificate x509Certificate = (X509Certificate)request.getAttribute(SamlWebKeys.SAML_X509_CERTIFICATE);
%>

<portlet:actionURL name="/admin/updateCertificate" var="updateCertificateURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/updateCertificate" />
</portlet:actionURL>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />requestCloseDialog',
		function(stateChange) {
			Liferay.Util.getOpener().<portlet:namespace />closeDialog('<portlet:namespace/>certificateDialog', stateChange);
		}
	);
</aui:script>

<c:choose>
	<c:when test="<%= x509Certificate == null %>">
		<aui:form action="<%= updateCertificateURL %>">
			<div class="lfr-form-content" id="<portlet:namespace />certificateForm">
				<div class="inline-alert-container lfr-alert-container"></div>

				<liferay-ui:error exception="<%= CertificateException.class %>" message="please-enter-a-valid-key-length-and-algorithm" />
				<liferay-ui:error exception="<%= CertificateKeyPasswordException.class %>" message="please-enter-a-valid-key-password" />
				<liferay-ui:error exception="<%= InvalidParameterException.class %>" message="please-enter-a-valid-key-length-and-algorithm" />
				<liferay-ui:error key="certificateValidityDays" message="please-enter-a-valid-certificate-validity" />

				<aui:input name="cmd" type="hidden" value="<%= cmd %>" />

				<c:choose>
					<c:when test='<%= cmd.equals("replace") %>'>
						<aui:input label="common-name" name="certificateCommonName" required="<%= true %>" value="<%= certificateCommonName %>" />

						<aui:input label="organization" name="certificateOrganization" value="<%= certificateOrganization %>" />

						<aui:input label="organization-unit" name="certificateOrganizationUnit" value="<%= certificateOrganizationUnit %>" />

						<aui:input label="locality" name="certificateLocality" value="<%= certificateLocality %>" />

						<aui:input label="state" name="certificateState" value="<%= certificateState %>" />

						<aui:input label="country" name="certificateCountry" value="<%= certificateCountry %>" />

						<aui:input label="validity-days" name="certificateValidityDays" value="<%= certificateValidityDays %>" />

						<aui:select label="key-algorithm" name="certificateKeyAlgorithm" required="<%= true %>">
							<aui:option label="rsa" selected='<%= certificateKeyAlgorithm.equals("RSA") %>' value="RSA" />
							<aui:option label="dsa" selected='<%= certificateKeyAlgorithm.equals("DSA") %>' value="DSA" />
						</aui:select>

						<aui:select label="key-length-bits" name="certificateKeyLength" required="<%= true %>">
							<aui:option label="4096" selected='<%= certificateKeyLength.equals("4096") %>' value="4096" />
							<aui:option label="2048" selected='<%= certificateKeyLength.equals("2048") %>' value="2048" />
							<aui:option label="1024" selected='<%= certificateKeyLength.equals("1024") %>' value="1024" />
							<aui:option label="512" selected='<%= certificateKeyLength.equals("512") %>' value="512" />
						</aui:select>
					</c:when>
				</c:choose>

				<aui:input label="key-password" name='<%= "settings--" + PortletPropsKeys.SAML_KEYSTORE_CREDENTIAL_PASSWORD + "--" %>' required="<%= true %>" type="password" value="" />
			</div>

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />
				<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "requestCloseDialog(false);" %>' type="cancel" value="cancel" />
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:otherwise>
		<aui:script>
			<portlet:namespace />requestCloseDialog(true);
		</aui:script>
	</c:otherwise>
</c:choose>