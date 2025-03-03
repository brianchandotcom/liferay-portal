<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="sheet-section">
	<div class="alert alert-info">
		<liferay-ui:message key="user-account-setup-description" />
	</div>

	<aui:input label="mfa-timebased-otp" name="mfaTimeBasedOTP" showRequiredLabel="yes" />

	<aui:input label="shared-secret" name="sharedSecret" readOnly="<%= true %>" type="text" value="<%= GetterUtil.getString(request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_SHARED_SECRET)) %>" />

	<div class="qrcode-setup" id="<portlet:namespace />qrcode"></div>
</div>

<div class="sheet-footer">
	<aui:button type="submit" value="submit" />
</div>

<%
MFATimeBasedOTPCheckerDisplayContext mfaTimeBasedOTPCheckerDisplayContext = (MFATimeBasedOTPCheckerDisplayContext)request.getAttribute(MFATimeBasedOTPWebKeys.MFA_TIME_BASED_OTP_CHECKER_DISPLAY_CONTEXT);
%>

<liferay-frontend:component
	context="<%= mfaTimeBasedOTPCheckerDisplayContext.getContext() %>"
	module="{generateQRCode} from multi-factor-authentication-timebased-otp-web"
/>