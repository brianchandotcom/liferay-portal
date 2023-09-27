<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/common/init.jsp" %>

<%
String forwardURL = null;

String forwardParam = PortalUtil.escapeRedirect(request.getParameter(WebKeys.FORWARD_URL));
String forwardRequest = (String)request.getAttribute(WebKeys.FORWARD_URL);
String forwardSession = (String)session.getAttribute(WebKeys.FORWARD_URL);

if (Validator.isNotNull(forwardParam)) {
	forwardURL = forwardParam;
}
else if (Validator.isNotNull(forwardRequest)) {
	forwardURL = forwardRequest;
}
else if (Validator.isNotNull(forwardSession)) {
	forwardURL = forwardSession;
}
else if (themeDisplay != null) {
	forwardURL = themeDisplay.getPathMain();
}
else {
	forwardURL = PortalUtil.getPathMain();
}

if (!CookiesManagerUtil.hasSessionId(request) && Validator.isNotNull(forwardURL)) {
	forwardURL = PortalUtil.getURLWithSessionId(forwardURL, session.getId());
}
%>