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

<portlet:renderURL var="loginRedirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/login/login_redirect" />
</portlet:renderURL>

<%
String facebookAuthRedirectURL = (String)request.getAttribute(FacebookConnectWebKeys.FACEBOOK_AUTH_REDIRECT_URL);
String facebookAuthURL = (String)request.getAttribute(FacebookConnectWebKeys.FACEBOOK_AUTH_URL);
String facebookAppId = (String)request.getAttribute(FacebookConnectWebKeys.FACEBOOK_APP_ID);

facebookAuthRedirectURL = HttpUtil.addParameter(facebookAuthRedirectURL, "redirect", loginRedirectURL);

facebookAuthURL = HttpUtil.addParameter(facebookAuthURL, "client_id", facebookAppId);
facebookAuthURL = HttpUtil.addParameter(facebookAuthURL, "redirect_uri", facebookAuthRedirectURL);
facebookAuthURL = HttpUtil.addParameter(facebookAuthURL, "scope", "email");

String taglibOpenFacebookConnectLoginWindow = "javascript:var facebookConnectLoginWindow = window.open('" + URLCodec.encodeURL(facebookAuthURL) + "', 'facebook', 'align=center,directories=no,height=560,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=1000'); void(''); facebookConnectLoginWindow.focus();";
%>

<liferay-ui:icon
	iconCssClass="icon-facebook"
	message="facebook"
	url="<%= taglibOpenFacebookConnectLoginWindow %>"
/>