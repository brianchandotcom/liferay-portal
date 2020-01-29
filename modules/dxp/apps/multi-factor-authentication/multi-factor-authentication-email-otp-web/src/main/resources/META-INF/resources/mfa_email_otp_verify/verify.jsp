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
String redirect = ParamUtil.getString(request, "redirect");

MFAEmailOTPChecker mfaEmailOTPChecker = (MFAEmailOTPChecker)request.getAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_CHECKER);

long mfaEmailOTPUserId = (Long)request.getAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_USER_ID);
%>

<portlet:actionURL name="/mfa_email_otp_verify/verify" var="verifyURL">
	<portlet:param name="mvcRenderCommandName" value="/mfa_email_otp_verify/verify" />
</portlet:actionURL>

<aui:form action="<%= verifyURL %>" cssClass="container-fluid-1280 sign-in-form" data-senna-off="true" method="post" name="fm">
	<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error key="mfaEmailOTPFailed" message="multi-factor-authentication-has-failed" />

	<%
	mfaEmailOTPChecker.includeBrowserVerification(request, response, mfaEmailOTPUserId);
	%>

	<aui:button-row>
		<aui:button type="submit" value="submit" />
	</aui:button-row>
</aui:form>