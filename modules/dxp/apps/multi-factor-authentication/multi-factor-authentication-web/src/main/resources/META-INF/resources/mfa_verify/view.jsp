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

BrowserMFAChecker browserMFAChecker = (BrowserMFAChecker)request.getAttribute(MFAWebKeys.BROWSER_MFA_CHECKER);
String browserMFACheckerName = (String)request.getAttribute(MFAWebKeys.BROWSER_MFA_CHECKER_NAME);
List<BrowserMFAChecker> browserMFACheckers = (List<BrowserMFAChecker>)request.getAttribute(MFAWebKeys.BROWSER_MFA_CHECKERS);
long mfaUserId = (Long)request.getAttribute(MFAWebKeys.MFA_USER_ID);

int mfaCheckerIndex = ParamUtil.getInteger(request, "mfaCheckerIndex");
%>

<portlet:actionURL name="/mfa_verify/verify" var="verifyURL">
	<portlet:param name="mvcRenderCommandName" value="/mfa_verify/view" />
</portlet:actionURL>

<aui:form action="<%= verifyURL %>" cssClass="container-fluid container-fluid-max-xl sign-in-form" data-senna-off="true" method="post" name="fm">
	<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="mfaCheckerIndex" type="hidden" value="<%= mfaCheckerIndex %>" />

	<liferay-ui:error key="mfaVerificationFailed" message="multi-factor-authentication-has-failed" />

	<%
	browserMFAChecker.includeBrowserVerification(request, response, mfaUserId);
	%>

	<c:if test="<%= browserMFACheckers.size() > 1 %>">
		<portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="useAnotherBrowserMFAChecker">
			<portlet:param name="saveLastPath" value="<%= Boolean.FALSE.toString() %>" />
			<portlet:param name="mvcRenderCommandName" value="/mfa_verify/view" />
			<portlet:param name="redirect" value='<%= ParamUtil.getString(request, "redirect") %>' />
			<portlet:param name="mfaCheckerIndex" value='<%= ((mfaCheckerIndex + 1) < browserMFACheckers.size()) ? String.valueOf(mfaCheckerIndex + 1) : "0" %>' />
		</portlet:renderURL>

		<b><a href="<%= HtmlUtil.escapeAttribute(useAnotherBrowserMFAChecker) %>"><%= LanguageUtil.format(request, "use-another-mfa-checker", browserMFACheckerName, false) %></a></b>
	</c:if>
</aui:form>