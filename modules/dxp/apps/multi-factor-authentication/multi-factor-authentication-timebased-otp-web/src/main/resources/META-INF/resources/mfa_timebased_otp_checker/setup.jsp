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

<aui:script require='<%= npmResolvedPackageName + "/qrcode/generateQRCode as generateQRCode" %>'>
	var account = '<%= HtmlUtil.escapeJS(selectedUser.getEmailAddress()) %>';
	var algorithm = '<%= HtmlUtil.escapeJS(mfaTimeBasedOTPAlgorithm) %>';
	var counter =
		'<%= GetterUtil.getInteger(request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_TIME_COUNTER)) %>';
	var digits =
		'<%= GetterUtil.getInteger(request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_DIGITS)) %>';
	var issuer = '<%= HtmlUtil.escapeJS(mfaTimeBasedOTPCompanyName) %>';
	var secret = '<%= HtmlUtil.escapeJS(mfaTimeBasedOTPSharedSecret) %>';

	generateQRCode.default('<portlet:namespace />qrcode', {
		account: account,
		algorithm: algorithm,
		counter: counter,
		digits: digits,
		issuer: issuer,
		secret: secret,
	});
</aui:script>