<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
Boolean staleSession = (Boolean)session.getAttribute(WebKeys.STALE_SESSION);

String userLogin = user.getEmailAddress();

if (Objects.equals(company.getAuthType(), CompanyConstants.AUTH_TYPE_SN)) {
	userLogin = user.getScreenName();
}
else if (Objects.equals(company.getAuthType(), CompanyConstants.AUTH_TYPE_ID)) {
	userLogin = String.valueOf(user.getUserId());
}
%>

<c:if test="<%= (staleSession != null) && staleSession.booleanValue() %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="you-have-been-logged-off-because-you-signed-on-with-this-account-using-a-different-session" />
	</div>

	<%
	session.invalidate();
	%>

</c:if>

<c:if test="<%= SessionErrors.contains(request, LayoutPermissionException.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="you-do-not-have-permission-to-view-this-page" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, PortletActiveException.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="this-page-is-part-of-an-inactive-portlet" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, PrincipalException.getNestedClasses()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="you-do-not-have-the-roles-required-to-access-this-page" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, RequiredLayoutException.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="please-contact-the-administrator-because-you-do-not-have-any-pages-configured" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, RequiredRoleException.class.getName()) %>">
	<div class="alert alert-danger">
		<liferay-ui:message key="please-contact-the-administrator-because-you-do-not-have-any-roles" />
	</div>
</c:if>

<c:if test="<%= SessionErrors.contains(request, UserActiveException.class.getName()) %>">
	<div class="alert alert-danger">
		<%= LanguageUtil.format(request, "your-account-with-login-x-is-not-active", new LanguageWrapper[] {new LanguageWrapper("", HtmlUtil.escape(user.getFullName()), ""), new LanguageWrapper("<strong><em>", HtmlUtil.escape(userLogin), "</em></strong>")}, false) %><br /><br />
	</div>

	<liferay-ui:message arguments="<%= HtmlUtil.escape(user.getFullName()) %>" key="if-you-are-not-x-log-out-and-try-again" translateArguments="<%= false %>" />
</c:if>