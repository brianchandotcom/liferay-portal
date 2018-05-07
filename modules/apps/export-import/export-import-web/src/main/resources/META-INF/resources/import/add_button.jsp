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

<%@ include file="/import/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
%>

<liferay-frontend:add-menu
	inline="<%= true %>"
>
	<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="addNewImportProcessURL">
		<portlet:param name="mvcPath" value="/import/new_import/import_layouts.jsp" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		<portlet:param name="validate" value="<%= String.valueOf(Boolean.TRUE) %>" />
	</liferay-portlet:renderURL>

	<liferay-frontend:add-menu-item
		title='<%= LanguageUtil.get(request, "import") %>'
		url="<%= addNewImportProcessURL %>"
	/>
</liferay-frontend:add-menu>