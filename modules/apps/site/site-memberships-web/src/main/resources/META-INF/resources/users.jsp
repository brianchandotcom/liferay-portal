<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
UsersDisplayContext usersDisplayContext = new UsersDisplayContext(request, renderRequest, renderResponse);

UsersManagementToolbarDisplayContext usersManagementToolbarDisplayContext = new UsersManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, usersDisplayContext);

Role role = usersDisplayContext.getRole();

Team team = usersDisplayContext.getTeam();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= usersManagementToolbarDisplayContext %>"
	propsTransformer="js/UserManagementToolbarPropsTransformer"
/>

<liferay-ui:error embed="<%= false %>" exception="<%= RequiredUserException.class %>" message="one-or-more-users-were-not-removed-since-they-belong-to-a-user-group" />

<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/site_memberships/users_info_panel" var="sidebarPanelURL">
		<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	</liferay-portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="users"
	>
		<liferay-util:include page="/user_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<clay:container-fluid>
			<portlet:actionURL name="deleteGroupUsers" var="deleteGroupUsersURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<aui:form action="<%= deleteGroupUsersURL %>" cssClass="portlet-site-memberships-users" method="post" name="fm">
				<aui:input name="tabs1" type="hidden" value="users" />
				<aui:input name="navigation" type="hidden" value="<%= usersDisplayContext.getNavigation() %>" />
				<aui:input name="addUserIds" type="hidden" />
				<aui:input name="roleId" type="hidden" value="<%= (role != null) ? role.getRoleId() : 0 %>" />
				<aui:input name="teamId" type="hidden" value="<%= (team != null) ? team.getTeamId() : 0 %>" />

				<liferay-site-navigation:breadcrumb
					breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, true, false, false, true, true) %>"
				/>

				<liferay-site:membership-policy-error />

				<liferay-ui:search-container
					id="users"
					searchContainer="<%= usersDisplayContext.getUserSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.User"
						escapedModel="<%= true %>"
						keyProperty="userId"
						modelVar="user2"
						rowIdProperty="screenName"
					>

						<%
						String displayStyle = usersDisplayContext.getDisplayStyle();

						row.setData(
							HashMapBuilder.<String, Object>put(
								"actions", usersManagementToolbarDisplayContext.getAvailableActions(user2)
							).build());
						%>

						<c:choose>
							<c:when test='<%= displayStyle.equals("icon") %>'>
								<liferay-ui:search-container-column-text>
									<clay:user-card
										propsTransformer="js/UserCardPropsTransformer"
										userCard="<%= new UsersUserCard(user2, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:when>
							<c:otherwise>

								<%
								Set<String> roles = new TreeSet<String>();

								List<Role> userRoles = new ArrayList<Role>();

								userRoles.addAll(RoleLocalServiceUtil.getUserGroupGroupRoles(user2.getUserId(), siteMembershipsDisplayContext.getGroupId()));

								for (Role userRole : userRoles) {
									if (RolePermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), userRole.getRoleId(), ActionKeys.VIEW)) {
										roles.add(userRole.getTitle(locale));
									}
								}

								List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

								userGroupRoles.addAll(UserGroupRoleLocalServiceUtil.getUserGroupRoles(user2.getUserId(), siteMembershipsDisplayContext.getGroupId()));

								for (UserGroupRole userGroupRole : userGroupRoles) {
									if (RolePermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), userGroupRole.getRoleId(), ActionKeys.VIEW)) {
										roles.add(
											userGroupRole.getRole(
											).getTitle(
												locale
											));
									}
								}

								Set<String> teams = new TreeSet<String>();

								List<Team> userTeams = new ArrayList<>();

								userTeams.addAll(TeamLocalServiceUtil.getUserOrUserGroupTeams(siteMembershipsDisplayContext.getGroupId(), user2.getUserId()));

								for (Team userTeam : userTeams) {
									if (TeamPermissionUtil.contains(permissionChecker, userTeam, ActionKeys.VIEW)) {
										teams.add(userTeam.getName());
									}
								}
								%>

								<c:choose>
									<c:when test='<%= displayStyle.equals("descriptive") %>'>
										<liferay-ui:search-container-column-text>
											<liferay-user:user-portrait
												userId="<%= user2.getUserId() %>"
											/>
										</liferay-ui:search-container-column-text>

										<liferay-ui:search-container-column-text
											colspan="<%= 2 %>"
										>
											<h5><%= user2.getFullName() %></h5>

											<h6 class="text-default">
												<span><%= user2.getScreenName() %></span>
											</h6>

											<%
											roles.addAll(teams);
											%>

											<h6>
												<%= HtmlUtil.escape(StringUtil.merge(roles, StringPool.COMMA_AND_SPACE)) %>
											</h6>
										</liferay-ui:search-container-column-text>

										<%
										UserActionDropdownItemsProvider userActionDropdownItemsProvider = new UserActionDropdownItemsProvider(user2, renderRequest, renderResponse);
										%>

										<liferay-ui:search-container-column-text>
											<clay:dropdown-actions
												aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
												dropdownItems="<%= userActionDropdownItemsProvider.getActionDropdownItems() %>"
												propsTransformer="js/UserDropdownDefaultPropsTransformer"
											/>
										</liferay-ui:search-container-column-text>
									</c:when>
									<c:otherwise>
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200 table-title"
											name="name"
											value="<%= user2.getFullName() %>"
										/>

										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200"
											name="screen-name"
											orderable="<%= true %>"
											property="screenName"
										/>

										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200"
											name="roles"
											value="<%= HtmlUtil.escape(StringUtil.merge(roles, StringPool.COMMA_AND_SPACE)) %>"
										/>

										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200"
											name="teams"
											value="<%= HtmlUtil.escape(StringUtil.merge(teams, StringPool.COMMA_AND_SPACE)) %>"
										/>

										<%
										UserActionDropdownItemsProvider userActionDropdownItemsProvider = new UserActionDropdownItemsProvider(user2, renderRequest, renderResponse);
										%>

										<liferay-ui:search-container-column-text>
											<clay:dropdown-actions
												aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
												dropdownItems="<%= userActionDropdownItemsProvider.getActionDropdownItems() %>"
												propsTransformer="js/UserDropdownDefaultPropsTransformer"
											/>
										</liferay-ui:search-container-column-text>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="<%= usersDisplayContext.getDisplayStyle() %>"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</aui:form>
		</clay:container-fluid>
	</div>
</div>

<portlet:actionURL name="addGroupUsers" var="addGroupUsersURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= addGroupUsersURL %>" cssClass="hide" method="post" name="addGroupUsersFm">
	<aui:input name="tabs1" type="hidden" value="users" />
</aui:form>

<aui:form cssClass="hide" method="post" name="editUserGroupRoleFm">
	<aui:input name="tabs1" type="hidden" value="users" />
</aui:form>