<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<portlet:renderURL var="loginRedirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/login/login_redirect" />
</portlet:renderURL>

<%
String facebookAuthRedirectURL = (String)request.getAttribute(FacebookConnectWebKeys.FACEBOOK_AUTH_REDIRECT_URL);
String facebookAuthURL = (String)request.getAttribute(FacebookConnectWebKeys.FACEBOOK_AUTH_URL);
String facebookAppId = (String)request.getAttribute(FacebookConnectWebKeys.FACEBOOK_APP_ID);

HttpSession portalHttpSession = PortalSessionThreadLocal.getHttpSession();

String nonce = PwdGenerator.getPassword(GetterUtil.getInteger(PropsUtil.get(PropsKeys.AUTH_TOKEN_LENGTH)));

portalHttpSession.setAttribute(WebKeys.FACEBOOK_NONCE, nonce);

facebookAuthURL = HttpComponentsUtil.addParameter(facebookAuthURL, "client_id", facebookAppId);
facebookAuthURL = HttpComponentsUtil.addParameter(facebookAuthURL, "redirect_uri", facebookAuthRedirectURL);
facebookAuthURL = HttpComponentsUtil.addParameter(facebookAuthURL, "scope", "email");

JSONObject stateJSONObject = JSONUtil.put(
	"redirect", loginRedirectURL
).put(
	"stateNonce", nonce
);

facebookAuthURL = HttpComponentsUtil.addParameter(facebookAuthURL, "state", stateJSONObject.toString());

String taglibOpenFacebookConnectLoginWindow = "javascript:var facebookConnectLoginWindow = window.open('" + URLCodec.encodeURL(facebookAuthURL) + "', 'facebook', 'align=center,directories=no,height=560,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=1000'); void(''); facebookConnectLoginWindow.focus();";
%>

<liferay-ui:icon
	cssClass="text-4"
	message="facebook"
	url="<%= taglibOpenFacebookConnectLoginWindow %>"
/>