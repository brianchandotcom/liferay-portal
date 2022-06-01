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

<%@ include file="/html/portal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNotNull(redirect)) {
	redirect = PortalUtil.escapeRedirect(redirect);
}
else {
	redirect = themeDisplay.getPathMain();
}

response.setHeader(HttpHeaders.CACHE_CONTROL, HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
response.setHeader(HttpHeaders.LOCATION, redirect);
response.setHeader(HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);

response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
%>

<html dir="<liferay-ui:message key="lang.dir" />">
	<head>
		<meta content="<%= ContentTypes.TEXT_HTML_UTF8 %>" http-equiv="content-type" />
		<meta content="no-cache" http-equiv="Cache-Control" />
		<meta content="no-cache" http-equiv="Pragma" />
		<meta content="0" http-equiv="Expires" />
		<meta content="1; url=<%= HtmlUtil.escapeAttribute(redirect) %>" http-equiv="refresh" />

		<link class="lfr-css-file" href="<%= HtmlUtil.escapeAttribute(themeDisplay.getClayCSSURL()) %>" rel="stylesheet" type="text/css" />
	</head>

	<body onLoad="javascript:location.replace('<%= HtmlUtil.escapeJS(redirect) %>')">
		<center>
			<table border="0" cellpadding="0" cellspacing="0" height="100%" width="600">
				<tr>
					<td align="center" valign="middle">
						<strong><liferay-ui:message key="processing-login" /></strong>

						<br /><br />

						<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>