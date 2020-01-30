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

		<%
		Map<String, Object> assignData = new HashMap<>();

		assignData.put("href", assignURL.toString());
		assignData.put("usergroupid", userGroup.getUserGroupId());
		%>

		<liferay-ui:icon
			cssClass="assign-site-roles"
			data="<%= assignData %>"
			id='<%= row.getRowId() + "assignSiteRoles" %>'
			message="assign-roles"
			url="javascript:;"
		/>

		<portlet:renderURL var="unassignURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/user_groups_roles.jsp" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
			<portlet:param name="assignRoles" value="<%= Boolean.FALSE.toString() %>" />
		</portlet:renderURL>

		<%
		Map<String, Object> unassignData = new HashMap<>();

		unassignData.put("href", unassignURL.toString());
		unassignData.put("usergroupid", userGroup.getUserGroupId());
		%>

		<liferay-ui:icon
			cssClass="unassign-site-roles"
			data="<%= unassignData %>"
			id='<%= row.getRowId() + "unassignSiteRoles" %>'
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

<aui:script require="metal-dom/src/dom as dom, frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var assignSiteRolesLink = document.getElementById(
		'<portlet:namespace /><%= row.getRowId() %>assignSiteRoles'
	);

	if (assignSiteRolesLink) {
		assignSiteRolesLink.addEventListener('click', function(event) {
			event.preventDefault();

			var target = event.target;

			if (!target.dataset.href) {
				target = target.parentElement;
			}

			var addUserGroupGroupRoleFm =
				document.<portlet:namespace />addUserGroupGroupRoleFm;

			Liferay.Util.setFormValues(addUserGroupGroupRoleFm, {
				userGroupId: target.dataset.usergroupid
			});

			var itemSelectorDialog = new ItemSelectorDialog.default({
				buttonAddLabel: '<liferay-ui:message key="done" />',
				eventName: '<portlet:namespace />selectUserGroupsRoles',
				title: '<liferay-ui:message key="assign-roles" />',
				url: target.dataset.href
			});

			itemSelectorDialog.on('selectedItemChange', function(event) {
				var selectedItems = event.selectedItem;

				if (selectedItems) {
					Array.prototype.forEach.call(selectedItems, function(
						selectedItem,
						index
					) {
						dom.append(addUserGroupGroupRoleFm, selectedItem);
					});

					submitForm(addUserGroupGroupRoleFm);
				}
			});

			itemSelectorDialog.open();
		});
	}

	var unassignSiteRolesLink = document.getElementById(
		'<portlet:namespace /><%= row.getRowId() %>unassignSiteRoles'
	);

	if (unassignSiteRolesLink) {
		unassignSiteRolesLink.addEventListener('click', function(event) {
			event.preventDefault();

			var target = event.target;

			if (!target.dataset.href) {
				target = target.parentElement;
			}

			var unassignUserGroupGroupRoleFm =
				document.<portlet:namespace />unassignUserGroupGroupRoleFm;

			Liferay.Util.setFormValues(unassignUserGroupGroupRoleFm, {
				userGroupId: target.dataset.usergroupid
			});

			var itemSelectorDialog = new ItemSelectorDialog.default({
				buttonAddLabel: '<liferay-ui:message key="done" />',
				eventName: '<portlet:namespace />selectUserGroupsRoles',
				title: '<liferay-ui:message key="unassign-roles" />',
				url: target.dataset.href
			});

			itemSelectorDialog.on('selectedItemChange', function(event) {
				var selectedItems = event.selectedItem;

				if (selectedItems) {
					Array.prototype.forEach.call(selectedItems, function(
						selectedItem,
						index
					) {
						dom.append(unassignUserGroupGroupRoleFm, selectedItem);
					});

					submitForm(unassignUserGroupGroupRoleFm);
				}
			});

			itemSelectorDialog.open();
		});
	}
</aui:script>