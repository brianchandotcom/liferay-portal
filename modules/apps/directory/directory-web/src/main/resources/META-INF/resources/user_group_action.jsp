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

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserGroup userGroup = (UserGroup)row.getObject();
%>

<portlet:renderURL var="viewUsersURL">
	<portlet:param name="mvcRenderCommandName" value="/directory/view" />
	<portlet:param name="tabs1" value="users" />
	<portlet:param name="viewUsersRedirect" value="<%= currentURL %>" />
	<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
</portlet:renderURL>

<liferay-ui:icon
	icon="users"
	markupView="lexicon"
	message="view-users"
	method="get"
	url="<%= viewUsersURL %>"
/>