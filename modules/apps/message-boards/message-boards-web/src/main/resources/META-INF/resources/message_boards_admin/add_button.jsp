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

<%@ include file="/message_boards/init.jsp" %>

<%
long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));
%>

<liferay-frontend:add-menu
	inline="<%= true %>"
>
	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_CATEGORY) %>">
		<portlet:renderURL var="addCategoryURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_category" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="parentCategoryId" value="<%= String.valueOf(categoryId) %>" />
		</portlet:renderURL>

		<liferay-frontend:add-menu-item
			title='<%= LanguageUtil.get(request, (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ? "category[message-board]" : "subcategory[message-board]") %>'
			url="<%= addCategoryURL.toString() %>"
		/>
	</c:if>

	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_MESSAGE) %>">
		<portlet:renderURL var="addMessageURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_message" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(categoryId) %>" />
		</portlet:renderURL>

		<liferay-frontend:add-menu-item
			title='<%= LanguageUtil.get(request, "thread") %>'
			url="<%= addMessageURL.toString() %>"
		/>
	</c:if>
</liferay-frontend:add-menu>