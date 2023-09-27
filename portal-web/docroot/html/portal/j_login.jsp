<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/common/init.jsp" %>

<%
String jSecurityCheck = "j_security_check";

if (!ServerDetector.isWebSphere()) {
	jSecurityCheck = themeDisplay.getPathMain() + "/portal/" + jSecurityCheck;
}

String jUserName = (String)session.getAttribute("j_username");
String jPassword = (String)session.getAttribute("j_password");

if (PropsValues.PORTAL_JAAS_ENABLE && (jUserName != null)) {
	long jUserId = GetterUtil.getLong(jUserName);

	User jUser = UserLocalServiceUtil.getUser(jUserId);

	if (PropsValues.PORTAL_JAAS_AUTH_TYPE.equals("emailAddress")) {
		jUserName = jUser.getEmailAddress();
	}
	else if (PropsValues.PORTAL_JAAS_AUTH_TYPE.equals("screenName")) {
		jUserName = jUser.getScreenName();
	}
	else if (PropsValues.PORTAL_JAAS_AUTH_TYPE.equals("login")) {
		jUserName = jUser.getLogin();
	}
}
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(jUserName) && Validator.isNotNull(jPassword) %>">
		<html dir="<liferay-ui:message key="lang.dir" />">
			<head>
				<meta content="<%= ContentTypes.TEXT_HTML_UTF8 %>" http-equiv="content-type" />
				<meta content="no-cache" http-equiv="Cache-Control" />
				<meta content="no-cache" http-equiv="Pragma" />
				<meta content="0" http-equiv="Expires" />

				<link class="lfr-css-file" href="<%= HtmlUtil.escapeAttribute(themeDisplay.getClayCSSURL()) %>" rel="stylesheet" type="text/css" />
			</head>

			<body onLoad="setTimeout('document.fm.submit()', 100);">
				<center>
					<table border="0" cellpadding="0" cellspacing="0" height="100%" width="600">
						<tr>
							<td align="center" valign="middle">
								<form action="<%= jSecurityCheck %>" method="post" name="fm">
									<input name="j_username" type="hidden" value="<%= HtmlUtil.escapeAttribute(jUserName) %>" />
									<input name="j_password" type="hidden" value="<%= HtmlUtil.escapeAttribute(jPassword) %>" />
								</form>

								<font face="Verdana, Tahoma, Arial" size="3">
									<strong><liferay-ui:message key="processing-login" /></strong>
								</font>

								<br /><br />

								<span aria-hidden="true" class="loading-animation loading-animation-sm"></span>
							</td>
						</tr>
					</table>
				</center>
			</body>
		</html>

		<%
		session.removeAttribute("j_username");
		session.removeAttribute("j_password");

		PortalMessages.clear(request);
		SessionMessages.clear(request);
		SessionErrors.clear(request);
		%>

	</c:when>
	<c:otherwise>

		<!--
		This only happens if you're logged in and the server is rebooted and
		the user's old session is not recovered. Forward the user to the default
		page instead of posting to the protected page with invalid credentials.
		-->

		<%
		try {
			session.invalidate();
		}
		catch (Exception e) {
			_log.error(e);
		}
		%>

		<html dir="<liferay-ui:message key="lang.dir" />">
			<head>
				<title></title>

				<meta content="0; url=<%= themeDisplay.getPathMain() %>" http-equiv="refresh" />
				<meta content="no-cache" http-equiv="Cache-Control" />
				<meta content="no-cache" http-equiv="Pragma" />
				<meta content="0" http-equiv="Expires" />
			</head>

			<body onLoad="javascript:location.replace('<%= themeDisplay.getPathMain() %>')">

			</body>
		</html>
	</c:otherwise>
</c:choose>

<%!
private static final Log _log = LogFactoryUtil.getLog("portal_web.docroot.html.portal.j_login_jsp");
%>