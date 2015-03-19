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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>

<portlet:renderURL var="loginRedirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/login/login_redirect" />
</portlet:renderURL>

<%
String facebookAuthRedirectURL = (String)request.getAttribute("facebookAuthRedirectURL");

String facebookAuthURL = (String)request.getAttribute("facebookAuthURL");

String facebookAppId = (String)request.getAttribute("facebookAppId");

facebookAuthRedirectURL = HttpUtil.addParameter(facebookAuthRedirectURL, "redirect", HttpUtil.encodeURL(loginRedirectURL));

facebookAuthURL = HttpUtil.addParameter(facebookAuthURL, "client_id", facebookAppId);
facebookAuthURL = HttpUtil.addParameter(facebookAuthURL, "redirect_uri", facebookAuthRedirectURL);
facebookAuthURL = HttpUtil.addParameter(facebookAuthURL, "scope", "email");

String taglibOpenFacebookConnectLoginWindow = "javascript:var facebookConnectLoginWindow = window.open('" + facebookAuthURL + "', 'facebook', 'align=center,directories=no,height=560,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=1000'); void(''); facebookConnectLoginWindow.focus();";
%>

<liferay-ui:icon
	iconCssClass="icon-facebook"
	message="facebook"
	url="<%= taglibOpenFacebookConnectLoginWindow %>"
/>