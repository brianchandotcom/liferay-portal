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

<%@ include file="/html/portlet/directory/init.jsp" %>

<%
PortletURL tabs1URL = renderResponse.createRenderURL();

tabs1URL.setParameter("struts_action", "/directory/view");

String tabs1Names = ParamUtil.getString(request, "tabs1Names", "users,organizations,user-groups");

tabs1Names = HtmlUtil.escape(tabs1Names);

String tabs1Values = tabs1Names;

String viewUsersRedirect = ParamUtil.getString(request, "viewUsersRedirect");

String redirect = ParamUtil.getString(request, "redirect", viewUsersRedirect);

String backURL = ParamUtil.getString(request, "backURL", redirect);
%>

<liferay-ui:tabs
	backURL="<%= backURL %>"
	names="<%= tabs1Names %>"
	tabsValues="<%= tabs1Values %>"
	url="<%= tabs1URL.toString() %>"
/>