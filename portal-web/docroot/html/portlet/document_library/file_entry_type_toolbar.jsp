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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String mvcPath = ParamUtil.getString(request, "mvcPath", "/html/portlet/document_library/view_file_entry_type.jsp");

String toolbarItem = ParamUtil.getString(request, "toolbarItem");

boolean includeBasicFileEntryType = ParamUtil.getBoolean(request, "includeBasicFileEntryType");
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewFileEntryTypesURL">
			<portlet:param name="mvcPath" value="<%= mvcPath %>" />
			<portlet:param name="includeBasicFileEntryType" value="<%= String.valueOf(includeBasicFileEntryType) %>" />
		</portlet:renderURL>

		<c:if test="<%= DLPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_DOCUMENT_TYPE) %>">
			<portlet:renderURL var="addFileEntryTypeURL">
				<portlet:param name="mvcPath" value="/document_library/edit_file_entry_type" />
				<portlet:param name="redirect" value="<%= viewFileEntryTypesURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addFileEntryTypeURL %>" iconCssClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>

	<aui:nav-bar-search>
		<div class="form-search">
			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="mvcPath" value="<%= mvcPath %>" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= searchURL.toString() %>" method="post" name="fm">
				<liferay-ui:input-search />
			</aui:form>
		</div>
	</aui:nav-bar-search>
</aui:nav-bar>