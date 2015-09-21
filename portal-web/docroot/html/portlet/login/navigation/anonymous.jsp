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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

boolean showAnonymousIcon = false;

if (!mvcRenderCommandName.startsWith("/login/create_anonymous_account") && company.isStrangers() && portletName.equals(PortletKeys.FAST_LOGIN)) {
	showAnonymousIcon = true;
}
%>

<c:if test="<%= showAnonymousIcon %>">
	<portlet:renderURL var="anonymousURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="/login/create_anonymous_account" />
	</portlet:renderURL>

	<liferay-ui:icon
		iconCssClass="icon-user"
		message="guest"
		url="<%= anonymousURL %>"
	/>
</c:if>