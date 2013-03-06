<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");
List<UserGroup> userGroups = (List<UserGroup>)request.getAttribute("user.userGroups");
%>

<liferay-ui:error-marker key="errorSection" value="user-groups" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeUserGroupIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<h3><liferay-ui:message key="user-groups" /></h3>

<liferay-ui:search-container
	headerNames="name,null"
>
	<liferay-ui:search-container-results
		results="<%= userGroups %>"
		total="<%= userGroups.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="name"
		/>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !UserGroupMembershipPolicyUtil.isMembershipRequired(selUser.getUserId(), userGroup.getUserGroupId()) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= userGroup.getUserGroupId() %>" href="javascript:;"><%= removeUserGroupIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<br />

	<liferay-ui:icon
		cssClass="modify-link"
		image="add"
		label="<%= true %>"
		message="select"
		url='<%= "javascript:" + renderResponse.getNamespace() + "openUserGroupSelector();" %>'
	/>
</c:if>

<aui:script>
	function <portlet:namespace />openUserGroupSelector() {
		var userGroupWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/user_groups_admin/select_user_group" /><portlet:param name="p_u_i_d" value='<%= String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>', 'usergroup', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		userGroupWindow.focus();
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectUserGroup',
		function(userGroupId, name) {
			var A = AUI();

			var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />userGroupsSearchContainer');

			var rowColumns = [];

			rowColumns.push(name);
			rowColumns.push('<a class="modify-link" data-rowId="' + userGroupId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeUserGroupIcon) %></a>');

			searchContainer.addRow(rowColumns, userGroupId);
			searchContainer.updateDataStore();
		},
		['liferay-search-container']
	);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />userGroupsSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;
			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>