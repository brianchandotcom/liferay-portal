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
String displayStyle = (String)request.getAttribute("edit_role_assignments.jsp-displayStyle");

SearchContainer searchContainer = (SearchContainer)request.getAttribute("edit_role_assignments.jsp-searchContainer");
%>

<liferay-ui:search-container
	id="assigneesSearch"
	searchContainer="<%= searchContainer %>"
	var="userGroupSearchContainer"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="group.groupId"
		modelVar="userGroup"
	>
		<%@ include file="/user_group_columns.jspf" %>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="<%= displayStyle %>"
		markupView="lexicon"
	/>
</liferay-ui:search-container>