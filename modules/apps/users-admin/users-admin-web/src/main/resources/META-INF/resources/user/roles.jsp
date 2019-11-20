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
User selUser = userDisplayContext.getSelectedUser();

List<Group> groups = new ArrayList<>();

groups.addAll(userDisplayContext.getGroups());
groups.addAll(userDisplayContext.getInheritedSites());

List<Organization> organizations = userDisplayContext.getOrganizations();

Long[] organizationIds = UsersAdminUtil.getOrganizationIds(organizations);
List<Role> roles = userDisplayContext.getRoles();
List<UserGroupRole> organizationRoles = userDisplayContext.getOrganizationRoles();
List<UserGroupRole> siteRoles = userDisplayContext.getSiteRoles();
List<UserGroupGroupRole> inheritedSiteRoles = userDisplayContext.getInheritedSiteRoles();
List<Group> roleGroups = userDisplayContext.getRoleGroups();

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "roles");

String regularRoleSyncEntitiesEventName = liferayPortletResponse.getNamespace() + "syncRegularRoles";
String siteRoleSyncEntitiesEventName = liferayPortletResponse.getNamespace() + "syncSiteRoles";
String organizationRoleSyncEntitiesEventName = liferayPortletResponse.getNamespace() + "syncOrganizationRoles";
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="roles"
/>

<liferay-ui:membership-policy-error />

<liferay-util:buffer
	var="removeRoleIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addGroupRolesGroupIds" type="hidden" />
<aui:input name="addGroupRolesRoleIds" type="hidden" />
<aui:input name="addRoleIds" type="hidden" />
<aui:input name="deleteGroupRolesGroupIds" type="hidden" />
<aui:input name="deleteGroupRolesRoleIds" type="hidden" />
<aui:input name="deleteRoleIds" type="hidden" />

<div class="sheet-section">
	<h3 class="autofit-row sheet-subtitle">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text"><liferay-ui:message key="regular-roles" /></span>
		</span>

		<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
			<span class="autofit-col">
				<span class="heading-end">
					<liferay-ui:icon
						cssClass="modify-link"
						id="selectRegularRoleLink"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="select"
						method="get"
						url="javascript:;"
					/>
				</span>
			</span>
		</c:if>
	</h3>

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		cssClass="lfr-search-container-roles"
		curParam="regularRolesCur"
		emptyResultsMessage="this-user-is-not-assigned-any-regular-roles"
		headerNames="title,null"
		id="rolesSearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= roles.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= roles.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Role"
			keyProperty="roleId"
			modelVar="role"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
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

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
		<aui:script sandbox="<%= true %>">
			var selectRegularRoleLink = document.getElementById(
				'<portlet:namespace />selectRegularRoleLink'
			);

			if (selectRegularRoleLink) {
				selectRegularRoleLink.addEventListener('click', function(event) {
					var searchContainerName = '<portlet:namespace />rolesSearchContainer';

					var searchContainer = Liferay.SearchContainer.get(searchContainerName);

					var searchContainerData = searchContainer.getData();

					if (!searchContainerData.length) {
						searchContainerData = [];
					} else {
						searchContainerData = searchContainerData.split(',');
					}

					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								modal: true
							},

							<%
							String regularRoleEventName = liferayPortletResponse.getNamespace() + "selectRegularRole";
							%>

							id: '<%= regularRoleEventName %>',
							selectedData: searchContainerData,
							title:
								'<liferay-ui:message arguments="regular-role" key="select-x" />',

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
							<portlet:namespace />selectRole(
								event.entityid,
								event.entityname,
								event.searchcontainername,
								event.groupdescriptivename,
								event.groupid,
								event.iconcssclass
							);
						}
					);
				});
			}
		</aui:script>
	</c:if>

	<c:if test="<%= !roleGroups.isEmpty() %>">
		<h4 class="sheet-tertiary-title"><liferay-ui:message key="inherited-regular-roles" /></h4>

		<liferay-ui:search-container
			cssClass="lfr-search-container-inherited-regular-roles"
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
				className="com.liferay.portal.kernel.model.Group"
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

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</c:if>
</div>

<div class="sheet-section">
	<h3 class="autofit-row sheet-subtitle">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text"><liferay-ui:message key="organization-roles" /></span>
		</span>

		<c:if test="<%= !portletName.equals(myAccountPortletId) && (!organizations.isEmpty() || !organizationRoles.isEmpty()) %>">
			<span class="autofit-col">
				<span class="heading-end">
					<liferay-ui:icon
						cssClass="modify-link"
						id="selectOrganizationRoleLink"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="select"
						method="get"
						url="javascript:;"
					/>
				</span>
			</span>
		</c:if>
	</h3>

	<c:if test="<%= organizations.isEmpty() && organizationRoles.isEmpty() %>">
		<div class="text-muted"><liferay-ui:message key="this-user-does-not-belong-to-an-organization-to-which-an-organization-role-can-be-assigned" /></div>
	</c:if>

	<c:if test="<%= !organizations.isEmpty() %>">
		<liferay-ui:search-container
			compactEmptyResultsMessage="<%= true %>"
			cssClass="lfr-search-container-organization-roles"
			curParam="organizationRolesCur"
			emptyResultsMessage="this-user-is-not-assigned-any-organization-roles"
			headerNames="title,organization,null"
			id="organizationRolesSearchContainer"
			iteratorURL="<%= currentURLObj %>"
			total="<%= organizationRoles.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= organizationRoles.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.UserGroupRole"
				keyProperty="roleId"
				modelVar="userGroupRole"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="title"
				>
					<liferay-ui:icon
						iconCssClass="<%= RolesAdminUtil.getIconCssClass(userGroupRole.getRole()) %>"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
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

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>

		<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
			<aui:script use="liferay-search-container">
				var Util = Liferay.Util;

				var searchContainer = Liferay.SearchContainer.get(
					'<portlet:namespace />organizationRolesSearchContainer'
				);

				var searchContainerContentBox = searchContainer.get('contentBox');

				searchContainerContentBox.delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var tr = link.ancestor('tr');

						var groupId = link.getAttribute('data-groupId');
						var rowId = link.getAttribute('data-rowId');

						var selectOrganizationRole = Util.getWindow(
							'<portlet:namespace />selectOrganizationRole'
						);

						if (selectOrganizationRole) {
							var selectButton = selectOrganizationRole.iframe.node
								.get('contentWindow.document')
								.one(
									'.selector-button[data-groupid="' +
										groupId +
										'"][data-entityid="' +
										rowId +
										'"]'
								);

							Util.toggleDisabled(selectButton, false);
						}

						searchContainer.deleteRow(tr, rowId);

						<portlet:namespace />deleteGroupRole(rowId, groupId);
					},
					'.modify-link'
				);

				Liferay.on('<%= organizationRoleSyncEntitiesEventName %>', function(event) {
					event.selectors.each(function(item, index, collection) {
						var groupId = item.attr('data-groupid');
						var roleId = item.attr('data-entityid');

						for (
							var i = 0;
							i < <portlet:namespace />addGroupRolesGroupIds.length;
							i++
						) {
							if (
								<portlet:namespace />addGroupRolesGroupIds[i] == groupId &&
								<portlet:namespace />addGroupRolesRoleIds[i] == roleId
							) {
								Util.toggleDisabled(item, true);

								break;
							}
						}

						for (
							var j = 0;
							j < <portlet:namespace />deleteGroupRolesGroupIds.length;
							j++
						) {
							if (
								<portlet:namespace />deleteGroupRolesGroupIds[j] == groupId &&
								<portlet:namespace />deleteGroupRolesRoleIds[j] == roleId
							) {
								Util.toggleDisabled(item, false);

								break;
							}
						}
					});
				});
			</aui:script>
		</c:if>
	</c:if>

	<c:if test="<%= !organizations.isEmpty() && !portletName.equals(myAccountPortletId) %>">
		<aui:script sandbox="<%= true %>">
			var selectOrganizationRoleLink = document.getElementById(
				'<portlet:namespace />selectOrganizationRoleLink'
			);

			if (selectOrganizationRoleLink) {
				selectOrganizationRoleLink.addEventListener('click', function(event) {
					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								modal: true
							},

							<%
							String organizationRoleEventName = liferayPortletResponse.getNamespace() + "selectOrganizationRole";
							%>

							id: '<%= organizationRoleEventName %>',
							selectedData: [],
							title:
								'<liferay-ui:message arguments="organization-role" key="select-x" />',

							<%
							PortletURL selectOrganizationRoleURL = PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.BROWSE);

							selectOrganizationRoleURL.setParameter("p_u_i_d", (selUser == null) ? "0" : String.valueOf(selUser.getUserId()));
							selectOrganizationRoleURL.setParameter("step", "1");
							selectOrganizationRoleURL.setParameter("roleType", String.valueOf(RoleConstants.TYPE_ORGANIZATION));
							selectOrganizationRoleURL.setParameter("organizationIds", StringUtil.merge(organizationIds));
							selectOrganizationRoleURL.setParameter("eventName", organizationRoleEventName);
							selectOrganizationRoleURL.setParameter("syncEntitiesEventName", organizationRoleSyncEntitiesEventName);
							selectOrganizationRoleURL.setWindowState(LiferayWindowState.POP_UP);
							%>

							uri: '<%= selectOrganizationRoleURL.toString() %>'
						},
						function(event) {
							<portlet:namespace />selectRole(
								event.entityid,
								event.entityname,
								event.searchcontainername,
								event.groupdescriptivename,
								event.groupid,
								event.iconcssclass
							);
						}
					);
				});
			}
		</aui:script>
	</c:if>
</div>

<div class="sheet-section">
	<h3 class="autofit-row sheet-subtitle">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text"><liferay-ui:message key="site-roles" /></span>
		</span>

		<c:if test="<%= !portletName.equals(myAccountPortletId) && (!groups.isEmpty() || !siteRoles.isEmpty()) %>">
			<span class="autofit-col">
				<span class="heading-end">
					<liferay-ui:icon
						cssClass="modify-link"
						id="selectSiteRoleLink"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="select"
						method="get"
						url="javascript:;"
					/>
				</span>
			</span>
		</c:if>
	</h3>

	<c:if test="<%= groups.isEmpty() && siteRoles.isEmpty() %>">
		<div class="text-muted"><liferay-ui:message key="this-user-does-not-belong-to-a-site-to-which-a-site-role-can-be-assigned" /></div>
	</c:if>

	<c:if test="<%= !groups.isEmpty() %>">
		<liferay-ui:search-container
			compactEmptyResultsMessage="<%= true %>"
			cssClass="lfr-search-container-site-roles"
			curParam="siteRolesCur"
			emptyResultsMessage="this-user-is-not-assigned-any-site-roles"
			headerNames="title,site,null"
			id="siteRolesSearchContainer"
			iteratorURL="<%= currentURLObj %>"
			total="<%= siteRoles.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= siteRoles.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.UserGroupRole"
				keyProperty="roleId"
				modelVar="userGroupRole"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="title"
				>
					<liferay-ui:icon
						iconCssClass="<%= RolesAdminUtil.getIconCssClass(userGroupRole.getRole()) %>"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="site"
				>
					<liferay-staging:descriptive-name
						group="<%= userGroupRole.getGroup() %>"
					/>
				</liferay-ui:search-container-column-text>

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

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>

		<c:if test="<%= !portletName.equals(myAccountPortletId) %>">
			<aui:script use="liferay-search-container">
				var Util = Liferay.Util;

				var searchContainer = Liferay.SearchContainer.get(
					'<portlet:namespace />siteRolesSearchContainer'
				);

				var searchContainerContentBox = searchContainer.get('contentBox');

				searchContainerContentBox.delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var tr = link.ancestor('tr');

						var groupId = link.getAttribute('data-groupId');
						var rowId = link.getAttribute('data-rowId');

						var selectSiteRole = Util.getWindow(
							'<portlet:namespace />selectSiteRole'
						);

						if (selectSiteRole) {
							var selectButton = selectSiteRole.iframe.node
								.get('contentWindow.document')
								.one(
									'.selector-button[data-groupid="' +
										groupId +
										'"][data-entityid="' +
										rowId +
										'"]'
								);

							Util.toggleDisabled(selectButton, false);
						}

						searchContainer.deleteRow(tr, rowId);

						<portlet:namespace />deleteGroupRole(rowId, groupId);
					},
					'.modify-link'
				);

				Liferay.on('<%= siteRoleSyncEntitiesEventName %>', function(event) {
					event.selectors.each(function(item, index, collection) {
						var groupId = item.attr('data-groupid');
						var roleId = item.attr('data-entityid');

						for (
							var k = 0;
							k < <portlet:namespace />addGroupRolesGroupIds.length;
							k++
						) {
							if (
								<portlet:namespace />addGroupRolesGroupIds[k] == groupId &&
								<portlet:namespace />addGroupRolesRoleIds[k] == roleId
							) {
								Util.toggleDisabled(item, true);

								break;
							}
						}

						for (
							var n = 0;
							n < <portlet:namespace />deleteGroupRolesGroupIds.length;
							n++
						) {
							if (
								<portlet:namespace />deleteGroupRolesGroupIds[n] == groupId &&
								<portlet:namespace />deleteGroupRolesRoleIds[n] == roleId
							) {
								Util.toggleDisabled(item, false);

								break;
							}
						}
					});
				});

				A.one('#<portlet:namespace />selectSiteRoleLink').on('click', function(event) {
					Util.selectEntity(
						{
							dialog: {
								constrain: true,
								modal: true
							},

							<%
							String siteRoleEventName = liferayPortletResponse.getNamespace() + "selectSiteRole";
							%>

							id: '<%= siteRoleEventName %>',
							selectedData: [],
							title:
								'<liferay-ui:message arguments="site-role" key="select-x" />',

							<%
							PortletURL selectSiteRoleURL = PortletProviderUtil.getPortletURL(request, Role.class.getName(), PortletProvider.Action.BROWSE);

							selectSiteRoleURL.setParameter("p_u_i_d", (selUser == null) ? "0" : String.valueOf(selUser.getUserId()));
							selectSiteRoleURL.setParameter("step", "1");
							selectSiteRoleURL.setParameter("roleType", String.valueOf(RoleConstants.TYPE_SITE));
							selectSiteRoleURL.setParameter("eventName", siteRoleEventName);
							selectSiteRoleURL.setParameter("syncEntitiesEventName", siteRoleSyncEntitiesEventName);
							selectSiteRoleURL.setWindowState(LiferayWindowState.POP_UP);
							%>

							uri: '<%= selectSiteRoleURL.toString() %>'
						},
						function(event) {
							<portlet:namespace />selectRole(
								event.entityid,
								event.entityname,
								event.searchcontainername,
								event.groupdescriptivename,
								event.groupid,
								event.iconcssclass
							);
						}
					);
				});
			</aui:script>
		</c:if>
	</c:if>

	<c:if test="<%= !inheritedSiteRoles.isEmpty() %>">
		<h4 class="sheet-tertiary-title"><liferay-ui:message key="inherited-site-roles" /></h4>

		<liferay-ui:search-container
			cssClass="lfr-search-container-inherited-site-roles"
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
				className="com.liferay.portal.kernel.model.UserGroupGroupRole"
				keyProperty="roleId"
				modelVar="userGroupGroupRole"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="title"
				>
					<liferay-ui:icon
						iconCssClass="<%= RolesAdminUtil.getIconCssClass(userGroupGroupRole.getRole()) %>"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(userGroupGroupRole.getRole().getTitle(locale)) %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="site"
				>
					<liferay-staging:descriptive-name
						group="<%= userGroupGroupRole.getGroup() %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="user-group"
					value="<%= HtmlUtil.escape(userGroupGroupRole.getUserGroup().getName()) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
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

				document.<portlet:namespace />fm.<portlet:namespace />addRoleIds.value = <portlet:namespace />addRoleIds.join(
					','
				);
				document.<portlet:namespace />fm.<portlet:namespace />deleteRoleIds.value = <portlet:namespace />deleteRoleIds.join(
					','
				);
			}

			function <portlet:namespace />deleteGroupRole(roleId, groupId) {
				for (var i = 0; i < <portlet:namespace />addGroupRolesRoleIds.length; i++) {
					if (
						<portlet:namespace />addGroupRolesGroupIds[i] == groupId &&
						<portlet:namespace />addGroupRolesRoleIds[i] == roleId
					) {
						<portlet:namespace />addGroupRolesGroupIds.splice(i, 1);
						<portlet:namespace />addGroupRolesRoleIds.splice(i, 1);

						break;
					}
				}

				<portlet:namespace />deleteGroupRolesGroupIds.push(groupId);
				<portlet:namespace />deleteGroupRolesRoleIds.push(roleId);

				document.<portlet:namespace />fm.<portlet:namespace />addGroupRolesGroupIds.value = <portlet:namespace />addGroupRolesGroupIds.join(
					','
				);
				document.<portlet:namespace />fm.<portlet:namespace />addGroupRolesRoleIds.value = <portlet:namespace />addGroupRolesRoleIds.join(
					','
				);
				document.<portlet:namespace />fm.<portlet:namespace />deleteGroupRolesGroupIds.value = <portlet:namespace />deleteGroupRolesGroupIds.join(
					','
				);
				document.<portlet:namespace />fm.<portlet:namespace />deleteGroupRolesRoleIds.value = <portlet:namespace />deleteGroupRolesRoleIds.join(
					','
				);
			}

			Liferay.provide(
				window,
				'<portlet:namespace />selectRole',
				function(roleId, name, searchContainer, groupName, groupId, iconCssClass) {
					var A = AUI();
					var LString = A.Lang.String;

					var searchContainerName =
						'<portlet:namespace />' + searchContainer + 'SearchContainer';

					searchContainer = Liferay.SearchContainer.get(searchContainerName);

					var rowColumns = [];

					rowColumns.push(
						'<i class="' + iconCssClass + '"></i> ' + LString.escapeHTML(name)
					);

					if (groupName) {
						rowColumns.push(groupName);
					}

					if (groupId) {
						rowColumns.push(
							'<a class="modify-link" data-groupId="' +
								groupId +
								'" data-rowId="' +
								roleId +
								'" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>'
						);

						for (
							var i = 0;
							i < <portlet:namespace />deleteGroupRolesRoleIds.length;
							i++
						) {
							if (
								<portlet:namespace />deleteGroupRolesGroupIds[i] ==
									groupId &&
								<portlet:namespace />deleteGroupRolesRoleIds[i] == roleId
							) {
								<portlet:namespace />deleteGroupRolesGroupIds.splice(i, 1);
								<portlet:namespace />deleteGroupRolesRoleIds.splice(i, 1);

								break;
							}
						}

						<portlet:namespace />addGroupRolesGroupIds.push(groupId);
						<portlet:namespace />addGroupRolesRoleIds.push(roleId);

						document.<portlet:namespace />fm.<portlet:namespace />addGroupRolesGroupIds.value = <portlet:namespace />addGroupRolesGroupIds.join(
							','
						);
						document.<portlet:namespace />fm.<portlet:namespace />addGroupRolesRoleIds.value = <portlet:namespace />addGroupRolesRoleIds.join(
							','
						);
						document.<portlet:namespace />fm.<portlet:namespace />deleteGroupRolesGroupIds.value = <portlet:namespace />deleteGroupRolesGroupIds.join(
							','
						);
						document.<portlet:namespace />fm.<portlet:namespace />deleteGroupRolesRoleIds.value = <portlet:namespace />deleteGroupRolesRoleIds.join(
							','
						);
					} else {
						rowColumns.push(
							'<a class="modify-link" data-rowId="' +
								roleId +
								'" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>'
						);

						A.Array.removeItem(<portlet:namespace />deleteRoleIds, roleId);

						<portlet:namespace />addRoleIds.push(roleId);

						document.<portlet:namespace />fm.<portlet:namespace />addRoleIds.value = <portlet:namespace />addRoleIds.join(
							','
						);
						document.<portlet:namespace />fm.<portlet:namespace />deleteRoleIds.value = <portlet:namespace />deleteRoleIds.join(
							','
						);
					}

					searchContainer.addRow(rowColumns, roleId);

					searchContainer.updateDataStore();
				},
				['liferay-search-container']
			);
		</aui:script>

		<aui:script use="liferay-search-container">
			var Util = Liferay.Util;

			var searchContainer = Liferay.SearchContainer.get(
				'<portlet:namespace />rolesSearchContainer'
			);

			var searchContainerContentBox = searchContainer.get('contentBox');

			searchContainerContentBox.delegate(
				'click',
				function(event) {
					var link = event.currentTarget;

					var rowId = link.attr('data-rowId');

					var tr = link.ancestor('tr');

					var selectRegularRole = Util.getWindow(
						'<portlet:namespace />selectRegularRole'
					);

					if (selectRegularRole) {
						var selectButton = selectRegularRole.iframe.node
							.get('contentWindow.document')
							.one('.selector-button[data-entityid="' + rowId + '"]');

						Util.toggleDisabled(selectButton, false);
					}

					searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

					<portlet:namespace />deleteRegularRole(rowId);
				},
				'.modify-link'
			);

			Liferay.on('<%= regularRoleSyncEntitiesEventName %>', function(event) {
				event.selectors.each(function(item, index, collection) {
					var roleId = item.attr('data-entityid');

					if (<portlet:namespace />deleteRoleIds.indexOf(roleId) != -1) {
						Util.toggleDisabled(item, false);
					}
				});
			});
		</aui:script>
	</c:if>
</div>