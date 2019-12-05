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
EmailOTPMFAChecker emailOtpMfaChecker = (EmailOTPMFAChecker)request.getAttribute("emailMFAChecker");

long mfaUserId = (Long)request.getAttribute("mfaUserId");
%>

<portlet:actionURL name="/mfa_verify/verify" var="verifyActionURL">
	<portlet:param name="mvcRenderCommandName" value="/mfa_verify/view" />
</portlet:actionURL>

<aui:form action="<%= verifyActionURL %>" cssClass="container-fluid-1280 sign-in-form" data-senna-off="true" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value='<%= ParamUtil.getString(request, "redirect") %>' />
	<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />

	<liferay-ui:error key="mfaFailed" message="multi-factor-authentication-failed" />

	<%
	emailOtpMfaChecker.includeBrowserVerification(request, response, mfaUserId);
	%>

	<aui:button-row>
		<aui:button type="submit" value="submit" />
	</aui:button-row>
</aui:form>