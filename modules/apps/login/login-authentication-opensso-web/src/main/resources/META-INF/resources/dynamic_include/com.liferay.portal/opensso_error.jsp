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

<%@ include file="/dynamic_include/init.jsp" %>

<%
String openSSOSubjectScreenName = (String)request.getAttribute(OpenSSOWebKeys.OPEN_SSO_SUBJECT_SCREEN_NAME);
%>

<liferay-util:buffer
	var="msg"
>
	<liferay-ui:message arguments='<%= "<strong>" + HtmlUtil.escape(openSSOSubjectScreenName) + "</strong>" %>' key="your-user-x-could-not-be-logged-in" />

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

<script type="text/javascript">
	AUI().use('liferay-notification', function(A) {
		new Liferay.Notification({
			closeable: true,
			delay: {
				hide: 10000,
				show: 0
			},
			duration: 500,
			message: '<%= HtmlUtil.escapeJS(msg) %>',
			render: true,
			title: '<liferay-ui:message key="warning" />',
			type: 'warning'
		}).render('body');
	});
</script>