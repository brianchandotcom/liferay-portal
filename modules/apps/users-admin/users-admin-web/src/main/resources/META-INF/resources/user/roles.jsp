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
User selUser = (User)request.getAttribute("user.selUser");
List<Group> groups = (List<Group>)request.getAttribute("user.groups");
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");
Long[] organizationIds = UsersAdminUtil.getOrganizationIds(organizations);
List<Role> roles = (List<Role>)request.getAttribute("user.roles");
List<UserGroupRole> organizationRoles = (List<UserGroupRole>)request.getAttribute("user.organizationRoles");
List<UserGroupRole> siteRoles = (List<UserGroupRole>)request.getAttribute("user.siteRoles");
List<UserGroupGroupRole> inheritedSiteRoles = (List<UserGroupGroupRole>)request.getAttribute("user.inheritedSiteRoles");
List<Group> roleGroups = (List<Group>)request.getAttribute("user.roleGroups");

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "roles");

String regularRoleSyncEntitiesEventName = liferayPortletResponse.getNamespace() + "syncRegularRoles";
String siteRoleSyncEntitiesEventName = liferayPortletResponse.getNamespace() + "syncSiteRoles";
String organizationRoleSyncEntitiesEventName = liferayPortletResponse.getNamespace() + "syncOrganizationRoles";
%>

<liferay-ui:error-marker key="errorSection" value="roles" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon
		iconCssClass="icon-remove"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addGroupRolesGroupIds" type="hidden" />
<aui:input name="addGroupRolesRoleIds" type="hidden" />
<aui:input name="addRoleIds" type="hidden" />
<aui:input name="deleteGroupRolesGroupIds" type="hidden" />
<aui:input name="deleteGroupRolesRoleIds" type="hidden" />
<aui:input name="deleteRoleIds" type="hidden" />

<h3><liferay-ui:message key="regular-roles" /></h3>

<liferay-ui:search-container
	curParam="regularRolesCur"
	headerNames="title,null"
	id="rolesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= roles.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= roles.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		keyProperty="roleId"
		modelVar="role"
	>
		<liferay-ui:search-container-column-text
			name="title"
		>
			<liferay-ui:icon
				iconCssClass="<%= RolesAdminUtil.getIconCssClass(role) %>"
				label="<%= true %>"
				message="<%= HtmlUtil.escape(role.getTitle(locale)) %>"
			/>
		</liferay-ui:search-container-column-text>

		<c:if test="<%= !portletName.equals(myAccountPortletId) && !RoleMembershipPolicyUtil.isRoleRequired(selUser.getUserId(), role.getRoleId()) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= role.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectRegularRoleLink"
		label="<%= true %>"
		linkCssClass="btn btn-default"
		message="select"
		method="get"
		url="javascript:;"
	/>

	<aui:script>
		AUI.$('#<portlet:namespace />selectRegularRoleLink').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							width: 600
						},

						<%
						String regularRoleEventName = liferayPortletResponse.getNamespace() + "selectRegularRole";
						%>

						id: '<%= regularRoleEventName %>',
						title: '<liferay-ui:message arguments="regular-role" key="select-x" />',

						<%
						PortletURL selectRegularRoleURL = PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.BROWSE);

						selectRegularRoleURL.setParameter("p_u_i_d", (selUser == null) ? "0" : String.valueOf(selUser.getUserId()));
						selectRegularRoleURL.setParameter("eventName", regularRoleEventName);
						selectRegularRoleURL.setParameter("syncEntitiesEventName", regularRoleSyncEntitiesEventName);
						selectRegularRoleURL.setWindowState(LiferayWindowState.POP_UP);
						%>

						uri: '<%= selectRegularRoleURL.toString() %>'
					},
					function(event) {
						<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupdescriptivename, event.groupid, event.iconcssclass);
					}
				);
			}
		);
	</aui:script>
</c:if>

<h3><liferay-ui:message key="inherited-regular-roles" /></h3>

<c:if test="<%= roleGroups.isEmpty() %>">
	<liferay-ui:message key="this-user-does-not-have-any-inherited-regular-roles" />
</c:if>

<liferay-ui:search-container
	curParam="inheritedRegularRolesCur"
	headerNames="title,group"
	id="inheritedRolesSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= roleGroups.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= roleGroups.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		keyProperty="groupId"
		modelVar="group"
		rowIdProperty="friendlyURL"
	>

		<%
		List<Role> groupRoles = RoleLocalServiceUtil.getGroupRoles(group.getGroupId());
		%>

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(ListUtil.toString(groupRoles, Role.NAME_ACCESSOR)) %>"
		>
			<liferay-ui:icon
				iconCssClass="<%= RolesAdminUtil.getIconCssClass(groupRoles.get(0)) %>"
				label="<%= true %>"
				message="<%= HtmlUtil.escape(ListUtil.toString(groupRoles, Role.NAME_ACCESSOR)) %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="group"
			value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<h3><liferay-ui:message key="organization-roles" /></h3>

<c:if test="<%= organizations.isEmpty() && organizationRoles.isEmpty() %>">
	<liferay-ui:message key="this-user-does-not-belong-to-an-organization-to-which-an-organization-role-can-be-assigned" />
</c:if>

<c:if test="<%= !organizations.isEmpty() %>">
	<liferay-ui:search-container
		curParam="organizationRolesCur"
		headerNames="title,organization,null"
		id="organizationRolesSearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= organizationRoles.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= organizationRoles.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroupRole"
			keyProperty="roleId"
			modelVar="userGroupRole"
		>
			<liferay-ui:search-container-column-text
				name="title"
			>
				<liferay-ui:icon
					iconCssClass="<%= RolesAdminUtil.getIconCssClass(userGroupRole.getRole()) %>"
					label="<%= true %>"
					message="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
				/>
			</liferay-ui:search-container-column-text>

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

			<c:if test="<%= !portletName.equals(myAccountPortletId) && !membershipProtected %>">
				<liferay-ui:search-container-column-text>
					<a class="modify-link" data-groupId="<%= userGroupRole.getGroupId() %>" data-rowId="<%= userGroupRole.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>

	<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
		<aui:script use="liferay-search-container">
			var Util = Liferay.Util;

			var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />organizationRolesSearchContainer');

			var searchContainerContentBox = searchContainer.get('contentBox');

			searchContainerContentBox.delegate(
				'click',
				function(event) {
					var link = event.currentTarget;
					var tr = link.ancestor('tr');

					var groupId = link.getAttribute('data-groupId');
					var rowId = link.getAttribute('data-rowId');

					var selectOrganizationRole = Util.getWindow('<portlet:namespace />selectOrganizationRole');

					if (selectOrganizationRole) {
						var selectButton = selectOrganizationRole.iframe.node.get('contentWindow.document').one('.selector-button[data-groupid="' + groupId + '"][data-roleid="' + rowId + '"]');

						Util.toggleDisabled(selectButton, false);
					}

					searchContainer.deleteRow(tr, rowId);

					<portlet:namespace />deleteGroupRole(rowId, groupId);
				},
				'.modify-link'
			);

			Liferay.on(
				'<%= organizationRoleSyncEntitiesEventName %>',
				function(event) {
					event.selectors.each(
						function(item, index, collection) {
							var groupId = item.attr('data-groupid');
							var roleId = item.attr('data-roleid');

							for (var i = 0; i < <portlet:namespace />deleteGroupRolesGroupIds.length; i++) {
								if ((<portlet:namespace />deleteGroupRolesGroupIds[i] == groupId) && (<portlet:namespace />deleteGroupRolesRoleIds[i] == roleId)) {
									Util.toggleDisabled(item, false);

									break;
								}
							}
						}
					);
				}
			);
		</aui:script>
	</c:if>
</c:if>

<c:if test="<%= !organizations.isEmpty() && !portletName.equals(myAccountPortletId) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectOrganizationRoleLink"
		label="<%= true %>"
		linkCssClass="btn btn-default"
		message="select"
		method="get"
		url="javascript:;"
	/>

	<aui:script>
		AUI.$('#<portlet:namespace />selectOrganizationRoleLink').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							modal: true
						},

						<%
						String organizationRoleEventName = liferayPortletResponse.getNamespace() + "selectOrganizationRole";
						%>

						id: '<%= organizationRoleEventName %>',
						title: '<liferay-ui:message arguments="organization-role" key="select-x" />',

						<%
						PortletURL selectOrganizationRoleURL = PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.BROWSE);

						selectOrganizationRoleURL.setParameter("p_u_i_d", (selUser == null) ? "0" : String.valueOf(selUser.getUserId()));
						selectOrganizationRoleURL.setParameter("eventName", organizationRoleEventName);
						selectOrganizationRoleURL.setParameter("organizationIds", StringUtil.merge(organizationIds));
						selectOrganizationRoleURL.setParameter("roleType", String.valueOf(RoleConstants.TYPE_ORGANIZATION));
						selectOrganizationRoleURL.setParameter("step", "1");
						selectOrganizationRoleURL.setParameter("syncEntitiesEventName", organizationRoleSyncEntitiesEventName);
						selectOrganizationRoleURL.setWindowState(LiferayWindowState.POP_UP);
						%>

						uri: '<%= selectOrganizationRoleURL.toString() %>'
					},
					function(event) {
						<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupdescriptivename, event.groupid, event.iconcssclass);
					}
				);
			}
		);
	</aui:script>
</c:if>

<h3><liferay-ui:message key="site-roles" /></h3>

<c:choose>
	<c:when test="<%= groups.isEmpty() %>">
		<liferay-ui:message key="this-user-does-not-belong-to-a-site-to-which-a-site-role-can-be-assigned" />
	</c:when>
	<c:otherwise>
		<liferay-ui:search-container
			curParam="siteRolesCur"
			headerNames="title,site,null"
			id="siteRolesSearchContainer"
			iteratorURL="<%= currentURLObj %>"
			total="<%= siteRoles.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= siteRoles.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserGroupRole"
				keyProperty="roleId"
				modelVar="userGroupRole"
			>
				<liferay-ui:search-container-column-text
					name="title"
				>
					<liferay-ui:icon
						iconCssClass="<%= RolesAdminUtil.getIconCssClass(userGroupRole.getRole()) %>"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
					/>
				</liferay-ui:search-container-column-text>

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

				<c:if test="<%= !portletName.equals(myAccountPortletId) && !membershipProtected %>">
					<liferay-ui:search-container-column-text>
						<a class="modify-link" data-groupId="<%= userGroupRole.getGroupId() %>" data-rowId="<%= userGroupRole.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
					</liferay-ui:search-container-column-text>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
			<liferay-ui:icon
				cssClass="modify-link"
				iconCssClass="icon-search"
				id="selectSiteRoleLink"
				label="<%= true %>"
				linkCssClass="btn btn-default"
				message="select"
				method="get"
				url="javascript:;"
			/>

			<aui:script use="liferay-search-container">
				var Util = Liferay.Util;

				var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />siteRolesSearchContainer');

				var searchContainerContentBox = searchContainer.get('contentBox');

				searchContainerContentBox.delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var tr = link.ancestor('tr');

						var groupId = link.getAttribute('data-groupId');
						var rowId = link.getAttribute('data-rowId');

						var selectSiteRole = Util.getWindow('<portlet:namespace />selectSiteRole');

						if (selectSiteRole) {
							var selectButton = selectSiteRole.iframe.node.get('contentWindow.document').one('.selector-button[data-groupid="' + groupId + '"][data-roleid="' + rowId + '"]');

							Util.toggleDisabled(selectButton, false);
						}

						searchContainer.deleteRow(tr, rowId);

						<portlet:namespace />deleteGroupRole(rowId, groupId);
					},
					'.modify-link'
				);

				Liferay.on(
					'<%= siteRoleSyncEntitiesEventName %>',
					function(event) {
						event.selectors.each(
							function(item, index, collection) {
								var groupId = item.attr('data-groupid');
								var roleId = item.attr('data-roleid');

								for (var i = 0; i < <portlet:namespace />deleteGroupRolesGroupIds.length; i++) {
									if ((<portlet:namespace />deleteGroupRolesGroupIds[i] == groupId) && (<portlet:namespace />deleteGroupRolesRoleIds[i] == roleId)) {
										Util.toggleDisabled(item, false);

										break;
									}
								}
							}
						);
					}
				);

				A.one('#<portlet:namespace />selectSiteRoleLink').on(
					'click',
					function(event) {
						Util.selectEntity(
							{
								dialog: {
									constrain: true,
									modal: true,
									width: 600
								},

								<%
								String siteRoleEventName = liferayPortletResponse.getNamespace() + "selectSiteRole";
								%>

								id: '<%= siteRoleEventName %>',
								title: '<liferay-ui:message arguments="site-role" key="select-x" />',

								<%
								PortletURL selectSiteRoleURL = PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.BROWSE);

								selectSiteRoleURL.setParameter("p_u_i_d", (selUser == null) ? "0" : String.valueOf(selUser.getUserId()));
								selectSiteRoleURL.setParameter("eventName", siteRoleEventName);
								selectSiteRoleURL.setParameter("roleType", String.valueOf(RoleConstants.TYPE_SITE));
								selectSiteRoleURL.setParameter("step", "1");
								selectSiteRoleURL.setParameter("syncEntitiesEventName", siteRoleSyncEntitiesEventName);
								selectSiteRoleURL.setWindowState(LiferayWindowState.POP_UP);
								%>

								uri: '<%= selectSiteRoleURL.toString() %>'
							},
							function(event) {
								<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupdescriptivename, event.groupid, event.iconcssclass);
							}
						);
					}
				);
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>

<h3><liferay-ui:message key="inherited-site-roles" /></h3>

<c:if test="<%= inheritedSiteRoles.isEmpty() %>">
	<liferay-ui:message key="this-user-does-not-have-any-inherited-site-roles" />
</c:if>

<c:if test="<%= !inheritedSiteRoles.isEmpty() %>">
	<liferay-ui:search-container
		curParam="inheritedSiteRolesCur"
		headerNames="title,site,user-group"
		id="inheritedSiteRolesSearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= inheritedSiteRoles.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= inheritedSiteRoles.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroupGroupRole"
			keyProperty="roleId"
			modelVar="userGroupGroupRole"
		>
			<liferay-ui:search-container-column-text
				name="title"
			>
				<liferay-ui:icon
					iconCssClass="<%= RolesAdminUtil.getIconCssClass(userGroupGroupRole.getRole()) %>"
					label="<%= true %>"
					message="<%= HtmlUtil.escape(userGroupGroupRole.getRole().getTitle(locale)) %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="site"
				value="<%= HtmlUtil.escape(userGroupGroupRole.getGroup().getDescriptiveName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="user-group"
				value="<%= HtmlUtil.escape(userGroupGroupRole.getUserGroup().getName()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</c:if>

<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
	<aui:script>
		var <portlet:namespace />addRoleIds = [];
		var <portlet:namespace />deleteRoleIds = [];

		var <portlet:namespace />addGroupRolesGroupIds = [];
		var <portlet:namespace />addGroupRolesRoleIds = [];
		var <portlet:namespace />deleteGroupRolesGroupIds = [];
		var <portlet:namespace />deleteGroupRolesRoleIds = [];

		function <portlet:namespace />deleteRegularRole(roleId) {
			var A = AUI();

			A.Array.removeItem(<portlet:namespace />addRoleIds, roleId);

			<portlet:namespace />deleteRoleIds.push(roleId);

			document.<portlet:namespace />fm.<portlet:namespace />addRoleIds.value = <portlet:namespace />addRoleIds.join(',');
			document.<portlet:namespace />fm.<portlet:namespace />deleteRoleIds.value = <portlet:namespace />deleteRoleIds.join(',');
		}

		function <portlet:namespace />deleteGroupRole(roleId, groupId) {
			for (var i = 0; i < <portlet:namespace />addGroupRolesRoleIds.length; i++) {
				if ((<portlet:namespace />addGroupRolesGroupIds[i] == groupId) && (<portlet:namespace />addGroupRolesRoleIds[i] == roleId)) {
					<portlet:namespace />addGroupRolesGroupIds.splice(i, 1);
					<portlet:namespace />addGroupRolesRoleIds.splice(i, 1);

					break;
				}
			}

			<portlet:namespace />deleteGroupRolesGroupIds.push(groupId);
			<portlet:namespace />deleteGroupRolesRoleIds.push(roleId);

			document.<portlet:namespace />fm.<portlet:namespace />addGroupRolesGroupIds.value = <portlet:namespace />addGroupRolesGroupIds.join(',');
			document.<portlet:namespace />fm.<portlet:namespace />addGroupRolesRoleIds.value = <portlet:namespace />addGroupRolesRoleIds.join(',');
			document.<portlet:namespace />fm.<portlet:namespace />deleteGroupRolesGroupIds.value = <portlet:namespace />deleteGroupRolesGroupIds.join(',');
			document.<portlet:namespace />fm.<portlet:namespace />deleteGroupRolesRoleIds.value = <portlet:namespace />deleteGroupRolesRoleIds.join(',');
		}

		Liferay.provide(
			window,
			'<portlet:namespace />selectRole',
			function(roleId, name, searchContainer, groupName, groupId, iconCssClass) {
				var A = AUI();

				var searchContainerName = '<portlet:namespace />' + searchContainer + 'SearchContainer';

				searchContainer = Liferay.SearchContainer.get(searchContainerName);

				var rowColumns = [];

				rowColumns.push('<i class="' + iconCssClass + '"></i> ' + name);

				if (groupName) {
					rowColumns.push(groupName);
				}

				if (groupId) {
					rowColumns.push('<a class="modify-link" data-groupId="' + groupId + '" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

					for (var i = 0; i < <portlet:namespace />deleteGroupRolesRoleIds.length; i++) {
						if ((<portlet:namespace />deleteGroupRolesGroupIds[i] == groupId) && (<portlet:namespace />deleteGroupRolesRoleIds[i] == roleId)) {
							<portlet:namespace />deleteGroupRolesGroupIds.splice(i, 1);
							<portlet:namespace />deleteGroupRolesRoleIds.splice(i, 1);

							break;
						}
					}

					<portlet:namespace />addGroupRolesGroupIds.push(groupId);
					<portlet:namespace />addGroupRolesRoleIds.push(roleId);

					document.<portlet:namespace />fm.<portlet:namespace />addGroupRolesGroupIds.value = <portlet:namespace />addGroupRolesGroupIds.join(',');
					document.<portlet:namespace />fm.<portlet:namespace />addGroupRolesRoleIds.value = <portlet:namespace />addGroupRolesRoleIds.join(',');
					document.<portlet:namespace />fm.<portlet:namespace />deleteGroupRolesGroupIds.value = <portlet:namespace />deleteGroupRolesGroupIds.join(',');
					document.<portlet:namespace />fm.<portlet:namespace />deleteGroupRolesRoleIds.value = <portlet:namespace />deleteGroupRolesRoleIds.join(',');
				}
				else {
					rowColumns.push('<a class="modify-link" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

					A.Array.removeItem(<portlet:namespace />deleteRoleIds, roleId);

					<portlet:namespace />addRoleIds.push(roleId);

					document.<portlet:namespace />fm.<portlet:namespace />addRoleIds.value = <portlet:namespace />addRoleIds.join(',');
					document.<portlet:namespace />fm.<portlet:namespace />deleteRoleIds.value = <portlet:namespace />deleteRoleIds.join(',');
				}

				searchContainer.addRow(rowColumns, roleId);

				searchContainer.updateDataStore();
			},
			['liferay-search-container']
		);
	</aui:script>

	<aui:script use="liferay-search-container">
		var Util = Liferay.Util;

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />rolesSearchContainer');

		var searchContainerContentBox = searchContainer.get('contentBox');

		searchContainerContentBox.delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				var rowId = link.attr('data-rowId');

				var tr = link.ancestor('tr');

				var selectRegularRole = Util.getWindow('<portlet:namespace />selectRegularRole');

				if (selectRegularRole) {
					var selectButton = selectRegularRole.iframe.node.get('contentWindow.document').one('.selector-button[data-roleid="' + rowId + '"]');

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

				<portlet:namespace />deleteRegularRole(rowId);
			},
			'.modify-link'
		);

		Liferay.on(
			'<%= regularRoleSyncEntitiesEventName %>',
			function(event) {
				event.selectors.each(
					function(item, index, collection) {
						var roleId = item.attr('data-roleid');

						if (<portlet:namespace />deleteRoleIds.indexOf(roleId) != -1) {
							Util.toggleDisabled(item, false);
						}
					}
				);
			}
		);
	</aui:script>
</c:if>