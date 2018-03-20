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
long groupId = ParamUtil.getLong(request, "groupId", PortalUtil.getScopeGroupId(request, refererPortletName, true));

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
String tabs1 = ParamUtil.getString(request, "tabs1", "structures");

PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= !user.isDefaultUser() %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"modified-date", "id"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<portlet:renderURL var="searchURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="tabs1" value="<%= tabs1 %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</portlet:renderURL>

			<aui:form action="<%= searchURL.toString() %>" method="post" name="searchForm">
				<liferay-util:include page="/structure_search.jsp" servletContext="<%= application %>" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteStructures();" %>'
			icon="trash"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>

	<c:if test="<%= ddmDisplay.isShowAddButton(themeDisplay.getScopeGroup()) && DDMStructurePermission.containsAddStruturePermission(permissionChecker, groupId, scopeClassNameId) %>">
		<liferay-frontend:management-bar-buttons>
			<liferay-portlet:renderURL var="viewStructuresURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</liferay-portlet:renderURL>

			<liferay-portlet:renderURL var="addStructureURL">
				<portlet:param name="mvcPath" value="/edit_structure.jsp" />
				<portlet:param name="redirect" value="<%= viewStructuresURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add") %>'
					url="<%= addStructureURL %>"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>
	</c:if>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteStructures() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			var searchContainer = AUI.$('#<portlet:namespace />entriesContainer', form);

			form.attr('method', 'post');
			form.fm('deleteStructureIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteStructure"><portlet:param name="mvcPath" value="/view.jsp" /></portlet:actionURL>');
		}
	}
</aui:script>