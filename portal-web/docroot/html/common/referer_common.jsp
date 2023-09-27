<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ page session="false" %>

<%@ include file="/html/common/init.jsp" %>

<%
String referer = null;

String refererParam = PortalUtil.escapeRedirect(request.getParameter(WebKeys.REFERER));
String refererRequest = (String)request.getAttribute(WebKeys.REFERER);

String refererSession = null;

HttpSession httpSession = request.getSession(false);

if (httpSession != null) {
	refererSession = (String)httpSession.getAttribute(WebKeys.REFERER);
}

if (Validator.isNotNull(refererParam)) {
	referer = refererParam;
}
else if (Validator.isNotNull(refererRequest)) {
	referer = refererRequest;
}
else if (Validator.isNotNull(refererSession)) {
	referer = refererSession;
}
else if (themeDisplay != null) {
	referer = themeDisplay.getPathMain();
}
else {
	referer = PortalUtil.getPathMain();
}

if ((httpSession != null) && !CookiesManagerUtil.hasSessionId(request) && Validator.isNotNull(referer)) {
	referer = PortalUtil.getURLWithSessionId(referer, httpSession.getId());
}
%>