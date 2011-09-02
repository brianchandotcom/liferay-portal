<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ page session="false"%>

<%@ include file="/html/common/init.jsp" %>

<%
String referer = null;

String refererParam = PortalUtil.escapeRedirect(request.getParameter(WebKeys.REFERER));
String refererRequest = (String)request.getAttribute(WebKeys.REFERER);
String refererSession = null;

HttpSession session = request.getSession(false);

if (session != null) {
	refererSession = (String)session.getAttribute(WebKeys.REFERER);
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

if ((session != null) && !CookieKeys.hasSessionId(request) && Validator.isNotNull(referer)) {
	referer = PortalUtil.getURLWithSessionId(referer, session.getId());
}
%>