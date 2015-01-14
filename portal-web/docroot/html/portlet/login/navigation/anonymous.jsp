<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

boolean showAnonymousIcon = false;

if (!strutsAction.startsWith("/login/create_anonymous_account") && company.isStrangers() && portletName.equals(PortletKeys.FAST_LOGIN)) {
	showAnonymousIcon = true;
}
%>

<c:if test="<%= showAnonymousIcon %>">
	<portlet:renderURL var="anonymousURL">
		<portlet:param name="struts_action" value="/login/create_anonymous_account" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="guest"
		src='<%= themeDisplay.getPathThemeImages() + "/common/user_icon.png" %>'
		url="<%= anonymousURL %>"
	/>
</c:if>