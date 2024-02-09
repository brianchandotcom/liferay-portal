<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User selectedUser = PortalUtil.getSelectedUser(request);

String mfaTimeBasedOTPAlgorithm = GetterUtil.getString(request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_ALGORITHM));
String mfaTimeBasedOTPCompanyName = GetterUtil.getString(request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_COMPANY_NAME));
String mfaTimeBasedOTPSharedSecret = GetterUtil.getString(request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_SHARED_SECRET));
%>

<div class="sheet-section">
	<div class="alert alert-info">
		<liferay-ui:message key="user-account-setup-description" />
	</div>

	<aui:input label="mfa-timebased-otp" name="mfaTimeBasedOTP" showRequiredLabel="yes" />

	<aui:input label="shared-secret" name="sharedSecret" readOnly="<%= true %>" type="text" value="<%= mfaTimeBasedOTPSharedSecret %>" />

	<div class="qrcode-setup" id="<portlet:namespace />qrcode"></div>
</div>

<div class="sheet-footer">
	<aui:button type="submit" value="submit" />
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"account", HtmlUtil.escapeJS(selectedUser.getEmailAddress())
		).put(
			"algorithm", HtmlUtil.escapeJS(mfaTimeBasedOTPAlgorithm)
		).put(
			"containerId", portletDisplay.getNamespace() + "qrcode"
		).put(
			"counter", GetterUtil.getInteger(request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_TIME_COUNTER))
		).put(
			"digits", GetterUtil.getInteger(request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_DIGITS))
		).put(
			"issuer", HtmlUtil.escapeJS(mfaTimeBasedOTPCompanyName)
		).put(
			"secret", HtmlUtil.escapeJS(mfaTimeBasedOTPSharedSecret)
		).build()
	%>'
	module="{generateQRCode} from multi-factor-authentication-timebased-otp-web"
/>