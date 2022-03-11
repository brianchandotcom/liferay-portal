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
EditRolesDisplayContext editRolesDisplayContext = new EditRolesDisplayContext(request, renderRequest);
%>

<liferay-ui:search-container
	searchContainer="<%= editRolesDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Role"
		escapedModel="<%= true %>"
		keyProperty="roleId"
		modelVar="role"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="redirect" value='<%= String.valueOf(request.getAttribute("edit_roles.jsp-redirect")) %>' />
			<portlet:param name="className" value='<%= String.valueOf(request.getAttribute("edit_roles.jsp-className")) %>' />
			<portlet:param name="groupId" value="<%= String.valueOf(editRolesDisplayContext.getGroupId()) %>" />
			<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-small table-cell-minw-200 table-title"
			href="<%= rowURL %>"
			name="title"
			value="<%= role.getTitle(locale) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand-smaller table-cell-minw-150 table-cell-ws-nowrap"
			href="<%= rowURL %>"
			name="type"
			value="<%= LanguageUtil.get(request, role.getTypeLabel()) %>"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-300"
			href="<%= rowURL %>"
			name="description"
			value="<%= role.getDescription(locale) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>