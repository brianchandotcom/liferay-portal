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
String toolbarItem = ParamUtil.getString(request, "toolbar-item", "view-all");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewApplicationsURL">
		<portlet:param name="mvcPath" value="/admin/view.jsp" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<aui:a href="<%= viewApplicationsURL %>" label='<%= permissionChecker.isCompanyAdmin() ? "view-all" : "my-applications" %>' />
	</span>

	<c:if test="<%= OAuthPermission.contains(permissionChecker, OAuthActionKeys.ADD_APPLICATION) %>">
		<portlet:renderURL var="addApplicationURL">
			<portlet:param name="mvcPath" value="/admin/edit_application.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add") ? "current" : StringPool.BLANK %>">
			<aui:a href="<%= addApplicationURL %>" label="add" />
		</span>
	</c:if>
</div>