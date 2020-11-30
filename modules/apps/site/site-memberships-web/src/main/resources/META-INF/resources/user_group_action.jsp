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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserGroup userGroup = (UserGroup)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroup(), ActionKeys.ASSIGN_USER_ROLES) %>">
		<portlet:renderURL var="assignURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/user_groups_roles.jsp" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			cssClass="assign-roles"
			data='<%=
				HashMapBuilder.<String, Object>put(
					"href", assignURL.toString()
				).put(
					"usergroupid", userGroup.getUserGroupId()
				).build()
			%>'
			id='<%= row.getRowId() + "assignRoles" %>'
			message="assign-roles"
			url="javascript:;"
		/>

		<portlet:renderURL var="unassignURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/user_groups_roles.jsp" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
			<portlet:param name="assignRoles" value="<%= Boolean.FALSE.toString() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			cssClass="unassign-roles"
			data='<%=
				HashMapBuilder.<String, Object>put(
					"href", unassignURL.toString()
				).put(
					"usergroupid", userGroup.getUserGroupId()
				).build()
			%>'
			id='<%= row.getRowId() + "unassignRoles" %>'
			message="unassign-roles"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroup(), ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:actionURL name="deleteGroupUserGroups" var="deleteGroupUserGroupsURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
			<portlet:param name="removeUserGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			message="remove-membership"
			url="<%= deleteGroupUserGroupsURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>

<aui:script sandbox="<%= true %>">
	var assignRolesLink = document.getElementById(
		'<portlet:namespace /><%= row.getRowId() %>assignRoles'
	);

	if (assignRolesLink) {
		assignRolesLink.addEventListener('click', function (event) {
			event.preventDefault();

			var target = event.target;

			if (!target.dataset.href) {
				target = target.parentElement;
			}

			var addUserGroupGroupRoleFm =
				document.<portlet:namespace />addUserGroupGroupRoleFm;

			Liferay.Util.setFormValues(addUserGroupGroupRoleFm, {
				userGroupId: target.dataset.usergroupid,
			});

			Liferay.Util.openSelectionModal({
				buttonAddLabel: '<liferay-ui:message key="done" />',
				multiple: true,
				onSelect: function (selectedItems) {
					if (selectedItems) {
						Array.prototype.forEach.call(selectedItems, function (
							selectedItem,
							index
						) {
							addUserGroupGroupRoleFm.append(selectedItem);
						});

						submitForm(addUserGroupGroupRoleFm);
					}
				},
				selectEventName: '<portlet:namespace />selectUserGroupsRoles',
				title: '<liferay-ui:message key="assign-roles" />',
				url: target.dataset.href,
			});
		});
	}

	var unassignRolesLink = document.getElementById(
		'<portlet:namespace /><%= row.getRowId() %>unassignRoles'
	);

	if (unassignRolesLink) {
		unassignRolesLink.addEventListener('click', function (event) {
			event.preventDefault();

			var target = event.target;

			if (!target.dataset.href) {
				target = target.parentElement;
			}

			var unassignUserGroupGroupRoleFm =
				document.<portlet:namespace />unassignUserGroupGroupRoleFm;

			Liferay.Util.setFormValues(unassignUserGroupGroupRoleFm, {
				userGroupId: target.dataset.usergroupid,
			});

			Liferay.Util.openSelectionModal({
				buttonAddLabel: '<liferay-ui:message key="done" />',
				multiple: true,
				onSelect: function (selectedItems) {
					if (selectedItems) {
						Array.prototype.forEach.call(selectedItems, function (
							selectedItem,
							index
						) {
							unassignUserGroupGroupRoleFm.append(selectedItem);
						});

						submitForm(unassignUserGroupGroupRoleFm);
					}
				},
				selectEventName: '<portlet:namespace />selectUserGroupsRoles',
				title: '<liferay-ui:message key="unassign-roles" />',
				url: target.dataset.href,
			});
		});
	}
</aui:script>