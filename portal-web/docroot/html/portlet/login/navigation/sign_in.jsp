<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

boolean showSignInIcon = false;

if (Validator.isNotNull(strutsAction) && !strutsAction.equals("/login/login")) {
	showSignInIcon = true;
}
%>

<c:if test="<%= showSignInIcon %>">

	<%
	String signInURL = themeDisplay.getURLSignIn();

	if (portletName.equals(PortletKeys.FAST_LOGIN)) {
		signInURL = HttpUtil.addParameter(signInURL, "windowState", LiferayWindowState.POP_UP.toString());
	}
	%>

	<liferay-ui:icon
		image="status_online"
		message="sign-in"
		url="<%= signInURL %>"
	/>
</c:if>