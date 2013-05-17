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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");
List<Group> groups = (List<Group>)request.getAttribute("user.groups");
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");
Long[] organizationIds = UsersAdminUtil.getOrganizationIds(organizations);
List<Role> roles = (List<Role>)request.getAttribute("user.roles");
List<UserGroupRole> organizationRoles = (List<UserGroupRole>)request.getAttribute("user.organizationRoles");
List<UserGroupRole> siteRoles = (List<UserGroupRole>)request.getAttribute("user.siteRoles");
List<Group> allGroups = (List<Group>)request.getAttribute("user.allGroups");

List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

userGroupRoles.addAll(organizationRoles);
userGroupRoles.addAll(siteRoles);
%>

<liferay-ui:error-marker key="errorSection" value="roles" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="groupRolesRoleIds" type="hidden" value="<%= ListUtil.toString(userGroupRoles, UserGroupRole.ROLE_ID_ACCESSOR) %>" />
<aui:input name="groupRolesGroupIds" type="hidden" value="<%= ListUtil.toString(userGroupRoles, UserGroupRole.GROUP_ID_ACCESSOR) %>" />

<h3><liferay-ui:message key="regular-roles" /></h3>

<liferay-ui:search-container
	headerNames="title,null"
	id="rolesSearchContainer"
>
	<liferay-ui:search-container-results
		results="<%= roles %>"
		total="<%= roles.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		keyProperty="roleId"
		modelVar="role"
	>
		<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(role) %>" />
		<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(role) %>" />

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
		/>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !RoleMembershipPolicyUtil.isRoleRequired(selUser.getUserId(), role.getRoleId()) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= role.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		id="selectRegularRoleLink"
		image="add"
		label="<%= true %>"
		message="select"
		method="get"
		url="javascript:;"
	/>

	<aui:script use="aui-base">
		A.one('#<portlet:namespace />selectRegularRoleLink').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							zIndex: Liferay.zIndex.WINDOW + 2,
							width: 600
						},
						id: '<portlet:namespace />selectRegularRole',
						title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "regular-role") %>',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/users_admin/select_regular_role" /><portlet:param name="p_u_i_d" value='<%= (selUser == null) ? "0" : String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>'
					},
					function(event){
						<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupname, event.groupid);
					}
				);
			}
		);
	</aui:script>
</c:if>

<br /><br />

<h3><liferay-ui:message key="inherited-roles" /></h3>

<liferay-ui:search-container
	headerNames="title,null"
	id="inheritedRolesSearchContainer"
>
	<liferay-ui:search-container-results
		results="<%= allGroups %>"
		total="<%= allGroups.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		keyProperty="groupId"
		modelVar="group"
		rowIdProperty="friendlyURL"
	>

		<%
		List<Role> groupRoles = RoleLocalServiceUtil.getGroupRoles(group.getGroupId());

		if (!groupRoles.isEmpty()) {
			Role groupRole = groupRoles.get(0);
		%>

			<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(groupRole) %>" />
			<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(groupRole) %>" />

			<liferay-ui:search-container-column-text
				name="group"
				value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
			/>
			<liferay-ui:search-container-column-text
				name="title"
				value="<%= HtmlUtil.escape(ListUtil.toString(groupRoles, Role.NAME_ACCESSOR)) %>"
			/>

		<%
		}
		%>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<br /><br />

<h3><liferay-ui:message key="organization-roles" /></h3>

<c:if test="<%= organizations.isEmpty() && organizationRoles.isEmpty() %>">
	<liferay-ui:message key="this-user-does-not-belong-to-an-organization-to-which-an-organization-role-can-be-assigned" />
</c:if>

<c:if test="<%= !organizations.isEmpty() %>">
	<liferay-ui:search-container
		headerNames="title,organization,null"
		id="organizationRolesSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= organizationRoles %>"
			total="<%= organizationRoles.size() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroupRole"
			keyProperty="roleId"
			modelVar="userGroupRole"
		>
			<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(userGroupRole.getRole()) %>" />
			<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(userGroupRole.getRole()) %>" />

			<liferay-ui:search-container-column-text
				name="title"
				value="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="organization"
				value="<%= HtmlUtil.escape(userGroupRole.getGroup().getDescriptiveName(locale)) %>"
			/>

			<%
			boolean membershipProtected = false;

			Group group = userGroupRole.getGroup();

			Role role = userGroupRole.getRole();

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				membershipProtected = OrganizationMembershipPolicyUtil.isMembershipProtected(permissionChecker, userGroupRole.getUserId(), group.getOrganizationId());
			}
			else {
				membershipProtected = SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, userGroupRole.getUserId(), group.getGroupId());
			}
			%>

			<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !membershipProtected %>">
				<liferay-ui:search-container-column-text>
					<a class="modify-link" data-groupId="<%= userGroupRole.getGroupId() %>" data-rowId="<%= userGroupRole.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator paginate="<%= false %>" />
	</liferay-ui:search-container>

	<aui:script use="liferay-search-container">
		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />organizationRolesSearchContainer');

		searchContainer.get('contentBox').delegate(
			'click',
			function(event) {
				var link = event.currentTarget;
				var tr = link.ancestor('tr');

				var rowId = link.getAttribute('data-rowId');
				var groupId =link.getAttribute('data-groupId');

				searchContainer.deleteRow(tr, rowId);

				<portlet:namespace />deleteGroupRole(rowId, groupId);
			},
			'.modify-link'
		);
	</aui:script>
</c:if>

<c:if test="<%= !organizations.isEmpty() && !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		id="selectOrganizationRoleLink"
		image="add"
		label="<%= true %>"
		message="select"
		method="get"
		url="javascript:;"
	/>

	<aui:script use="aui-base">
		A.one('#<portlet:namespace />selectOrganizationRoleLink').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							modal: true,
							zIndex: Liferay.zIndex.WINDOW + 2,
							width: 600
						},
						id: '<portlet:namespace />selectOrganizationRole',
						title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "organization-role") %>',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/users_admin/select_organization_role" /><portlet:param name="step" value="1" /><portlet:param name="organizationIds" value="<%= StringUtil.merge(organizationIds) %>" /><portlet:param name="p_u_i_d" value='<%= (selUser == null) ? "0" : String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>'
					},
					function(event){
						<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupname, event.groupid);
					}
				);
			}
		);
	</aui:script>
</c:if>

<br /><br />

<h3><liferay-ui:message key="site-roles" /></h3>

<c:choose>
	<c:when test="<%= groups.isEmpty() %>">
		<liferay-ui:message key="this-user-does-not-belong-to-a-site-to-which-a-site-role-can-be-assigned" />
	</c:when>
	<c:otherwise>
		<liferay-ui:search-container
			headerNames="title,site,null"
			id="siteRolesSearchContainer"
		>
			<liferay-ui:search-container-results
				results="<%= siteRoles %>"
				total="<%= siteRoles.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserGroupRole"
				keyProperty="roleId"
				modelVar="userGroupRole"
			>
				<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(userGroupRole.getRole()) %>" />
				<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(userGroupRole.getRole()) %>" />

				<liferay-ui:search-container-column-text
					name="title"
					value="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					name="site"
					value="<%= HtmlUtil.escape(userGroupRole.getGroup().getDescriptiveName(locale)) %>"
				/>

				<%
				boolean membershipProtected = false;

				Group group = userGroupRole.getGroup();

				Role role = userGroupRole.getRole();

				if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
					membershipProtected = OrganizationMembershipPolicyUtil.isMembershipProtected(permissionChecker, userGroupRole.getUserId(), group.getOrganizationId());
				}
				else {
					membershipProtected = SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, userGroupRole.getUserId(), group.getGroupId());
				}
				%>

				<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !membershipProtected %>">
					<liferay-ui:search-container-column-text>
						<a class="modify-link" data-groupId="<%= userGroupRole.getGroupId() %>" data-rowId="<%= userGroupRole.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
					</liferay-ui:search-container-column-text>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator paginate="<%= false %>" />
		</liferay-ui:search-container>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
			<liferay-ui:icon
				cssClass="modify-link"
				id="selectSiteRoleLink"
				image="add"
				label="<%= true %>"
				message="select"
				method="get"
				url="javascript:;"
			/>

			<aui:script use="liferay-search-container">
				var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />siteRolesSearchContainer');

				searchContainer.get('contentBox').delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var tr = link.ancestor('tr');

						var rowId = link.getAttribute('data-rowId');
						var groupId =link.getAttribute('data-groupId');

						searchContainer.deleteRow(tr, rowId);

						<portlet:namespace />deleteGroupRole(rowId, groupId);
					},
					'.modify-link'
				);

				A.one('#<portlet:namespace />selectSiteRoleLink').on(
					'click',
					function(event) {
						Liferay.Util.selectEntity(
							{
								dialog: {
									,
									constrain: true,
									modal: true,
									zIndex: Liferay.zIndex.WINDOW + 2,
									width: 600
								},
								id: '<portlet:namespace />selectSiteRole',
								title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "site-role") %>',
								uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/users_admin/select_site_role" /><portlet:param name="step" value="1" /><portlet:param name="p_u_i_d" value='<%= (selUser == null) ? "0" : String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>'
							},
							function(event){
								<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupname, event.groupid);
							}
						);
					}
				);
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>

<aui:script>
	var <portlet:namespace />groupRolesGroupIds = ['<%= ListUtil.toString(userGroupRoles, UserGroupRole.GROUP_ID_ACCESSOR, "', '") %>'];
	var <portlet:namespace />groupRolesRoleIds = ['<%= ListUtil.toString(userGroupRoles, UserGroupRole.ROLE_ID_ACCESSOR, "', '") %>'];

	function <portlet:namespace />deleteGroupRole(roleId, groupId) {
		for (var i = 0; i < <portlet:namespace />groupRolesRoleIds.length; i++) {
			if ((<portlet:namespace />groupRolesRoleIds[i] == roleId) && (<portlet:namespace />groupRolesGroupIds[i] == groupId)) {
				 <portlet:namespace />groupRolesGroupIds.splice(i, 1);
				 <portlet:namespace />groupRolesRoleIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />groupRolesGroupIds.value = <portlet:namespace />groupRolesGroupIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />groupRolesRoleIds.value = <portlet:namespace />groupRolesRoleIds.join(',');
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectRole',
		function(roleId, name, searchContainer, groupName, groupId) {
			var A = AUI();

			var searchContainerName = '<portlet:namespace />' + searchContainer + 'SearchContainer';

			searchContainer = Liferay.SearchContainer.get(searchContainerName);

			var rowColumns = [];

			rowColumns.push(A.Escape.html(name));

			if (groupName) {
				rowColumns.push(A.Escape.html(groupName));
			}

			if (groupId) {
				rowColumns.push('<a class="modify-link" data-groupId="' + groupId + '" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

				<portlet:namespace />groupRolesRoleIds.push(roleId);
				<portlet:namespace />groupRolesGroupIds.push(groupId);

				document.<portlet:namespace />fm.<portlet:namespace />groupRolesRoleIds.value = <portlet:namespace />groupRolesRoleIds.join(',');
				document.<portlet:namespace />fm.<portlet:namespace />groupRolesGroupIds.value = <portlet:namespace />groupRolesGroupIds.join(',');
			}
			else {
				rowColumns.push('<a class="modify-link" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');
			}

			searchContainer.addRow(rowColumns, roleId);
			searchContainer.updateDataStore();
		},
		['liferay-search-container', 'escape']
	);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />rolesSearchContainer');

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