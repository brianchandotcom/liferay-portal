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

<%@ include file="/admin/init.jsp" %>

<%
String currentTab = ParamUtil.getString(request, "currentTab", "forms");
%>

<c:choose>
	<c:when test='<%= currentTab.equals("forms") %>'>
		<c:if test="<%= ddmFormAdminDisplayContext.isShowAddButton() %>">
			<portlet:renderURL var="addFormInstanceURL">
				<portlet:param name="mvcPath" value="/admin/edit_form_instance.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "new-form") %>'
					url="<%= addFormInstanceURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</c:when>
	<c:when test='<%= currentTab.equals("element-set") %>'>
		<c:if test="<%= ddmFormAdminDisplayContext.isShowAddButton() %>">
			<portlet:renderURL var="addFieldSetURL">
				<portlet:param name="mvcPath" value="/admin/edit_element_set.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "new-element-set") %>'
					url="<%= addFieldSetURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</c:when>
</c:choose>