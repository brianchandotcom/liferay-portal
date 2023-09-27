<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirectURL = (String)request.getAttribute(PunchOutConstants.PUNCH_OUT_REDIRECT_URL_ATTRIBUTE_NAME);
String signOutURL = themeDisplay.getURLSignOut();
%>

<div>
	<c:set var="redirectLink">
		<a href="<%= HtmlUtil.escapeHREF(redirectURL) %>"><%= HtmlUtil.escape(redirectURL) %></a>
	</c:set>

	<liferay-ui:message arguments="${redirectLink}" key="the-punch-out-cart-transfer-process-has-been-initiated.-you-should-be-redirected-automatically.-if-the-page-does-not-reload-within-a-few-seconds-please-click-this-link-x" />
</div>

<script>
	window.location.href = '<%= HtmlUtil.escapeJS(signOutURL) %>';
</script>