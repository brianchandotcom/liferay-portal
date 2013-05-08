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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "strutsAction", "/document_library/view_file_entry_type");

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");
%>

<aui:nav-bar>
	<aui:nav>
		<portlet:renderURL var="viewFileEntryTypesURL">
			<portlet:param name="struts_action" value="<%= strutsAction %>" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewFileEntryTypesURL %>" label="view-all" selected='<%= toolbarItem.equals("view-all") %>' />

		<c:if test="<%= DLPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_DOCUMENT_TYPE) %>">
			<portlet:renderURL var="addFileEntryTypeURL">
				<portlet:param name="struts_action" value="/document_library/edit_file_entry_type" />
				<portlet:param name="redirect" value="<%= viewFileEntryTypesURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addFileEntryTypeURL %>" iconClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />
		</c:if>
	</aui:nav>
</aui:nav-bar>