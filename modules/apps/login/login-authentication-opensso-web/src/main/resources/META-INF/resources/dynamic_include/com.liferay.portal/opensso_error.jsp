<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
String openSSOSubjectScreenName = (String)request.getAttribute(OpenSSOWebKeys.OPEN_SSO_SUBJECT_SCREEN_NAME);
%>

<liferay-util:buffer
	var="msg"
>
	<liferay-ui:message arguments='<%= "<strong>" + HtmlUtil.escape(openSSOSubjectScreenName) + "</strong>" %>' key="your-user-x-could-not-be-signed-in" />

	<c:choose>
		<c:when test='<%= SessionMessages.contains(request, "MustNotUseCompanyMx") %>'>
			<liferay-ui:message key="the-email-address-associated-with-your-opensso-account-cannot-be-used-to-register-a-new-user-because-its-email-domain-is-reserved" />
		</c:when>
		<c:when test='<%= SessionMessages.contains(request, "StrangersNotAllowedException") %>'>
			<liferay-ui:message key="only-known-users-are-allowed-to-sign-in-using-opensso" />
		</c:when>
		<c:when test='<%= SessionMessages.contains(request, "ContactNameException") %>'>
			<liferay-ui:message key="your-contact-name-is-incomplete-or-invalid" />
		</c:when>
	</c:choose>

	<a href="<%= themeDisplay.getURLSignOut() %>"><liferay-ui:message arguments='<%= "<strong>" + HtmlUtil.escapeAttribute(openSSOSubjectScreenName) + "</strong>" %>' key="not-x" /></a>
</liferay-util:buffer>

<aui:script>
	Liferay.Util.openToast({
		message: '<%= HtmlUtil.escapeJS(msg) %>',
		type: 'warning',
	});
</aui:script>